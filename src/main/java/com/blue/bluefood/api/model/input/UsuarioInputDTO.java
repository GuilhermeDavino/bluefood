package com.blue.bluefood.api.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInputDTO {
	
	@NotBlank
	private String nome;
	
	@Email
	private String email;
	
	@NotBlank
	private String senha;
}
