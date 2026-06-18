package com.multikino.service;

import com.multikino.model.Reservation;
import com.multikino.model.ReservationStatus;
import com.multikino.model.Screening;
import com.multikino.repository.ReservationRepository;
import com.multikino.repository.ScreeningRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ScreeningRepository screeningRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ScreeningRepository screeningRepository) {
        this.reservationRepository = reservationRepository;
        this.screeningRepository = screeningRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> getReservationsByUser(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    public Reservation createReservation(Reservation reservation) {
        Screening screening = screeningRepository.findById(reservation.getScreening().getId())
                .orElseThrow(() -> new RuntimeException("Screening not found"));

        if (screening.getAvailableSeats() < reservation.getSeatsReserved()) {
            throw new RuntimeException("Not enough available seats. Available: " + screening.getAvailableSeats());
        }

        screening.setAvailableSeats(screening.getAvailableSeats() - reservation.getSeatsReserved());
        screeningRepository.save(screening);

        reservation.setReservationTime(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.CONFIRMED);

        return reservationRepository.save(reservation);
    }

    public Reservation cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));

        reservation.setStatus(ReservationStatus.CANCELLED);

        Screening screening = reservation.getScreening();
        screening.setAvailableSeats(screening.getAvailableSeats() + reservation.getSeatsReserved());
        screeningRepository.save(screening);

        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
