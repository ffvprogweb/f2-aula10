package com.fatec.tdd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
class Req01CadastraLivro {
    @Test
    void ct01_cadastra_livro_com_sucesso() {
        // dado que o livro nao esta cadastrado
        Biblioteca biblioteca = new Biblioteca();
        Livro livro = new Livro("1111", "Pressman", "Engenharia de Software");
        // quando o usuario cadastra um livro com informaçoes validas
        biblioteca.save(livro);

        // entao o livro fica disponivel para consulta e emprestimo
        assertEquals(1, biblioteca.size());
    }

}
