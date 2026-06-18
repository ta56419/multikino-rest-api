package com.multikino.controller;

import com.multikino.model.Screening;
import com.multikino.service.ScreeningService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screenings")
public class ScreeningController {

    private final ScreeningService screeningService;

    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @GetMapping
    public List<Screening> getAllScreenings() {
        return screeningService.getAllScreenings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Screening> getScreeningById(@PathVariable Long id) {
        return screeningService.getScreeningById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/movie/{movieId}")
    public List<Screening> getScreeningsByMovie(@PathVariable Long movieId) {
        return screeningService.getScreeningsByMovie(movieId);
    }

    @PostMapping
    public ResponseEntity<Screening> createScreening(@RequestBody Screening screening) {
        Screening created = screeningService.createScreening(screening);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Screening> updateScreening(@PathVariable Long id, @RequestBody Screening screening) {
        Screening updated = screeningService.updateScreening(id, screening);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScreening(@PathVariable Long id) {
        screeningService.deleteScreening(id);
        return ResponseEntity.noContent().build();
    }
}
