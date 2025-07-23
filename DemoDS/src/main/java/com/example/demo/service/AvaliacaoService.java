package com.example.demo.service;

import com.example.demo.model.Avaliacao;
import com.example.demo.repository.AvaliacaoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AvaliacaoService {
    private final AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
    }

    public List<Avaliacao> listarTodos() {
        return avaliacaoRepository.findAll();
    }

    public Avaliacao salvar(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }

    public Avaliacao buscarPorId(Long id) {
        return avaliacaoRepository.findById(id).orElse(null);
    }

    public void deletar(Long id) {
        avaliacaoRepository.deleteById(id);
    }
}
