package com.healthitemsvc.item.Services.Impl;

import com.healthitemsvc.item.DTO.AddItemDataDTO;
import com.healthitemsvc.item.DTO.ResponseBasicDTO;
import com.healthitemsvc.item.Repository.CategoriasRepository;
import com.healthitemsvc.item.Repository.CuentasRepository;
import com.healthitemsvc.item.Repository.ItemsRepository;
import com.healthitemsvc.item.Repository.UsuariosRepository;
import com.healthitemsvc.item.Services.ItemService;
import jakarta.transaction.Transactional;
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
    @Autowired
    ItemsRepository itemsRepository;

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

    @Transactional
    @Override
    public ResponseBasicDTO agregarItem(AddItemDataDTO itemData){
        ResponseBasicDTO response = new ResponseBasicDTO();
        try{
            if(categoriasRepository.existsCategory(itemData.getIdCategoria(), itemData.getIdUsuario())){
                if(cuentasRepository.existsAccount(itemData.getId_cuenta(), itemData.getIdUsuario())){
                    if(usuariosRepository.existsById(itemData.getIdUsuario())){
                        //Egreso
                        if(itemData.getIngresoEgreso() == 0){
                            cuentasRepository.restarCantidadCuenta(itemData.getCantidad(), itemData.getId_cuenta(), itemData.getIdUsuario());
                            itemsRepository.saveItem(itemData.getCantidad(), itemData.getIdCategoria(), itemData.getId_cuenta(), itemData.getComentarios(), itemData.getIdUsuario(), itemData.getIngresoEgreso());
                            response.setStatus(1);
                            response.setMensaje("Item de egreso agreado correctamente");
                        }else{//Ingreso
                            cuentasRepository.sumarCantidadCuenta(itemData.getCantidad(), itemData.getId_cuenta(), itemData.getIdUsuario());
                            itemsRepository.saveItem(itemData.getCantidad(), itemData.getIdCategoria(), itemData.getId_cuenta(), itemData.getComentarios(), itemData.getIdUsuario(), itemData.getIngresoEgreso());
                            response.setStatus(1);
                            response.setMensaje("Item de ingreso agreado correctamente");
                        }
                    }else{
                        response.setStatus(0);
                        response.setMensaje("El usuario asociado al item no existe");
                    }
                }else{
                    response.setStatus(0);
                    response.setMensaje("La cuenta asociada al item no existe");
                }
            }else{
                response.setStatus(0);
                response.setMensaje("La categoria asociada al item no existe");
            }
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
