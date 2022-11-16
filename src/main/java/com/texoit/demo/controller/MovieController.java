package com.texoit.demo.controller;

import com.texoit.demo.model.MovieResponse;
import com.texoit.demo.service.ImportCsvService;
import com.texoit.demo.service.MovieService;
import com.texoit.demo.util.TexoItException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ImportCsvService importCsvService;

    @GetMapping(value = "/interval/prizes", produces = "application/json")
    public ResponseEntity<MovieResponse> checkIntervalPrizesMovie() {
        MovieResponse movieResponse = null;
        try {
            movieResponse = movieService.checkIntervalPrizesMovie();

            if(movieResponse != null){
                return new ResponseEntity<>(movieResponse, header(), HttpStatus.OK);
            }
        } catch (TexoItException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return new ResponseEntity<>(movieResponse, header(), HttpStatus.OK);
    }

    @GetMapping(value = "csv/import", produces = "application/json")
    public ResponseEntity<String> listIteractorTransport() {
        String csv = null;
        try {
            csv = importCsvService.consultDocument();

            if(csv != null){
                return new ResponseEntity<>(csv, header(), HttpStatus.OK);
            }
        } catch (TexoItException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return new ResponseEntity<>(csv, header(), HttpStatus.OK);
    }

    private HttpHeaders header() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json"));
        return headers;
    }
}
