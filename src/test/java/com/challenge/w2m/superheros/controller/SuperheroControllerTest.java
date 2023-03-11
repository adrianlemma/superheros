package com.challenge.w2m.superheros.controller;

import com.challenge.w2m.superheros.entity.Superhero;
import com.challenge.w2m.superheros.exception.ApiException;
import com.challenge.w2m.superheros.service.SuperheroService;
import com.challenge.w2m.superheros.constants.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.challenge.w2m.superheros.mocks.Mocks.*;
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
    @WithMockUser(username = "TestUser", password = "TestUser", roles = "USER")
    class TestPostMethod {
        @Test
        @DisplayName("Test when superhero is saved successfully")
        void testWhenSuperheroIsSavedSuccessfully() throws Exception {
            Superhero request = mockSuperhero();
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
    }

    @Nested
    @DisplayName("Test application PUT method")
    @WithMockUser(username = "TestUser", password = "TestUser", roles = "USER")
    class TestPutMethod {
        @Test
        @DisplayName("Test when superhero is updated successfully")
        void testWhenSuperheroIsUpdatedSuccessfully() throws Exception {
            Superhero request = mockSuperhero();
            superheroServiceUpdateMock(superheroService, request);

            mvc.perform(put("/w2m/superhero/update/1")
                            .content(mapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Test when fails updating superhero data")
        void testWhenFailsUpdatingSuperheroData() throws Exception {
            superheroServiceUpdateThrowsExceptionMock(superheroService, new ApiException("Fail Updating"));

            mvc.perform(put("/w2m/superhero/update/1")
                            .content(mapper.writeValueAsString(mockSuperhero()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        @DisplayName("Test when fails saving superhero by bad request")
        void testWhenFailsUpdatingSuperheroByBadRequest() throws Exception {
            superheroServiceUpdateMock(superheroService, null);

            mvc.perform(put("/w2m/superhero/update/1")
                            .content("{}")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("Test application DELETE method")
    @WithMockUser(username = "TestUser", password = "TestUser", roles = "ADMIN")
    class TestDeleteMethod {
        @Test
        @DisplayName("Test when superhero is deleted successfully")
        void testWhenSuperheroIsDeletedSuccessfully() throws Exception {
            superheroServiceExistsByIdMock(superheroService, true);
            superheroServiceDeleteMock(superheroService);

            mvc.perform(delete("/w2m/superhero/delete/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Test when throws exception trying to deleted superhero")
        void testWhenThrowsExceptionTryingToDeletedSuperhero() throws Exception {
            superheroServiceExistsByIdMock(superheroService, true);
            superheroServiceDeleteThrowsExceptionMock(superheroService, new ApiException("Error trying to delete"));

            mvc.perform(delete("/w2m/superhero/delete/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }
    }

    @Nested
    @DisplayName("Test application GET method")
    class testGetMethod {
        @Test
        @DisplayName("Test when superhero is found by id successfully")
        void testWhenSuperheroIsFoundByIdSuccessfully() throws Exception {
            superheroServiceFindByIdMock(superheroService, mockSuperhero());

            mvc.perform(MockMvcRequestBuilders.get("/w2m/superhero/1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.superheroId").isNumber())
                    .andExpect(jsonPath("$.name").value(Constants.SUPERHERO_NAME));
        }

        @Test
        @DisplayName("Test when throws an exception trying to find superhero by id")
        void testWhenThrowsAnExceptionTryingToFindSuperheroById() throws Exception {
            superheroServiceFindByIdThrowsExceptionMock(superheroService, new ApiException("Error getting data"));

            mvc.perform(MockMvcRequestBuilders.get("/w2m/superhero/1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        @DisplayName("Test when all superheros are listed successfully")
        void testWhenAllSuperherosAreListedSuccessfully() throws Exception {
            superheroServiceFindAllMock(superheroService, List.of(mockSuperhero()));

            mvc.perform(get("/w2m/superhero/list")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].superheroId").isNumber())
                    .andExpect(jsonPath("$[0].name").value(Constants.SUPERHERO_NAME));
        }

        @Test
        @DisplayName("Test when throws an exception trying to list all superheros")
        void testWhenThrowsAnExceptionTryingToListAllSuperheros() throws Exception {
            superheroServiceFindAllThrowsExceptionMock(superheroService, new ApiException("Error listing data"));

            mvc.perform(get("/w2m/superhero/list")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        @DisplayName("Test when find superheros by name part successfully")
        void testWhenFindSuperherosByNamePartSuccessfully() throws Exception {
            superheroServiceFindByNamePartMock(superheroService, List.of(mockSuperhero()));

            mvc.perform(MockMvcRequestBuilders.get("/w2m/superhero/name-part/man")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].superheroId").isNumber())
                    .andExpect(jsonPath("$[0].name").value(Constants.SUPERHERO_NAME));
        }

        @Test
        @DisplayName("Test when find superheros by name part throws an exception")
        void testWhenFindSuperherosByNamePartThrowsAnException() throws Exception {
            superheroServiceFindByNamePartThrowsExceptionMock(superheroService, new ApiException("Error listing data"));

            mvc.perform(MockMvcRequestBuilders.get("/w2m/superhero/name-part/man")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }
    }
}
