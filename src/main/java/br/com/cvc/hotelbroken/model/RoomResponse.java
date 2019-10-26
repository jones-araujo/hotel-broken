package br.com.cvc.hotelbroken.model;

import lombok.Data;

@Data
public class RoomResponse {
    private int roomID;
    private String categoryName;
    private double totalPrice;
    private PriceDetailResponse priceDetail;
}
