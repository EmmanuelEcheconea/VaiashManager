package com.vaiashmanager.db.entity;

import com.vaiashmanager.db.enums.CartState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@Table(name = "carro")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_client",referencedColumnName = "id")
    private Client client;
    @Column(name = "fecha_creacion")
    private Timestamp fechaCreacion;
    private String state;
}
