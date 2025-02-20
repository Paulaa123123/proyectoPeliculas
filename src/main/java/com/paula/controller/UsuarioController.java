package com.paula.controller;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paula.entity.Usuario;
import com.paula.repository.UsuarioJpaRepository;
import com.paula.service.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	private static final String USUARIO_ANADIR_VIEW = "usuarioAnadir";
	private static final String USUARIO_LOGIN_VIEW = "login";
	private static final Log LOG = LogFactory.getLog(UsuarioController.class);

	@Autowired
	@Qualifier("usuarioServiceImpl")
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioJpaRepository usuarioJpaRepository;

	@GetMapping("/anadir")
	public ModelAndView anadirUsuario() {

		ModelAndView mav = new ModelAndView(USUARIO_ANADIR_VIEW);
		mav.addObject("usuario", new Usuario());

		return mav;
	}

	@PostMapping("/agregar")
	public ModelAndView addUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult) {
		ModelAndView mav = new ModelAndView(USUARIO_ANADIR_VIEW);

		if (bindingResult.hasErrors()) {
			if (bindingResult.hasFieldErrors("email")) {
				mav.addObject("emailError", "El email no es válido.");
			} else if (bindingResult.hasFieldErrors("password")) {
				mav.addObject("error", "La contraseña no cumple con los requisitos.");
			} else {
				mav.addObject("error", "Error en los datos del formulario.");
			}
			mav.addObject("bindingResult", bindingResult);
			return mav;
		}

		Usuario usuarioExistente = usuarioJpaRepository.findByEmail(usuario.getEmail());
		if (usuarioExistente != null) {
			mav.addObject("emailError", "El email ya está registrado.");
			return mav;
		}

		usuarioService.anadirUsuario(usuario);

		mav.setViewName("redirect:/usuario/login");
		return mav;
	}

	@GetMapping("/login")
	public ModelAndView loginUsuarioForm() {
		ModelAndView mav = new ModelAndView(USUARIO_LOGIN_VIEW);
		mav.addObject("usuario", new Usuario());
		return mav;
	}

	@PostMapping("/login")
	public String loginUsuario(@ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "usuarioLogin";
		}

		Usuario usuarioDb = usuarioService.loginUsuario(usuario);

		// Hacer validacion de email
		if (usuarioDb == null) {
			return "redirect:/usuario/login?error=email";
		}

		return "redirect:/usuario/index";
	}

}
