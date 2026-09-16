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

import com.blue.bluefood.api.assembler.EstadoDTOAssembler;
import com.blue.bluefood.api.assembler.EstadoInputDisassembler;
import com.blue.bluefood.api.model.EstadoDTO;
import com.blue.bluefood.domain.service.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	
	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private EstadoDTOAssembler assembler;
	
	@Autowired
	private EstadoInputDisassembler disassembler; 
	
	@GetMapping
	public ResponseEntity<List<EstadoDTO>> listar() {
		var estados = estadoService.listar();
		var estadosDTO = assembler.toCollectionDTO(estados);
		return ResponseEntity.ok(estadosDTO);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<EstadoDTO> buscarPorId(@PathVariable("id") Long estadoId) {
		var estado = estadoService.buscarOuFalhar(estadoId);
		var estadoDTO = assembler.toEstadoDTO(estado);
		return ResponseEntity.ok(estadoDTO);
	}
	
	@PostMapping
	public ResponseEntity<EstadoDTO> adicionar(@RequestBody @Valid EstadoDTO estadoInputDTO) {
		var estado = disassembler.toDomainObject(estadoInputDTO);
		estado = estadoService.adicionar(estado);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(estado.getId())
				.toUri();
		var estadoDTO = assembler.toEstadoDTO(estado);
		return ResponseEntity.created(uri).body(estadoDTO);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<EstadoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid EstadoDTO estadoInputDTO) {
		var estado = disassembler.toDomainObject(estadoInputDTO);
		estado = estadoService.atualizar(id, estado);
		var estadoDTO = assembler.toEstadoDTO(estado);
		return ResponseEntity.ok(estadoDTO);
		
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		estadoService.deletar(id);	
	}
	
	
}
