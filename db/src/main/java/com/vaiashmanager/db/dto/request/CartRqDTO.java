package com.vaiashmanager.db.dto.request;

import com.vaiashmanager.db.enums.CartState;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartRqDTO {
    private String state;
}
