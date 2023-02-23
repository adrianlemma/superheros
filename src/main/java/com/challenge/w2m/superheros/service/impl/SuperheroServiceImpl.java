package com.challenge.w2m.superheros.service.impl;

import com.challenge.w2m.superheros.entity.Superhero;
import com.challenge.w2m.superheros.exception.ApiException;
import com.challenge.w2m.superheros.repository.SuperheroRepository;
import com.challenge.w2m.superheros.service.SuperheroService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SuperheroServiceImpl implements SuperheroService {

    @Autowired
    SuperheroRepository superheroRepository;

    @Override
    @CacheEvict(value = "w2m-superhero", allEntries = true)
    public Superhero save(Superhero superhero) {
        try {
            return superheroRepository.save(superhero);
        } catch (Exception e) {
            throw new ApiException("Fail trying to save superhero data in database");
        }
    }

    @Override
    @CacheEvict(value = "w2m-superhero", allEntries = true)
    public void deleteById(Integer superheroId) {
        try {
            superheroRepository.deleteById(superheroId);
        } catch (Exception e) {
            throw new ApiException("Fail trying to delete superhero data in database");
        }
    }

    @Override
    @Cacheable("w2m-superhero")
    public Optional<Superhero> findById(Integer superheroId) {
        try {
            return superheroRepository.findById(superheroId);
        } catch (Exception e) {
            throw new ApiException("Fail trying to find superhero data in database");
        }
    }

    @Override
    @Cacheable("w2m-superhero")
    public List<Superhero> findAll() {
        try {
            return superheroRepository.findAll();
        } catch (Exception e) {
            throw new ApiException("Fail trying to get all superheros data in database");
        }
    }

    @Override
    @Cacheable("w2m-superhero")
    public List<Superhero> findByNamePart(String namePart) {
        try {
            return superheroRepository.findByNameContainingIgnoreCase(namePart);
        } catch (Exception e) {
            throw new ApiException("Fail trying to find superheros by name part data in database");
        }
    }

    @Override
    @Cacheable("w2m-superhero")
    public Boolean existsById(Integer superheroId) {
        return superheroRepository.existsById(superheroId);
    }

    @Override
    @Cacheable("w2m-superhero")
    public Boolean existsByName(String namePart) {
        return superheroRepository.existsByNameIgnoreCase(namePart);
    }
}
