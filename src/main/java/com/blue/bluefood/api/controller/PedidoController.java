package com.blue.bluefood.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
	public ResponseEntity<Page<PedidoResumoDTO>> pesquisarPedidos(PedidoFilter filtros, Pageable pageable) {
		var pedidos = pedidoService.listarPedidosComFiltros(PedidoSpecs.usandoFiltro(filtros), pageable);
		var pedidosDTO = pedidoResumoAssembler.toCollectionDTO(pedidos.getContent());
		return ResponseEntity.ok(new PageImpl<>(pedidosDTO, pageable, pedidos.getTotalElements()));
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
