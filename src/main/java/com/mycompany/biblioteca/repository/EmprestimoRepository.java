/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.biblioteca.repository;

import com.mycompany.biblioteca.model.Emprestimo;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author RafaelG
 */
public interface EmprestimoRepository  extends MongoRepository<Emprestimo, String> {
        List<Emprestimo> findByUsuarioId(String usuarioId);
}
