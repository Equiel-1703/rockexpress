package com.example.demo.service;

import com.example.demo.model.Produto;
import com.example.demo.model.Vendedor;
import com.example.demo.repository.ProdutoRepository;
import com.example.demo.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.dto.ProdutoCreateDTO;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private VendedorRepository vendedorRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }


    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }
    
    public List<Produto> buscarPorNomeComOrdenacao(String nome, String sort) {
        Sort ordenacao = Sort.unsorted();

        if ("preco".equalsIgnoreCase(sort)) {
            ordenacao = Sort.by("preco");
        } else if ("nome".equalsIgnoreCase(sort)) {
            ordenacao = Sort.by("nome");
        }

        return produtoRepository.findByNomeContainingIgnoreCase(nome, ordenacao);
    }

    public List<Produto> listarOrdenado(String sort) {
        Sort ordenacao = Sort.unsorted();

        if ("preco".equalsIgnoreCase(sort)) {
            ordenacao = Sort.by("preco");
        } else if ("nome".equalsIgnoreCase(sort)) {
            ordenacao = Sort.by("nome");
        }

        return produtoRepository.findAll(ordenacao);
    }

    // Listar N produtos do banco (não importa o vendedor). Retornar lista vazia se não houver produtos.
    public List<Produto> listarNProdutos(int n) {
        return produtoRepository.findAll(Sort.by("id")).stream().limit(n).toList();
    }
    
    // Listar todos os produtos de um vendedor específico
    public List<Produto> listarPorVendedor(Long vendedorId) {
        return produtoRepository.findByVendedorId(vendedorId);
    }
    
    // Adicionar um produto ao catálogo de um vendedor
    @Transactional
    public Produto adicionarProduto(Long vendedorId, ProdutoCreateDTO produtoDTO) {
        // 1. Buscar apenas o vendedor
        Vendedor vendedor = vendedorRepository.findById(vendedorId)
                .orElseThrow(() -> new RuntimeException("Vendedor não encontrado!"));

        // 2. Criar a nova entidade Produto
        Produto novoProduto = new Produto();

        // 3. Mapear os dados do DTO para a Entidade
        novoProduto.setNome(produtoDTO.getNome());
        novoProduto.setDescricao(produtoDTO.getDescricao());
        novoProduto.setPreco(produtoDTO.getPreco());
        novoProduto.setEstoque(produtoDTO.getEstoque());
        novoProduto.setAtivo(produtoDTO.getAtivo());
        novoProduto.setImagemBase64(produtoDTO.getImagemBase64());

        // 4. Associar o vendedor
        novoProduto.setVendedor(vendedor);

        // 5. Deixar a categoria como nula (ou removê-la da entidade)
        novoProduto.setCategoria(null);

        // 6. Salvar no banco
        return produtoRepository.save(novoProduto);
    }

}
