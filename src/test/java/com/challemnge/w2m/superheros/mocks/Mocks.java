package com.challemnge.w2m.superheros.mocks;

import java.util.Arrays;
import java.util.Optional;

import static com.challemnge.w2m.superheros.constants.Constants.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class Mocks {

    // Mock DTOs
    public static Superhero mockSuperhero() {
        return new Superhero(SUPERHERO_ID, SUPERHERO_NAME, SUPERHERO_SECRET_IDENTITY, Arrays.asList(SUPERHERO_SUPER_POWER));
    }

    // Mock Services
    public static void superheroServiceSaveMock(SuperheroService service, Superhero superhero) {
        when(service.save(any())).thenReturn(superhero);
    }

    public static void superheroServiceSaveThrowsExceptionMock(SuperheroService service, ApiException ex) {
        when(service.save(any())).thenThrow(ex);
    }

    public static void superheroServiceExistsByIdMock(SuperheroService service, Boolean result) {
        when(service.existsById(anyInt())).thenReturn(result);
    }

    public static void superheroServiceExistsByNameMock(SuperheroService service, Boolean result) {
        when(service.existsById(anyString())).thenReturn(result);
    }

    public static void superheroServiceUpdateMock(SuperheroService service, Superhero superhero) {
        when(service.update(any())).thenReturn(superhero);
    }

    public static void superheroServiceUpdateThrowsExceptionMock(SuperheroService service, ApiException ex) {
        when(service.update(any())).thenThrow(ex);
    }

    public static void superheroServiceDeleteMock(SuperheroService service) {
        doNothing().when(service.deleteById(anyInt()));
    }

    public static void superheroServiceDeleteThrowsExceptionMock(SuperheroService service, ApiException ex) {
        when(service.deleteById(anyInt())).thenThrow(ex);
    }

    public static void superheroServiceFindByIdMock(SuperheroService service, Superhero superhero) {
        when(service.findById(anyInt())).thenReturn(superhero);
    }

    public static void superheroServiceFindByIdThrowsExceptionMock(SuperheroService service, ApiException ex) {
        when(service.findById(anyInt())).thenThrow(ex);
    }

    public static void superheroServiceFindAllMock(SuperheroService service, List<Superhero> superheros) {
        when(service.findAll()).thenReturn(superheros);
    }

    public static void superheroServiceFindAllThrowsExceptionMock(SuperheroService service, ApiException ex) {
        when(service.findAll()).thenThrow(ex);
    }

    public static void superheroServiceFindByNamePartMock(SuperheroService service, List<Superhero> superheros) {
        when(service.findByNamePart()).thenReturn(superheros);
    }

    public static void superheroServiceFindByNamePartThrowsExceptionMock(SuperheroService service, ApiException ex) {
        when(service.findByNamePart()).thenThrow(ex);
    }

    // Mock Repository
    public static void superheroRepositorySaveMock(SuperheroRepository superheroRepository, Superhero superhero) {
        when(superheroRepository.save(any())).thenReturn(superhero);
    }

    public static void superheroRepositorySaveThrowsExceptionMock(SuperheroRepository superheroRepository, ApiException ex) {
        when(superheroRepository.save(any())).thenThrow(ex);
    }

    public static void superheroRepositoryFindByIdMock(SuperheroRepository superheroRepository, Superhero superhero) {
        when(superheroRepository.findById(any())).thenReturn(superhero == null ? Optional.empty() : Optional.of(superhero));
    }

    public static void superheroRepositoryFindByIdThrowsExceptionMock(SuperheroRepository superheroRepository, ApiException ex) {
        when(superheroRepository.findById(any())).thenThrow(ex);
    }

    public static void superheroRepositoryFindAllMock(SuperheroRepository superheroRepository, List<Superhero> superheros) {
        when(superheroRepository.findAll(any())).thenReturn(superheros);
    }

    public static void superheroRepositoryFindAllThrowsExceptionMock(SuperheroRepository superheroRepository, ApiException ex) {
        when(superheroRepository.findAll(any())).thenThrow(ex);
    }

    public static void superheroRepositoryFindByNamePartMock(SuperheroRepository superheroRepository, List<Superhero> superheros) {
        when(superheroRepository.findByNamePart(any())).thenReturn(superheros);
    }

    public static void superheroRepositoryFindByNamePartThrowsExceptionMock(SuperheroRepository superheroRepository, ApiException ex) {
        when(superheroRepository.findByNamePart(any())).thenThrow(ex);
    }

    public static void superheroRepositoryDeleteMock(SuperheroRepository superheroRepository) {
        doNothing().when(superheroRepository.deleteById(any()));
    }

    public static void superheroRepositoryDeleteThrowsExceptionMock(SuperheroRepository superheroRepository, ApiException ex) {
        when(superheroRepository.deleteById(any())).thenThrow(ex);
    }

    public static void superheroRepositoryExistsByIdMock(SuperheroRepository superheroRepository, Boolean result) {
        when(superheroRepository.existsById(anyInt())).thenReturn(result);
    }

    public static void superheroRepositoryExistsByNameMock(SuperheroRepository superheroRepository, Boolean result) {
        when(superheroRepository.existsByName(anyString())).thenReturn(result);
    }
}
