package com.blue.bluefood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blue.bluefood.domain.model.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long>, EstadoRepositoryQueries {

}
