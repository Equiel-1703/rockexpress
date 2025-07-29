package com.example.demo.service;

import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

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
}
