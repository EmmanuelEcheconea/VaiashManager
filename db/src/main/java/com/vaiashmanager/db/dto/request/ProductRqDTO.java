package com.vaiashmanager.db.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vaiashmanager.db.entity.Category;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRqDTO {
    @NotNull
    private String nombre;
    private String description;
    private double precio;
    private int cantidadStock;
    private Long idCategoria;
}
