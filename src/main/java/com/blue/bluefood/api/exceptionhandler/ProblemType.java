package com.blue.bluefood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
	ENTITDADE_EM_USO("/entidade-em-uso", "Entidade em uso");
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title) {
		this.uri = "https://bluefood.com.br" + path;
		this.title = title;
	}
}
