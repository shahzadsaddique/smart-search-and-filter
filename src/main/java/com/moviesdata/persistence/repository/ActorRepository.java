package com.moviesdata.persistence.builder.repository;

import com.moviesdata.domain.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    Optional<Actor> findActorByName(String name);
}
