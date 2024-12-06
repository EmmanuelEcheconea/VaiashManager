package com.vaiashmanager.db.dto.response;

import com.vaiashmanager.db.entity.Client;
import com.vaiashmanager.db.enums.CartState;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartRsDTO {
    private Long id;
    private Client client;
    private Date fechaCreacion;
    private String state;
}
