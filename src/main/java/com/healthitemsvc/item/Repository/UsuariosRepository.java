package com.healthitemsvc.item.Repository;

import com.healthitemsvc.item.Entities.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Integer>{
    @Query(value = "SELECT NOW()", nativeQuery = true)
    Instant getCurrentTimestamp();
}
