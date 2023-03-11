package com.challenge.w2m.superheros.controller;

import com.challenge.w2m.superheros.aop.TimeTracker;
import com.challenge.w2m.superheros.entity.Superhero;
import com.challenge.w2m.superheros.exception.SuperheroException;
import com.challenge.w2m.superheros.service.SuperheroService;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/w2m/superhero")
public class SuperheroController {

    @Autowired
    SuperheroService superheroService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('USER')")
    @TimeTracker
    public ResponseEntity<Superhero> saveSuperhero(@Valid @RequestBody Superhero request, BindingResult bindingResult) {
        validateBadRequest(request, bindingResult);
        return ResponseEntity.ok(superheroService.save(request));
    }

    @PutMapping("/update/{superhero-id}")
    @PreAuthorize("hasRole('USER')")
    @TimeTracker
    public ResponseEntity<Superhero> updateSuperhero(@PathVariable("superhero-id") Integer superheroId,
                                                  @Valid @RequestBody Superhero request, BindingResult bindingResult) {
        validateBadRequest(request, bindingResult);
        return ResponseEntity.ok(superheroService.update(request, superheroId));
    }

    @DeleteMapping("/delete/{superhero-id}")
    @PreAuthorize("hasRole('ADMIN')")
    @TimeTracker
    public ResponseEntity<Void> deleteSuperhero(@PathVariable("superhero-id") Integer superheroId) {
        superheroService.deleteById(superheroId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{superhero-id}")
    @TimeTracker
    public ResponseEntity<Superhero> findSuperheroById(@PathVariable("superhero-id") Integer superheroId) {
        return ResponseEntity.ok(superheroService.findById(superheroId));
    }

    @GetMapping("/list")
    @TimeTracker
    public ResponseEntity<List<Superhero>> findAllSuperheros() {
        return ResponseEntity.ok(superheroService.findAll());
    }

    @GetMapping("/name-part/{name-part}")
    @TimeTracker
    public ResponseEntity<List<Superhero>> findSuperherosByNamePart(@PathVariable("name-part") String namePart) {
        return ResponseEntity.ok(superheroService.findByNamePart(namePart));
    }

    private void validateBadRequest(Superhero request, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || request == null) {
            throw new SuperheroException(Objects.requireNonNull(bindingResult.getFieldError()).getField(),
                    bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
