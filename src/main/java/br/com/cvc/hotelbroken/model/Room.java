package br.com.cvc.hotelbroken.model;

import lombok.Data;

@Data
public class Room {
    private int roomID;
    private String categoryName;
    private Price price;
}
