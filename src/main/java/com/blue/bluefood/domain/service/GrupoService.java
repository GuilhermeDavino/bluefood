package com.blue.bluefood.domain.service;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.blue.bluefood.domain.exception.EntidadeNaoEncontradaException;
import com.blue.bluefood.domain.exception.GrupoNaoEncontradoException;
import com.blue.bluefood.domain.model.Grupo;
import com.blue.bluefood.domain.model.Permissao;
import com.blue.bluefood.domain.repository.GrupoRepository;

@Service
public class GrupoService {
	
	private static final String MSG_GRUPO_EM_USO = "O grupo de id %d está em uso";
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private PermissaoService permissaoService;
	
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
	
	@Transactional
	public Collection<Permissao> buscarPermissoesDoGrupo(Long grupoId) {
		var grupo = buscarOuFalhar(grupoId);
		return grupo.getPermissoes();
	}
	
	@Transactional
	public void associarPermissaoAoGrupo(Long grupoId, Long permissaoId) {
		var permissao = permissaoService.buscarOuFalhar(permissaoId);
		var grupo = buscarOuFalhar(grupoId);
		grupo.associarPermissao(permissao);
	}
	
	@Transactional
	public void desassociarPermissaoAoGrupo(Long grupoId, Long permissaoId) {
		var permissao = permissaoService.buscarOuFalhar(permissaoId);
		var grupo = buscarOuFalhar(grupoId);
		grupo.desassociarPermissao(permissao);
	}
}
