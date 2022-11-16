package com.texoit.demo.service;

import com.texoit.demo.model.CSV;
import com.texoit.demo.model.entity.Movie;
import com.texoit.demo.repository.MovieRepository;
import com.texoit.demo.util.TexoItException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ImportCsvService {

    private static final String newLine = System.getProperty("line.separator");

    @Autowired
    private MovieService movieService;

    public String consultDocument() throws TexoItException {
        List<CSV> csvs = new ArrayList<>();
        InputStream inputStream = null;
        try {

            File file = new File(new File( "." ).getCanonicalPath()+File.separator+"movielist.csv");
            if (!file.exists()) file.createNewFile();

            inputStream = new FileInputStream(file);

            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            String data = new String(bdata, StandardCharsets.UTF_8);

            String[] splitted = Arrays.stream(data.split(";|\\n")).map(String::trim).toArray(String[]::new);


            int positionSplitted = 5;
            while (positionSplitted < splitted.length-1) {
                CSV csv = new CSV();
                if(positionSplitted+3 <  splitted.length-1){
                    String[] producers = Arrays.stream(splitted[positionSplitted+3].split(",|\\tand\\t")).map(String::trim).toArray(String[]::new);
                    int producersSplitted = 0;
                    while (producersSplitted < producers.length) {
                        csv.setProducers(producers[producersSplitted].trim());
                        csv.setYear(splitted[positionSplitted]);
                        csv.setTitle(positionSplitted + 1 > splitted.length - 1 ? null : splitted[positionSplitted + 1]);
                        csv.setStudios(positionSplitted + 2 > splitted.length - 1 ? null : splitted[positionSplitted + 2]);
                        csv.setWinner(positionSplitted + 4 > splitted.length - 1 ? null : splitted[positionSplitted + 4]);
                        csvs.add(csv);
                        producersSplitted += 1;
                    }
                }
                positionSplitted += 5;
            }
            insertCSV(csvs);
        } catch (Exception e) {
            throw new TexoItException(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "Importado";
    }

    public void insertCSV(List<CSV> csvs) throws TexoItException {
        try {
            movieService.saveMovies(csvs);
        }catch (Exception e){
            throw new TexoItException(e.getMessage());
        }
    }

}
