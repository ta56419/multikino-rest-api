package com.multikino.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    private Screening screening;

    @Column(name = "seats_reserved")
    private int seatsReserved;

    @Column(name = "reservation_time")
    private LocalDateTime reservationTime;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public Reservation() {}

    public Reservation(User user, Screening screening, int seatsReserved) {
        this.user = user;
        this.screening = screening;
        this.seatsReserved = seatsReserved;
        this.reservationTime = LocalDateTime.now();
        this.status = ReservationStatus.CONFIRMED;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Screening getScreening() { return screening; }
    public void setScreening(Screening screening) { this.screening = screening; }

    public int getSeatsReserved() { return seatsReserved; }
    public void setSeatsReserved(int seatsReserved) { this.seatsReserved = seatsReserved; }

    public LocalDateTime getReservationTime() { return reservationTime; }
    public void setReservationTime(LocalDateTime reservationTime) { this.reservationTime = reservationTime; }

    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }
}
