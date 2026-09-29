package com.blue.bluefood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.blue.bluefood.domain.exception.EntidadeEmUsoException;
import com.blue.bluefood.domain.exception.NegocioException;
import com.blue.bluefood.domain.exception.UsuarioNaoEncontradoException;
import com.blue.bluefood.domain.model.Usuario;
import com.blue.bluefood.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario buscarOuFalhar(Long usuarioId) {
		return usuarioRepository.findById(usuarioId).orElseThrow(
				() -> new UsuarioNaoEncontradoException(usuarioId));
	}
	
	public List<Usuario> listar() {
		return usuarioRepository.findAll();
	}
	
	@Transactional
	public Usuario adicionar(Usuario usuario) {
		usuarioRepository.detach(usuario);
		var usuarioOptional = usuarioRepository.findByEmail(usuario.getEmail());
		if (usuarioOptional.isPresent() && !usuarioOptional.get().equals(usuario)) {
			throw new NegocioException(
					String.format("O Email %s já está cadastrado", 
							usuario.getEmail()));
		}
		
		usuario.setId(null); 
		return usuarioRepository.save(usuario);
	}
	
	@Transactional
	public Usuario atualizar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	@Transactional
	public void deletar(Long usuarioId) {
		try {
			usuarioRepository.deleteById(usuarioId);
		} catch (EmptyResultDataAccessException exception) {
			throw new UsuarioNaoEncontradoException(usuarioId);
		} catch (DataIntegrityViolationException exception) {
			throw new EntidadeEmUsoException(String.format("O usuario de id %id está em uso", usuarioId));
		}
	}
}
