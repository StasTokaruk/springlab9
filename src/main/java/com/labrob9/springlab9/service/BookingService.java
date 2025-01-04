package com.labrob9.springlab9.service;

import com.labrob9.springlab9.model.Booking;
import com.labrob9.springlab9.model.Room;
import com.labrob9.springlab9.repo.BookingRepository;
import com.labrob9.springlab9.repo.RoomRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public BookingService(BookingRepository bookingRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    public boolean isRoomAvailable(Long roomId, LocalDate startDate, LocalDate endDate) {
        List<Booking> Bookings = bookingRepository.findBookings(roomId, startDate, endDate);
        return Bookings.isEmpty();
    }

    public Booking createBooking(Long id_room, LocalDate startDate, LocalDate endDate) {
        if (!isRoomAvailable(id_room, startDate, endDate)) {
            throw new IllegalArgumentException("Кімната не доступна в межах цих дат.");
        }

        Optional<Room> roomOptional = roomRepository.findById(id_room);
        Room room = roomOptional.orElseThrow(() -> new IllegalArgumentException("Кімнату з Id " + id_room + " не знайдено."));

        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        booking.setPaid(false);
        booking.setCreatedAt(LocalDateTime.now());
        bookingRepository.save(booking);

        return booking;
    }

    public List<Booking> getBookingsForRoomId(Long roomId) {
        return bookingRepository.findByRoomId(roomId);
    }

    public void deleteBookingId(Long bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            throw new IllegalArgumentException("<Бронювання з ID " + bookingId + " не знайдено.");
        }
        bookingRepository.deleteById(bookingId);
    }

    //Кожні 10хв видаляє не оплачені бронювання
    @Scheduled(fixedRate = 60000)
    public void removeUnpaidBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        for (Booking booking : bookings) {
            if (!booking.getPaid() && booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now())) {
                bookingRepository.delete(booking);
                System.out.println("Deleted unpaid booking with ID: " + booking.getId());
            }
        }
    }
}