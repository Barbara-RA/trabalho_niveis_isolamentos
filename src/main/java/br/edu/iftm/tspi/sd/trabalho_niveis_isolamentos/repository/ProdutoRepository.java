package br.edu.iftm.tspi.sd.trabalho_niveis_isolamentos.repository;


import br.edu.iftm.tspi.sd.trabalho_niveis_isolamentos.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    @Modifying
    @Query("UPDATE Produto p SET p.unidadesEmEstoque = p.unidadesEmEstoque - :quantidade WHERE p.produtoId = :id AND p.unidadesEmEstoque >= :quantidade")
    int debitarEstoque(@Param("id") Integer id, @Param("quantidade") Integer quantidade);
}
