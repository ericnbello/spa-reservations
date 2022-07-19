package com.reservation_system.repos;

import com.reservation_system.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Collection<Object> findReservationsByReservationDateAndStartTimeBeforeAndEndTimeAfterOrStartTimeBetween(LocalDate reservationDate, LocalTime startTime, LocalTime endTime, LocalTime startTime1, LocalTime endTime1);
}
