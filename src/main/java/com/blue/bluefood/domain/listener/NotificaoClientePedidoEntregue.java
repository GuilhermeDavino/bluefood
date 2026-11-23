package com.blue.bluefood.domain.listener;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.blue.bluefood.domain.event.PedidoEntregadoEvent;
import com.blue.bluefood.domain.model.Pedido;
import com.blue.bluefood.domain.service.EnvioEmailService;
import com.blue.bluefood.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificaoClientePedidoEntregue {
	
	@Autowired
	private EnvioEmailService envioEmail;
	
	@TransactionalEventListener
	public void aoEntregarPedido(PedidoEntregadoEvent event) {
		Pedido pedido = event.getPedido();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
		var dataEntrega = pedido.getDataEntrega();
		String dataFormatada = dataEntrega.format(format);
		var mensagem = Mensagem
				.builder()
				.destinatario(pedido.getCliente().getEmail())
				.assunto("Bluefood - pedido Entregue!")
				.corpo("pedido-entregue.html")
				.variavel("pedido", pedido)
				.variavel("dataEntrega", dataFormatada)
				.build();
		envioEmail.enviar(mensagem);
	}
}
