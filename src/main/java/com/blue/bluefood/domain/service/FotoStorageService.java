package com.blue.bluefood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {
	
	FotoRecuperada recuperar(String nomeArquivo);
	
	void armazenar(NovaFoto novaFoto);
	
	void remover(String nomeArquivo);
	
	default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {
		this.armazenar(novaFoto);
		
		if(nomeArquivoAntigo != null) {
			this.remover(nomeArquivoAntigo);
		}
	}
	
	default String geralNomeArquivo(String nomeOriginal) {
		return UUID.randomUUID() + "_" + nomeOriginal;
	}
	
	
	@Getter
	@Builder
	class NovaFoto {
		
		private String nomeArquivo;
		private String contentType;
		private Long contentLength;
		private InputStream inputStream;
	}
	
	@Getter
	@Builder
	class FotoRecuperada {
		private InputStream inputStream;
		private String url;
		
		public boolean temUrl() {
			return this.url != null;
		}
		
		public boolean temInputStream() {
			return this.inputStream != null;
		}
	}
}
