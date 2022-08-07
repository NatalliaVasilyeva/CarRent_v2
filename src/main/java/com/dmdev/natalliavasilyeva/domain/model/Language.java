package com.dmdev.natalliavasilyeva.domain.model;

public enum Language {

    EN_US("en_US"),

    RU_RU("ru_RU");

    Language(String locale) {
        this.locale = locale;
    }

    String locale;

    public String getLocale() {
        return locale;
    }

    @Override
    public String toString() {
        return this.getLocale();
    }

    public static Language getEnum(String value) {
        for(Language v : values())
            if(v.getLocale().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}