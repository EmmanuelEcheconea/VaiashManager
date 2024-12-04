package com.vaiashmanager.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @OneToOne
    @JoinColumn(name = "id_carro",referencedColumnName = "id")
    private Cart cart;
    @Column(name = "fecha_venta")

    @NotNull(message = "La fecha de venta no puede estar vacio")
    private Date fechaVenta;
    @Column(name = "total_venta")
    @Min(0)
    private Double totalVenta;
    private boolean estado;
}
