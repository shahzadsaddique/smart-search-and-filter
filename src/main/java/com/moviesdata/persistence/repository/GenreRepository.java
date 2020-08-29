package com.moviesdata.persistence.builder.repository;

import com.moviesdata.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface GenreRepository extends JpaRepository<Genre, Long>{

    Optional<Genre> findGenreByName(String name);
}
