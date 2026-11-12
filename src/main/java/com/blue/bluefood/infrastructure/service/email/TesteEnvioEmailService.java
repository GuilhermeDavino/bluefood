package com.blue.bluefood.infrastructure.service.email;

import com.blue.bluefood.domain.service.EnvioEmailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TesteEnvioEmailService implements EnvioEmailService {

	@Override
	public void enviar(Mensagem mensagem) {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		for (String destinatario : mensagem.getDestinatarios()) {
			sb.append(destinatario + ", ");
		}
		sb.delete(sb.length() - 2, sb.length() - 1);
		sb.append(" ]");
		String texto = String.format("O e-mail de assunto %s e corpo %s foi enviado para %s", mensagem.getAssunto(), mensagem.getCorpo(), sb.toString());
		log.info(texto);
	}

}
