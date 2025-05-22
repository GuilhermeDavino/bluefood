package com.blue.bluefood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.blue.bluefood.domain.model.Cidade;
import com.blue.bluefood.domain.repository.CidadeRepository;

@Component
public class CidadeRepositoryImpl implements CidadeRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Cidade> listar() {
		
		return manager.createNamedQuery("from cidade", Cidade.class).getResultList();
	}

	@Override
	public Cidade porId(Long id) {
		
		return manager.find(Cidade.class, id);
	}

	@Override
	public Cidade salvar(Cidade cidade) {
		
		return manager.merge(cidade);
	}

	@Override
	public void remover(Cidade cidade) {
		manager.remove(cidade);
		
	}

}
