package com.blue.bluefood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro interno do sistema"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompeensível"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	ENTITDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_PROPRIEDADE_JSON("/erro-propriedade-json", "Propriedade JSON inválida"),
	PARAMETRO_INVALIDO("/erro-de-parametro", "Parâmetro utilizado na URL é inválido"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio");
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title) {
		this.uri = "https://bluefood.com.br" + path;
		this.title = title;
	}
}
