package com.challenge.w2m.superheros.service.impl;

import com.challenge.w2m.superheros.entity.Superhero;
import com.challenge.w2m.superheros.exception.ApiException;
import com.challenge.w2m.superheros.exception.SuperheroException;
import com.challenge.w2m.superheros.repository.SuperheroRepository;
import com.challenge.w2m.superheros.service.SuperheroService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SuperheroServiceImpl implements SuperheroService {

    @Autowired
    SuperheroRepository superheroRepository;

    @Override
    @CacheEvict(value = "w2m-superhero", allEntries = true)
    public Superhero save(Superhero superhero) {
        StringBuilder stringBuilder = new StringBuilder();
        if (superheroRepository.existsByNameIgnoreCase(superhero.getName())) {
            throw new SuperheroException("name", stringBuilder.append("Already exists a superhero with this name [")
                    .append(superhero.getName()).append("]").toString(),
                    HttpStatus.BAD_REQUEST);
        }
        try {
            superhero.setSuperheroId(null); // Para no forzar IDs en BD
            return superheroRepository.save(superhero);
        } catch (Exception e) {
            throw new ApiException("Fail trying to save superhero data in database");
        }
    }

    @Override
    @CacheEvict(value = "w2m-superhero", allEntries = true)
    public Superhero update(Superhero superhero, Integer superheroId) {
        StringBuilder stringBuilder= new StringBuilder();
        Superhero oldRecord = this.findById(superheroId);
        if (!oldRecord.getName().equals(superhero.getName()) && this.existsByName(superhero.getName())) {
            throw new SuperheroException("name", stringBuilder.append("Already exists a superhero with this name [")
                    .append(superhero.getName()).append("]").toString(),
                    HttpStatus.BAD_REQUEST);
        }
        oldRecord.setName(superhero.getName());
        oldRecord.setSecretIdentity(superhero.getSecretIdentity());
        oldRecord.setSuperPowers(superhero.getSuperPowers());
        return superheroRepository.save(oldRecord);
    }

    @Override
    @CacheEvict(value = "w2m-superhero", allEntries = true)
    public void deleteById(Integer superheroId) {
        StringBuilder stringBuilder= new StringBuilder();
        try {
            if (!superheroRepository.existsById(superheroId)) {
                throw new SuperheroException("superhero-id", stringBuilder.append("Superhero with id [")
                        .append(superheroId).append("] does not exist").toString(),
                        HttpStatus.NOT_FOUND);
            }
            superheroRepository.deleteById(superheroId);
        } catch (Exception e) {
            throw new ApiException("Fail trying to delete superhero data in database");
        }
    }

    @Override
    @Cacheable("w2m-superhero")
    public Superhero findById(Integer superheroId) {
        Optional<Superhero> result;
        try {
            result = superheroRepository.findById(superheroId);
        } catch (Exception e) {
            throw new ApiException("Fail trying to find superhero data in database");
        }
        if (result.isEmpty()) {
            throw new SuperheroException("superhero-id", "not found", HttpStatus.NOT_FOUND);
        }
        return result.get();
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
