package com.paula.controller;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	private UsuarioJpaRepository usuarioJpaRepository;

	@GetMapping("/anadir")
	public ModelAndView anadirUsuario() {

		ModelAndView mav = new ModelAndView(USUARIO_ANADIR_VIEW);
		mav.addObject("usuario", new Usuario());

		return mav;
	}

	@PostMapping("/agregar")
	public String addUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult) {
		LOG.info("Call: addUsuario() --- Param: " + usuario.toString());

		
		usuarioService.anadirUsuario(usuario);

		return "redirect:/usuario/login";
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

	        //Hacer validacion de email
	        if (usuarioDb == null) {
	            return "redirect:/usuario/login?error=email"; 
	        }

	        return "redirect:/usuario/index"; 
	    }
		

}
