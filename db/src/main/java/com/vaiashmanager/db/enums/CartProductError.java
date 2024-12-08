package com.vaiashmanager.db.enums;

public enum CartProductError {
    NOT_FOUND("Carro producto no encontrado", 404),
    PRODUCT_EMPTY("El Carro producto esta vacio. no podemos crearlo.", 400),
    OUT_OF_STOCK("No hay mas producto de este tipo. No podemos agregarlo al carro", 400),
    NO_OPERATION("Debes indicar una operacion", 405);

    private final String message;
    private final int status;

    CartProductError(String message, int status) {
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
