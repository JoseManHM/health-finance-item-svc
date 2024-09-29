package com.healthitemsvc.item.Controllers;

import com.healthitemsvc.item.DTO.AddItemDataDTO;
import com.healthitemsvc.item.DTO.ApiResponseDTO;
import com.healthitemsvc.item.DTO.ResponseBasicDTO;
import com.healthitemsvc.item.DTO.UpdateItemDataDTO;
import com.healthitemsvc.item.Services.ItemService;
import com.healthitemsvc.item.Util.Meta;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
