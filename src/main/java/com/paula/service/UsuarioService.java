package com.paula.service;

import java.util.List;

import com.paula.entity.Usuario;

public interface UsuarioService {

	public abstract Usuario anadirUsuario(Usuario usuario);

	public abstract Usuario agregarUsuario(Usuario usuario);
	
	public abstract Usuario loginUsuario(Usuario usuario);
	
	public abstract Usuario findByEmail(String email);
	
	public abstract List<Usuario> findAllUsers();

	//public abstract List<Pelicula> findAllPeliculas();
	

}
