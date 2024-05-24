package com.fatec.sigvs.ti_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.fatec.sigvs.model.Produto;
import com.fatec.sigvs.service.IProdutoRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class Req06AtualizarProdutoTests {
    String urlBase = "/api/v1/produtos";
    @Autowired
    TestRestTemplate testRestTemplate;
    IProdutoRepository repository;

    void setup() {
        Produto produto = new Produto("Eletrobomba 110V para Maquina de Lavar e Lava Louças", "maquina de lavar",
                "51.66",
                "12");

        ResponseEntity<Produto> result = testRestTemplate.postForEntity(urlBase, produto, Produto.class);
        assertEquals("201 CREATED", result.getStatusCode().toString());
    }

    @Test
    void ct01_quando_atualiza_informacoes_de_produto_retorna_detalhes_do_produto_atualizado() {
        // dado que o produto foi cadastrado
        setup();
        String urlBase = "/api/v1/produtos";
        // quando atualizo informações do produto
        Produto produtoAtualizado = new Produto("Tirante", "lava louças",
                "51.66",
                "12");
        produtoAtualizado.setId(1L);
        HttpEntity<Produto> request = new HttpEntity<>(produtoAtualizado);
        ResponseEntity<Produto> response = testRestTemplate.exchange(urlBase + "/1", HttpMethod.PUT, request,
                Produto.class);
        // entao os detalhes do produto ficam disponvieis para consulta
        assertEquals("200 OK", response.getStatusCode().toString());
        assertTrue(response.getBody().getDescricao().contains("Tirante"));
    }

}
