package com.blue.bluefood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.blue.bluefood.domain.exception.EntidadeNaoEncontradaException;
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
	public Estado buscarPorId(Long id) {
		Estado estado = manager.find(Estado.class, id);
		if(estado == null) throw new EntidadeNaoEncontradaException(String.format("O estado de id %d não foi encontrado ou não existe!", id));
		return estado;
	}

	@Override
	public Estado salvar(Estado estado) {
		
		return manager.merge(estado);
	}

	@Override
	public void remover(Long id) {
		Estado estado = buscarPorId(id);
		manager.remove(estado);
		
	}

}
