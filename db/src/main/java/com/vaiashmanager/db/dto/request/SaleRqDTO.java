package com.vaiashmanager.db.dto.request;

import com.vaiashmanager.db.entity.Cart;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class SaleRqDTO {
    private Long idCart;
}
