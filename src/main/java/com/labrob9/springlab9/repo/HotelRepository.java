package com.labrob9.springlab9.repo;

import com.labrob9.springlab9.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("SELECT h FROM Hotel h WHERE " +
            "LOWER(h.location) LIKE LOWER(CONCAT('%', :searchQuery, '%')) " +
            "AND (:stars = 0 OR h.stars = :stars)")
    List<Hotel> findHotelsByCriteria(String searchQuery, Integer stars);
}
