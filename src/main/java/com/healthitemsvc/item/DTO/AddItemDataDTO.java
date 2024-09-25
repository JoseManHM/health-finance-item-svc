package com.healthitemsvc.item.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddItemDataDTO {
    @JsonProperty("cantidad")
    @NotNull
    @PositiveOrZero
    private float cantidad;
    @JsonProperty("id_categoria")
    @NotNull
    @PositiveOrZero
    private int idCategoria;
    @JsonProperty("id_cuenta")
    @NotNull
    @PositiveOrZero
    private int id_cuenta;
    @JsonProperty("comentarios")
    @NotNull
    private String comentarios;
    @JsonProperty("id_usuario")
    @NotNull
    @PositiveOrZero
    private int idUsuario;
    @JsonProperty("ingreso_egreso")
    @NotNull
    @PositiveOrZero
    private int IngresoEgreso;
}
