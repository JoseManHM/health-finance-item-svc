package com.healthitemsvc.item.Services.Impl;

import com.healthitemsvc.item.DTO.AddItemDataDTO;
import com.healthitemsvc.item.DTO.ResponseBasicDTO;
import com.healthitemsvc.item.Repository.CategoriasRepository;
import com.healthitemsvc.item.Repository.CuentasRepository;
import com.healthitemsvc.item.Repository.UsuariosRepository;
import com.healthitemsvc.item.Services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ItemServiceImpl implements ItemService {
    private static final Logger log = LoggerFactory.getLogger(ItemService.class);
    @Autowired
    UsuariosRepository usuariosRepository;
    @Autowired
    CategoriasRepository categoriasRepository;
    @Autowired
    CuentasRepository cuentasRepository;

    @Override
    public String pruebaConexion(){
        String respuesta = null;
        try{
            respuesta = usuariosRepository.getCurrentTimestamp().toString();
        }catch (Exception e){
            String error = "Ocurrio un error: " + e.getMessage();
            log.error(error);
            System.out.println(error);
        }
        return respuesta;
    }

    @Override
    public ResponseBasicDTO agregarItem(AddItemDataDTO itemData){
        ResponseBasicDTO response = new ResponseBasicDTO();
        try{

        }catch (Exception e){
            String error = "Ocurrio un error al guardar el item: " + e.getMessage();
            System.out.println(error);
            log.error(error);
            response.setStatus(-1);
            response.setMensaje(error);
        }
        return response;
    }
}
