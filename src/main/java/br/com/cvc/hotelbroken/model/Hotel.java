package br.com.cvc.hotelbroken.model;

import lombok.Data;

import java.util.List;

@Data
public class Hotel {
    private int id;
    private String name;
    private int cityCode;
    private String cityName;
    private List<Room> rooms;
}
