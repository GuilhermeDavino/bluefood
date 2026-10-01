package com.blue.bluefood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blue.bluefood.api.assembler.GrupoDTOAssembler;
import com.blue.bluefood.api.model.GrupoDTO;
import com.blue.bluefood.domain.service.UsuarioService;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private GrupoDTOAssembler grupoAssembler;
	
	@GetMapping
	public ResponseEntity<List<GrupoDTO>> listar(@PathVariable Long usuarioId) {
		var grupos = usuarioService.listarGrupos(usuarioId);
		var gruposDTO = grupoAssembler.toCollectionDTO(grupos);
		return ResponseEntity.ok(gruposDTO);
	}
	
	@PutMapping("/{grupoId}")
	public ResponseEntity<Void> associarGrupoAoUsuario(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		usuarioService.associarGrupoAoUsuario(usuarioId, grupoId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{grupoId}")
	public ResponseEntity<Void> desassociarGrupoAoUsuario(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		usuarioService.desassociarGrupoAoUsuario(usuarioId, grupoId);
		return ResponseEntity.noContent().build();
	}
	
	
}
