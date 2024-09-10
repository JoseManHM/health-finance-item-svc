package com.healthitemsvc.item.Repository;

import com.healthitemsvc.item.Entities.Cuentas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentasRepository extends JpaRepository<Cuentas, Integer>{
}
