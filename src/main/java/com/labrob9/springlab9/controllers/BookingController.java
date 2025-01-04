package com.labrob9.springlab9.controllers;

import com.labrob9.springlab9.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;


@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;


    @GetMapping("/booking")
    public String bookingMain(Model model) {
        model.addAttribute("id_room", null); // Щоб вводити вручну
        return "booking-add";
    }

    @PostMapping("/booking")
    public String bookingPostAdd(@RequestParam Long id_room, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate, Model model) {
        Long booking_id;
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Дата початку бронювання не може бути пізніше завершення.");
        } else {
            booking_id = bookingService.createBooking(id_room, startDate, endDate).getId();
        }
        return "redirect:/payment/" + booking_id;
    }

    @GetMapping("/booking/{id}")
    public String bookingId(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("id_room", id);


        return "booking-add";
    }



}
