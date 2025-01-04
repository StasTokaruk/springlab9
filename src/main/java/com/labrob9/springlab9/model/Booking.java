package com.labrob9.springlab9.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean paid;
    private LocalDateTime createdAt;

    public Booking() {
    }

    public Booking(Room room, LocalDate startDate, LocalDate endDate) {
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paid = false;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isValidate() {
        return endDate.isAfter(LocalDate.now().minusDays(1));
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Boolean getPaid() {
        return paid;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public long getDurationInDays() {
        if (startDate != null && endDate != null) {
            return ChronoUnit.DAYS.between(startDate, endDate);
        }
        throw new IllegalStateException("Дати startDate та endDate не встановлені.");
    }
}
