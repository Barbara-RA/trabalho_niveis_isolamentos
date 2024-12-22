package br.edu.iftm.tspi.sd.trabalho_niveis_isolamentos.service;

import br.edu.iftm.tspi.sd.trabalho_niveis_isolamentos.domain.DetalhePedido;
import br.edu.iftm.tspi.sd.trabalho_niveis_isolamentos.domain.Pedido;
import br.edu.iftm.tspi.sd.trabalho_niveis_isolamentos.domain.Produto;
import br.edu.iftm.tspi.sd.trabalho_niveis_isolamentos.repository.DetalhePedidoRepository;
import br.edu.iftm.tspi.sd.trabalho_niveis_isolamentos.repository.PedidoRepository;
import br.edu.iftm.tspi.sd.trabalho_niveis_isolamentos.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final DetalhePedidoRepository detalhePedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, DetalhePedidoRepository detalhePedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.detalhePedidoRepository = detalhePedidoRepository;
    }

    @Transactional
    public void processarPedidoSemLock(Integer produtoId, Integer quantidade, String clienteId) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        if (produto.getUnidadesEmEstoque() < quantidade) {
            throw new IllegalStateException("Estoque insuficiente");
        }

        produto.setUnidadesEmEstoque(produto.getUnidadesEmEstoque() - quantidade);
        produtoRepository.save(produto);

        Pedido pedido = new Pedido();
        pedido.setClienteId(clienteId);
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        DetalhePedido detalhe = new DetalhePedido();
        detalhe.setPedido(pedidoSalvo);
        detalhe.setProduto(produto);
        detalhe.setPrecoVenda(produto.getPreco());
        detalhe.setQuantidade(quantidade);
        detalhePedidoRepository.save(detalhe);
    }

    @Transactional
    public void processarPedidoComLockOtimista(Integer produtoId, Integer quantidade, String clienteId) {
        // Implementação semelhante à acima, mas com controle de lock otimista.
    }

    @Transactional
    public void processarPedidoComLockPessimista(Integer produtoId, Integer quantidade, String clienteId) {
        // Implementação semelhante à acima, mas com controle de lock pessimista.
    }
}
