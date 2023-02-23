package com.challenge.w2m.superheros.repository;

import com.challenge.w2m.superheros.entity.Superhero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuperheroRepository extends JpaRepository<Superhero, Integer> {

    Boolean existsByNameIgnoreCase(String name);

    List<Superhero> findByNameContainingIgnoreCase(String namePart);
}
