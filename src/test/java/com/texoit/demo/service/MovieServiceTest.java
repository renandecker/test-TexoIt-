package com.texoit.demo.service;

import com.texoit.demo.model.MovieResponse;
import com.texoit.demo.model.entity.Movie;
import com.texoit.demo.repository.MovieRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class MovieServiceTest {
    @Mock
    MovieRepository movieRepository;
    @InjectMocks
    MovieService movieService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCheckIntervalPrizesMovie() throws Exception {
        when(movieRepository.findById(1L).get()).thenReturn(new Movie());

        MovieResponse result = movieService.checkIntervalPrizesMovie();
        Assert.assertNotNull(result);
    }
}