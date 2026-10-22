package com.blue.bluefood.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.blue.bluefood.core.validation.FileContentType;
import com.blue.bluefood.core.validation.FileSize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoInput {
	
	
	@NotNull
	@FileSize(max = "500KB")
	@FileContentType(allowed = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
	private MultipartFile arquivo;
	
	@NotBlank
	private String descricao;
}
