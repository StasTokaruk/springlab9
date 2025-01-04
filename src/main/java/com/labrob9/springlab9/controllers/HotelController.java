package com.labrob9.springlab9.controllers;

import com.labrob9.springlab9.model.Hotel;
import com.labrob9.springlab9.repo.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.labrob9.springlab9.service.HotelService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class HotelController {

    private static final String ACCESS_PASSWORD = "admin";

    private boolean isAuthenticated = false;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelService hotelService;

    @GetMapping("/hotels")
    public String hotelsMain(Model model) {
        return "hotels-main";
    }

    @PostMapping("/hotels")
    public String hotelsSearch(@RequestParam String searchQuery,
                               @RequestParam(required = false) LocalDate startDate,
                               @RequestParam(required = false) LocalDate endDate,
                               @RequestParam Integer stars,
                               Model model) {
        if ((startDate != null && endDate != null) && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Дата початку бронювання не може бути пізніше завершення.");
        }else{
            List<Hotel> hotels = hotelService.searchHotels(searchQuery, startDate, endDate, stars);
            model.addAttribute("hotels", hotels);
        }
        return "hotels-main";
    }

    @GetMapping("/hotels/add")
    public String hotelsMainAdd(Model model) {
        if (!isAuthenticated) {
            return "redirect:/hotels/add/auth";
        }return "hotels-add";
    }

    @PostMapping("/hotels/add/auth")
    public String authenticate(@RequestParam String password, Model model) {
        if (ACCESS_PASSWORD.equals(password)) {
            isAuthenticated = true;
            return "redirect:/hotels/add";
        }
        model.addAttribute("error", "Невірний пароль!");
        return "enter-password";
    }

    @PostMapping("/hotels/add/submit")
    public String hotelsPostAdd(@RequestParam String title,
                                @RequestParam Integer stars,
                                @RequestParam Integer rooms,
                                @RequestParam Integer cost,
                                @RequestParam String location,
                                @RequestParam String description,
                                Model model) {
        Hotel hotel = new Hotel(title, location, stars, rooms, cost, description);
        hotelRepository.save(hotel);
        return "redirect:/hotels/add";
    }

    @GetMapping("/hotels/{id}")
    public String hotelsDetails(@PathVariable(value = "id") long id, Model model) {
        Optional<Hotel> res = hotelRepository.findById(id);
        ArrayList<Hotel> hotel = new ArrayList<>();
        res.ifPresent(hotel::add);
        model.addAttribute("hotel", hotel);
        return "hotel-details";
    }

    @GetMapping("/hotels/add/auth")
    public String hotelsAddAuth(Model model) {
        return "enter-password";
    }

}