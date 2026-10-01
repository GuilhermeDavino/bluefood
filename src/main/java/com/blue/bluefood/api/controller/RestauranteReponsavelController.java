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

import com.blue.bluefood.api.assembler.UsuarioDTOAssembler;
import com.blue.bluefood.api.model.UsuarioDTO;
import com.blue.bluefood.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteReponsavelController {
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private UsuarioDTOAssembler usuarioAssembler;
	
	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> listarResponsaveis(@PathVariable Long restauranteId) {
		var responsaveis = restauranteService.listarResponsaveis(restauranteId);
		var responsaveisDTO = usuarioAssembler.toCollectionDTO(responsaveis);
		return ResponseEntity.ok(responsaveisDTO);
	}
	
	@PutMapping("/{responsavelId}")
	public ResponseEntity<Void> associarResponsavelAoRestaurante(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
		restauranteService.associarResponsavelAoRestaurante(restauranteId, responsavelId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{responsavelId}")
	public ResponseEntity<Void> desassociarResponsavelAoRestaurante(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
		restauranteService.desassociarResponsavelAoRestaurante(restauranteId, responsavelId);
		return ResponseEntity.noContent().build();
	}
}
