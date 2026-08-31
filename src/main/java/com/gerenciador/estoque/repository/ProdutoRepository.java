package com.gerenciador.estoque.repository;

import com.gerenciador.estoque.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByNomeContainingIgnoreCase(String nome);

    List<Produto> findByCategoriaId(Long categoriaId);

    List<Produto> findByFornecedorId(Long fornecedorId);

    List<Produto> findByQuantidadeEstoqueLessThan(Integer quantidade);

    List<Produto> findAllByOrderByNomeAsc();

    @Query("SELECT p FROM Produto p WHERE p IS NOT NULL")
    List<Produto> findByDataValidadeAfter(LocalDate date);

    @Query("SELECT p FROM Produto p WHERE TYPE(p) = :tipo")
    List<Produto> findByTipo(@Param("tipo") Class<? extends Produto> tipo);
}