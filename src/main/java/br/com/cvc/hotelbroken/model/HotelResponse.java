package br.com.cvc.hotelbroken.model;

import lombok.Data;

import java.util.List;

@Data
public class HotelResponse {
    private int id;
    private String cityName;
    private List<RoomResponse> rooms;
}
