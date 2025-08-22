package com.example.demo.service;

import com.example.demo.model.Carrinho;
import com.example.demo.model.Cliente;
import com.example.demo.model.ItemCarrinho;
import com.example.demo.model.Produto;
import com.example.demo.repository.CarrinhoRepository;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CarrinhoService {

    private static final Logger logger = LoggerFactory.getLogger(CarrinhoService.class);


    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional(readOnly = true)
    public Carrinho getCarrinhoByClienteId(Long clienteId) {
        return carrinhoRepository.findByClienteIdWithItens(clienteId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado para o cliente ID: " + clienteId));
    }

    @Transactional
    public Carrinho adicionarItem(Long clienteId, Long produtoId, Integer quantidade) {
        logger.info("Iniciando adição de item - Cliente: {}, Produto: {}, Quantidade: {}",
                clienteId, produtoId, quantidade);

        try {
            // Buscar ou criar carrinho
            Carrinho carrinho = carrinhoRepository.findByClienteId(clienteId)
                    .orElseGet(() -> {
                        logger.debug("Criando novo carrinho para cliente: {}", clienteId);
                        return criarNovoCarrinho(clienteId);
                    });

            // Garantir que a lista não seja null
            if (carrinho.getItens() == null) {
                carrinho.setItens(new ArrayList<>());
                logger.warn("Lista de itens era null - inicializada para cliente: {}", clienteId);
            }

            // Buscar produto
            Produto produto = produtoRepository.findById(produtoId)
                    .orElseThrow(() -> {
                        logger.error("Produto não encontrado: {}", produtoId);
                        return new RuntimeException("Produto não encontrado");
                    });

            logger.debug("Produto encontrado: {} (Estoque: {})", produto.getNome(), produto.getEstoque());

            // Verificar estoque
            if (produto.getEstoque() < quantidade) {
                logger.warn("Estoque insuficiente - Produto: {}, Estoque: {}, Solicitado: {}",
                        produto.getNome(), produto.getEstoque(), quantidade);
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
            }

            // Verificar se item já existe no carrinho
            Optional<ItemCarrinho> itemExistente = carrinho.getItens().stream()
                    .filter(item -> item.getProduto().getId().equals(produtoId))
                    .findFirst();

            if (itemExistente.isPresent()) {
                // Atualizar quantidade do item existente
                ItemCarrinho item = itemExistente.get();
                int quantidadeAntiga = item.getQuantidade();
                item.setQuantidade(item.getQuantidade() + quantidade);

                logger.info("Item atualizado - Produto: {}, Quantidade antiga: {}, Nova quantidade: {}",
                        produto.getNome(), quantidadeAntiga, item.getQuantidade());
            } else {
                // Criar novo item
                ItemCarrinho novoItem = new ItemCarrinho(carrinho, produto, quantidade, produto.getPreco());
                carrinho.getItens().add(novoItem);

                logger.info("Novo item adicionado - Produto: {}, Quantidade: {}, Preço: {}",
                        produto.getNome(), quantidade, produto.getPreco());
            }

            // Recalcular valor total
            BigDecimal valorAntigo = carrinho.getValorTotal();
            carrinho.recalcularValorTotal();

            Carrinho carrinhoSalvo = carrinhoRepository.save(carrinho);

            logger.info("Item adicionado com sucesso - Cliente: {}, Valor antigo: {}, Valor novo: {}",
                    clienteId, valorAntigo, carrinhoSalvo.getValorTotal());

            return carrinhoSalvo;

        } catch (RuntimeException e) {
            // Captura exceções específicas de regra de negócio
            logger.error("Erro de negócio ao adicionar item - Cliente: {}, Produto: {}, Erro: {}",
                    clienteId, produtoId, e.getMessage());
            throw e; // Re-lança para o controller tratar

        } catch (Exception e) {
            // Captura qualquer outra exceção inesperada
            logger.error("Erro inesperado ao adicionar item - Cliente: {}, Produto: {}, Erro: {}",
                    clienteId, produtoId, e.getMessage(), e);
            throw new RuntimeException("Erro interno no sistema. Contate o suporte.");
        }
    }

    @Transactional
    public Carrinho atualizarQuantidadeItem(Long clienteId, Long itemId, Integer novaQuantidade) {
        logger.info("Atualizando quantidade - Cliente: {}, Item: {}, Nova quantidade: {}",
                clienteId, itemId, novaQuantidade);

        try {
            Carrinho carrinho = carrinhoRepository.findByClienteIdWithItens(clienteId)
                    .orElseThrow(() -> {
                        logger.error("Carrinho não encontrado para cliente: {}", clienteId);
                        return new RuntimeException("Carrinho não encontrado");
                    });

            ItemCarrinho item = carrinho.getItens().stream()
                    .filter(i -> i.getId().equals(itemId))
                    .findFirst()
                    .orElseThrow(() -> {
                        logger.error("Item {} não encontrado no carrinho do cliente {}", itemId, clienteId);
                        return new RuntimeException("Item não encontrado no carrinho");
                    });

            logger.debug("Item encontrado: {} (Quantidade atual: {})",
                    item.getProduto().getNome(), item.getQuantidade());

            // Verificar estoque
            if (item.getProduto().getEstoque() < novaQuantidade) {
                logger.warn("Estoque insuficiente para atualização - Produto: {}, Estoque: {}, Solicitado: {}",
                        item.getProduto().getNome(), item.getProduto().getEstoque(), novaQuantidade);
                throw new RuntimeException("Estoque insuficiente para o produto: " + item.getProduto().getNome());
            }

            int quantidadeAntiga = item.getQuantidade();
            item.setQuantidade(novaQuantidade);

            BigDecimal valorAntigo = carrinho.getValorTotal();
            carrinho.recalcularValorTotal();

            Carrinho carrinhoAtualizado = carrinhoRepository.save(carrinho);

            logger.info("Quantidade atualizada - Produto: {}, De: {}, Para: {}, Valor antigo: {}, Valor novo: {}",
                    item.getProduto().getNome(), quantidadeAntiga, novaQuantidade,
                    valorAntigo, carrinhoAtualizado.getValorTotal());

            return carrinhoAtualizado;

        } catch (RuntimeException e) {
            logger.error("Erro de negócio ao atualizar quantidade - Cliente: {}, Item: {}, Erro: {}",
                    clienteId, itemId, e.getMessage());
            throw e;

        } catch (Exception e) {
            logger.error("Erro inesperado ao atualizar quantidade - Cliente: {}, Item: {}, Erro: {}",
                    clienteId, itemId, e.getMessage(), e);
            throw new RuntimeException("Erro interno no sistema. Contate o suporte.");
        }
    }

    @Transactional
    public Carrinho removerItem(Long clienteId, Long itemId) {
        logger.info("Removendo item - Cliente: {}, Item: {}", clienteId, itemId);

        try {
            Carrinho carrinho = carrinhoRepository.findByClienteIdWithItens(clienteId)
                    .orElseThrow(() -> {
                        logger.error("Carrinho não encontrado para cliente: {}", clienteId);
                        return new RuntimeException("Carrinho não encontrado");
                    });

            int tamanhoAntes = carrinho.getItens().size();
            BigDecimal valorAntigo = carrinho.getValorTotal();

            boolean removed = carrinho.getItens().removeIf(item -> {
                if (item.getId().equals(itemId)) {
                    logger.debug("Removendo item: {}", item.getProduto().getNome());
                    return true;
                }
                return false;
            });

            if (!removed) {
                logger.warn("Tentativa de remover item não existente: {} do cliente {}", itemId, clienteId);
                throw new RuntimeException("Item não encontrado no carrinho");
            }

            carrinho.recalcularValorTotal();

            Carrinho carrinhoAtualizado = carrinhoRepository.save(carrinho);

            logger.info("Item removido com sucesso - Cliente: {}, Itens antes: {}, Itens depois: {}, Valor antigo: {}, Valor novo: {}",
                    clienteId, tamanhoAntes, carrinhoAtualizado.getItens().size(),
                    valorAntigo, carrinhoAtualizado.getValorTotal());

            return carrinhoAtualizado;

        } catch (RuntimeException e) {
            logger.error("Erro de negócio ao remover item - Cliente: {}, Item: {}, Erro: {}",
                    clienteId, itemId, e.getMessage());
            throw e;

        } catch (Exception e) {
            logger.error("Erro inesperado ao remover item - Cliente: {}, Item: {}, Erro: {}",
                    clienteId, itemId, e.getMessage(), e);
            throw new RuntimeException("Erro interno no sistema. Contate o suporte.");
        }
    }

    @Transactional
    public void limparCarrinho(Long clienteId) {
        Carrinho carrinho = carrinhoRepository.findByClienteIdWithItens(clienteId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        carrinho.getItens().clear();
        carrinho.setValorTotal(BigDecimal.ZERO);

        carrinhoRepository.save(carrinho);
    }

    private Carrinho criarNovoCarrinho(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Carrinho novoCarrinho = new Carrinho(cliente);
        return carrinhoRepository.save(novoCarrinho);
    }

}