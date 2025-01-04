package com.labrob9.springlab9.repo;

import com.labrob9.springlab9.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId AND " +
            "(b.startDate <= :endDate AND b.endDate >= :startDate)")
    List<Booking> findBookings(@Param("roomId") Long roomId,
                                          @Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);
    List<Booking> findByRoomId(Long roomId);


}
