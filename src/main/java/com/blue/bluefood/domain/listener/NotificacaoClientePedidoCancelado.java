package com.blue.bluefood.domain.listener;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.blue.bluefood.domain.event.PedidoCanceladoEvent;
import com.blue.bluefood.domain.model.Pedido;
import com.blue.bluefood.domain.service.EnvioEmailService;
import com.blue.bluefood.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoCancelado {
	
	@Autowired
	private EnvioEmailService envioEmail;
	
	@TransactionalEventListener
	public void aoCancelarPedido(PedidoCanceladoEvent event) {
		Pedido pedido = event.getPedido();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
		var dataCancelamento = pedido.getDataCancelamento();
		String dataFormatada = dataCancelamento.format(format);
		var mensagem = Mensagem
				.builder()
				.destinatario(pedido.getCliente().getEmail())
				.assunto("Bluefood - pedido Cancelado!")
				.corpo("pedido-cancelado.html")
				.variavel("pedido", pedido)
				.variavel("dataCancelamento", dataFormatada)
				.build();
		envioEmail.enviar(mensagem);
	}
			
}
