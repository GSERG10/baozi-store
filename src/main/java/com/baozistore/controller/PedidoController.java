
package com.baozistore.controller;

import com.baozistore.model.Pedido;
import com.baozistore.repository.ClienteRepository;
import com.baozistore.repository.ProdutoRepository;
import com.baozistore.repository.PedidoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoRepository repository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoController(
            PedidoRepository repository,
            ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository) {

        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @PostMapping
    public ResponseEntity<Pedido> criar(@RequestBody Pedido pedido) {

        // Verifica se os dados obrigatorios foram informados.
        if (pedido.getClienteId() == null ||
                pedido.getProdutoId() == null ||
                pedido.getQuantidade() == null ||
                pedido.getQuantidade() <= 0) {

            return ResponseEntity.badRequest().build();
        }

        // Verifica se o cliente existe no banco.
        if (!clienteRepository.existsById(pedido.getClienteId())) {
            return ResponseEntity.badRequest().build();
        }

        // Verifica se o produto existe no banco.
        if (!produtoRepository.existsById(pedido.getProdutoId())) {
            return ResponseEntity.badRequest().build();
        }

        Pedido salvo = repository.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public List<Pedido> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
