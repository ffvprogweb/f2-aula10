package com.fatec.sigvs.ti_api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.fatec.sigvs.model.Produto;
import com.google.gson.Gson;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

class Req01CadastrarProdutoTest {
    String urlBase = "/api/v1/produtos";
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void ct01_quando_dados_validos_cadatra_produto_com_sucesso() {
        Produto produto = new Produto("Eletrobomba 110V para Maquina de Lavar e Lava Louças", "maquina de lavar",
                "51.66",
                "12");

        ResponseEntity<Produto> result = testRestTemplate.postForEntity(urlBase, produto, Produto.class);
        assertEquals("201 CREATED", result.getStatusCode().toString());

    }

    @Test
    void ct02_quando_dados_invalidos_rejeita_cadastro() {
        record ProdutoDTO(String descricao, String categoria, String custo, String quantidadeNoEstoque) {
        }

        ProdutoDTO novoProduto = new ProdutoDTO("Eletrobomba 110V para Maquina de Lavar e Lava Louças", null, "51.66",
                "12");
        Gson gson = new Gson();
        String produtoJson = gson.toJson(novoProduto);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(produtoJson, headers);

        ResponseEntity<Produto> result = testRestTemplate.exchange(urlBase, HttpMethod.POST, request, Produto.class);
        assertEquals("400 BAD_REQUEST", result.getStatusCode().toString());
    }
}
