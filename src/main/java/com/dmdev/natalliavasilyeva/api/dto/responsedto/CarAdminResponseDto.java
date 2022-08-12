package com.dmdev.natalliavasilyeva.api.dto.responsedto;


import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class CarAdminResponseDto extends CarUserResponseDto {

    private String number;
    private String vin;
    private boolean isRepaired;
    private List<AccidentResponseDto> accidents;

    private List<OrderResponseDto> orders;

    public CarAdminResponseDto(Long id, String brand, String model, String transmission, String engineType, String color, String yearOfProduction, String image, BigDecimal pricePerDay, String number, String vin, boolean isRepaired, List<AccidentResponseDto> accidents, List<OrderResponseDto> orders) {

        super(id, brand, model, transmission, engineType, color, yearOfProduction, image, pricePerDay);
        this.number = number;
        this.vin = vin;
        this.isRepaired = isRepaired;
        this.accidents = accidents;
        this.orders = orders;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getVin() {
        return vin;
    }
    public void setVin(String vin) {
        this.vin = vin;
    }
    public boolean isRepaired() {
        return isRepaired;
    }
    public void setRepaired(boolean repaired) {
        isRepaired = repaired;
    }
    public List<AccidentResponseDto> getAccidents() {
        return accidents;
    }
    public void setAccidents(List<AccidentResponseDto> accidents) {
        this.accidents = accidents;
    }
    public List<OrderResponseDto> getOrders() {
        return orders;
    }
    public void setOrders(List<OrderResponseDto> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CarAdminResponseDto that = (CarAdminResponseDto) o;
        return isRepaired == that.isRepaired && Objects.equals(number, that.number) && Objects.equals(vin, that.vin) && Objects.equals(accidents, that.accidents) && Objects.equals(orders, that.orders);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), number, vin, isRepaired, accidents, orders);
    }

    @Override
    public String toString() {
        return "CarAdminResponseDto{" +
                "number='" + number + '\'' +
                ", vin='" + vin + '\'' +
                ", isRepaired=" + isRepaired +
                ", accidents=" + accidents +
                ", orders=" + orders +
                '}';
    }
}