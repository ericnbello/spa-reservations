package com.reservation_system;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.reservation_system.exception.UserAlreadyExistException;
import com.reservation_system.model.Reservation;
import com.reservation_system.model.User;
import com.reservation_system.service.ReservationService;
import com.reservation_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
public class HomeController {

    final UserService userService;
    final ReservationService reservationService;

    public HomeController(UserService userService, ReservationService reservationService) {
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/reservations")
    public String reservations(Model model, HttpSession session) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        User user = userService.getUserByUsername(name);

        // This should always be the case
        if (user != null) {
            session.setAttribute("user", user);

            // Empty reservation object in case the user creates a new reservation
            Reservation reservation = new Reservation();
            model.addAttribute("reservation", reservation);

            return "reservations";
        }

        return "index";
    }

    @PostMapping("/reservations-submit")
    public String reservationsSubmit(@ModelAttribute Reservation reservation,
                                     @SessionAttribute("user") User user) {

        // Save to DB after updating
        assert user != null;
        reservation.setUser(user);
        reservationService.create(reservation);
        Set<Reservation> userReservations = user.getReservations();
        userReservations.add(reservation);
        user.setReservations(userReservations);
        userService.update(user.getId(), user);
        return "redirect:/reservations";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/register")
    public String register(WebRequest request, final Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String userRegistration(final @Valid User user, final BindingResult bindingResult, final Model model) throws UserAlreadyExistException {
        if(bindingResult.hasErrors()){
            model.addAttribute("registrationForm", user);
            return "registration";
        }
        userService.create(user);
        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
}
