package com.healthitemsvc.item.Services;

import com.healthitemsvc.item.DTO.AddItemDataDTO;
import com.healthitemsvc.item.DTO.ResponseBasicDTO;
import com.healthitemsvc.item.DTO.UpdateItemDataDTO;
import org.springframework.stereotype.Service;

@Service
public interface ItemService {
    String pruebaConexion();
    ResponseBasicDTO agregarItem(AddItemDataDTO itemData);
    ResponseBasicDTO modificarItem(UpdateItemDataDTO itemData);
}
