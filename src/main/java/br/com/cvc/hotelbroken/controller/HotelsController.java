package br.com.cvc.hotelbroken.controller;

import br.com.cvc.hotelbroken.model.HotelResponse;
import br.com.cvc.hotelbroken.service.CalculationHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO;

@RestController
public class HotelsController {
    @Autowired
    private CalculationHotelService calculationHotelService;


    @GetMapping(value="/hotels", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<HotelResponse> getHotels(@RequestParam(name="cityCode", required = true) int cityCode,
                                 @RequestParam(name="checkIn", required = true) @DateTimeFormat(iso = ISO.DATE) LocalDate checkIn,
                                 @RequestParam(name="checkOut", required= true) @DateTimeFormat(iso = ISO.DATE) LocalDate checkOut,
                                 @RequestParam(name="quantityAdult", required = true) int quantityAdult,
                                 @RequestParam(name="quantityChild", required = true) int quantityChild) {

        return calculationHotelService.getHotelsCalculatedPeriod(cityCode, checkIn, checkOut, quantityAdult, quantityChild);
    }

    @GetMapping(value="/hotel/{hotelId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<HotelResponse> getHotel(@PathVariable(name="hotelId", required = true) int hotelId,
                                        @RequestParam(name="checkIn", required = true) @DateTimeFormat(iso = ISO.DATE) LocalDate checkIn,
                                        @RequestParam(name="checkOut", required= true) @DateTimeFormat(iso = ISO.DATE) LocalDate checkOut,
                                        @RequestParam(name="quantityAdult", required = true) int quantityAdult,
                                        @RequestParam(name="quantityChild", required = true) int quantityChild){

        return calculationHotelService.getHotelCalculatedPeriod(hotelId, checkIn, checkOut, quantityAdult, quantityChild);
    }
}
