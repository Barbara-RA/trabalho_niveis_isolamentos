package br.edu.iftm.tspi.sd.trabalho_niveis_isolamentos.repository;


import br.edu.iftm.tspi.sd.trabalho_niveis_isolamentos.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
