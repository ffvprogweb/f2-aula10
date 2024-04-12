package com.fatec.sigvs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.sigvs.model.IProdutoRepository;
import com.fatec.sigvs.model.Produto;

@SpringBootTest
class Req01CadastrarProdutoTests {
    @Autowired
    IProdutoRepository repository;

    @Test
    void ct01_cadastrar_produto_com_sucesso() {
        // dado um produto
        Produto produto1 = new Produto("eletrobomba 110v", "maquina de lavar", 22.30, 10);
        // quando salvo o produto
        Produto p = repository.save(produto1);
        // entao o repositorio eh incrementado de 1
        assertEquals(1, repository.count());
        assertEquals(1, p.getId());
    }

    @Test
    void ct02_cadastrar_produto_descricao_invalida() {
        Produto produto = null;
        try {
            produto = new Produto("", "maquina de lavar", 22.30, 10);
        } catch (Exception e) {
            assertEquals("A descricao não deve estar em branco", e.getMessage());
            assertNull(produto);
        }
    }

}
