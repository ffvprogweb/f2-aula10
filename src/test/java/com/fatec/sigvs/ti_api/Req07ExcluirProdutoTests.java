package com.fatec.sigvs.ti_api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.fatec.sigvs.model.Produto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class Req07ExcluirProdutoTests {
    String urlBase = "/api/v1/produtos";
    @Autowired
    TestRestTemplate testRestTemplate;

    void setup() {
        Produto produto = new Produto("Eletrobomba 110V para Maquina de Lavar e Lava Louças", "maquina de lavar",
                "51.66",
                "12");

        ResponseEntity<Produto> result = testRestTemplate.postForEntity(urlBase, produto, Produto.class);
        assertEquals("201 CREATED", result.getStatusCode().toString());
    }

    @Test
    void ct01_quando_excluo_o_produto_a_consulta_retorna_nao_encontrado() {
        // dado que o produto foi cadastrado
        setup();
        // quando excluo o produto
        ResponseEntity<String> response = testRestTemplate.exchange(urlBase + "/1", HttpMethod.DELETE, null,
                String.class);
        // os detalhes do produto ficam disponvieis para consulta
        assertEquals("200 OK", response.getStatusCode().toString());
        response = testRestTemplate.getForEntity(urlBase + "/1", String.class);
        assertEquals("404 NOT_FOUND", response.getStatusCode().toString());

    }
}
