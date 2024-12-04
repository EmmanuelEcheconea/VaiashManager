package com.vaiashmanager.db.dto.response;

import com.vaiashmanager.db.entity.Category;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRsDTO {
    private String nombre;
    private String description;
    private Long id;
    private double precio;
    private int cantidadStock;
    private Category categoria;
}
