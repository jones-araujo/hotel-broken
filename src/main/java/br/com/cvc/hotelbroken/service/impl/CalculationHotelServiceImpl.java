package br.com.cvc.hotelbroken.service.impl;

import br.com.cvc.hotelbroken.model.Hotel;
import br.com.cvc.hotelbroken.model.HotelResponse;
import br.com.cvc.hotelbroken.model.PriceDetailResponse;
import br.com.cvc.hotelbroken.model.RoomResponse;
import br.com.cvc.hotelbroken.service.CalculationHotelService;
import br.com.cvc.hotelbroken.service.HotelService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class CalculationHotelServiceImpl implements CalculationHotelService {
    private final double COMMISSION = 0.7;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Flux<HotelResponse> getHotelsCalculatedPeriod(int cityCode, LocalDate checkIn, LocalDate checkout, int quantityAdult, int quantityChild) {
        Flux<Hotel> hotels = hotelService.getHotelsByCity(cityCode);

        long days = DAYS.between(checkIn, checkout);

        return hotels.map(hotel -> calculatePrices(hotel, days, quantityAdult, quantityChild));
    }

    @Override
    public Mono<HotelResponse> getHotelCalculatedPeriod(int hotelId, LocalDate checkIn, LocalDate checkout, int quantityAdult, int quantityChild) {
        Mono<Hotel> hotelApi = hotelService.getHotelById(hotelId);

        long days = DAYS.between(checkIn, checkout);

        return hotelApi.map(hotel -> calculatePrices(hotel, days, quantityAdult, quantityChild));
    }

    private HotelResponse calculatePrices(Hotel hotel, long days, int quantityAdult, int quantityChild) {
        HotelResponse hotelResponse = convertToHotelResponse(hotel);

        hotel.getRooms().forEach(room -> {
            Optional<RoomResponse> roomResponse = hotelResponse.getRooms().stream()
                    .filter(roomResp -> roomResp.getRoomID() == room.getRoomID())
                    .findFirst();

            roomResponse.ifPresent(roomResp -> {
                roomResp.setPriceDetail(new PriceDetailResponse(calculateCommission(room.getPrice().getAdult() * quantityAdult),
                        calculateCommission(room.getPrice().getChild() * quantityChild)));
                roomResp.setTotalPrice(calculateTotalPrice(days,
                        roomResp.getPriceDetail().getPricePerDayAdult(),
                        roomResp.getPriceDetail().getPricePerDayChild()));
            });
        });

        return hotelResponse;
    }

    private double calculateTotalPrice(long days, double priceAdult, double priceChild) {
        double totalPriceAdult = (days * priceAdult);
        double totalPriceChild = (days * priceChild);

        return new BigDecimal(totalPriceAdult + totalPriceChild).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private double calculateCommission(double value) {
        double total = value / COMMISSION;
        return new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private HotelResponse convertToHotelResponse(Hotel hotel) {
        HotelResponse hotelResponse = modelMapper.map(hotel, HotelResponse.class);

        return hotelResponse;
    }
}
