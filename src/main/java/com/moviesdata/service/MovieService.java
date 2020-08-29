package com.moviesdata.service;

import com.moviesdata.domain.Actor;
import com.moviesdata.domain.Genre;
import com.moviesdata.domain.Movie;
import com.moviesdata.persistence.builder.MovieSpecificationsBuilder;
import com.moviesdata.persistence.builder.repository.MovieRepository;
import com.moviesdata.utils.CSVMovieData;
import com.moviesdata.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
public class MovieService {

    private MovieRepository movieRepository;
    private ActorService actorService;
    private GenreService genreService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);


    public MovieService(MovieRepository movieRepository, ActorService actorService, GenreService genreService) {
        this.movieRepository = movieRepository;
        this.actorService = actorService;
        this.genreService = genreService;
    }

    public List<Movie> parseCSVFileAndSaveMoviesData(MultipartFile moviesDataFile) throws IOException {
        List<CSVMovieData> moviesDataFromCSV =  CommonUtil.parseCSVFile(moviesDataFile);
        List<Movie> moviesToSave = getMoviesToSave(moviesDataFromCSV);

        moviesToSave.forEach(movie -> {
             movieRepository.save(movie);
        });
        return moviesToSave;
    }

    private List<Movie> getMoviesToSave(List<CSVMovieData> moviesDataFromCSV) {

        //Title of movie must not be null
        //As dataset does not contain a unique identifier for each movie
        //We are going to identify movies uniquely by {Title+Year+RuntimeMinutes}

        return moviesDataFromCSV.stream()
                .filter(csvMovieData -> csvMovieData.getTitle() != null && !csvMovieData.getTitle().isEmpty())
                .filter(csvMovieData -> !movieRepository.
                        existsByTitleAndYearAndRuntimeMinutes(
                                csvMovieData.getTitle(), csvMovieData.getYear(), csvMovieData.getMinutes()))
                .map(csvMovieData -> {
                    Movie movie = new Movie();
                    movie.setTitle(csvMovieData.getTitle());
                    movie.setDescription(csvMovieData.getDescription());
                    movie.setDirector(csvMovieData.getDirector());
                    movie.setRank(csvMovieData.getRank());
                    movie.setRating(csvMovieData.getRating());
                    movie.setMetascore(csvMovieData.getMetascore());
                    movie.setRevenueMillions(csvMovieData.getMillions());
                    movie.setRuntimeMinutes(csvMovieData.getMinutes());
                    movie.setVotes(csvMovieData.getVotes());
                    movie.setYear(csvMovieData.getYear());
                    setupMovieGenres(movie, csvMovieData.getGenres());
                    setupMovieActors(movie, csvMovieData.getActors());
                    return movie;
                }).collect(Collectors.toList());
    }

    public void setupMovieActors(Movie movie, String actors) {
        if(actors==null || actors.isEmpty())
            return;
        Set<Actor> actorsSet = new HashSet<>();
        for (String actorName: actors.split(",")) {
            actorsSet.add(actorService.getActorByName(actorName));
        }

        movie.setActors(actorsSet);
    }

    public void setupMovieGenres(Movie movie, String genres) {
        if(genres==null || genres.isEmpty())
            return;
        Set<Genre> genreSet = new HashSet<>();
        for (String genreName: genres.split(",")) {
            genreSet.add(genreService.getGenreByName(genreName));
        }

        movie.setGenres(genreSet);
    }

    @Transactional(readOnly = true)
    public Page<Movie> search(String query, Pageable pageable) {

        MovieSpecificationsBuilder builder = new MovieSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(query.replace(" ","_") + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<Movie> spec = builder.build();
        return movieRepository.findAll(spec, pageable);
    }

    public Movie save(Movie movie){
       return movieRepository.save(movie);
    }
}
