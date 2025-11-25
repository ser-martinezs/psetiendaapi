package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Integer>{

    @Modifying
    @Query(value = "DELETE FROM producto_categorias pc WHERE pc.id_categoria = :id_categoria", nativeQuery = true)
    void deleteProductoCategoriaRelations(@Param("id_categoria") Integer id_categoria);
}
