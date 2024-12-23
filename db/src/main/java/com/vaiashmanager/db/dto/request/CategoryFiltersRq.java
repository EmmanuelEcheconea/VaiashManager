package com.vaiashmanager.db.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CategoryFiltersRq {
    private Long id;
    private String nombreCategoria;
}
