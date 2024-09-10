package com.healthitemsvc.item.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity(name = "item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @PositiveOrZero
    private float cantidad;
    @OneToOne
    @JoinColumn(name = "id")
    private Categorias categoria_id;
    @OneToOne
    @JoinColumn(name = "id")
    private Cuentas cuenta_id;
    @ColumnDefault("")
    private String comentarios;
    @ColumnDefault("0")
    private int activo;
    @OneToOne
    @JoinColumn(name = "id")
    private Usuarios usuario_id;
}
