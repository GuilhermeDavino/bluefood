package com.blue.bluefood.core.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.blue.bluefood.domain.service.EnvioEmailService;
import com.blue.bluefood.infrastructure.service.email.SmtpEnvioEmailService;
import com.blue.bluefood.infrastructure.service.email.TesteEnvioEmailService;

@Configuration
public class EmailConfig {
	
	@Value("${blufood.email.impl}")
	private String emailTipo;
	
	@Bean
	public EnvioEmailService enviarEmailService() {
		if (emailTipo.equals("smtp")) {
			var emailImpl = new SmtpEnvioEmailService();
			emailImpl.setIsSandbox(false);
			return emailImpl;
		} else if (emailTipo.equals("sandbox")) {
			var emaiImpl = new SmtpEnvioEmailService();
			emaiImpl.setIsSandbox(true);
			return emaiImpl;
		} else {
			return new TesteEnvioEmailService();
		}
	}
}
