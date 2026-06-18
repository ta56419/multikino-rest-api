package com.multikino;

import com.multikino.model.*;
import com.multikino.repository.ReservationRepository;
import com.multikino.repository.ScreeningRepository;
import com.multikino.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ScreeningRepository screeningRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void shouldCreateReservationWhenSeatsAvailable() {
        Movie movie = new Movie("Inception", "Sci-Fi", 148, "A mind-bending thriller");
        Screening screening = new Screening(movie, "Room 1", LocalDateTime.now().plusDays(1), 100, 25.0);
        screening.setId(1L);

        User user = new User("Jan Kowalski", "jan@example.com", "123456789");
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setScreening(screening);
        reservation.setSeatsReserved(2);

        when(screeningRepository.findById(1L)).thenReturn(Optional.of(screening));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation result = reservationService.createReservation(reservation);

        assertNotNull(result);
        assertEquals(98, screening.getAvailableSeats());
        verify(screeningRepository).save(screening);
    }

    @Test
    void shouldThrowExceptionWhenNotEnoughSeats() {
        Movie movie = new Movie("Inception", "Sci-Fi", 148, "A mind-bending thriller");
        Screening screening = new Screening(movie, "Room 1", LocalDateTime.now().plusDays(1), 1, 25.0);
        screening.setId(1L);

        Reservation reservation = new Reservation();
        reservation.setScreening(screening);
        reservation.setSeatsReserved(5);

        when(screeningRepository.findById(1L)).thenReturn(Optional.of(screening));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                reservationService.createReservation(reservation));

        assertTrue(exception.getMessage().contains("Not enough available seats"));
    }
}
