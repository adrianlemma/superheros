package com.challenge.w2m.superheros.service.impl;

import com.challenge.w2m.superheros.entity.Superhero;
import com.challenge.w2m.superheros.exception.ApiException;
import com.challenge.w2m.superheros.repository.SuperheroRepository;
import java.util.List;
import java.util.Optional;

import com.challenge.w2m.superheros.constants.Constants;
import com.challenge.w2m.superheros.mocks.Mocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

public class SuperheroServiceImplTest {

    @Mock
    SuperheroRepository superheroRepository;

    @InjectMocks
    SuperheroServiceImpl superheroService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Test save method")
    class TestSaveMethod {
        @Test
        @DisplayName("test when superhero is saved successfully")
        void testWhenSuperheroIsSavedSuccessfully() {
            Mocks.superheroRepositorySaveMock(superheroRepository, Mocks.mockSuperhero());
            Superhero result = superheroService.save(Mocks.mockSuperhero());
            assertNotNull(result);
            assertEquals(Constants.SUPERHERO_ID, result.getSuperheroId());
            assertEquals(Constants.SUPERHERO_NAME, result.getName());
            assertEquals(Constants.SUPERHERO_SECRET_IDENTITY, result.getSecretIdentity());
            assertEquals(Constants.SUPERHERO_SUPER_POWER, result.getSuperPowers().get(0));
        }

        @Test
        @DisplayName("test when fails saving superhero")
        void testWhenFailsSavingSuperhero() {
            Mocks.superheroRepositorySaveThrowsExceptionMock(superheroRepository, new ApiException("Error saving"));
            assertThrows(ApiException.class, () -> superheroService.save(Mocks.mockSuperhero()));
        }
    }

    @Nested
    @DisplayName("Test delete method")
    class TestDeleteMethod {
        @Test
        @DisplayName("test when superhero is deleted successfully")
        void testWhenSuperheroIsDeletedSuccessfully() {
            Mocks.superheroRepositoryDeleteMock(superheroRepository);
            superheroService.deleteById(Constants.SUPERHERO_ID);
            verify(superheroRepository).deleteById(anyInt());
        }

        @Test
        @DisplayName("test when fails deleting superhero")
        void testWhenFailsSavingSuperhero() {
            Mocks.superheroRepositoryDeleteThrowsExceptionMock(superheroRepository, new ApiException("Error deleting"));
            assertThrows(ApiException.class, () -> superheroService.deleteById(Constants.SUPERHERO_ID));
        }
    }

    @Nested
    @DisplayName("Test all find methods")
    class TestAllSindMethods {
        @Test
        @DisplayName("test when superhero is found by id successfully")
        void testWhenSuperheroIsFoundByIdSuccessfully() {
            Mocks.superheroRepositoryFindByIdMock(superheroRepository, Mocks.mockSuperhero());
            Optional<Superhero> result = superheroService.findById(Constants.SUPERHERO_ID);
            assertFalse(result.isEmpty());
        }

        @Test
        @DisplayName("test when fails getting superhero by id")
        void testWhenFailsGettingSuperheroById() {
            Mocks.superheroRepositoryFindByIdThrowsExceptionMock(superheroRepository, new ApiException("Error deleting"));
            assertThrows(ApiException.class, () -> superheroService.findById(Constants.SUPERHERO_ID));
        }

        @Test
        @DisplayName("test when superheros are listed successfully")
        void testWhenSuperherosAreListedSuccessfully() {
            Mocks.superheroRepositoryFindAllMock(superheroRepository, List.of(Mocks.mockSuperhero()));
            List<Superhero> result = superheroService.findAll();
            assertEquals(1, result.size());
        }

        @Test
        @DisplayName("test when fails listing all superheros")
        void testWhenFailsListingAllSuperheros() {
            Mocks.superheroRepositoryFindAllThrowsExceptionMock(superheroRepository, new ApiException("Error deleting"));
            assertThrows(ApiException.class, () -> superheroService.findAll());
        }

        @Test
        @DisplayName("test when superheros are listed by name part successfully")
        void testWhenSuperherosAreListedByNamePartSuccessfully() {
            Mocks.superheroRepositoryfindByNameContainingIgnoreCaseMock(superheroRepository, List.of(Mocks.mockSuperhero()));
            List<Superhero> result = superheroService.findByNamePart(Constants.SUPERHERO_NAME_PART);
            assertEquals(1, result.size());
        }

        @Test
        @DisplayName("test when fails listing superheros by name part")
        void testWhenFailsListingSuperherosByNamePart() {
            Mocks.superheroRepositoryfindByNameContainingIgnoreCaseThrowsExceptionMock(superheroRepository, new ApiException("Error deleting"));
            assertThrows(ApiException.class, () -> superheroService.findByNamePart(Constants.SUPERHERO_NAME_PART));
        }
    }

    @Nested
    @DisplayName("test all exists methods")
    class TestAllExistsMethods {
        @Test
        @DisplayName("test exists by id method")
        void testExistsByIdMethod() {
            Mocks.superheroRepositoryExistsByIdMock(superheroRepository, true);
            assertTrue(superheroService.existsById(Constants.SUPERHERO_ID));
        }

        @Test
        @DisplayName("test exists by name method")
        void testExistsByNameMethod() {
            Mocks.superheroRepositoryExistsByNameMock(superheroRepository, true);
            assertTrue(superheroService.existsByName(Constants.SUPERHERO_NAME));
        }
    }
}
