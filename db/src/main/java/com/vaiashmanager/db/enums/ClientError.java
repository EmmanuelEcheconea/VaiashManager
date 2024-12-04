package com.vaiashmanager.db.enums;

public enum ClientError {
    NOT_FOUND("Cliente no encontrado", 404),
    PRODUCT_EMPTY("No enviaste ningun cliente. no podemos crearlo.", 400);
    private final String message;
    private final int status;


    ClientError(String message, int status) {
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
