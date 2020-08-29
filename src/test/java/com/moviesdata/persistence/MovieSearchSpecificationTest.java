package com.moviesdata.persistence;

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
public class MovieSearchSpecificationTest {

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


    private static final String TEST1_title = "Guardians of the Galaxy";
    private static final String TEST1_genres = "Action,Adventure,Sci-Fi";
    private static final String TEST1_description = "A group of intergalactic criminals are forced to work together to stop a fanatical warrior from taking control of the universe.";
    private static final String TEST1_director = "James Gunn";
    private static final String TEST1_actors = "Chris Pratt, Vin Diesel, Bradley Cooper, Zoe Saldanau";
    private static final Integer TEST1_rank = 1;
    private static final Integer TEST1_year = 2014;
    private static final Integer TEST1_runtimeMinutes = 121;
    private static final Integer TEST1_votes = 757074;
    private static final float TEST1_rating =8.1f ;
    private static final float TEST1_revenueMillions = 333.13f;
    private static final float TEST1_metascore = 76;

    @Autowired
    private MovieService movieService;

    private Movie testMovie1,
            testMovie2;

    @Before
    public void init() {
        testMovie1 = new Movie();
        testMovie1.setTitle(TEST_title);
        testMovie1.setDescription(TEST_description);
        testMovie1.setDirector(TEST_director);
        testMovie1.setYear(TEST_year);
        testMovie1.setVotes(TEST_votes);
        testMovie1.setRevenueMillions(TEST_revenueMillions);
        testMovie1.setRating(TEST_rating);
        testMovie1.setMetascore(TEST_metascore);
        testMovie1.setRank(TEST_rank);
        testMovie1.setRuntimeMinutes(TEST_runtimeMinutes);
        movieService.setupMovieActors(testMovie1, TEST_actors);
        movieService.setupMovieGenres(testMovie1,TEST_genres);


        testMovie2 = new Movie();
        testMovie2.setTitle(TEST1_title);
        testMovie2.setDescription(TEST1_description);
        testMovie2.setDirector(TEST1_director);
        testMovie2.setYear(TEST1_year);
        testMovie2.setVotes(TEST1_votes);
        testMovie2.setRevenueMillions(TEST1_revenueMillions);
        testMovie2.setRating(TEST1_rating);
        testMovie2.setMetascore(TEST1_metascore);
        testMovie2.setRank(TEST1_rank);
        testMovie2.setRuntimeMinutes(TEST1_runtimeMinutes);
        movieService.setupMovieActors(testMovie2, TEST1_actors);
        movieService.setupMovieGenres(testMovie2,TEST1_genres);

    }

    @Test
    public void searchWithMovieTitle_whenMovieContainsTitle() {

        Movie mayBeSaved1 = movieService.save(testMovie1);
        assertThat(mayBeSaved1).isNotNull();

        Movie mayBeSaved2 = movieService.save(testMovie2);
        assertThat(mayBeSaved2).isNotNull();

        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        String searchQuery = "title:Guardians of the Galaxy";

        Page<Movie> searchResults = movieService.search(searchQuery,pageable);

        assertThat(searchResults).isNotNull();
        assertThat(searchResults).isNotEmpty();
        assertThat(searchResults.getContent().size()).isEqualTo(1);
    }

    @Test
    public void searchWithMovieTitle_whenMoviesNotContainTitle() {

        Movie mayBeSaved1 = movieService.save(testMovie1);
        assertThat(mayBeSaved1).isNotNull();

        Movie mayBeSaved2 = movieService.save(testMovie2);
        assertThat(mayBeSaved2).isNotNull();

        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        String searchQuery = "title:XYZ LMN";

        Page<Movie> searchResults = movieService.search(searchQuery,pageable);

        assertThat(searchResults).isEmpty();
        assertThat(searchResults.getContent().size()).isEqualTo(0);
    }

    @Test
    public void searchWithMovieTitleAndYear() {

        Movie mayBeSaved1 = movieService.save(testMovie1);
        assertThat(mayBeSaved1).isNotNull();

        Movie mayBeSaved2 = movieService.save(testMovie2);
        assertThat(mayBeSaved2).isNotNull();

        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        String searchQuery = "title:Guardians of the Galaxy,year:2014";

        Page<Movie> searchResults = movieService.search(searchQuery,pageable);

        assertThat(searchResults).isNotNull();
        assertThat(searchResults).isNotEmpty();
        assertThat(searchResults.getContent().size()).isEqualTo(1);
    }
}
