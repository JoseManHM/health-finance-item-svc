package com.healthitemsvc.item.Repository;

import com.healthitemsvc.item.Entities.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Integer>{
}
