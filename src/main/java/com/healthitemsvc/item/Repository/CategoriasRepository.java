package com.healthitemsvc.item.Repository;

import com.healthitemsvc.item.Entities.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriasRepository extends JpaRepository<Categorias, Integer>{
    @Query(value = "SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM categoria a WHERE id = :id AND id_usuario = :usuario AND activo = 0", nativeQuery = true)
    boolean existsCategory(@Param("id") int id, @Param("usuario") int usuario);
}
