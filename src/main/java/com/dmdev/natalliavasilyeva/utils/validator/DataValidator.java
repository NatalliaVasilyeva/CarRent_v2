package com.dmdev.natalliavasilyeva.utils.validator;

import com.dmdev.natalliavasilyeva.utils.RegularExpressionHolder;
import com.dmdev.natalliavasilyeva.utils.VariablesNameHolder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;


public final class DataValidator {

    private static final DataValidator INSTANCE = new DataValidator();
    public static DataValidator getInstance() {
        return INSTANCE;
    }
    private DataValidator() {
    }

    private static final Map<String, Supplier<String>> variableRegexMap = new HashMap<>();

    static {
        variableRegexMap.put(VariablesNameHolder.ORDER_ID, () -> RegularExpressionHolder.ID_REGEX);
        variableRegexMap.put(VariablesNameHolder.BRAND_ID, () -> RegularExpressionHolder.ID_REGEX);
        variableRegexMap.put(VariablesNameHolder.MODEL_ID, () -> RegularExpressionHolder.ID_REGEX);
        variableRegexMap.put(VariablesNameHolder.CAR_ID, () -> RegularExpressionHolder.ID_REGEX);
        variableRegexMap.put(VariablesNameHolder.CATEGORY_ID, () -> RegularExpressionHolder.ID_REGEX);
        variableRegexMap.put(VariablesNameHolder.USER_ID, () -> RegularExpressionHolder.ID_REGEX);
        variableRegexMap.put(VariablesNameHolder.PRICE_ID, () -> RegularExpressionHolder.ID_REGEX);
        variableRegexMap.put(VariablesNameHolder.ACCIDENT_DATE, () -> RegularExpressionHolder.DATE_TIME_PATTERN_REGEX);
        variableRegexMap.put(VariablesNameHolder.START_RENTAL_DATE, () -> RegularExpressionHolder.DATE_TIME_PATTERN_REGEX);
        variableRegexMap.put(VariablesNameHolder.END_RENTAL_DATE, () -> RegularExpressionHolder.DATE_TIME_PATTERN_REGEX);
        variableRegexMap.put(VariablesNameHolder.DRIVER_LICENSE_ISSUE_DATE, () -> RegularExpressionHolder.DATE_PATTERN_REGEX);
        variableRegexMap.put(VariablesNameHolder.DRIVER_LICENSE_EXPIRED_DATE, () -> RegularExpressionHolder.DATE_PATTERN_REGEX);
        variableRegexMap.put(VariablesNameHolder.BIRTHDAY, () -> RegularExpressionHolder.DATE_PATTERN_REGEX);
        variableRegexMap.put(VariablesNameHolder.BRAND_NAME, () -> RegularExpressionHolder.BRAND_NAME_REGEX);
        variableRegexMap.put(VariablesNameHolder.MODEL_NAME, () -> RegularExpressionHolder.MODEL_NAME_REGEX);
        variableRegexMap.put(VariablesNameHolder.CATEGORY_NAME, () -> RegularExpressionHolder.NAME_STRING_REGEX);
        variableRegexMap.put(VariablesNameHolder.USER_NAME, () -> RegularExpressionHolder.NAME_STRING_REGEX);
        variableRegexMap.put(VariablesNameHolder.SURNAME, () -> RegularExpressionHolder.NAME_STRING_REGEX);
        variableRegexMap.put(VariablesNameHolder.DESCRIPTION, () -> RegularExpressionHolder.DESCRIPTION_REGEX);
        variableRegexMap.put(VariablesNameHolder.DAMAGE, () -> RegularExpressionHolder.BIG_DECIMAL_REGEX);
        variableRegexMap.put(VariablesNameHolder.SUM, () -> RegularExpressionHolder.BIG_DECIMAL_REGEX);
        variableRegexMap.put(VariablesNameHolder.PRICE, () -> RegularExpressionHolder.BIG_DECIMAL_REGEX);
        variableRegexMap.put(VariablesNameHolder.TRANSMISSION, () -> RegularExpressionHolder.MODEL_TRANSMISSION_REGEX);
        variableRegexMap.put(VariablesNameHolder.ENGINE_TYPE, () -> RegularExpressionHolder.MODEL_ENGINE_REGEX);
        variableRegexMap.put(VariablesNameHolder.COLOR, () -> RegularExpressionHolder.MODEL_COLOR_REGEX);
        variableRegexMap.put(VariablesNameHolder.YEAR, () -> RegularExpressionHolder.MODEL_YEAR_REGEX);
        variableRegexMap.put(VariablesNameHolder.CAR_NUMBER, () -> RegularExpressionHolder.CAR_NUMBER_REGEX);
        variableRegexMap.put(VariablesNameHolder.VIN, () -> RegularExpressionHolder.CAR_VIN_REGEX);
        variableRegexMap.put(VariablesNameHolder.IS_REPAIRED, () -> RegularExpressionHolder.BOOLEAN_REGEX);
        variableRegexMap.put(VariablesNameHolder.IS_INSURANCE_NEEDED, () -> RegularExpressionHolder.BOOLEAN_REGEX);
        variableRegexMap.put(VariablesNameHolder.IMAGE, () -> RegularExpressionHolder.IMG_REG_EX);
        variableRegexMap.put(VariablesNameHolder.PASSPORT, () -> RegularExpressionHolder.USER_PASSPORT_REGEX);
        variableRegexMap.put(VariablesNameHolder.DRIVER_LICENSE_NUMBER, () -> RegularExpressionHolder.USER_PASSPORT_REGEX);
        variableRegexMap.put(VariablesNameHolder.ORDER_STATUS, () -> RegularExpressionHolder.ORDER_STATUS_REGEX);
        variableRegexMap.put(VariablesNameHolder.EMAIL, () -> RegularExpressionHolder.USER_EMAIL_REGEX);
        variableRegexMap.put(VariablesNameHolder.LOGIN, () -> RegularExpressionHolder.USER_LOGIN_REGEX);
        variableRegexMap.put(VariablesNameHolder.PASSWORD, () -> RegularExpressionHolder.USER_PASSWORD_REGEX);
        variableRegexMap.put(VariablesNameHolder.ADDRESS, () -> RegularExpressionHolder.ADDRESS_REGEX);
        variableRegexMap.put(VariablesNameHolder.PHONE, () -> RegularExpressionHolder.PHONE_REGEX);
        variableRegexMap.put(VariablesNameHolder.ROLE, () -> RegularExpressionHolder.USER_ROLE_REGEX);
        variableRegexMap.put("", () -> RegularExpressionHolder.EMPTY_STRING_REGEX);
    }

