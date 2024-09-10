package com.healthitemsvc.item.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "categoria")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categorias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private int ingreso_egreso;
    private String icono;
    private String color;
    @OneToOne
    @JoinColumn(name = "id")
    private Usuarios id_usuario;
    private int activo;
}
