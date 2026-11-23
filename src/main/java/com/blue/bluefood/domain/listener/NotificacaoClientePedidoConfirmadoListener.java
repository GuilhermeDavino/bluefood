package com.blue.bluefood.domain.listener;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
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
		DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
		var dataConfirmacao = pedido.getDataConfirmacao();
		var dataCriacao = pedido.getDataCriacao();
		String dataConfirmacaoFormatada = dataConfirmacao.format(format);
		String dataCriacaoFormatada = dataCriacao.format(format);
		
		var mensagem = Mensagem
				.builder()
				.destinatario(pedido.getCliente().getEmail())
				.assunto("Bluefood - pedido confirmado!")
				.corpo("pedido-confirmacao.html")
				.variavel("pedido", pedido)
				.variavel("dataCriacao", dataCriacaoFormatada)
				.variavel("dataConfirmacao", dataConfirmacaoFormatada)
				.build();
		envioEmail.enviar(mensagem);
	}
}
