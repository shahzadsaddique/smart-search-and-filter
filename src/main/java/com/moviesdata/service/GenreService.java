package com.moviesdata.service;

import com.moviesdata.domain.Genre;
import com.moviesdata.persistence.builder.repository.GenreRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class GenreService {
    private  GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre getGenreByName(String name){

        Optional<Genre> genreByName = genreRepository.findGenreByName(name);
        return genreByName.orElseGet(() -> saveGenre(name));
    }

    private Genre saveGenre(String name) {
        Genre genre =  new Genre();
        genre.setName(name);
        return genreRepository.save(genre);
    }
}
