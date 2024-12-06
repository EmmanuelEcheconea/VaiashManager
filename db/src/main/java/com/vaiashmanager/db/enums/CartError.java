package com.vaiashmanager.db.enums;

public enum CartError {
    NOT_FOUND("Carro no encontrado", 404),
    PRODUCT_EMPTY("El Carro esta vacio. no podemos crearlo.", 400);

    private final String message;
    private final int status;

    CartError(String message, int status) {
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
