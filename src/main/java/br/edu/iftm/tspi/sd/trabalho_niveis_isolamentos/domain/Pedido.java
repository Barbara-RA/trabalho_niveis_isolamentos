package br.edu.iftm.tspi.sd.trabalho_niveis_isolamentos.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
public class Pedido implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pedidoId;

    @Column(nullable = false)
    private String clienteId;

    @Column(nullable = false)
    private LocalDateTime dataPedido = LocalDateTime.now();
}
