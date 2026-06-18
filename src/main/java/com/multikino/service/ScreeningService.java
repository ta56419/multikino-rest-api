package com.multikino.service;

import com.multikino.model.Screening;
import com.multikino.repository.ScreeningRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScreeningService {

    private final ScreeningRepository screeningRepository;

    public ScreeningService(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }

    public List<Screening> getAllScreenings() {
        return screeningRepository.findAll();
    }

    public Optional<Screening> getScreeningById(Long id) {
        return screeningRepository.findById(id);
    }

    public List<Screening> getScreeningsByMovie(Long movieId) {
        return screeningRepository.findByMovieId(movieId);
    }

    public Screening createScreening(Screening screening) {
        return screeningRepository.save(screening);
    }

    public Screening updateScreening(Long id, Screening details) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Screening not found with id: " + id));

        screening.setMovie(details.getMovie());
        screening.setScreenRoom(details.getScreenRoom());
        screening.setStartTime(details.getStartTime());
        screening.setAvailableSeats(details.getAvailableSeats());
        screening.setTicketPrice(details.getTicketPrice());

        return screeningRepository.save(screening);
    }

    public void deleteScreening(Long id) {
        screeningRepository.deleteById(id);
    }
}
