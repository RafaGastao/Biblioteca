package com.mycompany.biblioteca.controller;

import com.mycompany.biblioteca.model.Usuario;
import com.mycompany.biblioteca.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String fazerLogin(@RequestParam String login,
                             @RequestParam String senha,
                             HttpSession session,
                             Model model) {

        Usuario usuario = usuarioService.autenticar(login, senha);

        if (usuario != null) {

            session.setAttribute("usuarioLogado", usuario);

            if (usuario.getTipoUsuario().equals("FUNCIONARIO")) {
                return "redirect:/admin";
            } else {
                return "redirect:/livros";
            }
        }

        model.addAttribute("erro", "Login ou senha inválidos");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/admin")
    public String admin(HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

        if (usuario == null || !usuario.getTipoUsuario().equals("FUNCIONARIO")) {
            return "redirect:/login";
        }

        return "admin";
    }
}