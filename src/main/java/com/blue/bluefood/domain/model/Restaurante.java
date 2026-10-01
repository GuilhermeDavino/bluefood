package com.blue.bluefood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.blue.bluefood.core.validation.Groups;
import com.blue.bluefood.core.validation.ValorZeroIncluiDescricao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@ValorZeroIncluiDescricao(valorField = "taxaFrete", 
descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {
	
	@NotNull(groups = {Groups.RestauranteId.class})
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	@PositiveOrZero
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;
	
	@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
	@Valid
	@NotNull
	@JsonIgnoreProperties("hibernateLazyInitializer")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;
	
	private Boolean ativo = Boolean.TRUE;
	
	@NotNull
	private Boolean aberto;
	
	@JsonIgnore
	@Embedded
	private Endereco endereco;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento",
	joinColumns = @JoinColumn(name = "restaurante_id", nullable = false),
	inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id", nullable = false))
	private Set<FormaPagamento> formasPagamento;
	
	@JsonIgnore
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos;
	
	@JsonIgnore
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;
	
	@JsonIgnore
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;
	
	@ManyToMany
	@JoinTable(name = "restaurante_usuario_responsavel", 
	joinColumns = @JoinColumn(name = "restaurante_id", nullable = false), 
	inverseJoinColumns = @JoinColumn(name = "usuario_id", nullable = false))
	private Set<Usuario> usuarios;
	
	public void ativar() {
		this.ativo = true;
	}
	
	public void inativar() {
		this.ativo = false;
	}
	
	public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().remove(formaPagamento);
	}
	
	public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().add(formaPagamento);
	}
	
	public boolean removerProduto(Produto produto) {
		return getProdutos().remove(produto);
	}
	
	public boolean adicionarProduto(Produto produto) {
		
		if(!contemProduto(produto)) {
			return getProdutos().add(produto);
		}
		return true;
		
	}
	
	public boolean contemProduto(Produto produto) {
		return getProdutos().contains(produto);
	}
	
	public void fechar() {
		this.aberto = false;
	}
	
	public void abrir() {
		this.aberto = true;
	}
	
	public boolean adicionarResponsavel(Usuario responsavel) {
		return getUsuarios().add(responsavel);
	}
	
	public boolean removerResponsavel(Usuario responsavel) {
		return getUsuarios().remove(responsavel);
	}

}
