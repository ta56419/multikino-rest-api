package com.multikino;

import com.multikino.model.Movie;
import com.multikino.repository.MovieRepository;
import com.multikino.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Test
    void shouldReturnAllMovies() {
        List<Movie> movies = Arrays.asList(
                new Movie("Inception", "Sci-Fi", 148, "A mind-bending thriller"),
                new Movie("The Godfather", "Drama", 175, "A mafia epic")
        );
        when(movieRepository.findAll()).thenReturn(movies);

        List<Movie> result = movieService.getAllMovies();

        assertEquals(2, result.size());
        assertEquals("Inception", result.get(0).getTitle());
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnMovieById() {
        Movie movie = new Movie("Inception", "Sci-Fi", 148, "A mind-bending thriller");
        movie.setId(1L);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        Optional<Movie> result = movieService.getMovieById(1L);

        assertTrue(result.isPresent());
        assertEquals("Inception", result.get().getTitle());
    }

    @Test
    void shouldReturnEmptyWhenMovieNotFound() {
        when(movieRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Movie> result = movieService.getMovieById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void shouldCreateMovie() {
        Movie movie = new Movie("Interstellar", "Sci-Fi", 169, "Space exploration epic");
        when(movieRepository.save(movie)).thenReturn(movie);

        Movie result = movieService.createMovie(movie);

        assertEquals("Interstellar", result.getTitle());
        assertEquals("Sci-Fi", result.getGenre());
        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    void shouldSearchMoviesByTitle() {
        List<Movie> movies = Arrays.asList(
                new Movie("Inception", "Sci-Fi", 148, "A mind-bending thriller")
        );
        when(movieRepository.findByTitleContainingIgnoreCase("incep")).thenReturn(movies);

        List<Movie> result = movieService.searchByTitle("incep");

        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).getTitle());
    }
}