    private Set<String> canBeNullVariable = Set.of(VariablesNameHolder.PRICE_ID, VariablesNameHolder.DESCRIPTION, VariablesNameHolder.DAMAGE,
            VariablesNameHolder.TRANSMISSION, VariablesNameHolder.ENGINE_TYPE, VariablesNameHolder.COLOR, VariablesNameHolder.YEAR, VariablesNameHolder.CAR_NUMBER,
            VariablesNameHolder.VIN, VariablesNameHolder.IS_REPAIRED, VariablesNameHolder.IMAGE, VariablesNameHolder.ORDER_STATUS, VariablesNameHolder.ROLE, VariablesNameHolder.DRIVER_LICENSE_NUMBER,
            VariablesNameHolder.DRIVER_LICENSE_ISSUE_DATE, VariablesNameHolder.DRIVER_LICENSE_EXPIRED_DATE);

    public boolean isValidData(Map<String, String> inData) {
        for (Map.Entry<String, String> entry : inData.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue();
            var result = isValidData(key, value);
            if (!result) {
                return false;
            }
        }
        return true;
    }

    public boolean isValidData(String key, String value) {
        if (value == null || value.isEmpty() || value.isBlank() || value.equals("")) {
            return canBeNullVariable.contains(key);
        }
        var pattern = definePattern(key);
        return isValid(pattern, value);
    }

    private boolean isValid(String pattern, String expression) {
        return expression.matches(pattern);
    }

    private String definePattern(String type) {
        if (!variableRegexMap.containsKey(type)) {
            throw new IllegalArgumentException(String.format("%s is not supported", type));
        }
        return variableRegexMap.get(type).get();
    }

    public boolean isValidDates(LocalDateTime start, LocalDateTime end) {
        if(start == null && end == null) {
            return true;
        }
        return start.isBefore(end) && start.isAfter(LocalDateTime.now().minusMinutes(60));
    }
}