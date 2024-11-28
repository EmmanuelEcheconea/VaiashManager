package com.vaiashmanager.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "detalle_venta")
public class SaleDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_venta")
    @NotBlank(message = "El id de venta no puede estar vacio")
    private Long idVenta;
    @Column(name = "id_producto")
    @NotBlank(message = "El id producto no puede estar vacio")
    private Long idProducto;
    private Integer cantidad;
    @Column(name = "precio_unitario")
    private Double precioUnitario;
    @Column(name = "suma_total")
    private Double sumaTotal;
}
