package com.blue.bluefood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.blue.bluefood.domain.exception.EntidadeNaoEncontradaException;
import com.blue.bluefood.domain.exception.GrupoNaoEncontradoException;
import com.blue.bluefood.domain.model.Grupo;
import com.blue.bluefood.domain.repository.GrupoRepository;

@Service
public class GrupoService {
	
	private static final String MSG_GRUPO_EM_USO = "O grupo de id %d está em uso";
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	public Grupo buscarOuFalhar(Long grupoId) {
		return grupoRepository.findById(grupoId)
				.orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
	}
	
	public List<Grupo> listar() {
		return grupoRepository.findAll();
	}
	
	@Transactional
	public Grupo adicionar(Grupo grupo) {
		grupo.setId(null);
		grupo = grupoRepository.save(grupo);
		return grupo;
	}
	
	@Transactional
	public Grupo atualizar(Grupo grupo) {
		grupo = grupoRepository.save(grupo);
		return grupo;
	}
	
	@Transactional
	public void deletar(Long grupoId) {
		try {
			grupoRepository.deleteById(grupoId);
		} catch (EmptyResultDataAccessException exception) {
			throw new GrupoNaoEncontradoException(grupoId, exception);
		} catch (DataIntegrityViolationException exception) {
			throw new EntidadeNaoEncontradaException(
					String.format(MSG_GRUPO_EM_USO, grupoId), exception);
		}
	}
}
