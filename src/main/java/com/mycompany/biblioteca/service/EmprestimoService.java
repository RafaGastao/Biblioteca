package com.mycompany.biblioteca.service;

import com.mycompany.biblioteca.model.Emprestimo;
import com.mycompany.biblioteca.model.Livro;
import com.mycompany.biblioteca.repository.EmprestimoRepository;
import com.mycompany.biblioteca.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository,
                             LivroRepository livroRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
    }

    public String realizarEmprestimo(String usuarioId, String livroId) {

        Optional<Livro> livroOpt = livroRepository.findById(livroId);

        if (livroOpt.isEmpty()) return "Livro não encontrado";

        Livro livro = livroOpt.get();

        if (!livro.verificarDisponibilidade()) return "Livro indisponível";

        livro.diminuirQuantidade();
        livroRepository.save(livro);

        Emprestimo emprestimo = new Emprestimo(usuarioId, livroId);

        emprestimo.setDataRetirada(LocalDate.now());
        emprestimo.setDataDevolucao(LocalDate.now().plusDays(7));
        emprestimo.setSituacao("EMPRESTADO");
        emprestimo.setMulta(0.0);

        emprestimoRepository.save(emprestimo);

        return "OK";
    }

    public List<Emprestimo> listarTodos() {
        return emprestimoRepository.findAll();
    }

    public List<Emprestimo> listarPorUsuario(String usuarioId) {
        return emprestimoRepository.findByUsuarioId(usuarioId);
    }

    public Emprestimo buscarPorId(String id) {
        return emprestimoRepository.findById(id).orElse(null);
    }

    public void devolverLivro(String emprestimoId) {

        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId).orElse(null);
        if (emprestimo == null) return;

        Livro livro = livroRepository.findById(emprestimo.getLivroId()).orElse(null);

        if (livro != null) {
            livro.aumentarQuantidade();
            livroRepository.save(livro);
        }

        emprestimo.setSituacao("DEVOLVIDO");

        if (LocalDate.now().isAfter(emprestimo.getDataDevolucao())) {
            long diasAtraso = ChronoUnit.DAYS.between(
                    emprestimo.getDataDevolucao(),
                    LocalDate.now()
            );
            emprestimo.setMulta(diasAtraso * 2.0);
        }

        emprestimoRepository.save(emprestimo);
    }

    public void verificarAtrasos() {

        List<Emprestimo> lista = emprestimoRepository.findAll();

        for (Emprestimo e : lista) {

            if ("DEVOLVIDO".equals(e.getSituacao())) continue;

            if (e.getDataDevolucao().isBefore(LocalDate.now())) {

                e.setSituacao("ATRASADO");

                long diasAtraso = ChronoUnit.DAYS.between(
                        e.getDataDevolucao(),
                        LocalDate.now()
                );

                e.setMulta(diasAtraso * 2.0);

                emprestimoRepository.save(e);
            }
        }
    }
}