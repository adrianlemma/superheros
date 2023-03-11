package com.challenge.w2m.superheros.mocks;

import com.challenge.w2m.superheros.entity.Superhero;
import com.challenge.w2m.superheros.exception.ApiException;
import com.challenge.w2m.superheros.repository.SuperheroRepository;
import com.challenge.w2m.superheros.service.SuperheroService;
import java.util.List;
import java.util.Optional;

import static com.challenge.w2m.superheros.constants.Constants.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class Mocks {

    // Mock DTOs
    public static Superhero mockSuperhero() {
        return new Superhero(SUPERHERO_ID, SUPERHERO_NAME, SUPERHERO_SECRET_IDENTITY, List.of(SUPERHERO_SUPER_POWER));
    }

    // Mock Services
    public static void superheroServiceSaveMock(SuperheroService service, Superhero superhero) {
        when(service.save(any())).thenReturn(superhero);
    }

    public static void superheroServiceSaveThrowsExceptionMock(SuperheroService service, ApiException ex) {
        when(service.save(any())).thenThrow(ex);
    }

    public static void superheroServiceUpdateMock(SuperheroService service, Superhero superhero) {
        when(service.update(any(), anyInt())).thenReturn(superhero);
    }

    public static void superheroServiceUpdateThrowsExceptionMock(SuperheroService service, ApiException ex) {
        when(service.update(any(), anyInt())).thenThrow(ex);
    }

    public static void superheroServiceExistsByIdMock(SuperheroService service, Boolean result) {
        when(service.existsById(anyInt())).thenReturn(result);
    }

    public static void superheroServiceExistsByNameMock(SuperheroService service, Boolean result) {
        when(service.existsByName(anyString())).thenReturn(result);
    }

    public static void superheroServiceDeleteMock(SuperheroService service) {
        doNothing().when(service).deleteById(anyInt());
    }

    public static void superheroServiceDeleteThrowsExceptionMock(SuperheroService service, ApiException ex) {
        doThrow(ex).when(service).deleteById(anyInt());
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
        when(service.findByNamePart(anyString())).thenReturn(superheros);
    }

    public static void superheroServiceFindByNamePartThrowsExceptionMock(SuperheroService service, ApiException ex) {
        when(service.findByNamePart(anyString())).thenThrow(ex);
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
        when(superheroRepository.findAll()).thenReturn(superheros);
    }

    public static void superheroRepositoryFindAllThrowsExceptionMock(SuperheroRepository superheroRepository, ApiException ex) {
        when(superheroRepository.findAll()).thenThrow(ex);
    }

    public static void superheroRepositoryfindByNameContainingIgnoreCaseMock(SuperheroRepository superheroRepository, List<Superhero> superheros) {
        when(superheroRepository.findByNameContainingIgnoreCase(any())).thenReturn(superheros);
    }

    public static void superheroRepositoryfindByNameContainingIgnoreCaseThrowsExceptionMock(SuperheroRepository superheroRepository, ApiException ex) {
        when(superheroRepository.findByNameContainingIgnoreCase(any())).thenThrow(ex);
    }

    public static void superheroRepositoryDeleteMock(SuperheroRepository superheroRepository) {
        doNothing().when(superheroRepository).deleteById(any());
    }

    public static void superheroRepositoryDeleteThrowsExceptionMock(SuperheroRepository superheroRepository, ApiException ex) {
        doThrow(ex).when(superheroRepository).deleteById(any());
    }

    public static void superheroRepositoryExistsByIdMock(SuperheroRepository superheroRepository, Boolean result) {
        when(superheroRepository.existsById(anyInt())).thenReturn(result);
    }

    public static void superheroRepositoryExistsByNameMock(SuperheroRepository superheroRepository, Boolean result) {
        when(superheroRepository.existsByNameIgnoreCase(anyString())).thenReturn(result);
    }
}
