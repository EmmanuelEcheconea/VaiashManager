package com.vaiashmanager.db.enums;

public enum CategoryError {
    NOT_FOUND("Categoria no encontrada", 404),
    PRODUCT_EMPTY("No enviaste ninguna categoria. no podemos crearlo.", 400);
    private final String message;
    private final int status;


    CategoryError(String message, int status) {
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
