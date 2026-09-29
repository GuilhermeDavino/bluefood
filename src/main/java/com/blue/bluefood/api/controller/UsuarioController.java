package com.blue.bluefood.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

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

import com.blue.bluefood.api.assembler.UsuarioDTOAssembler;
import com.blue.bluefood.api.assembler.UsuarioInputDisassembler;
import com.blue.bluefood.api.model.UsuarioDTO;
import com.blue.bluefood.api.model.UsuarioInputDTO;
import com.blue.bluefood.domain.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioDTOAssembler assembler;
	
	@Autowired
	private UsuarioInputDisassembler disassembler;
	
	@GetMapping("/{usuarioId}")
	public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long usuarioId) {
		var usuario = usuarioService.buscarOuFalhar(usuarioId);
		var usuarioDTO = assembler.toUsuarioDTO(usuario);
		return ResponseEntity.ok(usuarioDTO);
	}
	
	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> listar() {
		var usuarios = usuarioService.listar();
		var usuariosDTO = assembler.toCollectionDTO(usuarios);
		return ResponseEntity.ok(usuariosDTO);
	}
	
	@PostMapping
	public ResponseEntity<UsuarioDTO> adicionar(@RequestBody @Valid UsuarioInputDTO usuarioInput) {
		var usuario = disassembler.toDomainObject(usuarioInput);
		usuario = usuarioService.adicionar(usuario);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(usuario.getId())
				.toUri();
		var usuarioDTO = assembler.toUsuarioDTO(usuario);
		return ResponseEntity.created(uri).body(usuarioDTO);
	}
	
	@PutMapping("/{usuarioId}")
	public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long usuarioId, 
			@RequestBody @Valid UsuarioDTO usuarioInput) { 
		var usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);
		BeanUtils.copyProperties(usuarioInput, usuarioAtual, "id");
		usuarioAtual = usuarioService.atualizar(usuarioAtual);
		return ResponseEntity.ok(assembler.toUsuarioDTO(usuarioAtual));
	}
	
	@DeleteMapping("/{usuarioId}")
	public ResponseEntity<Void> deletar(Long usuarioId) {
		usuarioService.deletar(usuarioId);
		return ResponseEntity.noContent().build();
	}
	
	
}
