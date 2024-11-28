package com.vaiashmanager.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "venta")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_carro")
    @NotBlank(message = "El id del carro no puede estar vacio")
    private Long idCarro;
    @Column(name = "fecha_venta")
    @NotBlank(message = "La fecha de venta no puede estar vacio")
    private Date fechaVenta;
    @Column(name = "total_venta")
    @NotBlank(message = "El total no puede estar vacio")
    private Double totalVenta;
    @NotBlank(message = "El estado no puede estar vacio")
    private boolean estado;
}
