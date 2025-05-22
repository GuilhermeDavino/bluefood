package com.blue.bluefood.domain.repository;

import java.util.List;

public interface FormaPagamento {
	
	List<FormaPagamento> pagamentos();
	
	FormaPagamento porId(Long id);
	
	FormaPagamento salvar(FormaPagamento obj);
	
	void remover(FormaPagamento obj);
}
