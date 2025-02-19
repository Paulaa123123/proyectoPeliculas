package com.paula.component;

import org.springframework.stereotype.Component;

@Component
public class Validador {

	public boolean validarTexto(String texto) {
		return texto != null && texto.length() >= 4;
	}

	public boolean validarDni(String dni) {
		return dni != null && dni.matches("\\d{8}[A-Z]") && letraDniCorrecta(dni);
	}

	private boolean letraDniCorrecta(String dni) {
		String letrasDni = "TRWAGMYFPDXBNJZSQVHLCKE";
		int numero = Integer.parseInt(dni.substring(0, 8));
		return dni.charAt(8) == letrasDni.charAt(numero % 23);
	}

}
