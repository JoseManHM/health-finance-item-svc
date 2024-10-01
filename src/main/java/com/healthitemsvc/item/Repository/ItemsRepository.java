package com.healthitemsvc.item.Repository;

import com.healthitemsvc.item.DTO.GetItemDetailDataProjection;
import com.healthitemsvc.item.DTO.GetMontoIngresoEgresoOriginalDataProjection;
import com.healthitemsvc.item.Entities.Items;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Integer>{
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO item(cantidad, id_categoria, id_cuenta, comentarios, id_usuario, ingreso_egreso) VALUES (:cantidad, :categoria, :cuenta, :comentarios, :usuario, :ingresoegreso)", nativeQuery = true)
    void saveItem(@Param("cantidad") float cantidad, @Param("categoria") int categoria, @Param("cuenta") int cuenta, @Param("comentarios") String comentarios, @Param("usuario") int usuario, @Param("ingresoegreso") int ingresoegreso);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE item SET cantidad = :cantidad, id_categoria = :categoria, id_cuenta = :cuenta, comentarios = :comentarios, ingreso_egreso = :ingresoegreso WHERE id = :id AND id_usuario = :usuario AND activo = 0", nativeQuery = true)
    void updateItem(@Param("cantidad") float cantidad, @Param("categoria") int categoria, @Param("cuenta") int cuenta, @Param("comentarios") String comentarios, @Param("ingresoegreso") int ingresoegreso, @Param("id") int id, @Param("usuario") int usuario);

    @Query(value = "SELECT cantidad AS monto, ingreso_egreso, id_cuenta FROM item WHERE id = :id AND id_usuario = :usuario AND activo = 0 LIMIT 1", nativeQuery = true)
    GetMontoIngresoEgresoOriginalDataProjection obtenerMontoIngEgrOriginal(@Param("id") int id, @Param("usuario") int usuario);

    @Query(value = "SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END FROM item i WHERE id = :id AND id_usuario = :usuario AND activo = 0", nativeQuery = true)
    boolean existItemActive(@Param("id") int id, @Param("usuario") int usuario);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE item SET activo = 1 WHERE id = :id AND id_usuario = :usuario AND activo = 0", nativeQuery = true)
    void deleteItem(@Param("id") int id, @Param("usuario") int usuario);

    @Query(value = "SELECT i.id, i.cantidad, i.comentarios, i.id_categoria, c.nombre AS categoria, i.id_cuenta, cu.nombre AS cuenta, i.fecha_registro FROM item i INNER JOIN categoria c ON i.id_categoria = c.id INNER JOIN cuenta cu ON i.id_cuenta = cu.id WHERE i.id = :id AND i.activo = 0 AND i.id_usuario = :usuario LIMIT 1", nativeQuery = true)
    List<GetItemDetailDataProjection> obtenerItemDetailSIngle(@Param("id") int id, @Param("usuario") int usuario);

    @Query(value = "SELECT i.id, i.cantidad, i.comentarios, i.id_categoria, c.nombre AS categoria, i.id_cuenta, cu.nombre AS cuenta, i.fecha_registro FROM item i INNER JOIN categoria c ON i.id_categoria = c.id INNER JOIN cuenta cu ON i.id_cuenta = cu.id WHERE i.activo = 0 AND i.id_usuario = :usuario AND i.ingreso_egreso = :tipo AND i.fecha_registro BETWEEN CAST(:fechaOrigen as timestamp) AND now() ORDER BY fecha_registro DESC", nativeQuery = true)
    List<GetItemDetailDataProjection> obtenerItemsDetailMany(@Param("usuario") int usuario, @Param("tipo") int tipo, @Param("fechaOrigen") String fechaOrigen);
}
