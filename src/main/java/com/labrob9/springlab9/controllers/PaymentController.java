package com.labrob9.springlab9.controllers;

import com.labrob9.springlab9.model.Booking;
import com.labrob9.springlab9.model.Payment;
import com.labrob9.springlab9.repo.BookingRepository;
import com.labrob9.springlab9.repo.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PaymentController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @GetMapping("/payment/{booking_id}")
    public String paymentMain(@PathVariable Long booking_id, Model model) {
        Booking booking = bookingRepository.getById(booking_id);
        if (booking == null) {
            throw new IllegalArgumentException("Бронювання не знайдено.");
        }
        model.addAttribute("booking_id", booking_id);
        model.addAttribute("room", booking.getRoom());
        model.addAttribute("startDate", booking.getStartDate());
        model.addAttribute("endDate", booking.getEndDate());
        Integer cost = Math.toIntExact(booking.getDurationInDays() * booking.getRoom().getCost());
        model.addAttribute("cost", cost);
        return "payment";
    }

    @PostMapping("/payment")
    public String hotelsSearch(@RequestParam String cardName,
                               @RequestParam String cardNumber,
                               @RequestParam String expiryDate,
                               @RequestParam Integer cvv,
                               @RequestParam Integer cost,
                               @RequestParam Long booking_id,
                               Model model) {
        Payment payment = new Payment(cardName, cardNumber, expiryDate, cvv);
        // Перевірка оплати
        boolean isPaymentValid = payment.validatePayment(cost);

        if (isPaymentValid) {
            Booking booking = bookingRepository.getById(booking_id);
            booking.setPaid(Boolean.TRUE);
            paymentRepository.save(payment);
            return "redirect:/payment/success";
        } else {
            model.addAttribute("error", "Недостатньо коштів для виконання операції.");
            return "error-page";
        }
    }

    @GetMapping("/payment/success")
    public String paymentSuccess() {
        return "paymentSuccess";
    }

}



