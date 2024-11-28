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
@Table(name = "carro")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_cliente")
    @NotBlank(message = "El id no puede estar vacio")
    private Long idCliente;
    @Column(name = "fecha_creacion")
    @NotBlank(message = "La fecha no puede estar vacia")
    private String fechaCreacion;
    @NotBlank(message = "El estado no puede estar vacio")
    private Boolean estado;
}
