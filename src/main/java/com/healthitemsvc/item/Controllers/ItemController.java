package com.healthitemsvc.item.Controllers;

import com.healthitemsvc.item.DTO.*;
import com.healthitemsvc.item.Services.ItemService;
import com.healthitemsvc.item.Util.Meta;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    ItemService itemService;

    Meta metaOk = new Meta(UUID.randomUUID().toString(), "SUCCESS", 200);
    Meta metaNotFound = new Meta(UUID.randomUUID().toString(), "DATA NOT FOUND", 404);
    Meta metaServerError = new Meta(UUID.randomUUID().toString(), "INTERNAL SERVER ERROR", 500);

    @GetMapping("/test")
    public ApiResponseDTO testService(){
        return new ApiResponseDTO(metaOk, itemService.pruebaConexion());
    }

    @PostMapping("/operation")
    public ApiResponseDTO agregarItem(@RequestBody @Valid AddItemDataDTO itemData){
        ResponseBasicDTO responseAddItem = itemService.agregarItem(itemData);
        if(responseAddItem.getStatus() == 1){
            return new ApiResponseDTO(metaOk, responseAddItem.getMensaje());
        }else if(responseAddItem.getStatus() == 0){
            return new ApiResponseDTO(metaNotFound, responseAddItem.getMensaje());
        }else{
            return new ApiResponseDTO(metaServerError, responseAddItem.getMensaje());
        }
    }

    @PutMapping("/operation")
    public ApiResponseDTO modificarItem(@RequestBody @Valid UpdateItemDataDTO itemData){
        ResponseBasicDTO responseUpdateItem = itemService.modificarItem(itemData);
        if(responseUpdateItem.getStatus() == 1){
            return new ApiResponseDTO(metaOk, responseUpdateItem.getMensaje());
        }else if(responseUpdateItem.getStatus() == 0){
            return new ApiResponseDTO(metaNotFound, responseUpdateItem.getMensaje());
        }else{
            return new ApiResponseDTO(metaServerError, responseUpdateItem.getMensaje());
        }
    }

    @DeleteMapping("/operation/{id}/{id_usuario}")
    public ApiResponseDTO eliminarItem(@PathVariable(name = "id") int id, @PathVariable(name = "id_usuario") int idUsuario){
        ResponseBasicDTO responseDeleteItem = itemService.eliminarItem(id, idUsuario);
        if(responseDeleteItem.getStatus() == 1){
            return new ApiResponseDTO(metaOk, responseDeleteItem.getMensaje());
        }else if(responseDeleteItem.getStatus() == 0){
            return new ApiResponseDTO(metaNotFound, responseDeleteItem.getMensaje());
        }else{
            return new ApiResponseDTO(metaServerError, responseDeleteItem.getMensaje());
        }
    }

    @GetMapping("/operation/single/{id}/{usuario}")
    public ApiResponseDTO obtenerDetalleItemSingle(@PathVariable(name = "id") int id, @PathVariable(name = "usuario") int usuario){
        try{
            List<GetItemDetailDataProjection> infoItem = itemService.obtenerDetalleItemSingle(id, usuario);
            if(!infoItem.isEmpty()){
                return new ApiResponseDTO(metaOk, infoItem);
            }else{
                return new ApiResponseDTO(metaNotFound, "No se encontraron datos");
            }
        }catch (Exception e){
            return new ApiResponseDTO(metaServerError, "Ocurrio un error al obtener el detalle del item: " + e.getMessage());
        }
    }

    @GetMapping("/operation/many/{usuario}/{tipo}/{fechaOrigen}")
    public ApiResponseDTO obtenerDetalleItemsMany(@PathVariable(name = "usuario") int usuario, @PathVariable("tipo") int tipo, @PathVariable(name = "fechaOrigen") String fechaOrigen){
        try{
            List<GetItemDetailDataProjection> infoItems = itemService.obtenerDetalleItemsMany(usuario, tipo, fechaOrigen);
            if(!infoItems.isEmpty()){
                return new ApiResponseDTO(metaOk, infoItems);
            }else{
                return new ApiResponseDTO(metaNotFound, "No se encontraron datos");
            }
        }catch (Exception e){
            return new ApiResponseDTO(metaServerError, "Ocurrio un error al obtener la lista de items: " + e.getMessage());
        }
    }
}
