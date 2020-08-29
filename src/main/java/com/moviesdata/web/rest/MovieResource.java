package com.moviesdata.web.rest;

import com.moviesdata.domain.Movie;
import com.moviesdata.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/movies/")
public class MovieResource {

    private  MovieService movieService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieResource.class);

    public MovieResource(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping(value = "/parse-csv-file",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Movie>> parseMoviesData(@Valid @RequestParam("moviesDataFile") MultipartFile moviesDataFile) throws Exception {
        try {
            return ResponseEntity.ok().body(
                    movieService.parseCSVFileAndSaveMoviesData(moviesDataFile)
            );
        }
        catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Movie>> searchMovies(@RequestParam(value = "q",
            required = false) String query,@PageableDefault(sort = {"rating"}, direction = Sort.Direction.DESC, size = 25) Pageable pageable) {
        Page<Movie> page = movieService.search(query, pageable);
        return ResponseEntity.ok().body(page);
    }
}
