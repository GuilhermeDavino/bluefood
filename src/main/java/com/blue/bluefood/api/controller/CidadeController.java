package com.blue.bluefood.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.blue.bluefood.api.assembler.CidadeDTOAssembler;
import com.blue.bluefood.api.assembler.CidadeInputDisassembler;
import com.blue.bluefood.api.model.CidadeDTO;
import com.blue.bluefood.api.model.CidadeInputDTO;
import com.blue.bluefood.domain.exception.EstadoNaoEncontradoException;
import com.blue.bluefood.domain.exception.NegocioException;
import com.blue.bluefood.domain.model.Cidade;
import com.blue.bluefood.domain.repository.CidadeRepositoryQueries;
import com.blue.bluefood.domain.service.CidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private CidadeRepositoryQueries cidadeRepository;
	
	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private CidadeDTOAssembler assembler;
	
	@Autowired
	private CidadeInputDisassembler disassembler;
	
	@GetMapping
	public ResponseEntity<List<CidadeDTO>> listar() {
		var cidades = cidadeRepository.listar();
		var cidadesDTO = assembler.toCollectionDTO(cidades);
		return ResponseEntity.ok(cidadesDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CidadeDTO> buscarPorId(@PathVariable Long id) {
		Cidade cidade = cidadeRepository.buscarPorId(id);
		CidadeDTO cidadeDTO = assembler.toCidadeDTO(cidade);
		return ResponseEntity.ok(cidadeDTO);
	}
	
	@PostMapping
	public ResponseEntity<CidadeDTO> adicionar(@RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
		Cidade cidade = disassembler.toDomainObject(cidadeInputDTO);
		cidade = cidadeService.adicionar(cidade);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cidade.getId())
				.toUri();
		var cidadeDTO = assembler.toCidadeDTO(cidade);
		return ResponseEntity.created(uri).body(cidadeDTO);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CidadeDTO> atualizar(@PathVariable("id") Long cidadeId, @RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
		try {
			var cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);
			disassembler.copyToDomainObject(cidadeInputDTO, cidadeAtual);
			
			cidadeAtual = cidadeService.atualizar(cidadeAtual);
			
			var cidadeDTO = assembler.toCidadeDTO(cidadeAtual);
			return ResponseEntity.ok(cidadeDTO);
		
		} catch (EstadoNaoEncontradoException exception) {
			throw new NegocioException(exception.getMessage(), exception);
		}
		
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		cidadeService.remover(id);
		return ResponseEntity.noContent().build();
	}
	
	
	
}
