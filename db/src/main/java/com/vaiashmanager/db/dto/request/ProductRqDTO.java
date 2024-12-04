package com.vaiashmanager.db.dto.request;

import com.vaiashmanager.db.entity.Category;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRqDTO {
    private Long id;
    private String nombre;
    private String description;
    private double precio;
    private int cantidadStock;
    private Category categoria;
}
