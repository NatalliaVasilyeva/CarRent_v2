package com.dmdev.natalliavasilyeva.utils;

public final class RegularExpressionHolder {

    public RegularExpressionHolder() {
    }

    public static final String ID_REGEX = "[1-9]\\d{0,18}";
    public static final String USER_ROLE_REGEX = "(ADMIN|CLIENT)";
    public static final String USER_LOGIN_REGEX = "[a-zA-Z0-9._-]{4,45}";
    public static final String ADDRESS_REGEX = "[a-zA-Z0-9а-яА-ЯёЁ\\s/,_:;.'\"-]+";
    public static final String PHONE_REGEX = "(\\+?(375|80)?\\s?)?\\(?(17|29|33|44|25)\\)?\\s?(\\d{3})[-|\\s]?(\\d{2})[-|\\s]?(\\d{2})";
    public static final String USER_PASSWORD_REGEX = "(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[A-Za-z0-9!@#$%^&*]{6,45}";
    public static final String USER_EMAIL_REGEX = "[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}";
    public static final String USER_PASSPORT_REGEX = "^(?!^0+$)[a-zA-Z0-9]{3,20}$\n";
    public static final String DATE_TIME_PATTERN_REGEX = "yyyy-MM-dd'T'HH:mm";
    public static final String BIG_DECIMAL_REGEX = "\\d{1,20}(.\\d{2})?";
    public static final String DESCRIPTION_REGEX = "[a-zA-Z0-9а-яА-ЯёЁ\\s/,_:;.'\"\\-]+";
    public static final String EMPTY_STRING_REGEX = "";
    public static final String NAME_STRING_REGEX = "[a-zA-Zа-яА-ЯёЁ\\s]+";
    public static final String BRAND_NAME_REGEX = "[a-zA-Z0-9а-яА-ЯёЁ\\s\\-]+";
    public static final String MODEL_TRANSMISSION_REGEX = "(MANUAL|AUTOMATIC|ROBOT)";
    public static final String MODEL_ENGINE_REGEX = "(FUEL|ELECTRIC|GAS|DIESEL)";
    public static final String MODEL_COLOR_REGEX = "(RED|BLUE|WHITE|BLACK|GREEN|YELLOW)";
    public static final String MODEL_YEAR_REGEX = "^(\\d{4})$";
    public static final String CAR_VIN_REGEX = "[\\w*]{20}";
    public static final String CAR_NUMBER_REGEX = "[\\d]{4}[A-Z]{2}-[\\d]{1}";
    public static final String BOOLEAN_REGEX = "(TRUE|FALSE)";
    public static final String IMG_REG_EX = "[\\w]+\\.((jpg)|(png))";
    public static final String ORDER_STATUS_REGEX = "(CONFIRMATION_WAIT|DECLINED|PAYED|NOT_PAYED|CANCELLED)";
}