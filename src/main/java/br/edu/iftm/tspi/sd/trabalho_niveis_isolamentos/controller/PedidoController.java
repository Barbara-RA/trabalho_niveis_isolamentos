package br.edu.iftm.tspi.sd.trabalho_niveis_isolamentos.controller;

import br.edu.iftm.tspi.sd.trabalho_niveis_isolamentos.dto.PedidoRequest;
import br.edu.iftm.tspi.sd.trabalho_niveis_isolamentos.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/novo")
    public ResponseEntity<String> novoPedidoSemLock(@RequestBody PedidoRequest pedidoRequest) {
        pedidoService.processarPedidoSemLock(
                pedidoRequest.getProdutoId(),
                pedidoRequest.getQuantidade(),
                pedidoRequest.getClienteId()
        );
        return ResponseEntity.status(HttpStatus.OK).body("Pedido realizado com sucesso (sem lock).");
    }

    @PostMapping("/otimista/novo")
    public ResponseEntity<String> novoPedidoComLockOtimista(@RequestBody PedidoRequest pedidoRequest) {
        pedidoService.processarPedidoComLockOtimista(
                pedidoRequest.getProdutoId(),
                pedidoRequest.getQuantidade(),
                pedidoRequest.getClienteId()
        );
        return ResponseEntity.status(HttpStatus.OK).body("Pedido realizado com sucesso (lock otimista).");
    }

    @PostMapping("/pessimista/novo")
    public ResponseEntity<String> novoPedidoComLockPessimista(@RequestBody PedidoRequest pedidoRequest) {
        pedidoService.processarPedidoComLockPessimista(
                pedidoRequest.getProdutoId(),
                pedidoRequest.getQuantidade(),
                pedidoRequest.getClienteId()
        );
        return ResponseEntity.status(HttpStatus.OK).body("Pedido realizado com sucesso (lock pessimista).");
    }
}
