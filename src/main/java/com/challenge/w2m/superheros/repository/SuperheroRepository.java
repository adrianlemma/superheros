package com.challenge.w2m.superheros.repository;

import com.challenge.w2m.superheros.entity.Superhero;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperheroRepository extends JpaRepository<Superhero, Integer> {

    Boolean existsByNameIgnoreCase(String name);

    List<Superhero> findByNameContainingIgnoreCase(String namePart);
}
