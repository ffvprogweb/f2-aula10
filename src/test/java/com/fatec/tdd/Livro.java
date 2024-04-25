package com.fatec.tdd;

public class Livro {
    String isbn;
    String autor;
    String titulo;

    public Livro(String isbn, String autor, String titulo) {
        this.isbn = isbn;
        this.autor = autor;
        this.titulo = titulo;

    }

    public String getAutor() {
        return autor;
    }

    public String getTitulo() {
        return titulo;
    }
}
