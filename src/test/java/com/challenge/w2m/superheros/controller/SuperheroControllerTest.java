package com.challenge.w2m.superheros.controller;

import com.challenge.w2m.superheros.entity.Superhero;
import com.challenge.w2m.superheros.exception.ApiException;
import com.challenge.w2m.superheros.service.SuperheroService;
import com.challenge.w2m.superheros.constants.Constants;
import com.challenge.w2m.superheros.mocks.Mocks;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
        void testWhenSuperheroIsSavedSuccessfully() throws Exception {
            Superhero request = Mocks.mockSuperhero();
            Mocks.superheroServiceExistsByNameMock(superheroService, false);
            Mocks.superheroServiceSaveMock(superheroService, request);

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
            Mocks.superheroServiceExistsByNameMock(superheroService, false);
            Mocks.superheroServiceSaveThrowsExceptionMock(superheroService, new ApiException("Error saving data"));

            mvc.perform(post("/w2m/superhero/save")
                            .content(mapper.writeValueAsString(Mocks.mockSuperhero()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        @DisplayName("Test when fails saving superhero by bad request")
        void testWhenFailsSavingSuperheroByBadRequest() throws Exception {
            Mocks.superheroServiceSaveMock(superheroService, null);

            mvc.perform(post("/w2m/superhero/save")
                            .content("{}")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Test when trying to save a superhero but it alreadi exists")
        void testWhenTryingToSaveASuperheroButItAlreadyExists() throws Exception {
            Mocks.superheroServiceExistsByNameMock(superheroService, true);

            mvc.perform(post("/w2m/superhero/save")
                            .content(mapper.writeValueAsString(Mocks.mockSuperhero()))
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
        void testWhenSuperheroIsUpdatedSuccessfully() throws Exception {
            Superhero request = Mocks.mockSuperhero();
            Mocks.superheroServiceExistsByIdMock(superheroService, true);
            Mocks.superheroServiceExistsByNameMock(superheroService, false);
            Mocks.superheroServiceFindByIdMock(superheroService, Mocks.mockSuperhero());
            Mocks.superheroServiceSaveMock(superheroService, request);

            mvc.perform(put("/w2m/superhero/update/" + Constants.SUPERHERO_ID)
                            .content(mapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Test when fails updating superhero data")
        void testWhenFailsUpdatingSuperheroData() throws Exception {
            Mocks.superheroServiceExistsByIdMock(superheroService, true);
            Mocks.superheroServiceExistsByNameMock(superheroService, false);
            Mocks.superheroServiceFindByIdMock(superheroService, Mocks.mockSuperhero());
            Mocks.superheroServiceSaveThrowsExceptionMock(superheroService, new ApiException("Error updating data"));

            mvc.perform(put("/w2m/superhero/update/" + Constants.SUPERHERO_ID)
                            .content(mapper.writeValueAsString(Mocks.mockSuperhero()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        @DisplayName("Test when fails saving superhero by bad request")
        void testWhenFailsUpdatingSuperheroByBadRequest() throws Exception {
            Mocks.superheroServiceSaveMock(superheroService, null);

            mvc.perform(put("/w2m/superhero/update/" + Constants.SUPERHERO_ID)
                            .content("{}")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Test when fails updating superhero by id not found")
        void testWhenFailsUpdatingSuperheroByIdNotFound() throws Exception {
            Mocks.superheroServiceExistsByIdMock(superheroService, false);

            mvc.perform(put("/w2m/superhero/update/" + Constants.SUPERHERO_ID)
                            .content(mapper.writeValueAsString(Mocks.mockSuperhero()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Test when fails updating superhero by updated name already exists")
        void testWhenFailsUpdatingSuperheroByUpdatedNameAlreadyExists() throws Exception {
            Superhero request = Mocks.mockSuperhero();
            request.setName("Other name");
            Mocks.superheroServiceExistsByIdMock(superheroService, true);
            Mocks.superheroServiceExistsByNameMock(superheroService, true);
            Mocks.superheroServiceFindByIdMock(superheroService, Mocks.mockSuperhero());

            mvc.perform(put("/w2m/superhero/update/" + Constants.SUPERHERO_ID)
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
            Mocks.superheroServiceExistsByIdMock(superheroService, true);
            Mocks.superheroServiceDeleteMock(superheroService);

            mvc.perform(delete("/w2m/superhero/delete/" + Constants.SUPERHERO_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Test when throws exception trying to deleted superhero")
        void testWhenThrowsExceptionTryingToDeletedSuperhero() throws Exception {
            Mocks.superheroServiceExistsByIdMock(superheroService, true);
            Mocks.superheroServiceDeleteThrowsExceptionMock(superheroService, new ApiException("Error trying to delete"));

            mvc.perform(delete("/w2m/superhero/delete/" + Constants.SUPERHERO_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        @DisplayName("Test when fails deleting superhero by id not found")
        void testWhenFailsDeletingSuperheroByIdNotFound() throws Exception {
            Mocks.superheroServiceExistsByIdMock(superheroService, false);

            mvc.perform(delete("/w2m/superhero/delete/" + Constants.SUPERHERO_ID)
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
            Mocks.superheroServiceFindByIdMock(superheroService, Mocks.mockSuperhero());

            mvc.perform(MockMvcRequestBuilders.get("/w2m/superhero/" + Constants.SUPERHERO_ID)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.superheroId").isNumber())
                    .andExpect(jsonPath("$.name").value(Constants.SUPERHERO_NAME));
        }

        @Test
        @DisplayName("Test when throws an exception trying to find superhero by id")
        void testWhenThrowsAnExceptionTryingToFindSuperheroById() throws Exception {
            Mocks.superheroServiceFindByIdThrowsExceptionMock(superheroService, new ApiException("Error getting data"));

            mvc.perform(MockMvcRequestBuilders.get("/w2m/superhero/" + Constants.SUPERHERO_ID)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        @DisplayName("Test when superhero is not found by id")
        void testWhenSuperheroIsNotFoundById() throws Exception {
            Mocks.superheroServiceFindByIdMock(superheroService, null);

            mvc.perform(MockMvcRequestBuilders.get("/w2m/superhero/" + Constants.SUPERHERO_ID)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Test when all superheros are listed successfully")
        void testWhenAllSuperherosAreListedSuccessfully() throws Exception {
            Mocks.superheroServiceFindAllMock(superheroService, List.of(Mocks.mockSuperhero()));

            mvc.perform(get("/w2m/superhero/list")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].superheroId").isNumber())
                    .andExpect(jsonPath("$[0].name").value(Constants.SUPERHERO_NAME));
        }

        @Test
        @DisplayName("Test when throws an exception trying to list all superheros")
        void testWhenThrowsAnExceptionTryingToListAllSuperheros() throws Exception {
            Mocks.superheroServiceFindAllThrowsExceptionMock(superheroService, new ApiException("Error listing data"));

            mvc.perform(get("/w2m/superhero/list")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        @DisplayName("Test when find superheros by name part successfully")
        void testWhenFindSuperherosByNamePartSuccessfully() throws Exception {
            Mocks.superheroServiceFindByNamePartMock(superheroService, List.of(Mocks.mockSuperhero()));

            mvc.perform(MockMvcRequestBuilders.get("/w2m/superhero/name-part/" + Constants.SUPERHERO_NAME_PART)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].superheroId").isNumber())
                    .andExpect(jsonPath("$[0].name").value(Constants.SUPERHERO_NAME));
        }

        @Test
        @DisplayName("Test when find superheros by name part throws an exception")
        void testWhenFindSuperherosByNamePartThrowsAnException() throws Exception {
            Mocks.superheroServiceFindByNamePartThrowsExceptionMock(superheroService, new ApiException("Error listing data"));

            mvc.perform(MockMvcRequestBuilders.get("/w2m/superhero/name-part/" + Constants.SUPERHERO_NAME_PART)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }
    }
}
