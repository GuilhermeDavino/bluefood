package com.blue.bluefood.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.blue.bluefood.core.storage.StorageProperties.TipoStorage;
import com.blue.bluefood.domain.service.FotoStorageService;
import com.blue.bluefood.infrastructure.service.storage.LocalStorageService;
import com.blue.bluefood.infrastructure.service.storage.S3FotoStorageService;


@Configuration
public class StorageConfig {
	
	@Autowired
	private StorageProperties properties;
	
	@Bean
	public AmazonS3 amazonS3() {
		var credentials = new BasicAWSCredentials(properties.getS3().getIdChaveAcesso(), 
				properties.getS3().getChaveAcessoSecreta());
		
		return AmazonS3ClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(properties.getS3().getRegiao())
				.build();
	}
	
	@Bean
	public FotoStorageService fotoStorageService() {
		if (TipoStorage.S3.equals(properties.getTipoStorage())) {
			return new S3FotoStorageService();
		} else {
			return new LocalStorageService();
		}
		
	}
}