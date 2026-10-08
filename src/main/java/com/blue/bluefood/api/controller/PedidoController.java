package com.blue.bluefood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.blue.bluefood.api.assembler.PedidoDTOAssembler;
import com.blue.bluefood.api.assembler.PedidoInputDisassembler;
import com.blue.bluefood.api.assembler.PedidoResumoDTOAssembler;
import com.blue.bluefood.api.model.PedidoDTO;
import com.blue.bluefood.api.model.PedidoResumoDTO;
import com.blue.bluefood.api.model.input.PedidoInputDTO;
import com.blue.bluefood.domain.repository.filter.PedidoFilter;
import com.blue.bluefood.domain.service.EmissaoPedidoService;
import com.blue.bluefood.domain.service.PedidoService;
import com.blue.bluefood.infrastructure.repository.spec.PedidoSpecs;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private PedidoDTOAssembler pedidoAssembler;
	
	@Autowired
	private PedidoInputDisassembler pedidoDisassembler;
	
	@Autowired 
	private EmissaoPedidoService emissaoService;
	
	@Autowired
	private PedidoResumoDTOAssembler pedidoResumoAssembler;
	
	@GetMapping
	public ResponseEntity<List<PedidoResumoDTO>> pesquisarPedidos(PedidoFilter filtros) {
		var pedidos = pedidoService.listarPedidosComFiltros(PedidoSpecs.usandoFiltro(filtros));
		var pedidosDTO = pedidoResumoAssembler.toCollectionDTO(pedidos);
		return ResponseEntity.ok(pedidosDTO);
	}
	
	@GetMapping("/{codigoPedido}")
	public ResponseEntity<PedidoDTO> buscarPedidoPorId(@PathVariable String codigoPedido) {
		var pedido = pedidoService.buscarOuFalhar(codigoPedido);
		var pedidoDTO = pedidoAssembler.toPedidoDTO(pedido);
		return ResponseEntity.ok(pedidoDTO);
	}
	
	@PostMapping
	public ResponseEntity<PedidoDTO> emitirPedido(@RequestBody @Valid PedidoInputDTO pedidoInput) {
		var pedido = pedidoDisassembler.toDomainObject(pedidoInput);
		pedido = emissaoService.emitirPedido(pedido);
		var uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(pedido.getId())
				.toUri();
		var pedidoDTO = pedidoAssembler.toPedidoDTO(pedido);
		return ResponseEntity.created(uri).body(pedidoDTO);
	}
}
