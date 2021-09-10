package com.tevore.ordersystem.services;

public enum Promotion {

    BOGO("BOGO"),
    B2GO("B2GO");

    private String name;

    Promotion(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
