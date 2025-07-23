package com.example.demo.repository;

//import com.example.demo.model.Pedido;
import com.example.demo.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
