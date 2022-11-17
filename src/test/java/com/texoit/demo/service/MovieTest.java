package com.texoit.demo.service;

import com.texoit.demo.model.CSV;
import com.texoit.demo.model.MovieResponse;
import com.texoit.demo.model.entity.Movie;
import com.texoit.demo.repository.MovieRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieTest {
    @Mock
    MovieRepository movieRepository;
    @Mock
    MovieService service;
    @InjectMocks
    MovieService movieService;
    @InjectMocks
    ImportCsvService importCsvService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        importCsvService = new ImportCsvService(service);
    }

    @Test
    public void testCheckIntervalPrizesMovie() throws Exception {

        List<CSV> csvs = importCsvService.consultDocument();

        List<Movie> movies = new ArrayList<>();
        for(CSV csv: csvs){
            Movie movie = new Movie();
            movie.setProducers(csv.getProducers());
            movie.setStudios(csv.getStudios());
            movie.setTitle(csv.getTitle());
            movie.setWinner(csv.getWinner());
            movie.setYear(csv.getYear());
            movies.add(movie);
        }

        when(movieRepository.findAll()).thenReturn(movies);

        MovieResponse resultMovieResponse = movieService.checkIntervalPrizesMovie();


        Assert.assertTrue(13==resultMovieResponse.getMax().get(0).getInterval());
        Assert.assertTrue(1==resultMovieResponse.getMin().get(0).getInterval());
        Assert.assertTrue(csvs.size()==movies.size());

    }
}