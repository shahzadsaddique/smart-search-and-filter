package com.moviesdata.persistence.builder.repository;

import com.moviesdata.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

    boolean existsByTitleAndYearAndRuntimeMinutes(String title, int year, int runtimeMinutes);
}
