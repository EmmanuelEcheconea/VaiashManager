package com.vaiashmanager.db.entity;

import com.vaiashmanager.db.enums.SaleState;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "venta")
@Builder
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
    private String estado;

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", cart=" + cart +
                ", cart id=" + cart.getId() +
                ", fechaVenta=" + fechaVenta +
                ", totalVenta=" + totalVenta +
                ", estado=" + estado +
                '}';
    }
}
