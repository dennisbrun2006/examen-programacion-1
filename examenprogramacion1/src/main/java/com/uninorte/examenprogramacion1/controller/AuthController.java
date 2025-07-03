package com.uninorte.examenprogramacion1.controller;

import com.uninorte.examenprogramacion1.model.Usuario;
import com.uninorte.examenprogramacion1.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute("usuario") @Valid Usuario usuario,
                                   BindingResult result,
                                   Model model) {

        if (usuarioService.existePorEmail(usuario.getEmail())) {
            result.rejectValue("email", "error.usuario", "Ya existe una cuenta con ese email.");
        }

        if (result.hasErrors()) {
            return "registro";
        }

        usuarioService.registrarUsuario(usuario);
        model.addAttribute("registroExitoso", true);
        return "redirect:/login";
    }
}
