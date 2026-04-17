/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.biblioteca.repository;

import com.mycompany.biblioteca.model.Livro;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author RafaelG
 */
public interface LivroRepository  extends MongoRepository<Livro, String> {
    
}
