package com.challenge.w2m.superheros.controller;

import com.challenge.w2m.superheros.entity.Superhero;
import com.challenge.w2m.superheros.exception.SuperheroException;
import com.challenge.w2m.superheros.service.SuperheroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/w2m/superhero")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET, RequestMethod.DELETE})
public class SuperheroController {

    @Autowired
    SuperheroService superheroService;

    @PostMapping("/save")
    public ResponseEntity<Object> saveSuperhero(@Valid @RequestBody Superhero request, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || request == null) {
            throw new SuperheroException(Objects.requireNonNull(bindingResult.getFieldError()).getField(),
                    bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        if (superheroService.existsByName(request.getName())) {
            throw new SuperheroException("name", "Already exists a superhero with this name [" + request.getName() + "]",
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(superheroService.save(request));
    }

    @PutMapping("/update/{superhero-id}")
    public ResponseEntity<Object> updateSuperhero(@PathVariable("superhero-id") Integer superheroId,
                                                  @Valid @RequestBody Superhero request, BindingResult bindingResult) {

        if (bindingResult.hasErrors() || request == null) {
            throw new SuperheroException(Objects.requireNonNull(bindingResult.getFieldError()).getField(),
                    bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        Optional<Superhero> oldRecord = superheroService.findById(superheroId);
        if (oldRecord.isEmpty()) {
            throw new SuperheroException("superhero-id", "Superhero with id [" + superheroId + "] does not exist",
                    HttpStatus.NOT_FOUND);
        }
        if (!oldRecord.get().getName().equals(request.getName()) && superheroService.existsByName(request.getName())) {
            throw new SuperheroException("name", "Already exists a superhero with this name [" + request.getName() + "]",
                    HttpStatus.BAD_REQUEST);
        }
        oldRecord.get().setName(request.getName());
        oldRecord.get().setSecretIdentity(request.getSecretIdentity());
        oldRecord.get().setSuperPowers(request.getSuperPowers());
        return ResponseEntity.ok(superheroService.save(oldRecord.get()));
    }

    @DeleteMapping("/delete/{superhero-id}")
    public ResponseEntity<Object> deleteSuperhero(@PathVariable("superhero-id") Integer superheroId) {
        if (!superheroService.existsById(superheroId)) {
            throw new SuperheroException("superhero-id", "Superhero with id [" + superheroId + "] does not exist",
                    HttpStatus.NOT_FOUND);
        }
        superheroService.deleteById(superheroId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{superhero-id}")
    public ResponseEntity<Object> findSuperheroById(@PathVariable("superhero-id") Integer superheroId) {
        Optional<Superhero> result = superheroService.findById(superheroId);
        if (result.isEmpty()) {
            throw new SuperheroException("superhero-id", "not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result.get());
    }

    @GetMapping("/list")
    public ResponseEntity<Object> findAllSuperheros() {
        return ResponseEntity.ok(superheroService.findAll());
    }

    @GetMapping("/name-part/{name-part}")
    public ResponseEntity<Object> findSuperherosByNamePart(@PathVariable("name-part") String namePart) {
        return ResponseEntity.ok(superheroService.findByNamePart(namePart));
    }

}
