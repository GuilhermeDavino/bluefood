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

import com.blue.bluefood.api.assembler.FormaPagamentoDTOAssembler;
import com.blue.bluefood.api.assembler.FormaPagamentoInputDisassembler;
import com.blue.bluefood.api.model.FormaPagamentoDTO;
import com.blue.bluefood.domain.service.FormaPagamentoService;


@RestController
@RequestMapping("/formaPagamentos")
public class FormaPagamentoController {
	
	@Autowired
	private FormaPagamentoService service;
	
	@Autowired
	private FormaPagamentoDTOAssembler assembler;
	
	@Autowired
	private FormaPagamentoInputDisassembler disassembler;
	
	@GetMapping("/${id}")
	public ResponseEntity<FormaPagamentoDTO> buscarPorId(@PathVariable("id") Long formaPagamentoId) {
		var formaPagamento = service.buscarOuFalhar(formaPagamentoId);
		var formaPagamentoDTO = assembler.toFormaPagamentoDTO(formaPagamento);
		return ResponseEntity.ok(formaPagamentoDTO);
	}
	
	@GetMapping
	public ResponseEntity<List<FormaPagamentoDTO>> listar() {
		var formasDePagamento = service.listar();
		var formasDePagamentoDTO = assembler.toCollectionDTO(formasDePagamento);
		return ResponseEntity.ok(formasDePagamentoDTO);
	}
	
	@PostMapping
	public ResponseEntity<FormaPagamentoDTO> adicionar(@RequestBody FormaPagamentoDTO formaPagamentoInputDTO) {
		var formaPagamento = disassembler.toDomainObject(formaPagamentoInputDTO);
		formaPagamento = service.adicionar(formaPagamento);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/${id}")
				.buildAndExpand(formaPagamento.getId())
				.toUri();
		var formaPagamentoDTO = assembler.toFormaPagamentoDTO(formaPagamento);
		return ResponseEntity.created(uri).body(formaPagamentoDTO);
	}
	
	@PutMapping("/${id}")
	public ResponseEntity<FormaPagamentoDTO> atualizar(
			@PathVariable("id") Long formaPagamentoId, @RequestBody FormaPagamentoDTO formaPagamentoInputDTO) {
		var formaPagamentoAtual = service.buscarOuFalhar(formaPagamentoId);
		BeanUtils.copyProperties(formaPagamentoInputDTO, formaPagamentoAtual, "id");
		var formaPagamento = service.atualizar(formaPagamentoAtual);
		var formaPagamentoDTO = assembler.toFormaPagamentoDTO(formaPagamento);
		return ResponseEntity.ok(formaPagamentoDTO);
	}
	
	@DeleteMapping("/${id}")
	public ResponseEntity<Void> deletar(@PathVariable("id") Long formaPagamentoId) {
		service.deletar(formaPagamentoId);
		return ResponseEntity.noContent().build();
	}
}
