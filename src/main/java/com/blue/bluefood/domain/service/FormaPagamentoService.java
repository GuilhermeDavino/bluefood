package com.blue.bluefood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.blue.bluefood.domain.exception.EntidadeEmUsoException;
import com.blue.bluefood.domain.exception.FormaPagamentoNaoEncontrada;
import com.blue.bluefood.domain.model.FormaPagamento;
import com.blue.bluefood.domain.repository.FormaPagamentoRepository;

@Service
public class FormaPagamentoService {
	
	public static final String MSG_FORMA_PAGAMENTO_EM_USO = "A forma pagamento de código %d não pode ser removido,"
			+ " pois está em uso";;
	
	@Autowired
	private FormaPagamentoRepository repository;
	
	public List<FormaPagamento> listar() {
		return repository.findAll();
	}
	
	public FormaPagamento buscarOuFalhar(Long formaPagamentoId) {
		return repository.findById(formaPagamentoId).orElseThrow(() -> 
			new FormaPagamentoNaoEncontrada(formaPagamentoId)
		);
	}
	
	@Transactional
	public FormaPagamento adicionar(FormaPagamento formaPagamento) {
		formaPagamento.setId(null);
		return repository.save(formaPagamento);
	}
	
	@Transactional
	public FormaPagamento atualizar(FormaPagamento formaPagamento) {
		return repository.save(formaPagamento);
	}
	
	@Transactional
	public void deletar(Long formaPagamentoId) {
		try {
			repository.deleteById(formaPagamentoId);
		} catch (EmptyResultDataAccessException exception) { 
			throw new FormaPagamentoNaoEncontrada(formaPagamentoId, exception);
			
		} catch (DataIntegrityViolationException exception) {
			
			throw new EntidadeEmUsoException(String.format(MSG_FORMA_PAGAMENTO_EM_USO, formaPagamentoId));
		}
	}
}
