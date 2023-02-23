package com.challemnge.w2m.superheros.service.impl;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.challemnge.w2m.superheros.constants.Constants.*;
import static com.challemnge.w2m.superheros.mocks.Mocks.*;
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
            superheroRepositorySaveMock(superheroRepository, mockSuperhero());
            Superhero result = superheroService.save(mockSuperhero());
            assertNotNull(result);
            assertEquals(SUPERHERO_ID, result.getId());
            assertEquals(SUPERHERO_NAME, result.getName());
            assertEquals(SUPERHERO_SECRET_IDENTITY, result.getSecretIdentity());
            assertEquals(SUPERHERO_SUPER_POWER, result.getSuperPower().get(0));
        }

        @Test
        @DisplayName("test when fails saving superhero")
        void testWhenFailsSavingSuperhero() {
            superheroRepositorySaveThrowsExceptionMock(superheroRepository, ApiException("Error saving"));
            assertThrows(ApiException.class, () -> superheroService.save(mockSuperhero()));
        }
    }

    @Nested
    @DisplayName("Test delete method")
    class TestDeleteMethod {
        @Test
        @DisplayName("test when superhero is deleted successfully")
        void testWhenSuperheroIsDeletedSuccessfully() {
            superheroRepositoryDeleteMock(superheroRepository);
            superheroService.deleteById(SUPERHERO_ID);
            verify(superheroRepository).delete(anyInt());
        }

        @Test
        @DisplayName("test when fails deleting superhero")
        void testWhenFailsSavingSuperhero() {
            superheroRepositoryDeleteThrowsExceptionMock(superheroRepository, ApiException("Error deleting"));
            assertThrows(ApiException.class, () -> superheroService.deleteById(SUPERHERO_ID));
        }
    }

    @Nested
    @DisplayName("Test all find methods")
    class TestAllSindMethods {
        @Test
        @DisplayName("test when superhero is found by id successfully")
        void testWhenSuperheroIsFoundByIdSuccessfully() {
            superheroRepositoryFindByIdMock(superheroRepository, mockSuperhero());
            Optional<Superhero> result = superheroService.findById(SUPERHERO_ID);
            assertFalse(result.isEmpty());
        }

        @Test
        @DisplayName("test when fails getting superhero by id")
        void testWhenFailsGettingSuperheroById() {
            superheroRepositoryFindByIdThrowsExceptionMock(superheroRepository, ApiException("Error deleting"));
            assertThrows(ApiException.class, () -> superheroService.findById(SUPERHERO_ID));
        }

        @Test
        @DisplayName("test when superheros are listed successfully")
        void testWhenSuperherosAreListedSuccessfully() {
            superheroRepositoryFindAllMock(superheroRepository, Arrays.asList(mockSuperhero()));
            List<Superhero> result = superheroService.findAll();
            assertEquals(1, result.size());
        }

        @Test
        @DisplayName("test when fails listing all superheros")
        void testWhenFailsListingAllSuperheros() {
            superheroRepositoryFindAllThrowsExceptionMock(superheroRepository, ApiException("Error deleting"));
            assertThrows(ApiException.class, () -> superheroService.findAll());
        }

        @Test
        @DisplayName("test when superheros are listed by name part successfully")
        void testWhenSuperherosAreListedByNamePartSuccessfully() {
            superheroRepositoryFindByNamePartMock(superheroRepository, Arrays.asList(mockSuperhero()));
            List<Superhero> result = superheroService.findByNamePart(SUPERHERO_NAME_PART);
            assertEquals(1, result.size());
        }

        @Test
        @DisplayName("test when fails listing superheros by name part")
        void testWhenFailsListingSuperherosByNamePart() {
            superheroRepositoryFindByNamePartThrowsExceptionMock(superheroRepository, ApiException("Error deleting"));
            assertThrows(ApiException.class, () -> superheroService.findByNamePart(SUPERHERO_NAME_PART));
        }
    }

    @Nested
    @DisplayName("test all exists methods")
    class TestAllExistsMethods {
        @Test
        @DisplayName("test exists by id method")
        void testExistsByIdMethod() {
            superheroRepositoryExistsByIdMock(superheroRepository, true);
            assertTrue(superheroService.existsById(SUPERHERO_ID));
        }

        @Test
        @DisplayName("test exists by name method")
        void testExistsByNameMethod() {
            superheroRepositoryExistsByNameMock(superheroRepository, true);
            assertTrue(superheroService.existsByName(SUPERHERO_NAME));
        }
    }
}
