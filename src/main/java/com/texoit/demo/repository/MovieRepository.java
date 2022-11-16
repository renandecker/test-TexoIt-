package com.texoit.demo.repository;

import com.texoit.demo.model.entity.Movie;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {
}
