package com.healthitemsvc.item.Services.Impl;

import com.healthitemsvc.item.DTO.AddItemDataDTO;
import com.healthitemsvc.item.DTO.GetMontoIngresoEgresoOriginalDataProjection;
import com.healthitemsvc.item.DTO.ResponseBasicDTO;
import com.healthitemsvc.item.DTO.UpdateItemDataDTO;
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

    @Transactional
    @Override
    public ResponseBasicDTO modificarItem(UpdateItemDataDTO itemData){
        ResponseBasicDTO response = new ResponseBasicDTO();
        try{
            if(categoriasRepository.existsCategory(itemData.getIdCategoria(), itemData.getIdUsuario())){
                if(cuentasRepository.existsAccount(itemData.getIdCuenta(), itemData.getIdUsuario())){
                    if(itemsRepository.existItemActive(itemData.getId(), itemData.getIdUsuario())){
                        //Obtener info original de item
                        GetMontoIngresoEgresoOriginalDataProjection dataOriginal = itemsRepository.obtenerMontoIngEgrOriginal(itemData.getId(), itemData.getIdUsuario());
                        //Regresar monto a estado anterior a la transaccion
                        if(dataOriginal.getIngresoEgreso() == 0){
                            cuentasRepository.sumarCantidadCuenta(dataOriginal.getMonto(), dataOriginal.getIdCuenta(), itemData.getIdUsuario());
                        }else{
                            cuentasRepository.restarCantidadCuenta(dataOriginal.getMonto(), dataOriginal.getIdCuenta(), itemData.getIdUsuario());
                        }
                        //Realizar actualización y resta/suma a cuenta
                        if(itemData.getIngresoegreso() == 0){
                            cuentasRepository.restarCantidadCuenta(itemData.getCantidad(), itemData.getIdCuenta(), itemData.getIdUsuario());
                        }else{
                            cuentasRepository.sumarCantidadCuenta(itemData.getCantidad(), itemData.getIdCuenta(), itemData.getIdUsuario());
                        }
                        itemsRepository.updateItem(itemData.getCantidad(), itemData.getIdCategoria(), itemData.getIdCuenta(), itemData.getComentarios(), itemData.getIngresoegreso(), itemData.getId(), itemData.getIdUsuario());
                        response.setStatus(1);
                        response.setMensaje("EL item se ha actualizado correctamente");
                    }else{
                        response.setStatus(0);
                        response.setMensaje("El item que se quiere actualizar no existe");
                    }
                }else{
                    response.setStatus(0);
                    response.setMensaje("La cuenta asociada al item no existe o se encuentra inactiva");
                }
            }else{
                response.setStatus(0);
                response.setMensaje("La categoria asociada al item no existe o se encuentra inactiva y/o el usuario no existe");
            }
        }catch (Exception e){
            String mensajeError = "Ocurrio un error al modificar el item, error: " + e.getMessage();
            System.out.println(mensajeError);
            log.error(mensajeError);
            response.setStatus(-1);
            response.setMensaje(mensajeError);
        }
        return response;
    }

    @Transactional
    @Override
    public ResponseBasicDTO eliminarItem(int id, int idUsuario){
        ResponseBasicDTO response = new ResponseBasicDTO();
        try{
            if(itemsRepository.existItemActive(id, idUsuario)){
                //Obtener info del item para regresar estado de cuenta original
                GetMontoIngresoEgresoOriginalDataProjection itemOriginal = itemsRepository.obtenerMontoIngEgrOriginal(id, idUsuario);
                if(itemOriginal.getIngresoEgreso() == 0){
                    cuentasRepository.sumarCantidadCuenta(itemOriginal.getMonto(), id, idUsuario);
                }else{
                    cuentasRepository.restarCantidadCuenta(itemOriginal.getMonto(), id, idUsuario);
                }
                itemsRepository.deleteItem(id, idUsuario);
                response.setStatus(1);
                response.setMensaje("El item se ha eliminado correctamente");
            }else{
                response.setStatus(0);
                response.setMensaje("El item que se quiere eliminar no existe y/o el usuario no fue encontrado");
            }
        }catch (Exception e){
            String errorMsg = "Ocurrio un error al intentar eliminar el item, error: " + e.getMessage();
            System.out.println(errorMsg);
            log.error(errorMsg);
            response.setStatus(-1);
            response.setMensaje(errorMsg);
        }
        return response;
    }
}
