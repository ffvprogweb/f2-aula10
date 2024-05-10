package com.fatec.sigvs.controller;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.fatec.sigvs.model.Produto;
import com.fatec.sigvs.service.IProdutoServico;

/**
 * CRUD de Produto
 */
@CrossOrigin("*") // desabilita o cors do spring security
@RestController
@RequestMapping("/api/v1/produtos")
public class APIProdutoController {
    Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private IProdutoServico produtoServico;

    record ProdutoDTO(String descricao, String categoria, String custo, String quantidadeNoEstoque) {
    };

    // A anotação @RequestBody indica que o Spring deve desserializar o body da
    // solicitação em um objeto. Este objeto é passado como um parâmetro do método

    @PostMapping
    public ResponseEntity<Object> cadastraProduto(@RequestBody ProdutoDTO p) {
        logger.info(">>>>>> apicontroller cadastrar produto iniciado...");
        try {
            Produto produtoNovo = new Produto();
            produtoNovo.setDescricao(p.descricao);
            produtoNovo.setCategoria(p.categoria);
            produtoNovo.setCusto(p.custo);
            produtoNovo.setQuantidadeNoEstoque(p.quantidadeNoEstoque);
            Optional<Produto> produto = produtoServico.cadastrar(produtoNovo);
            if (produto.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(produto.get());
            } else {
                logger.info(">>>>>> api produto controller cadastrar exception =>");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro não esperado ");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    // atualiza informacoes de produto
    @PutMapping("{id}")
    public ResponseEntity<Object> atualizaProduto(@PathVariable("id") Long produtoId,
            @RequestBody Produto produtoAtualizado) {
        logger.info(">>>>>> apicontroller atualizar informacoes de produto iniciado ");
        try {
            Produto produto = produtoServico.atualizar(produtoId, produtoAtualizado).get();
            return ResponseEntity.ok(produto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Retorna (i) detalhes do produto ou (ii) nao encontrado se o codigo do produto
     * nao existir
     * 
     * @param id - codigo do produto enviado pela aplicacao cliente
     * @return - 200 OK ou 404 NOT_FOUND
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> consultaPorId(@PathVariable String id) {
        logger.info(">>>>>> apicontroller consulta por id chamado");
        Optional<Produto> produto = produtoServico.consultarPorId(id);
        if (produto.isEmpty()) {
            logger.info(">>>>>> apicontroller id not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(produto.get());
    }

    /**
     * consulta todos considerando id.produto = id.imagem
     * 
     * @return
     */
    @GetMapping
    public ResponseEntity<Object> consultaProduto() {
        return ResponseEntity.status(HttpStatus.OK).body(produtoServico.consultaProduto());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluiProduto(@PathVariable String id) {
        logger.info(">>>>>> apicontroller exluir por id chamado");
        Optional<Produto> produto = produtoServico.consultarPorId(id);
        if (produto.isEmpty()) {
            logger.info(">>>>>> apicontroller id not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado.");
        } else {
            produtoServico.excluir(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }

    }

}