/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.biblioteca.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author RafaelG
 */
@Document(collection = "livros")
public class Livro {
     @Id
    private String id;

    private String titulo;
    private String autor;
    private Integer quantidade;
    private String categoria;
    private Integer anoPublicacao;

    public Livro() {
    }

    public Livro(String titulo, String autor, Integer quantidade, String categoria, Integer anoPublicacao) {
        this.titulo = titulo;
        this.autor = autor;
        this.quantidade = quantidade;
        this.categoria = categoria;
        this.anoPublicacao = anoPublicacao;
    }

    public boolean verificarDisponibilidade() {
        return quantidade != null && quantidade > 0;
    }

    public void diminuirQuantidade() {
        if (quantidade != null && quantidade > 0) {
            quantidade--;
        }
    }

    public void aumentarQuantidade() {
        if (quantidade == null) {
            quantidade = 1;
        } else {
            quantidade++;
        }
    }


    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }
}
    

