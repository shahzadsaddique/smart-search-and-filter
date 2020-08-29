package com.moviesdata.persistence.builder;

import com.moviesdata.domain.Movie;
import com.moviesdata.persistence.specs.MovieSpecification;
import com.moviesdata.persistence.specs.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieSpecificationsBuilder {

	private final List<SearchCriteria> params;
	 
    public MovieSpecificationsBuilder() {
        params = new ArrayList<SearchCriteria>();
    }
 
    public MovieSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }
 
    public Specification<Movie> build() {
        if (params.size() == 0) {
            return null;
        }
 
        List<Specification<Movie>> specs = params.stream()
          .map(MovieSpecification::new)
          .collect(Collectors.toList());
         
        Specification<Movie> result = specs.get(0);
 
        for (int i = 1; i < specs.size(); i++) {
            result = result.and(specs.get(i));
        }       
        return result;
    }
}
