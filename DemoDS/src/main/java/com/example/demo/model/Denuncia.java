package com.example.demo.model;

import com.example.demo.enums.StatusDenuncia;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // permite herança para tipos específicos
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente; // quem fez a denúncia

    @Column(nullable = false, length = 2000)
    private String motivo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusDenuncia status; // PENDENTE, APROVADA, REJEITADA

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataDenuncia;

    // Construtor vazio exigido pelo JPA
    public Denuncia() {}

    // Construtor auxiliar
    public Denuncia(Cliente cliente, String motivo, StatusDenuncia status) {
        this.cliente = cliente;
        this.motivo = motivo;
        this.status = status;
    }

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public StatusDenuncia getStatus() { return status; }
    public void setStatus(StatusDenuncia status) { this.status = status; }

    public LocalDateTime getDataDenuncia() { return dataDenuncia; }
}
