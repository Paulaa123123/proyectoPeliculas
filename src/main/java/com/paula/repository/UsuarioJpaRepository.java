package com.paula.repository;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paula.entity.Usuario;

@Repository
public interface UsuarioJpaRepository extends JpaRepository<Usuario, Serializable> {

	Usuario findByEmail(String email);
}
