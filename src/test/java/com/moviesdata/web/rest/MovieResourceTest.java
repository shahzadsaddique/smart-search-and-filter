package com.moviesdata.web.rest;

import com.MoviesDataApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = MoviesDataApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.properties")
public class MovieResourceTest {

    @Autowired
    private MockMvc mvc;


    @Test
    public void parseMoviesDataCSVFileTest() throws Exception {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        MockMultipartFile file = new MockMultipartFile("moviesDataFile", "IMDB-Moive-Data.csv",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                loader.getResourceAsStream("IMDB-Moive-Data.csv"));
        mvc.perform(multipart("/v1/movies/parse-csv-file").file(file))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAllMoviesWithoutAnyFilterTest() throws Exception {
        parseMoviesDataCSVFileTest();
        mvc.perform(
                get("/v1/movies/search")
                        .param("q","")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is(65)));
    }

    @Test
    public void searchMoviesBySingleYearValueTest() throws Exception {
        parseMoviesDataCSVFileTest();
        mvc.perform(
                get("/v1/movies/search")
                        .param("q","year:2016")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].year", is(2016)))
                .andExpect(jsonPath("$.*", hasSize(11)));
    }

    @Test
    public void searchMoviesByYearsInSpecificRangeTest() throws Exception {
        parseMoviesDataCSVFileTest();
        mvc.perform(
                get("/v1/movies/search")
                        .param("q","year>2010,year<2014")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].year", greaterThanOrEqualTo(2010)))
                .andExpect(jsonPath("$.content", hasSize(6)));
    }

    @Test
    public void searchMoviesByOneGenreTest() throws Exception {
        parseMoviesDataCSVFileTest();
        mvc.perform(
                get("/v1/movies/search")
                        .param("q","genre:comedy")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(14)));
    }

    @Test
    public void searchMoviesByMultipleGenresTest() throws Exception {
        parseMoviesDataCSVFileTest();
        mvc.perform(
                get("/v1/movies/search")
                        .param("q","genre:comedy,genre:drama")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(4)));
    }

    @Test
    public void searchMoviesByMultipleFiltersTest() throws Exception {
        parseMoviesDataCSVFileTest();
        mvc.perform(
                get("/v1/movies/search")
                        .param("q","genre:comedy,year>2010,rating>7")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(6)));
    }
}
