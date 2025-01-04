package com.labrob9.springlab9.service;

import com.labrob9.springlab9.model.Hotel;
import com.labrob9.springlab9.model.Room;
import com.labrob9.springlab9.repo.HotelRepository;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final BookingService bookingService;

    public HotelService(HotelRepository hotelRepository, BookingService bookingService) {
        this.hotelRepository = hotelRepository;
        this.bookingService = bookingService;
    }

    public List<Hotel> searchHotels(String searchQuery, LocalDate startDate, LocalDate endDate, int rating) {
        List<Hotel> hotels = hotelRepository.findHotelsByCriteria(searchQuery, rating);
        if(startDate == null || endDate == null) {
            return hotels;
        }else{
            List <Hotel> availableHotels = new ArrayList<>();
            for (Hotel hotel : hotels) {
                List<Room> rooms = hotel.getRooms();
                for (Room room : rooms) {
                    if(bookingService.isRoomAvailable(room.getId(), startDate, endDate)) {
                        availableHotels.add(hotel);
                        break;
                    }
                }
            }

            return availableHotels;
        }

    }
}
