package com.challemnge.w2m.superheros.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.challemnge.w2m.superheros.constants.Constants.*;
import static com.challemnge.w2m.superheros.mocks.Mocks.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SuperheroController.class)
public class SuperheroControllerTest {

    @MockBean
    SuperheroService superheroService;

    @Autowired
    MockMvc mvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @Nested
    @DisplayName("Test application POST method")
    class TestPostMethod {
        @Test
        @DisplayName("Test when superhero is saved successfully")
        void testWhenSuperheroIsSavedSuccessfully() {
            SuperHero request = mockSuperhero();
            superheroServiceExistsByNameMock(superheroService, false);
            superheroServiceSaveMock(superheroService, request);

            mvc.perform(post("/w2m/superhero/save")
                            .content(mapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.superheroId").isNumber());
        }

        @Test
        @DisplayName("Test when fails saving superhero data")
        void testWhenFailsSavingSuperheroData() throws Exception {
            superheroServiceExistsByNameMock(superheroService, false);
            superheroServiceSaveThrowsExceptionMock(superheroService, new ApiException("Error saving data"));

            mvc.perform(post("/w2m/superhero/save")
                            .content(mapper.writeValueAsString(mockSuperhero()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        @DisplayName("Test when fails saving superhero by bad request")
        void testWhenFailsSavingSuperheroByBadRequest() throws Exception {
            superheroServiceSaveMock(superheroService, null);

            mvc.perform(post("/w2m/superhero/save")
                            .content("{}")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Test when trying to save a superhero but it alreadi exists")
        void testWhenTryingToSaveASuperheroButItAlreadyExists() {
            superheroServiceExistsByNameMock(superheroService, true);

            mvc.perform(post("/w2m/superhero/save")
                            .content(mapper.writeValueAsString(mockSuperhero()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("Test application PUT method")
    class TestPutMethod {
        @Test
        @DisplayName("Test when superhero is updated successfully")
        void testWhenSuperheroIsUpdatedSuccessfully() {
            SuperHero request = mockSuperhero();
            superheroServiceExistsByIdMock(superheroService, true);
            superheroServiceExistsByNameMock(superheroService, false);
            superheroServiceUpdateMock(superheroService, request);

            mvc.perform(put("/w2m/superhero/update/" + SUPERHERO_ID)
                            .content(mapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Test when fails updating superhero data")
        void testWhenFailsUpdatingSuperheroData() throws Exception {
            superheroServiceExistsByIdMock(superheroService, true);
            superheroServiceExistsByNameMock(superheroService, false);
            superheroServiceUpdateThrowsExceptionMock(superheroService, new ApiException("Error updating data"));

            mvc.perform(put("/w2m/superhero/update/" + SUPERHERO_ID)
                            .content(mapper.writeValueAsString(mockSuperhero()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        @DisplayName("Test when fails saving superhero by bad request")
        void testWhenFailsUpdatingSuperheroByBadRequest() throws Exception {
            superheroServiceUpdateMock(superheroService, null);

            mvc.perform(put("/w2m/superhero/update/" + SUPERHERO_ID)
                            .content("{}")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Test when fails updating superhero by id not found")
        void testWhenFailsUpdatingSuperheroByIdNotFound() throws Exception {
            superheroServiceExistsByIdMock(superheroService, false);

            mvc.perform(put("/w2m/superhero/update/" + SUPERHERO_ID)
                            .content(mapper.writeValueAsString(mockSuperhero()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Test when fails updating superhero by updated name already exists")
        void testWhenFailsUpdatingSuperheroByUpdatedNameAlreadyExists() throws Exception {
            SuperHero request = mockSuperhero();
            superheroServiceExistsByIdMock(superheroService, true);
            superheroServiceExistsByNameMock(superheroService, true);

            mvc.perform(put("/w2m/superhero/update/" + SUPERHERO_ID)
                            .content(mapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("Test application DELETE method")
    class TestDeleteMethod {
        @Test
        @DisplayName("Test when superhero is deleted successfully")
        void testWhenSuperheroIsDeletedSuccessfully() throws Exception {
            superheroServiceExistsByIdMock(superheroService, true);
            superheroServiceDeleteMock(superheroService);

            mvc.perform(delete("/w2m/superhero/delete/" + SUPERHERO_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Test when throws exception trying to deleted superhero")
        void testWhenThrowsExceptionTryingToDeletedSuperhero() throws Exception {
            superheroServiceExistsByIdMock(superheroService, true);
            superheroServiceDeleteThrowsExceptionMock(superheroService, ApiException("Error trying to delete"));

            mvc.perform(delete("/w2m/superhero/delete/" + SUPERHERO_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        @DisplayName("Test when fails deleting superhero by id not found")
        void testWhenFailsDeletingSuperheroByIdNotFound() throws Exception {
            superheroServiceExistsByIdMock(superheroService, false);

            mvc.perform(delete("/w2m/superhero/update/" + SUPERHERO_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Test application GET method")
    class testGetMethod {
        @Test
        @DisplayName("Test when superhero is found by id successfully")
        void testWhenSuperheroIsFoundByIdSuccessfully() throws Exception {
            superheroServiceFindByIdMock(superheroService, mockSuperhero());

            mvc.perform(get("/w2m/superhero/" + SUPERHERO_ID)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.superheroId").isNumber())
                    .andExpect(jsonPath("$.name").value(SUPERHERO_NAME));
        }

        @Test
        @DisplayName("Test when throws an exception trying to find superhero by id")
        void testWhenThrowsAnExceptionTryingToFindSuperheroById() throws Exception {
            superheroServiceFindByIdThrowsExceptionMock(superheroService, ApiException("Error getting data"));

            mvc.perform(get("/w2m/superhero/" + SUPERHERO_ID)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.superheroId").isNumber())
                    .andExpect(jsonPath("$.name").value(SUPERHERO_NAME));
        }

        @Test
        @DisplayName("Test when all superheros are listed successfully")
        void testWhenAllSuperherosAreListedSuccessfully() throws Exception {
            superheroServiceFindAllMock(superheroService, Arrays.asList(mockSuperhero()));

            mvc.perform(get("/w2m/superhero/list")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].superheroId").isNumber())
                    .andExpect(jsonPath("$[0].name").value(SUPERHERO_NAME));
        }

        @Test
        @DisplayName("Test when throws an exception trying to list all superheros")
        void testWhenThrowsAnExceptionTryingToListAllSuperheros() throws Exception {
            superheroServiceFindAllThrowsExceptionMock(superheroService, ApiException("Error listing data"));

            mvc.perform(get("/w2m/superhero/list")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        @DisplayName("Test when find superheros by name part successfully")
        void testWhenFindSuperherosByNamePartSuccessfully() throws Exception {
            superheroServiceFindByNamePartMock(superheroService, Arrays.asList(mockSuperhero()));

            mvc.perform(get("/w2m/superhero/name-part/" + SUPERHERO_NAME_PART)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].superheroId").isNumber())
                    .andExpect(jsonPath("$[0].name").value(SUPERHERO_NAME));
        }

        @Test
        @DisplayName("Test when find superheros by name part throws an exception")
        void testWhenFindSuperherosByNamePartThrowsAnException() throws Exception {
            superheroServiceFindByNamePartThrowsExceptionMock(superheroService, new ApiException("Error listing data"));

            mvc.perform(get("/w2m/superhero/name-part/" + SUPERHERO_NAME_PART)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }
    }
}
