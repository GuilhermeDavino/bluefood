package com.blue.bluefood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.blue.bluefood.domain.model.Estado;
import com.blue.bluefood.domain.repository.EstadoRepository;

@Component
public class EstadoRepositoryImpl implements EstadoRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Estado> listar() {
	
		return manager.createQuery("from Estado", Estado.class).getResultList();
	}

	@Override
	public Estado porId(Long id) {
		return manager.find(Estado.class, id);
	}

	@Override
	public Estado adicionar(Estado estado) {
		
		return manager.merge(estado);
	}

	@Override
	public void remover(Estado estado) {
		manager.remove(estado);
		
	}

}
