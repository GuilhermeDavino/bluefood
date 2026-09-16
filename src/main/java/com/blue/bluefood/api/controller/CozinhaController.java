package com.blue.bluefood.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.blue.bluefood.api.assembler.CozinhaDTOAssembler;
import com.blue.bluefood.api.assembler.CozinhaInputDisassembler;
import com.blue.bluefood.api.model.CozinhaDTO;
import com.blue.bluefood.api.model.CozinhasXmlWrapper;
import com.blue.bluefood.domain.model.Cozinha;
import com.blue.bluefood.domain.repository.CozinhaRepository;
import com.blue.bluefood.domain.service.CozinhaService;

@RestController
@RequestMapping(value = "/cozinhas"/*, produces = MediaType.APPLICATION_JSON_VALUE*/)
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CozinhaService cozinhaService;
	
	@Autowired
	private CozinhaDTOAssembler assembler;
	
	@Autowired
	private CozinhaInputDisassembler disassembler;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CozinhaDTO> listar() {
		var cozinhas = cozinhaRepository.todas();
		return assembler.toCollectionDTO(cozinhas);
	}
	
	@GetMapping("consultarPorNome")
	public List<CozinhaDTO> listar(@RequestParam(name = "nome", defaultValue = "") String nome) {
		var cozinhas = cozinhaRepository.consultarPorNome(nome);
		return assembler.toCollectionDTO(cozinhas);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhasXmlWrapper listarXml() {
		return new CozinhasXmlWrapper(cozinhaRepository.todas());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CozinhaDTO> buscar(@PathVariable("id") Long cozinhaId) {
		var cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
		var cozinhaDTO = assembler.toCozinhaDTO(cozinha);
		return ResponseEntity.ok(cozinhaDTO);
	}
	
	@PostMapping
	public ResponseEntity<CozinhaDTO> adicionar(@RequestBody @Valid CozinhaDTO cozinhaDTO) {
		var cozinha = disassembler.toDomainObject(cozinhaDTO);
		cozinha = cozinhaService.salvar(cozinha);
		 URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
		            .path("/{id}")
		            .buildAndExpand(cozinha.getId())
		            .toUri();
		cozinhaDTO = assembler.toCozinhaDTO(cozinha);
		return ResponseEntity.created(uri).body(cozinhaDTO);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CozinhaDTO> atualizar(@PathVariable("id") Long cozinhaId, @RequestBody CozinhaDTO cozinhaDTO) {
		
		Cozinha cozinhaAtual = cozinhaService.buscarOuFalhar(cozinhaId);
		BeanUtils.copyProperties(cozinhaDTO, cozinhaAtual, "id");
		cozinhaAtual = cozinhaService.salvar(cozinhaAtual);
		cozinhaDTO = assembler.toCozinhaDTO(cozinhaAtual);
		return ResponseEntity.ok(cozinhaDTO);
		
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable("id") Long cozinhaId) {
		cozinhaService.remover(cozinhaId);	
	}
}

