package com.fatec.tdd;

import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    List<Livro> livros = new ArrayList<>();

    public void save(Livro livro) {
        livros.add(livro);
    }

    public int size() {
        return livros.size();
    }

}
