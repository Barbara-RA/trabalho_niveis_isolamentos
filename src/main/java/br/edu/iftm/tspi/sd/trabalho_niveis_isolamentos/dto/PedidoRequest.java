package br.edu.iftm.tspi.sd.trabalho_niveis_isolamentos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoRequest {
    private Integer produtoId;
    private Integer quantidade;
    private String clienteId;
}

