package com.blue.bluefood.infrastructure.service.storage;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.blue.bluefood.core.storage.StorageProperties;
import com.blue.bluefood.domain.service.FotoStorageService;


public class S3FotoStorageService implements FotoStorageService {
	
	@Autowired
	private AmazonS3 amazonS3;
	
	@Autowired
	private StorageProperties properties;
	
	
	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
		String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
		URL url = amazonS3.getUrl(properties.getS3().getBucket(), caminhoArquivo);
		var fotoRecuperada = FotoRecuperada.builder()
				.url(url.toString())
				.build();
		return fotoRecuperada;
	}

	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());
			
			var objectMetaData = new ObjectMetadata();
			objectMetaData.setContentType(novaFoto.getContentType());
			objectMetaData.setContentLength(novaFoto.getContentLength());
			
			var putObjectRequest = new PutObjectRequest(
					properties.getS3().getBucket(), 
					caminhoArquivo, 
					novaFoto.getInputStream(),
					objectMetaData)
					.withCannedAcl(CannedAccessControlList.PublicRead);
			amazonS3.putObject(putObjectRequest);
		} catch (Exception e) {
			throw new StorageException("Não foi possível enviar arquivo para Amazon S3", e);
		}
		
	}

	private String getCaminhoArquivo(String nomeArquivo) {
		return String.format("%s/%s", properties.getS3().getDiretorioFotos(), nomeArquivo);
	}

	@Override
	public void remover(String nomeArquivo) {
		String keyObject = getCaminhoArquivo(nomeArquivo);
		System.out.println(keyObject);
		var deleteObjectRequest = new DeleteObjectRequest(properties.getS3().getBucket(), keyObject);
		amazonS3.deleteObject(deleteObjectRequest);
	}

	

}
