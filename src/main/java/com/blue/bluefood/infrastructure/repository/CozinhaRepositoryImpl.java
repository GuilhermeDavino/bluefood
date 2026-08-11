package com.blue.bluefood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.blue.bluefood.domain.model.Cozinha;

@Repository
public class CozinhaRepositoryImpl {

	@PersistenceContext
	private EntityManager manager;
	
	
	public List<Cozinha> todas() {
		TypedQuery<Cozinha> query = manager.createQuery("from Cozinha", Cozinha.class);
		return query.getResultList();
	}
	
	
	public Cozinha buscarPorId(Long id) {
		return manager.find(Cozinha.class, id);
	}
	
	
	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		return manager.merge(cozinha);
	}
	
	@Transactional
	public void remover(Long id) {
		Cozinha cozinha = buscarPorId(id);
		if(cozinha == null) throw new EmptyResultDataAccessException(1);
		manager.remove(cozinha);
	}

	public List<Cozinha> consultarPorNome(String nome) {
		return manager.createQuery("from Cozinha where UPPER(nome) LIKE CONCAT('%', UPPER(:nome), '%')", Cozinha.class)
				.setParameter("nome", nome)
				.getResultList();
	}

}
