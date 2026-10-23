package com.blue.bluefood.infrastructure.service.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;

import com.blue.bluefood.domain.service.FotoStorageService;


public class LocalStorageService implements FotoStorageService {
	
	@Value("${bluefood.storage.local.diretorio-fotos}")
	private Path diretorioFotos;
	
	@Override
	public void armazenar(NovaFoto novaFoto) {
		Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());
		try {
			FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
		} catch (IOException e) {
			throw new StorageException("Não foi possível armazena o arquivo", e);
		}
		
	}
	
	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
		try {
			Path arquivoPath = getArquivoPath(nomeArquivo);
			var fotoRecuperada = FotoRecuperada.builder()
					.inputStream(Files.newInputStream(arquivoPath))
					.build();
			return fotoRecuperada;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void remover(String nomeArquivo) {
		try {
			Path path = getArquivoPath(nomeArquivo);
			Files.deleteIfExists(path);
		} catch (IOException e) {
			throw new StorageException("Não foi possível excluir o arquivo", e);
		}
		
		
	}
	
	private Path getArquivoPath(String nomeArquivo) {
		return diretorioFotos.resolve(Path.of(nomeArquivo));
	}

	

	

	

}
