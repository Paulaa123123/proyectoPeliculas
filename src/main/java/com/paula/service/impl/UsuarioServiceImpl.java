package com.paula.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.paula.entity.Usuario;
import com.paula.repository.UsuarioJpaRepository;
import com.paula.service.UsuarioService;

@Service("usuarioServiceImpl")
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	@Qualifier("usuarioJpaRepository")
	private UsuarioJpaRepository usuarioJpaRepository;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public Usuario anadirUsuario(Usuario usuario) {
		
		String encriptada = passwordEncoder.encode(usuario.getPassword());
		usuario.setPassword(encriptada);

	
		return usuarioJpaRepository.save(usuario);
	}

	@Override
	public Usuario agregarUsuario(Usuario usuario) {
		return usuarioJpaRepository.save(usuario);
	}

	@Override
	public Usuario loginUsuario(Usuario usuario) {
		Usuario usuarioDb = usuarioJpaRepository.findByEmail(usuario.getEmail());

		//Comprobacion email
		if (usuarioDb == null) {
			return null; 
		}

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if (!passwordEncoder.matches(usuario.getPassword(), usuarioDb.getPassword())) {
			return null; 
		}

		return usuarioDb; 
	}

	@Override
	public Usuario findByEmail(String email) { 
		return usuarioJpaRepository.findByEmail(email);
	}

	@Override
	public List<Usuario> findAllUsers() {
		return usuarioJpaRepository.findAll(); 
	}
}
