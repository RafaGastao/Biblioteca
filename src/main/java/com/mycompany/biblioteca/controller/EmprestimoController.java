package com.mycompany.biblioteca.controller;

import com.mycompany.biblioteca.model.Emprestimo;
import com.mycompany.biblioteca.model.Usuario;
import com.mycompany.biblioteca.service.EmprestimoService;
import com.mycompany.biblioteca.service.LivroService;
import com.mycompany.biblioteca.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;
    private final UsuarioService usuarioService;
    private final LivroService livroService;

    public EmprestimoController(EmprestimoService emprestimoService,
                               UsuarioService usuarioService,
                               LivroService livroService) {
        this.emprestimoService = emprestimoService;
        this.usuarioService = usuarioService;
        this.livroService = livroService;
    }

    @GetMapping
    public String listar(Model model, HttpSession session) {

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        emprestimoService.verificarAtrasos();

        model.addAttribute("usuarioLogado", usuarioLogado);

        if (usuarioLogado.getTipoUsuario().equals("FUNCIONARIO")) {
            model.addAttribute("emprestimos", emprestimoService.listarTodos());
        } else {
            model.addAttribute("emprestimos",
                    emprestimoService.listarPorUsuario(usuarioLogado.getId()));
        }

        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("livros", livroService.listarTodos());

        model.addAttribute("usuariosMap", usuarioService.listarTodos()
                .stream()
                .collect(java.util.stream.Collectors.toMap(
                        u -> u.getId(),
                        u -> u.getNome()
                )));

        model.addAttribute("livrosMap", livroService.listarTodos()
                .stream()
                .collect(java.util.stream.Collectors.toMap(
                        l -> l.getId(),
                        l -> l.getTitulo()
                )));

        return "emprestimos";
    }

   @PostMapping("/realizar")
public String realizarEmprestimo(@RequestParam String livroId,
                                 HttpSession session) {

    Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null) {
        return "redirect:/login";
    }

    emprestimoService.realizarEmprestimo(usuarioLogado.getId(), livroId);

    return "redirect:/emprestimos";
}

    @PostMapping("/devolver")
    public String devolver(@RequestParam String id, HttpSession session) {

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) return "redirect:/login";

        Emprestimo emprestimo = emprestimoService.buscarPorId(id);

        if (emprestimo == null) return "redirect:/emprestimos";

        if (!usuarioLogado.getTipoUsuario().equals("FUNCIONARIO")) {

            if (!emprestimo.getUsuarioId().equals(usuarioLogado.getId())) {
                return "redirect:/emprestimos";
            }
        }

        emprestimoService.devolverLivro(id);

        return "redirect:/emprestimos";
    }
}