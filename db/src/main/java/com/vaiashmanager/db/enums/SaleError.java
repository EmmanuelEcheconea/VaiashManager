package com.vaiashmanager.db.enums;

public enum SaleError {
    NOT_FOUND("Venta no encontrada", 404),
    PRODUCT_EMPTY("No enviaste ninguna Venta. no podemos crearlo.", 400);



    private final String message;
    private final int status;


    SaleError(String message, int status) {
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
