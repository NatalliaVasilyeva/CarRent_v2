package com.dmdev.natalliavasilyeva.domain.model;

public enum Transmission {
    MANUAL("manual"),
    AUTOMATIC("automatic"),
    ROBOT("robot");

    Transmission(String transmission) {
        this.transmission = transmission;
    }

    String transmission;

    public String getTransmission() {
        return transmission;
    }

    @Override
    public String toString() {
        return this.getTransmission();
    }

    public static Transmission getEnum(String value) {
        for (Transmission v : values())
            if (v.getTransmission().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}