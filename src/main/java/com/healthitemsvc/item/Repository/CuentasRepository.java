package com.healthitemsvc.item.Repository;

import com.healthitemsvc.item.Entities.Cuentas;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentasRepository extends JpaRepository<Cuentas, Integer>{
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE cuenta SET cantidad = cantidad + :monto WHERE id = :id AND id_usuario = :usuario", nativeQuery = true)
    void sumarCantidadCuenta(@Param("monto") float monto, @Param("id") int id, @Param("usuario") int usuario);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE cuenta SET cantidad = cantidad - :monto WHERE id = :id AND id_usuario = :usuario", nativeQuery = true)
    void restarCantidadCuenta(@Param("monto") float monto, @Param("id") int id, @Param("usuario") int usuario);

    @Query(value = "SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM cuenta a WHERE id = :id AND id_usuario = :usuario AND activo = 0", nativeQuery = true)
    boolean existsAccount(@Param("id") int id, @Param("usuario") int usuario);
}
