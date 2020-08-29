package com.moviesdata.service;

import com.moviesdata.domain.Actor;
import com.moviesdata.persistence.builder.repository.ActorRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ActorService {

    private ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public Actor getActorByName(String name){

        Optional<Actor> actorByName = actorRepository.findActorByName(name);
        return actorByName.orElseGet(() -> saveActor(name));
    }

    private Actor saveActor(String name) {
        Actor actor =  new Actor();
        actor.setName(name);
        return  actorRepository.save(actor);
    }
}
