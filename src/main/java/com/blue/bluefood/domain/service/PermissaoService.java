package com.blue.bluefood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blue.bluefood.domain.exception.PermissaoNaoEncontrada;
import com.blue.bluefood.domain.model.Permissao;
import com.blue.bluefood.domain.repository.PermissaoRepository;

@Service
public class PermissaoService {
	
	@Autowired
	private PermissaoRepository repository;
	
	@Transactional
	public Permissao buscarOuFalhar(Long permissaoId) {
		return repository.findById(permissaoId).orElseThrow(() -> new PermissaoNaoEncontrada(permissaoId));
	}
}
