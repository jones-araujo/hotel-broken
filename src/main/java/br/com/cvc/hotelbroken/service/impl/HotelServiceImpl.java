package br.com.cvc.hotelbroken.service.impl;

import br.com.cvc.hotelbroken.model.Hotel;
import br.com.cvc.hotelbroken.service.HotelService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HotelServiceImpl implements HotelService {
    private final String BASE_URL = "https://cvcbackendhotel.herokuapp.com";
    private final String HOTELS_CITY_ENDPOINT = "/hotels/avail/";
    private final String HOTEL_ENDPOINT = "/hotels/";

    private WebClient webClient = WebClient.builder()
            .baseUrl(BASE_URL)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    @Override
    public Flux<Hotel> getHotelsByCity(int cityId) {
        return webClient.get()
                .uri(HOTELS_CITY_ENDPOINT + cityId)
                .retrieve()
                .bodyToFlux(Hotel.class);
    }

    @Override
    public Mono<Hotel> getHotelById(int hotelId) {
        return webClient.get()
                .uri(HOTEL_ENDPOINT + hotelId)
                .retrieve()
                .bodyToFlux(Hotel.class).single();
    }

}
