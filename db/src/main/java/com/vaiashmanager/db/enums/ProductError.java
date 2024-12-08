package com.vaiashmanager.db.enums;

import org.springframework.http.HttpStatus;

import java.util.Date;

public enum ProductError {
    NOT_FOUND("Producto no encontrado", 404),
    PRODUCT_EMPTY("No enviaste ningun producto. no podemos crearlo.", 400),
    PRODUCT_EXIST("Ya existe un producto con ese nombre", 400),
    SIN_STOCK("No hay mas stock de este producto, lo sentimos mucho", 400);



    private final String message;
    private final int status;


    ProductError(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

}
