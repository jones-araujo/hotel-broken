package br.com.cvc.hotelbroken.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceDetailResponse {
    private double pricePerDayAdult;
    private double pricePerDayChild;
}
