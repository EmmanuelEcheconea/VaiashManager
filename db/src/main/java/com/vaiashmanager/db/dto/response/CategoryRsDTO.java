package com.vaiashmanager.db.dto.response;


import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class CategoryRsDTO {
    private Long id;
    private String nombreCategoria;
}
