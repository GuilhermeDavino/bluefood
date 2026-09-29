package com.blue.bluefood.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.blue.bluefood.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByEmail(String email);
}
