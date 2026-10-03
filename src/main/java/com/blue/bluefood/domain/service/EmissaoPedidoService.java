package com.blue.bluefood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blue.bluefood.domain.exception.NegocioException;
import com.blue.bluefood.domain.model.Pedido;
import com.blue.bluefood.domain.repository.PedidoRepository;

@Service
public class EmissaoPedidoService {
	
	private static final String MSG_FORMA_PAGAMENTO_NAO_ACEITA = "O restaurante de id %d não aceita"
			+ " a forma de pagamento de id %d";
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private CidadeService cidadeService;
	
	@Transactional
	public Pedido emitirPedido(Pedido pedido) {
		validarPedido(pedido);
		validarItens(pedido);
		var usuario = usuarioService.buscarOuFalhar(1L);
		pedido.setCliente(usuario);
		pedido.definirFrete();
		pedido.calcularValorTotal();
		return pedidoRepository.save(pedido);
	}
	
	@Transactional
	public void validarPedido(Pedido pedido) {
		var restaurante = restauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
		var formaPagamento = formaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());
		var cidade = cidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
		if (!restaurante.contemFormaPagamento(formaPagamento)) {
			throw new NegocioException(String.format(MSG_FORMA_PAGAMENTO_NAO_ACEITA, restaurante.getId(), formaPagamento.getId()));
		}
		pedido.setRestaurante(restaurante);
		pedido.setFormaPagamento(formaPagamento);
		pedido.getEnderecoEntrega().setCidade(cidade);
	}
	
	@Transactional
	public void validarItens(Pedido pedido) {
		pedido.getItens().stream().forEach(item ->  {
			var produto = restauranteService.buscarProdutoPorId(pedido.getRestaurante().getId(), item.getProduto().getId());
			item.setPedido(pedido);
			item.setProduto(produto);
			item.calcularPrecoTotal();
		});
	}
	
}
