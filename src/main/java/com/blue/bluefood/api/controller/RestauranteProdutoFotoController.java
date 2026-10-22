package com.blue.bluefood.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blue.bluefood.api.assembler.FotoProdutoAssembler;
import com.blue.bluefood.api.model.FotoProdutoDTO;
import com.blue.bluefood.api.model.input.FotoProdutoInput;
import com.blue.bluefood.domain.exception.EntidadeNaoEncontradaException;
import com.blue.bluefood.domain.model.FotoProduto;
import com.blue.bluefood.domain.service.CatalogoProdutoFotoService;
import com.blue.bluefood.domain.service.FotoStorageService;
import com.blue.bluefood.domain.service.ProdutoService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/fotos")
public class RestauranteProdutoFotoController {
	
	@Autowired
	private CatalogoProdutoFotoService fotoProdutoService;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private FotoProdutoAssembler assembler;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<FotoProdutoDTO> atualizarFotoProduto(@PathVariable Long restauranteId
			, @PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) throws IOException {
		
		var produto = produtoService.buscarProdutoPorRestaurante(produtoId, restauranteId);
		
		var arquivo = fotoProdutoInput.getArquivo();
		var fotoProduto = new FotoProduto();
		
		fotoProduto.setNomeArquivo(arquivo.getOriginalFilename());
		fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
		fotoProduto.setContentType(arquivo.getContentType());
		fotoProduto.setTamanho(arquivo.getSize());
		fotoProduto.setProduto(produto);
		
		fotoProduto = fotoProdutoService.salvar(fotoProduto, arquivo.getInputStream());
		
		var fotoProdutoDTO = assembler.toFotoProdutoDTO(fotoProduto);
		return ResponseEntity.ok(fotoProdutoDTO);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FotoProdutoDTO> buscarFotoProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		var fotoProduto = fotoProdutoService.buscar(produtoId, restauranteId);
		var fotoProdutoDTO = assembler.toFotoProdutoDTO(fotoProduto);
		return ResponseEntity.ok(fotoProdutoDTO);
	}
	@GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<InputStreamResource> servirFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader) 
					throws HttpMediaTypeNotAcceptableException {
		try {
			var fotoProduto = fotoProdutoService.buscar(produtoId, restauranteId);
			
			MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
			List<MediaType> mediaTypeAceitas = MediaType.parseMediaTypes(acceptHeader);
			
			verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypeAceitas);
			
			var inputStream = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
			
			return ResponseEntity.ok()
					.contentType(MediaType.IMAGE_JPEG)
					.body(new InputStreamResource(inputStream));
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
		
	}

	private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypeAceitas) throws HttpMediaTypeNotAcceptableException {
		boolean compativel = mediaTypeAceitas
				.stream().anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));
		
		if(!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypeAceitas);
		}
	}
}
