package com.multikino.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "screenings")
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(name = "screen_room", nullable = false)
    private String screenRoom;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "available_seats")
    private int availableSeats;

    @Column(name = "ticket_price")
    private double ticketPrice;

    @OneToMany(mappedBy = "screening", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    public Screening() {}

    public Screening(Movie movie, String screenRoom, LocalDateTime startTime, int availableSeats, double ticketPrice) {
        this.movie = movie;
        this.screenRoom = screenRoom;
        this.startTime = startTime;
        this.availableSeats = availableSeats;
        this.ticketPrice = ticketPrice;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }

    public String getScreenRoom() { return screenRoom; }
    public void setScreenRoom(String screenRoom) { this.screenRoom = screenRoom; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }

    public double getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(double ticketPrice) { this.ticketPrice = ticketPrice; }

    public List<Reservation> getReservations() { return reservations; }
    public void setReservations(List<Reservation> reservations) { this.reservations = reservations; }
}
