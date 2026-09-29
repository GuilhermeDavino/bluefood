package com.blue.bluefood.api.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
	
	@NotNull
	private Long id;
	@NotBlank
	private String nome;
	@Email
	private String email;
	
}
