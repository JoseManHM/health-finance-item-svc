package com.healthitemsvc.item.Repository;

import com.healthitemsvc.item.Entities.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Integer>{
    @Query(value = "SELECT NOW()", nativeQuery = true)
    Instant getCurrentTimestamp();

    @Query(value = "SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM usuarios a WHERE id = :id AND activo = 0", nativeQuery = true)
    boolean existsUser(@Param("id") int id);
}
