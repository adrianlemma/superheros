package com.challenge.w2m.superheros.service;

import com.challenge.w2m.superheros.entity.Superhero;

import java.util.List;
import java.util.Optional;

public interface SuperheroService {

    public Superhero save(Superhero superhero);

    public void deleteById(Integer superheroId);

    public Optional<Superhero> findById(Integer superheroId);

    public List<Superhero> findAll();

    public List<Superhero> findByNamePart(String namePart);

    public Boolean existsById(Integer superheroId);

    public Boolean existsByName(String namePart);

}
