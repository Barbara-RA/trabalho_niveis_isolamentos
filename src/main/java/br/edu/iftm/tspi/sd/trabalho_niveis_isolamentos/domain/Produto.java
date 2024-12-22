package br.edu.iftm.tspi.sd.trabalho_niveis_isolamentos.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@NoArgsConstructor
public class Produto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer produtoId;

    @Column(nullable = false)
    private String produtoNome;

    @Column(nullable = false)
    private Double preco;

    @Column(nullable = false)
    private Integer unidadesEmEstoque;

    @Version // Para suporte a lock otimista
    @Column(name = "versao")
    private Integer versao;
}