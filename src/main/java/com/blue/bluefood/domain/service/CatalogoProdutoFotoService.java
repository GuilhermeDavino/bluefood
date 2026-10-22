package com.blue.bluefood.domain.service;

import java.io.InputStream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blue.bluefood.domain.exception.FotoProdutoNaoEncontradaException;
import com.blue.bluefood.domain.model.FotoProduto;
import com.blue.bluefood.domain.repository.ProdutoRepository;
import com.blue.bluefood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoProdutoFotoService {
	
	@Autowired
	private ProdutoRepository repository;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream inputStream) {
		Long restauranteId = foto.getRestauranteId();
		Long produtoId = foto.getProduto().getId();
		String novoNomeArquivo = fotoStorageService.geralNomeArquivo(foto.getNomeArquivo());
		
		var fotoProdutoExistente = repository
				.findFotoById(produtoId, restauranteId);
		
		String nomeArquivoExistente = null;
		
		if (fotoProdutoExistente.isPresent()) {
			nomeArquivoExistente = fotoProdutoExistente.get().getNomeArquivo();
			repository.delete(fotoProdutoExistente.get());
		}
		foto.setNomeArquivo(novoNomeArquivo);
		foto = repository.save(foto);
		repository.flush();
		
		var novaFoto = NovaFoto.builder()
				.nomeArquivo(foto.getNomeArquivo())
				.inputStream(inputStream)
				.build();
		
		fotoStorageService.substituir(nomeArquivoExistente, novaFoto);
		return foto;
	}

	public FotoProduto buscar(Long produtoId, Long restauranteId) {
		return repository.findFotoById(produtoId, restauranteId)
				.orElseThrow(() -> new FotoProdutoNaoEncontradaException(produtoId, restauranteId));
	}
}
