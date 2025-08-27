package com.example.demo.service;

import com.example.demo.dto.CarrinhoResponseDTO;
import com.example.demo.model.Carrinho;
import com.example.demo.model.Cliente;
import com.example.demo.model.ItemCarrinho;
import com.example.demo.model.Produto;
import com.example.demo.repository.CarrinhoRepository;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.ProdutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarrinhoService {

    private static final Logger logger = LoggerFactory.getLogger(CarrinhoService.class);

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public CarrinhoResponseDTO adicionarProduto(Long clienteId, Long produtoId, Integer quantidade) {
        logger.info("Método adicionarProduto - Cliente: {}, Produto: {}, Quantidade: {}",
                clienteId, produtoId, quantidade);

        Carrinho carrinho = adicionarItem(clienteId, produtoId, quantidade);

        // Converter para DTO de resposta
        CarrinhoResponseDTO response = new CarrinhoResponseDTO();
        response.setId(carrinho.getId());
        response.setItens(carrinho.getItens().stream().map(item -> {
            CarrinhoResponseDTO.ItemDTO dtoItem = new CarrinhoResponseDTO.ItemDTO();
            dtoItem.setProdutoId((Long) item.getProduto().getId());
            dtoItem.setNomeProduto(item.getProduto().getNome());
            dtoItem.setQuantidade(item.getQuantidade());
            dtoItem.setPreco(item.getPreco());
            return dtoItem;
        }).collect(Collectors.toList()));
        response.setValorTotal(carrinho.getValorTotal());

        return response;
    }

    @Transactional
    public Carrinho adicionarItem(Long clienteId, Long produtoId, Integer quantidade) {
        logger.info("Iniciando adição de item - Cliente: {}, Produto: {}, Quantidade: {}",
                clienteId, produtoId, quantidade);

        // Buscar ou criar carrinho
        Carrinho carrinho = carrinhoRepository.findByClienteId(clienteId)
                .orElseGet(() -> criarNovoCarrinho(clienteId));

        if (carrinho.getItens() == null) {
            carrinho.setItens(new ArrayList<>());
        }

        // Buscar produto
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Verificar estoque
        if (produto.getEstoque() < quantidade) {
            throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
        }

        // Verificar se item já existe no carrinho
        Optional<ItemCarrinho> itemExistente = carrinho.getItens().stream()
                .filter(item -> item.getProduto().getId().equals(produtoId))
                .findFirst();

        if (itemExistente.isPresent()) {
            ItemCarrinho item = itemExistente.get();
            item.setQuantidade(item.getQuantidade() + quantidade);
        } else {
            ItemCarrinho novoItem = new ItemCarrinho(carrinho, produto, quantidade, produto.getPreco());
            carrinho.getItens().add(novoItem);
        }

        carrinho.recalcularValorTotal();
        return carrinhoRepository.save(carrinho);
    }

    private Carrinho criarNovoCarrinho(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        Carrinho novoCarrinho = new Carrinho(cliente);
        return carrinhoRepository.save(novoCarrinho);
    }

    public Carrinho getCarrinhoByClienteId(Long clienteId) {
        return carrinhoRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado para o cliente ID: " + clienteId));
    }

    // ... os outros métodos (atualizarQuantidadeItem, removerItem, limparCarrinho) podem ser mantidos como estão
}
