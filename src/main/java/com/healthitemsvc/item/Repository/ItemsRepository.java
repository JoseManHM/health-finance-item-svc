package com.healthitemsvc.item.Repository;

import com.healthitemsvc.item.Entities.Items;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Integer>{
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO item(cantidad, id_categoria, id_cuenta, comentarios, id_usuario, ingreso_egreso) VALUES (:cantidad, :categoria, :cuenta, :comentarios, :usuario, :ingresoegreso)", nativeQuery = true)
    void saveItem(@Param("cantidad") float cantidad, @Param("categoria") int categoria, @Param("cuenta") int cuenta, @Param("comentarios") String comentarios, @Param("usuario") int usuario, @Param("ingresoegreso") int ingresoegreso);
}
