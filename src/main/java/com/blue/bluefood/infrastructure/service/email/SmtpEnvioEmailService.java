package com.blue.bluefood.infrastructure.service.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.blue.bluefood.core.email.EmailProperties;
import com.blue.bluefood.domain.service.EnvioEmailService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.Setter;


public class SmtpEnvioEmailService implements EnvioEmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Autowired
	private Configuration freeMarkerConfig;
	
	@Setter
	private Boolean isSandbox;
	
	@Value("${bluefood.email.sandbox.destinatarios}")
	private String destinatarios;
	
	@Override
	public void enviar(Mensagem mensagem) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			
			String corpo = processarTemplate(mensagem);
			
			helper.setSubject(mensagem.getAssunto());
			helper.setText(corpo, true);
			if (isSandbox) {
				String[] destinatariosList = destinatarios.split(",");
				helper.setTo(destinatariosList);
			} else {
				helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
			}
			
			helper.setFrom(emailProperties.getRemetente());
			mailSender.send(mimeMessage);
		} catch (Exception exception) {
			System.out.println(exception);
			throw new EmailException("Não foi possível enviar o e-mail");
		}
		
		
		
	}

	private String processarTemplate(Mensagem mensagem) {
		try {
			Template template = freeMarkerConfig.getTemplate(mensagem.getCorpo());
			
			return FreeMarkerTemplateUtils
					.processTemplateIntoString(template, mensagem.getVariaveis());
		} catch (Exception e) {
			System.out.println(e);
			throw new EmailException("Não foi possível montar o template do e-mail", e);
		}	
	}

}
