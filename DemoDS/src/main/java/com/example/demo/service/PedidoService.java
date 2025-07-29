package com.example.demo.service;

import com.example.demo.enums.EnumStatusPedido;
import com.example.demo.model.Pedido;
import com.example.demo.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido salvar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void deletar(Long id) {
        pedidoRepository.deleteById(id);
    }
    
    // Buscar pedidos por cliente
    public List<Pedido> listarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }
    
    public Pedido atualizarStatus(Long id, String status) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedido.setStatus(status);
        return pedidoRepository.save(pedido);
    }

    // Calcular valor total do pedido
    public Double calcularTotal(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        return pedido.getItens().stream()
                .mapToDouble(item -> item.getPreco() * item.getQuantidade())
                .sum();
    }

    // Cancelar pedido
    public Pedido cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedido.setStatus("CANCELADO");
        return pedidoRepository.save(pedido);
    }
    
    public List<Pedido> filtrarPedidos(EnumStatusPedido status, LocalDate dataInicio, LocalDate dataFim) {
        if (status != null && dataInicio != null && dataFim != null) {
            return pedidoRepository.findByStatusAndDataInicioBetween(status, dataInicio, dataFim);
        } else if (status != null) {
            return pedidoRepository.findByStatus(status);
        } else if (dataInicio != null && dataFim != null) {
            return pedidoRepository.findByDataInicioBetween(dataInicio, dataFim);
        } else {
            return pedidoRepository.findAll();
        }
    }
}
