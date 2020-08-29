package com.moviesdata.persistence.specs;

import com.moviesdata.domain.Genre;
import com.moviesdata.domain.Movie;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class MovieSpecification implements Specification<Movie> {

	private final SearchCriteria criteria;
	
	public MovieSpecification(final SearchCriteria criteria) {
		super();
		this.criteria = criteria;
	}

	@Override
	public Specification<Movie> and(Specification<Movie> other) {
		return Specification.super.and(other);
	}

	@Override
	public Specification<Movie> or(Specification<Movie> other) {
		return Specification.super.or(other);
	}

	@Override
	public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		Object value = criteria.getValue();

		if(criteria.getKey().toLowerCase().equals("genre")){
			Join<Movie, Genre> groupJoin = root.join("genres");
			return builder.equal(builder.lower(groupJoin.<String> get("name")), value);
		}

		if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(
              root.<String> get(criteria.getKey()), value.toString());
        } 
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
              root.<String> get(criteria.getKey()), value.toString());
        } 
        else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                  root.<String>get(criteria.getKey()), "%" + value + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), value);
            }
        }
        return null;
	}

}
