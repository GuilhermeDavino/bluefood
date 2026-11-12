package com.blue.bluefood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.blue.bluefood.domain.event.PedidoConfirmadoEvent;
import com.blue.bluefood.domain.model.Pedido;
import com.blue.bluefood.domain.service.EnvioEmailService;
import com.blue.bluefood.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoConfirmadoListener {
	
	@Autowired
	private EnvioEmailService envioEmail;
	
	@TransactionalEventListener
	public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
		Pedido pedido = event.getPedido();
		var mensagem = Mensagem
				.builder()
				.destinatario(pedido.getCliente().getEmail())
				.assunto("Bluefood - pedido confirmado!")
				.corpo("pedido-confirmacao.html")
				.variavel("pedido", pedido)
				.build();
		envioEmail.enviar(mensagem);
	}
}
