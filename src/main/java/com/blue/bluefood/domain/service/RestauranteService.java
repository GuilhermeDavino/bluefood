package com.blue.bluefood.domain.service;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.blue.bluefood.domain.exception.EntidadeEmUsoException;
import com.blue.bluefood.domain.exception.RestauranteNaoEncontradoException;
import com.blue.bluefood.domain.model.Cidade;
import com.blue.bluefood.domain.model.Cozinha;
import com.blue.bluefood.domain.model.Produto;
import com.blue.bluefood.domain.model.Restaurante;
import com.blue.bluefood.domain.model.Usuario;
import com.blue.bluefood.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {
	
	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removido,"
			+ " pois está em uso";

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaService cozinhaService;
	
	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Transactional
	public List<Produto> buscarTodosProdutos(Long restauranteId) {
		var restaurante = buscarOuFalhar(restauranteId);
		var produtos = restaurante.getProdutos();
		return produtos;
	}
	
	@Transactional
	public Restaurante adicionar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();
		
		Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
		Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);
		
		restaurante.setId(null);
		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);
		
		restaurante = restauranteRepository.salvar(restaurante);
		return restaurante;
	}
	
	@Transactional
	public Restaurante atualizar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();
		
		Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
		Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);
		restaurante.setCozinha(cozinha);
		
		restaurante.getEndereco().setCidade(cidade);
		Restaurante restauranteNovo = restauranteRepository.salvar(restaurante);
		
		return restauranteNovo;
	}
	
	@Transactional
	public void deletar(Long restauranteId) {
		try {
			restauranteRepository.remover(restauranteId);
		} catch (EmptyResultDataAccessException exception) { 
			throw new RestauranteNaoEncontradoException(restauranteId, exception);
			
		} catch (DataIntegrityViolationException exception) {
			
			throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, restauranteId));
		}
		
	}
	
	
	@Transactional
	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId).
				orElseThrow(() -> 
				new RestauranteNaoEncontradoException(restauranteId));
		
	}
	
	@Transactional
	public void ativar(Long restauranteId) {
		var restaurante = buscarOuFalhar(restauranteId);
		restaurante.ativar();
	}
	
	@Transactional
	public void inativar(Long restauranteId) {
		var restaurante = buscarOuFalhar(restauranteId);
		restaurante.inativar();
	}
	
	@Transactional
	public void ativar(List<Long> restauranteIds) {
		restauranteIds.forEach(this::ativar);
	}
	
	@Transactional
	public void inativar(List<Long> restauranteIds) {
		restauranteIds.forEach(this::inativar);
	}
	
	@Transactional
	public void desassociar(Long restauranteId, Long formaPagamentoId) {
		var restaurante = buscarOuFalhar(restauranteId);
		var formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
		restaurante.removerFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void associar(Long restauranteId, Long formaPagamentoId) {
		var restaurante = buscarOuFalhar(restauranteId);
		var formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
		restaurante.adicionarFormaPagamento(formaPagamento);
	}
	
	
	
	@Transactional
	public Produto buscarProdutoPorId(Long restauranteId, Long produtoId) {
		return produtoService.buscarProdutoPorRestaurante(produtoId, restauranteId);
	}
	
	@Transactional
	public List<Produto> buscarTodosProdutosAtivos(Long restauranteId) {
		return produtoService.buscarTodosOsProdutosAtivos(restauranteId);
	}
	
	@Transactional
	public void fecharRestaurante(Long restauranteId) {
		var restaurante = buscarOuFalhar(restauranteId);
		restaurante.fechar();
	}
	
	@Transactional
	public void abrirRestaurante(Long restauranteId) {
		var restaurante = buscarOuFalhar(restauranteId);
		restaurante.abrir();
	}
	
	@Transactional
	public Collection<Usuario> listarResponsaveis(Long restauranteId) {
		var restaurante = buscarOuFalhar(restauranteId);
		var responsaveis = restaurante.getUsuarios();
		return responsaveis;
	}
	
	@Transactional
	public void associarResponsavelAoRestaurante(Long restauranteId, Long responsavelId) {
		var restaurante = buscarOuFalhar(restauranteId);
		var responsavel = usuarioService.buscarOuFalhar(responsavelId);
		restaurante.adicionarResponsavel(responsavel);
	}
	
	@Transactional
	public void desassociarResponsavelAoRestaurante(Long restauranteId, Long responsavelId) {
		var restaurante = buscarOuFalhar(restauranteId);
		var responsavel = usuarioService.buscarOuFalhar(responsavelId);
		restaurante.removerResponsavel(responsavel);
	}
	
}
