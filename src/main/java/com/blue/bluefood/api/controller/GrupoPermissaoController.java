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

import com.blue.bluefood.api.assembler.PermissaoDTOAssembler;
import com.blue.bluefood.api.model.PermissaoDTO;
import com.blue.bluefood.domain.service.GrupoService;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private PermissaoDTOAssembler assembler;
	
	@GetMapping
	public ResponseEntity<List<PermissaoDTO>> listar(@PathVariable Long grupoId) {
		var permissoes = grupoService.buscarPermissoesDoGrupo(grupoId);
		var permissoesDTO = assembler.toCollectionDTO(permissoes);
		return ResponseEntity.ok(permissoesDTO);
	}
	
	@PutMapping("/{permissaoId}")
	public ResponseEntity<Void> associarPermissaoAoGrupo(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoService.associarPermissaoAoGrupo(grupoId, permissaoId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{permissaoId}")
	public ResponseEntity<Void> desassociarPermissaoAoGrupo(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoService.desassociarPermissaoAoGrupo(grupoId, permissaoId);
		return ResponseEntity.noContent().build();
	}
	
	
}
