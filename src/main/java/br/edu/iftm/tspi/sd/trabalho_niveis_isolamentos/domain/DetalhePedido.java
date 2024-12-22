package br.edu.iftm.tspi.sd.trabalho_niveis_isolamentos.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Entity
@Table(name = "detalhes_pedido")
@Getter
@Setter
@NoArgsConstructor
public class DetalhePedido implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer detalheId;

    @ManyToOne
    @JoinColumn(name = "pedidoId", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produtoId", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private Double precoVenda;

    @Column(nullable = false)
    private Integer quantidade;

    @Column
    private Double desconto = 0.0;
}
