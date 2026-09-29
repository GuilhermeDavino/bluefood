package com.blue.bluefood.api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.blue.bluefood.api.assembler.GrupoDTOAssembler;
import com.blue.bluefood.api.assembler.GrupoInputDisassembler;
import com.blue.bluefood.api.model.GrupoDTO;
import com.blue.bluefood.domain.service.GrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private GrupoDTOAssembler assembler;
	
	@Autowired
	private GrupoInputDisassembler disassembler;
	
	@GetMapping("/{grupoId}")
	public ResponseEntity<GrupoDTO> buscarPorId(@PathVariable Long grupoId) {
		var grupo = grupoService.buscarOuFalhar(grupoId);
		var grupoDTO = assembler.toGrupoDTO(grupo);
		return ResponseEntity.ok(grupoDTO);
	}
	
	@GetMapping
	public ResponseEntity<List<GrupoDTO>> listar() {
		var grupos = grupoService.listar();
		var gruposDTO = assembler.toCollectionDTO(grupos);
		return ResponseEntity.ok(gruposDTO);
	}
	
	@PostMapping
	public ResponseEntity<GrupoDTO> adicionar(@RequestBody GrupoDTO grupoInputDTO) {
		var grupo = disassembler.toDomainObject(grupoInputDTO);
		grupo = grupoService.adicionar(grupo);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(grupo.getId())
				.toUri();
		
		var grupoDTO = assembler.toGrupoDTO(grupo);
		return ResponseEntity.created(uri).body(grupoDTO);
	}
	
	@PutMapping("/{grupoId}")
	public ResponseEntity<GrupoDTO> atualizar(@PathVariable Long grupoId, 
			@RequestBody GrupoDTO grupoInputDTO) {
		var grupoAtual = grupoService.buscarOuFalhar(grupoId);
		BeanUtils.copyProperties(grupoInputDTO, grupoAtual, "id");
		grupoAtual = grupoService.atualizar(grupoAtual);
		var grupoDTO = assembler.toGrupoDTO(grupoAtual);
		return ResponseEntity.ok(grupoDTO);
	}
	
	@DeleteMapping("/{grupoId}")
	public ResponseEntity<Void> deletar(@PathVariable Long grupoId) {
		grupoService.deletar(grupoId);
		return ResponseEntity.noContent().build();
	}

}
