# Multikino REST API

A cinema booking API built with **Spring Boot** and **Java 17**. It manages movies, screenings, users and reservations, including seat availability checks.

## Tech Stack

* **Java 17**
* **Spring Boot 3.2.5**
* **Spring Data JPA** (Hibernate under the hood)
* **H2 Database** (in-memory setup for quick local development)
* **MySQL** (JDBC driver included; the default configuration uses H2)
* **JUnit 5 + Mockito** (unit testing)
* **Maven**

---

## Project Layout

Here is how the codebase is structured:

```
src/main/java/com/multikino/
├── MultikinoApplication.java       # Main entry point of the app
├── model/                          # Database entities
│   ├── Movie.java
│   ├── Screening.java              # Represents a specific showtime
│   ├── Reservation.java
│   ├── ReservationStatus.java      # CONFIRMED / CANCELLED enum
│   └── User.java
├── repository/                     # Spring Data JPA repositories
│   ├── MovieRepository.java
│   ├── ScreeningRepository.java
│   ├── ReservationRepository.java
│   └── UserRepository.java
├── service/                        # Core business logic
│   ├── MovieService.java
│   ├── ScreeningService.java
│   ├── ReservationService.java     # Where seat validation happens
│   └── UserService.java
└── controller/                     # REST controllers
    ├── MovieController.java        # /api/movies endpoints
    ├── ScreeningController.java    # /api/screenings endpoints
    ├── ReservationController.java  # /api/reservations endpoints
    └── UserController.java         # /api/users endpoints
```

---

## Getting Started

### Local Setup

1. Clone the repo to your local machine:
```bash
git clone https://github.com/ta56419/multikino-rest-api.git
cd multikino-rest-api
```

2. Start the application using Maven:
```bash
mvn spring-boot:run
```

The server runs at `http://localhost:8080`.

If you need to check the database state, the H2 Console is available at `http://localhost:8080/h2-console` (make sure to use `jdbc:h2:mem:multikino` as the JDBC URL).

### Running the Tests

To run the unit test suite, just execute:

```bash
mvn test
```

---

## API Endpoints

Available endpoints:

### Movies (`/api/movies`)

- **GET** `/api/movies` — Fetch all movies in the system
- **GET** `/api/movies/{id}` — Get a single movie by its ID
- **GET** `/api/movies/search?title=...` — Look up movies by title keywords
- **GET** `/api/movies/genre/{genre}` — Filter the movie list by genre
- **POST** `/api/movies` — Add a new movie to the database
- **PUT** `/api/movies/{id}` — Update existing movie details
- **DELETE** `/api/movies/{id}` — Remove a movie

### Screenings (`/api/screenings`)

- **GET** `/api/screenings` — Get the full showtime schedule
- **GET** `/api/screenings/{id}` — Fetch details for a specific screening
- **GET** `/api/screenings/movie/{movieId}` — Get all scheduled showtimes for a specific movie
- **POST** `/api/screenings` — Create/schedule a new screening
- **PUT** `/api/screenings/{id}` — Modify a screening
- **DELETE** `/api/screenings/{id}` — Cancel/delete a screening

### Reservations (`/api/reservations`)

- **GET** `/api/reservations` — Get a list of all reservations
- **GET** `/api/reservations/{id}` — View a specific reservation by ID
- **GET** `/api/reservations/user/{userId}` — Fetch booking history for a specific user
- **POST** `/api/reservations` — Book seats (creates a new reservation)
- **PATCH** `/api/reservations/{id}/cancel` — Cancel a booking (updates status)
- **DELETE** `/api/reservations/{id}` — Hard delete a reservation record

### Users (`/api/users`)

- **GET** `/api/users` — Get all registered users
- **GET** `/api/users/{id}` — Fetch a user profile by ID
- **POST** `/api/users` — Register a new user
- **PUT** `/api/users/{id}` — Update user profile details
- **DELETE** `/api/users/{id}` — Delete a user account

---

## Payload Examples (Postman)

### Create a Movie (`POST /api/movies`)
```json
{
    "title": "Inception",
    "genre": "Sci-Fi",
    "durationMinutes": 148,
    "description": "A mind-bending thriller by Christopher Nolan"
}
```

### Create a User (`POST /api/users`)
```json
{
    "name": "Jan Kowalski",
    "email": "jan@example.com",
    "phone": "123456789"
}
```

### Create a Screening (`POST /api/screenings`)
```json
{
    "movie": { "id": 1 },
    "screenRoom": "Room 1",
    "startTime": "2025-07-15T18:30:00",
    "availableSeats": 120,
    "ticketPrice": 29.90
}
```

### Book Seats (`POST /api/reservations`)
```json
{
    "user": { "id": 1 },
    "screening": { "id": 1 },
    "seatsReserved": 2
}
```

---

## Reservation rules

- A reservation is rejected if there are not enough seats available.
- Cancelling a reservation returns its seats to the screening's available count.
- Reservations use the statuses `CONFIRMED` and `CANCELLED`.

---

## Testing Setup

There are **7 JUnit tests** using Mockito:

- **MovieServiceTest** — Covers standard CRUD logic and the title search feature.
- **ReservationServiceTest** — Checks seat availability, cancellation and expected exceptions.

---

**Author:** Andrii Tynskyi (Junior Software Engineer)
