package com.evaluacion.productosapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "productos")
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @NonNull
    private String nombre;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    @NonNull
    private BigDecimal precio;

    @Min(value = 0, message = "La cantidad disponible no puede ser negativa")
    @NonNull
    private Integer cantidadDisponible;

    @Size(max = 255, message = "La descripción no puede exceder 255 caracteres")
    @NonNull
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @NonNull
    private Categoria categoria;

}
