package com.blue.bluefood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blue.bluefood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {
	
	List<FormaPagamentoRepository> pagamentos();
	
	FormaPagamentoRepository porId(Long id);
	
	FormaPagamentoRepository salvar(FormaPagamentoRepository obj);
	
	void remover(FormaPagamentoRepository obj);
}
