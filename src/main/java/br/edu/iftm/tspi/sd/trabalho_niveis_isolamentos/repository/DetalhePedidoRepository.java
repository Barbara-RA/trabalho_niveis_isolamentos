package br.edu.iftm.tspi.sd.trabalho_niveis_isolamentos.repository;


import br.edu.iftm.tspi.sd.trabalho_niveis_isolamentos.domain.DetalhePedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalhePedidoRepository extends JpaRepository<DetalhePedido, Integer> {
}
