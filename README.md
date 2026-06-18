# Multikino REST API

REST API for a cinema ticket reservation system built with **Spring Boot**, **Java**, and **H2/MySQL** database.

## Technologies

- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Data JPA** (Hibernate)
- **H2 Database** (in-memory, for development)
- **MySQL** (production-ready connector included)
- **JUnit 5 + Mockito** (unit testing)
- **Maven**

## Project Structure

```
src/main/java/com/multikino/
├── MultikinoApplication.java       # Main application class
├── model/
│   ├── Movie.java                  # Movie entity
│   ├── Screening.java              # Screening (showtime) entity
│   ├── Reservation.java            # Reservation entity
│   ├── ReservationStatus.java      # CONFIRMED / CANCELLED enum
│   └── User.java                   # User entity
├── repository/
│   ├── MovieRepository.java        # Movie data access
│   ├── ScreeningRepository.java    # Screening data access
│   ├── ReservationRepository.java  # Reservation data access
│   └── UserRepository.java         # User data access
├── service/
│   ├── MovieService.java           # Movie business logic
│   ├── ScreeningService.java       # Screening business logic
│   ├── ReservationService.java     # Reservation logic + seat validation
│   └── UserService.java            # User business logic
└── controller/
    ├── MovieController.java        # /api/movies endpoints
    ├── ScreeningController.java    # /api/screenings endpoints
    ├── ReservationController.java  # /api/reservations endpoints
    └── UserController.java         # /api/users endpoints
```

## How to Run

```bash
# Clone the repository
git clone https://github.com/ta56419/multikino-rest-api.git
cd multikino-rest-api

# Build and run
mvn spring-boot:run

# The API will be available at http://localhost:8080
# H2 Console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:multikino)
```

## Run Tests

```bash
mvn test
```

## API Endpoints

### Movies `/api/movies`

| Method | Endpoint                  | Description              |
|--------|---------------------------|--------------------------|
| GET    | `/api/movies`             | Get all movies           |
| GET    | `/api/movies/{id}`        | Get movie by ID          |
| GET    | `/api/movies/search?title=` | Search movies by title |
| GET    | `/api/movies/genre/{genre}` | Filter by genre        |
| POST   | `/api/movies`             | Create a new movie       |
| PUT    | `/api/movies/{id}`        | Update a movie           |
| DELETE | `/api/movies/{id}`        | Delete a movie           |

### Screenings `/api/screenings`

| Method | Endpoint                          | Description                  |
|--------|-----------------------------------|------------------------------|
| GET    | `/api/screenings`                 | Get all screenings           |
| GET    | `/api/screenings/{id}`            | Get screening by ID          |
| GET    | `/api/screenings/movie/{movieId}` | Get screenings for a movie   |
| POST   | `/api/screenings`                 | Create a new screening       |
| PUT    | `/api/screenings/{id}`            | Update a screening           |
| DELETE | `/api/screenings/{id}`            | Delete a screening           |

### Reservations `/api/reservations`

| Method | Endpoint                            | Description               |
|--------|-------------------------------------|---------------------------|
| GET    | `/api/reservations`                 | Get all reservations      |
| GET    | `/api/reservations/{id}`            | Get reservation by ID     |
| GET    | `/api/reservations/user/{userId}`   | Get user's reservations   |
| POST   | `/api/reservations`                 | Create a new reservation  |
| PATCH  | `/api/reservations/{id}/cancel`     | Cancel a reservation      |
| DELETE | `/api/reservations/{id}`            | Delete a reservation      |

### Users `/api/users`

| Method | Endpoint          | Description        |
|--------|-------------------|--------------------|
| GET    | `/api/users`      | Get all users      |
| GET    | `/api/users/{id}` | Get user by ID     |
| POST   | `/api/users`      | Create a new user  |
| PUT    | `/api/users/{id}` | Update a user      |
| DELETE | `/api/users/{id}` | Delete a user      |

## Example Requests (Postman)

### Create a movie
```json
POST /api/movies
{
    "title": "Inception",
    "genre": "Sci-Fi",
    "durationMinutes": 148,
    "description": "A mind-bending thriller by Christopher Nolan"
}
```

### Create a user
```json
POST /api/users
{
    "name": "Jan Kowalski",
    "email": "jan@example.com",
    "phone": "123456789"
}
```

### Create a screening
```json
POST /api/screenings
{
    "movie": { "id": 1 },
    "screenRoom": "Room 1",
    "startTime": "2025-07-15T18:30:00",
    "availableSeats": 120,
    "ticketPrice": 29.90
}
```

### Create a reservation
```json
POST /api/reservations
{
    "user": { "id": 1 },
    "screening": { "id": 1 },
    "seatsReserved": 2
}
```

## Business Logic

- **Seat availability check**: When creating a reservation, the system verifies there are enough available seats in the screening. If not, it returns an error.
- **Seat restoration on cancel**: When a reservation is cancelled (PATCH), the reserved seats are returned to the screening's available pool.
- **Reservation status**: Each reservation has a status (CONFIRMED or CANCELLED).

## Testing

The project includes **7 JUnit tests** with Mockito:

- `MovieServiceTest` — tests for CRUD operations and search functionality
- `ReservationServiceTest` — tests for reservation creation with seat validation and error handling

## Author

**Andrii Tynskyi** — Junior Software Engineer
