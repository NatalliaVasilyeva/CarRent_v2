package com.dmdev.natalliavasilyeva.domain.model;

public enum EngineType {
    FUEL("fuel"),
    ELECTRIC("electric"),
    GAS("gas"),
    DIESEL("diesel");

    EngineType(String engineType) {
        this.engineType = engineType;
    }

    String engineType;

    public String getEngineType() {
        return engineType;
    }

    @Override
    public String toString() {
        return this.getEngineType();
    }

    public static EngineType getEnum(String value) {
        for(EngineType v : values())
            if(v.getEngineType().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}