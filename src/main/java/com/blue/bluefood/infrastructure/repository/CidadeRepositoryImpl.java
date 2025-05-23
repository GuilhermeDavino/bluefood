package com.blue.bluefood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.blue.bluefood.domain.exception.EntidadeEmUsoException;
import com.blue.bluefood.domain.exception.EntidadeNaoEncontradaException;
import com.blue.bluefood.domain.model.Cidade;
import com.blue.bluefood.domain.repository.CidadeRepository;

@Component
public class CidadeRepositoryImpl implements CidadeRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Cidade> listar() {
		return manager.createQuery("from Cidade", Cidade.class).getResultList();
	}

	@Override
	public Cidade buscarPorId(Long id) {
		return manager.find(Cidade.class, id);
	}

	@Transactional
	@Override
	public Cidade salvar(Cidade cidade) {
		return manager.merge(cidade);
	}

	@Transactional
	@Override
	public void remover(Long id) {
		Cidade cidade = buscarPorId(id);
		if(cidade == null) throw new EntidadeNaoEncontradaException("");
		manager.remove(cidade);
		
	}

}
