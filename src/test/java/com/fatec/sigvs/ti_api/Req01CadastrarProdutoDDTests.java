package com.fatec.sigvs.ti_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.sigvs.model.Produto;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class Req01CadastrarProdutoDDTests {
    String urlBase = "/api/v1/produtos";
    @Autowired
    TestRestTemplate testRestTemplate;

    record ProdutoDTO(String descricao, String categoria, String custo, String quantidadeNoEstoque) {
    };

    @ParameterizedTest
    @CsvSource({
            "Eletrobomba 110V para Maquina de Lavar e Lava Louças, maquina de lavar, 51.66, 12, 201 CREATED,''",
            " ' ' , maquina de lavar, 51.66, 12, 400 BAD_REQUEST, A descrição não deve estar em branco",
            "  , maquina de lavar, 51.66, 12, 400 BAD_REQUEST, A descrição não deve estar em branco",
            "Eletrobomba 110V para Maquina de Lavar e Lava Louças, , 51.66, 12, 400 BAD_REQUEST, A categoria não deve estar em branco",
            "Eletrobomba 110V para Maquina de Lavar e Lava Louças,'' , 51.66, 12, 400 BAD_REQUEST, A categoria não deve estar em branco",
            "Eletrobomba 110V para Maquina de Lavar e Lava Louças, maquina de lavar , 0, 12, 400 BAD_REQUEST, O custo deve ser maior que zero",
    })
    void cadastrarProduto(String descricao, String categoria, String custo, String quant, String re, String msgErro) {
        record ProdutoDTO(String descricao, String categoria, String custo, String quantidadeNoEstoque) {
        }
        ProdutoDTO produto = new ProdutoDTO(descricao, categoria, custo, quant);
        if (msgErro.equals("")) {
            ResponseEntity<Produto> result = testRestTemplate.postForEntity(urlBase, produto, Produto.class);
            assertEquals(re, result.getStatusCode().toString());
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<ProdutoDTO> request = new HttpEntity<>(produto, headers);
            ResponseEntity<String> result = testRestTemplate.postForEntity(urlBase, request, String.class);
            assertEquals(re, result.getStatusCode().toString());
            assertTrue(result.getBody().contains(msgErro));

        }
    }
}
