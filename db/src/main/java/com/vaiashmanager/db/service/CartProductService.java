package com.vaiashmanager.db.service;

import com.vaiashmanager.db.dto.request.CartProductRqDTO;
import com.vaiashmanager.db.dto.response.CartProductRsDTO;

public interface CartProductService {
    public void createCartProduct(final CartProductRqDTO cartProductRqDTO);
}
