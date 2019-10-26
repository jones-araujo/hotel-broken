package br.com.cvc.hotelbroken.service;

import br.com.cvc.hotelbroken.model.HotelResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface CalculationHotelService {
    Flux<HotelResponse> getHotelsCalculatedPeriod(int cityCode, LocalDate checkIn, LocalDate checkout, int quantityAdult, int quantityChild);
    Mono<HotelResponse> getHotelCalculatedPeriod(int hotelId, LocalDate checkIn, LocalDate checkout, int quantityAdult, int quantityChild);
}
