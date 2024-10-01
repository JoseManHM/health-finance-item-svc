package com.healthitemsvc.item.Services;

import com.healthitemsvc.item.DTO.AddItemDataDTO;
import com.healthitemsvc.item.DTO.GetItemDetailDataProjection;
import com.healthitemsvc.item.DTO.ResponseBasicDTO;
import com.healthitemsvc.item.DTO.UpdateItemDataDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemService {
    String pruebaConexion();
    ResponseBasicDTO agregarItem(AddItemDataDTO itemData);
    ResponseBasicDTO modificarItem(UpdateItemDataDTO itemData);
    ResponseBasicDTO eliminarItem(int id, int idUsuario);
    List<GetItemDetailDataProjection> obtenerDetalleItemSingle(int id, int id_usuario);

    List<GetItemDetailDataProjection> obtenerDetalleItemsMany(int idUsuario, int tipo, String fechaOrigen);
}
