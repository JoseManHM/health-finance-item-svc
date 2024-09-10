package com.healthitemsvc.item.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity(name = "cuenta")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cuentas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @NotEmpty
    private String nombre;
    @NotNull
    @ColumnDefault("")
    private String icono;
    @NotNull
    private float cantidad;
    @OneToOne
    @JoinColumn(name = "id")
    private Usuarios id_usuario;
    @NotNull
    @ColumnDefault("0")
    private int activo;
}
