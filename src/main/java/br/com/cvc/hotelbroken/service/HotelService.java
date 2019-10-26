package br.com.cvc.hotelbroken.service;

import br.com.cvc.hotelbroken.model.Hotel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HotelService {
    Flux<Hotel> getHotelsByCity(int cityId);
    Mono<Hotel> getHotelById(int hotelId);
}
