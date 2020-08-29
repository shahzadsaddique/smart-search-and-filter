package com.moviesdata.service;

import com.MoviesDataApplication;
import com.moviesdata.domain.Movie;
import com.moviesdata.service.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MoviesDataApplication.class)
@TestPropertySource(
        locations = "classpath:application.properties")
@Transactional
public class MovieServiceTest {
    private static final String TEST_title = "The Great Wall";
    private static final String TEST_genres = "Action,Adventure,Fantasy";
    private static final String TEST_description = "European mercenaries searching for black powder become embroiled in the defense of the Great Wall of China against a horde of monstrous creatures";
    private static final String TEST_director = "Yimou Zhang";
    private static final String TEST_actors = "Matt Damon, Tian Jing, Willem Dafoe, Andy Lau";

    private static final Integer TEST_rank = 6;
    private static final Integer TEST_year = 2016;
    private static final Integer TEST_runtimeMinutes = 103;
    private static final Integer TEST_votes = 56036;

    private static final float TEST_rating =6.1f ;
    private static final float TEST_revenueMillions = 45.13f;
    private static final float TEST_metascore = 42;

    @Autowired
    private MovieService movieService;

    private Movie testMovie;

    @Before
    public void init() {
        testMovie = new Movie();
        testMovie.setTitle(TEST_title);
        testMovie.setDescription(TEST_description);
        testMovie.setDirector(TEST_director);
        testMovie.setYear(TEST_year);
        testMovie.setVotes(TEST_votes);
        testMovie.setRevenueMillions(TEST_revenueMillions);
        testMovie.setRating(TEST_rating);
        testMovie.setMetascore(TEST_metascore);
        testMovie.setRank(TEST_rank);
        testMovie.setRuntimeMinutes(TEST_runtimeMinutes);
        movieService.setupMovieActors(testMovie, TEST_actors);
        movieService.setupMovieGenres(testMovie,TEST_genres);
    }

    @Test
    @Transactional
    public void saveMovieTest(){
        Movie mayBeSaved = movieService.save(testMovie);

        assertThat(mayBeSaved).isNotNull();
        assertThat(mayBeSaved.getTitle()).isEqualTo(testMovie.getTitle());
        assertThat(mayBeSaved.getDescription()).isEqualTo(testMovie.getDescription());
        assertThat(mayBeSaved.getRank()).isEqualTo(testMovie.getRank());
        assertThat(mayBeSaved.getYear()).isEqualTo(testMovie.getYear());
        assertThat(mayBeSaved.getActors()).isEqualTo(testMovie.getActors());
        assertThat(mayBeSaved.getGenres()).isEqualTo(testMovie.getGenres());
        assertThat(mayBeSaved.getRuntimeMinutes()).isEqualTo(testMovie.getRuntimeMinutes());
        assertThat(mayBeSaved.getDirector()).isEqualTo(testMovie.getDirector());
        assertThat(mayBeSaved.getMetascore()).isEqualTo(testMovie.getMetascore());
        assertThat(mayBeSaved.getVotes()).isEqualTo(testMovie.getVotes());
        assertThat(mayBeSaved.getRating()).isEqualTo(testMovie.getRating());
    }

    @Test
    @Transactional
    public void findAllMoviesTest(){

        movieService.save(testMovie);

        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        Page<Movie> movies = movieService.search("",pageable);

        assertThat(movies).isNotNull();
        assertThat(movies.getContent().size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void searchMoviesTest(){

        Movie mayBeSaved = movieService.save(testMovie);
        assertThat(mayBeSaved).isNotNull();

        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        String searchQuery = "year:2016,genre:action";

        Page<Movie> movies = movieService.search(searchQuery,pageable);

        assertThat(movies).isNotNull();
        assertThat(movies.getContent().size()).isEqualTo(1);
    }
}


