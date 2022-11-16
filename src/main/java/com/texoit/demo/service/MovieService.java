package com.texoit.demo.service;

import com.texoit.demo.model.CSV;
import com.texoit.demo.model.MovieResponse;
import com.texoit.demo.model.Producer;
import com.texoit.demo.model.entity.Movie;
import com.texoit.demo.repository.MovieRepository;
import com.texoit.demo.util.TexoItException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public MovieResponse checkIntervalPrizesMovie() throws TexoItException {
        try {
            MovieResponse movieResponse = new MovieResponse();
            List<Producer> max = new ArrayList<>();
            List<Producer> min = new ArrayList<>();
            List<Producer> producers = new ArrayList<>();

            List<Movie> movies = (List<Movie>) movieRepository.findAll();
            if(movies.size() == 0){
                return movieResponse;
            }

            Collections.sort(movies);

            Producer producer = new Producer();

            Movie movietemp = new Movie();

            for(Movie moviecheck: movies){
                if(moviecheck.getWinner() != null && !moviecheck.getWinner().equals("")){
                    if(moviecheck.getProducers() != null && moviecheck.getProducers().equals(movietemp.getProducers()) &&
                            !moviecheck.getTitle().equals(movietemp.getTitle()) && !moviecheck.getYear().equals(movietemp.getYear())){
                        producer.setFollowingWin(Integer.valueOf(moviecheck.getYear()));
                        producer.setInterval(producer.getFollowingWin()-producer.getPreviousWin());
                    }else{
                        if(producer.getFollowingWin() != null){
                            producers.add(producer);
                        }
                        producer = new Producer();
                        if(moviecheck.getYear() != null && !moviecheck.getYear().equals("")){
                            producer.setPreviousWin(Integer.valueOf(moviecheck.getYear()));
                            producer.setProducer(moviecheck.getProducers());
                        }
                    }
                    movietemp = moviecheck;
                }
            }

            Collections.sort(producers);

            producer = new Producer();

            for(Producer producertemp: producers) {

                if(producer.getInterval() == null){
                    producer = producertemp;
                    min.add(producertemp);
                }else{
                    if(producertemp.getInterval() <= producer.getInterval()){
                        min.add(producertemp);
                        producer = producertemp;
                    }else{
                        break;
                    }
                }

                producer = producertemp;
            }

            Collections.reverse(producers);


            for(Producer producertemp: producers) {

                if(producer.getInterval() == null){
                    producer = producertemp;
                    max.add(producertemp);
                }else{
                    if(producertemp.getInterval() == producer.getInterval()){
                        max.add(producertemp);
                        producer = producertemp;
                    }else{
                        break;
                    }
                }

                producer = producertemp;
            }


            movieResponse.setMax(max);
            movieResponse.setMin(min);

            return movieResponse;
        }catch (Exception e){
            new TexoItException(e.getMessage());
        }
        return null;
    }

    public void saveMovies(List<CSV> csvs) throws TexoItException {
        try {
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

            movieRepository.saveAll(movies);
        }catch (Exception e){
            throw new TexoItException(e.getMessage());
        }
    }
}
