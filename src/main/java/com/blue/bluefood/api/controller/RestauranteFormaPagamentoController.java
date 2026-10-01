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

import com.blue.bluefood.api.assembler.FormaPagamentoDTOAssembler;
import com.blue.bluefood.api.model.FormaPagamentoDTO;
import com.blue.bluefood.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {
	
	@Autowired 
	private RestauranteService restauranteService;
	
	@Autowired
	private FormaPagamentoDTOAssembler assembler;
	
	
	@GetMapping
	public ResponseEntity<List<FormaPagamentoDTO>> listar(@PathVariable Long restauranteId) {
		var restaurante = restauranteService.buscarOuFalhar(restauranteId);
		var formasPagamento = restaurante.getFormasPagamento();
		var formasPagamentosDTO = assembler.toCollectionDTO(formasPagamento);
		return ResponseEntity.ok(formasPagamentosDTO);
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	public ResponseEntity<Void> desassociarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		restauranteService.desassociar(restauranteId, formaPagamentoId);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{formaPagamentoId}")
	public ResponseEntity<Void> associarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		restauranteService.associar(restauranteId, formaPagamentoId);
		return ResponseEntity.noContent().build();
	}
	
	
	
	
}
