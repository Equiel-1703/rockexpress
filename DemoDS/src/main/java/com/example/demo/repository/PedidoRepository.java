package com.example.demo.repository;

import com.example.demo.enums.EnumStatusPedido;
//import com.example.demo.model.Pedido;
import com.example.demo.model.Pedido;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	// Buscar pedidos de um cliente
	List<Pedido> findByClienteId(Long clienteId);

	// Buscar pedidos por status
	 List<Pedido> findByStatus(EnumStatusPedido status);
	 List<Pedido> findByDataInicioBetween(LocalDate dataInicio, LocalDate dataFim);
	 List<Pedido> findByStatusAndDataInicioBetween(EnumStatusPedido status, LocalDate dataInicio, LocalDate dataFim);
}


