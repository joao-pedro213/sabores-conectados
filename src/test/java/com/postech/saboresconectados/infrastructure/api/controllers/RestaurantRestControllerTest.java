package com.postech.saboresconectados.infrastructure.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.saboresconectados.core.controller.RestaurantController;
import com.postech.saboresconectados.core.dtos.NewRestaurantDto;
import com.postech.saboresconectados.core.dtos.RestaurantOutputDto;
import com.postech.saboresconectados.core.dtos.UpdateRestaurantDto;
import com.postech.saboresconectados.helpers.JsonReaderUtil;
import com.postech.saboresconectados.helpers.RestaurantObjectMother;
import com.postech.saboresconectados.infrastructure.api.config.SecurityConfig;
import com.postech.saboresconectados.infrastructure.api.controllers.exceptions.HttpStatusResolver;
import com.postech.saboresconectados.infrastructure.api.dtos.NewRestaurantRequestDto;
import com.postech.saboresconectados.infrastructure.api.dtos.UpdateRestaurantRequestDto;
import com.postech.saboresconectados.infrastructure.data.RestaurantDataSourceJpa;
import com.postech.saboresconectados.infrastructure.data.UserDataSourceJpa;
import com.postech.saboresconectados.infrastructure.data.datamappers.RestaurantMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(RestaurantRestController.class)
class RestaurantRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RestaurantDataSourceJpa mockRestaurantDataSourceJpa;

    @MockitoBean
    private UserDataSourceJpa mockUserDataSourceJpa;

    @Mock
    private RestaurantController mockRestaurantController;

    private MockedStatic<RestaurantController> mockedStaticRestaurantController;

    private MockedStatic<HttpStatusResolver> mockedStaticHttpStatusResolver;

    private MockedStatic<RestaurantMapper> mockedStaticRestaurantMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private final JsonReaderUtil jsonReaderUtil = new JsonReaderUtil("infrastructure/api/controllers/restaurant");

    private final RestaurantObjectMother restaurantObjectMother = new RestaurantObjectMother();

    private static final String RESTAURANT_ID = "a238afda-dac3-4880-8769-b5be5141ce8b";

    private static final String RESTAURANT_LAST_UPDATED = "2025-08-03T13:27:35.081533224";

    @BeforeEach
    void setUp() {
        this.mockedStaticHttpStatusResolver = mockStatic(HttpStatusResolver.class);
        this.mockedStaticRestaurantController = mockStatic(RestaurantController.class);
        this.mockedStaticRestaurantController
                .when(() -> RestaurantController.create(any(RestaurantDataSourceJpa.class), any(UserDataSourceJpa.class)))
                .thenReturn(this.mockRestaurantController);
        this.mockedStaticRestaurantMapper = mockStatic(RestaurantMapper.class);
    }

    @AfterEach
    void tearDown() {
        if (this.mockedStaticRestaurantController != null) {
            this.mockedStaticRestaurantController.close();
        }
        if (this.mockedStaticHttpStatusResolver != null) {
            this.mockedStaticHttpStatusResolver.close();
        }
        if (this.mockedStaticRestaurantMapper != null) {
            this.mockedStaticRestaurantMapper.close();
        }
    }

    @Test
    void shouldCreateRestaurant() throws Exception {
        // Given
        final String requestBody = this.jsonReaderUtil.readJsonFromFile("new-restaurant-request-body.json");
        final String expectedResponseBody = this.jsonReaderUtil
                .readJsonFromFile("new-restaurant-response-body.json");
        final NewRestaurantDto mappedNewRestaurantDto = NewRestaurantDto.builder().build();
        this.mockedStaticRestaurantMapper
                .when(() -> RestaurantMapper.toNewRestaurantDto(any(NewRestaurantRequestDto.class)))
                .thenReturn(mappedNewRestaurantDto);
        final RestaurantOutputDto restaurantOutputDto = this.restaurantObjectMother
                .createSampleRestaurantOutputDto(this.objectMapper.readValue(requestBody, TreeMap.class))
                .toBuilder()
                .id(UUID.fromString(RESTAURANT_ID))
                .lastUpdated(LocalDateTime.parse(RESTAURANT_LAST_UPDATED))
                .build();
        when(this.mockRestaurantController.createRestaurant(mappedNewRestaurantDto)).thenReturn(restaurantOutputDto);

        //When & Then
        final MvcResult mvcResult = this.mockMvc
                .perform(
                        post("/restaurant")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();
        assertThat(this.objectMapper.readValue(mvcResult.getResponse().getContentAsString(), LinkedHashMap.class))
                .isEqualTo(this.objectMapper.readValue(expectedResponseBody, LinkedHashMap.class));
        verify(this.mockRestaurantController, times(1)).createRestaurant(mappedNewRestaurantDto);
    }

    @Test
    void shouldFindRestaurantById() throws Exception {
        // Given
        final String expectedResponseBody = this.jsonReaderUtil
                .readJsonFromFile("new-restaurant-response-body.json");
        final RestaurantOutputDto restaurantOutputDto = this.restaurantObjectMother
                .createSampleRestaurantOutputDto(this.objectMapper.readValue(expectedResponseBody, LinkedHashMap.class))
                .toBuilder()
                .id(UUID.fromString(RESTAURANT_ID))
                .lastUpdated(LocalDateTime.parse(RESTAURANT_LAST_UPDATED))
                .build();
        when(this.mockRestaurantController.retrieveRestaurantById(UUID.fromString(RESTAURANT_ID)))
                .thenReturn(restaurantOutputDto);

        //When & Then
        final MvcResult mvcResult = this.mockMvc
                .perform(get("/restaurant/{id}", RESTAURANT_ID))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(this.objectMapper.readValue(mvcResult.getResponse().getContentAsString(), LinkedHashMap.class))
                .isEqualTo(this.objectMapper.readValue(expectedResponseBody, LinkedHashMap.class));
        verify(this.mockRestaurantController, times(1))
                .retrieveRestaurantById(UUID.fromString(RESTAURANT_ID));
    }

    @Test
    void shouldUpdateRestaurant() throws Exception {
        // Given
        final String requestBody = this.jsonReaderUtil.readJsonFromFile("update-restaurant-request-body.json");
        final String expectedResponseBody = this.jsonReaderUtil.readJsonFromFile("update-restaurant-response-body.json");
        final UpdateRestaurantDto mappedUpdateRestaurantDto = UpdateRestaurantDto.builder().build();
        this.mockedStaticRestaurantMapper
                .when(() -> RestaurantMapper.toUpdateRestaurantDto(any(UpdateRestaurantRequestDto.class)))
                .thenReturn(mappedUpdateRestaurantDto);
        final RestaurantOutputDto restaurantOutputDto = this.restaurantObjectMother
                .createSampleRestaurantOutputDto(this.objectMapper.readValue(expectedResponseBody, LinkedHashMap.class))
                .toBuilder()
                .id(UUID.fromString(RESTAURANT_ID))
                .lastUpdated(LocalDateTime.parse(RESTAURANT_LAST_UPDATED))
                .build();
        when(this.mockRestaurantController.updateRestaurant(UUID.fromString(RESTAURANT_ID), mappedUpdateRestaurantDto))
                .thenReturn(restaurantOutputDto);

        //When & Then
        final MvcResult mvcResult = this.mockMvc
                .perform(
                        put("/restaurant/{id}", RESTAURANT_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(this.objectMapper.readValue(mvcResult.getResponse().getContentAsString(), LinkedHashMap.class))
                .isEqualTo(this.objectMapper.readValue(expectedResponseBody, LinkedHashMap.class));
        verify(this.mockRestaurantController, times(1))
                .updateRestaurant(UUID.fromString(RESTAURANT_ID), mappedUpdateRestaurantDto);
    }

    @Test
    void shouldDeleteRestaurantById() throws Exception {
        //When & Then
        this.mockMvc
                .perform(delete("/restaurant/{id}", RESTAURANT_ID))
                .andExpect(status().isNoContent());
        verify(this.mockRestaurantController, times(1))
                .deleteRestaurantById(UUID.fromString(RESTAURANT_ID));
    }
}
