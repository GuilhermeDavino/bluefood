package com.blue.bluefood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.blue.bluefood.domain.model.Restaurante;
import com.blue.bluefood.domain.repository.RestauranteRepository;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Restaurante> todos() {
		return manager.createQuery("from Restaurante", Restaurante.class).getResultList();
	}

	@Override
	public Restaurante porId(Long id) {
		return manager.find(Restaurante.class, id);
	}

	@Override
	public Restaurante adicionar(Restaurante restaurante) {
		return manager.merge(restaurante);
	}

	@Override
	public void remover(Restaurante restaurante) {
		restaurante = manager.find(Restaurante.class, restaurante.getId());
		manager.remove(restaurante);
		
	}
	
}
