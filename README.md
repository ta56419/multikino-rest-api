# Multikino REST API

Prosty system rezerwacji biletów kinowych w formie REST API. Projekt postawiłem na **Spring Boot** (Java), a dane przechowuję w bazie **H2** (lokalnie) z możliwością łatwego przełączenia na **MySQL** na produkcji.

## Tech stack

* **Java 17**
* **Spring Boot 3.2.5**
* **Spring Data JPA** (Hibernate do obsługi bazy)
* **H2 Database** (baza w pamięci RAM, idealna pod dev)
* **MySQL** (gotowy konektor w konfiguracji)
* **JUnit 5 + Mockito** (testy jednostkowe)
* **Maven**

---

## Architektura projektu

Podział na pakiety wygląda następująco:
