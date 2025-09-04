package com.postech.saboresconectados.infrastructure.restaurant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.saboresconectados.core.restaurant.controller.RestaurantController;
import com.postech.saboresconectados.core.item.dto.ItemDto;
import com.postech.saboresconectados.core.restaurant.dto.NewRestaurantDto;
import com.postech.saboresconectados.core.restaurant.dto.RestaurantDto;
import com.postech.saboresconectados.core.restaurant.dto.UpdateRestaurantDto;
import com.postech.saboresconectados.core.item.datasource.ItemDataSource;
import com.postech.saboresconectados.helpers.ItemObjectMother;
import com.postech.saboresconectados.helpers.JsonReaderUtil;
import com.postech.saboresconectados.helpers.RestaurantObjectMother;
import com.postech.saboresconectados.infrastructure.api.config.SecurityConfig;
import com.postech.saboresconectados.infrastructure.api.exception.HttpStatusResolver;
import com.postech.saboresconectados.infrastructure.item.dto.ItemResponseDto;
import com.postech.saboresconectados.infrastructure.restaurant.dto.NewRestaurantRequestDto;
import com.postech.saboresconectados.infrastructure.restaurant.dto.RestaurantResponseDto;
import com.postech.saboresconectados.infrastructure.restaurant.dto.UpdateRestaurantRequestDto;
import com.postech.saboresconectados.infrastructure.item.data.ItemDataSourceJpa;
import com.postech.saboresconectados.infrastructure.restaurant.data.RestaurantDataSourceJpa;
import com.postech.saboresconectados.infrastructure.user.data.UserDataSourceJpa;
import com.postech.saboresconectados.infrastructure.item.data.mapper.ItemMapper;
import com.postech.saboresconectados.infrastructure.restaurant.data.mapper.RestaurantMapper;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    private ItemDataSourceJpa mockItemDataSourceJpa;

    @MockitoBean
    private UserDataSourceJpa mockUserDataSourceJpa;

    @Mock
    private RestaurantController mockRestaurantController;

    private MockedStatic<RestaurantController> mockedStaticRestaurantController;

    private MockedStatic<HttpStatusResolver> mockedStaticHttpStatusResolver;

    private MockedStatic<RestaurantMapper> mockedStaticRestaurantMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private final JsonReaderUtil jsonReaderUtil = new JsonReaderUtil("infrastructure/restaurant/controller");

    private static final String RESTAURANT_ID = "a238afda-dac3-4880-8769-b5be5141ce8b";

    private static final String RESTAURANT_LAST_UPDATED = "2025-08-03T13:27:35.081533224";

    @BeforeEach
    void setUp() {
        this.mockedStaticHttpStatusResolver = mockStatic(HttpStatusResolver.class);
        this.mockedStaticRestaurantController = mockStatic(RestaurantController.class);
        this.mockedStaticRestaurantController
                .when(() -> RestaurantController.build(any(RestaurantDataSourceJpa.class), any(UserDataSourceJpa.class), any(ItemDataSource.class)))
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
        final RestaurantDto createdRestaurantDto = RestaurantDto.builder().build();
        when(mockRestaurantController.createRestaurant(mappedNewRestaurantDto)).thenReturn(createdRestaurantDto);
        final RestaurantResponseDto restaurantResponseDto = RestaurantObjectMother
                .buildRestaurantResponseDto(this.objectMapper.readValue(requestBody, TreeMap.class))
                .toBuilder()
                .id(UUID.fromString(RESTAURANT_ID))
                .lastUpdated(LocalDateTime.parse(RESTAURANT_LAST_UPDATED))
                .build();
        this.mockedStaticRestaurantMapper
                .when(() -> RestaurantMapper.toRestaurantResponseDto(any(RestaurantDto.class)))
                .thenReturn(restaurantResponseDto);

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
        verify(this.mockRestaurantController, times(1)).createRestaurant(any(NewRestaurantDto.class));
    }

    @Test
    void shouldFindRestaurantById() throws Exception {
        // Given
        final String expectedResponseBody = this.jsonReaderUtil
                .readJsonFromFile("new-restaurant-response-body.json");
        final RestaurantDto foundRestaurant = RestaurantDto.builder().build();
        when(this.mockRestaurantController.retrieveRestaurantById(UUID.fromString(RESTAURANT_ID)))
                .thenReturn(foundRestaurant);
        final RestaurantResponseDto restaurantResponseDto = RestaurantObjectMother
                .buildRestaurantResponseDto(this.objectMapper.readValue(expectedResponseBody, LinkedHashMap.class))
                .toBuilder()
                .id(UUID.fromString(RESTAURANT_ID))
                .lastUpdated(LocalDateTime.parse(RESTAURANT_LAST_UPDATED))
                .build();
        this.mockedStaticRestaurantMapper
                .when(() -> RestaurantMapper.toRestaurantResponseDto(any(RestaurantDto.class)))
                .thenReturn(restaurantResponseDto);

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
        final RestaurantDto updatedRestaurantDto = RestaurantDto.builder().build();
        when(this.mockRestaurantController.updateRestaurant(UUID.fromString(RESTAURANT_ID), mappedUpdateRestaurantDto))
                .thenReturn(updatedRestaurantDto);
        final RestaurantResponseDto restaurantResponseDto = RestaurantObjectMother
                .buildRestaurantResponseDto(this.objectMapper.readValue(expectedResponseBody, LinkedHashMap.class))
                .toBuilder()
                .id(UUID.fromString(RESTAURANT_ID))
                .lastUpdated(LocalDateTime.parse(RESTAURANT_LAST_UPDATED))
                .build();
        this.mockedStaticRestaurantMapper
                .when(() -> RestaurantMapper.toRestaurantResponseDto(any(RestaurantDto.class)))
                .thenReturn(restaurantResponseDto);

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

    @Test
    void shouldRetrieveMenu() throws Exception {
        // Given
        try (MockedStatic<ItemMapper> mockedStaticItemMapper = mockStatic(ItemMapper.class)) {
            Map<String, Object> itemSampleData = new LinkedHashMap<>();
            itemSampleData.put("id", UUID.randomUUID().toString());
            itemSampleData.put("restaurantId", UUID.randomUUID().toString());
            itemSampleData.put("name", "Pepperoni Pizza");
            itemSampleData.put("description", "A delicious Pepperoni Pizza");
            itemSampleData.put("price", BigDecimal.valueOf(25.0));
            itemSampleData.put("availableOnlyAtRestaurant", false);
            itemSampleData.put("photoPath", "/peperoni-pizza.jpg");
            itemSampleData.put("lastUpdated", LocalDateTime.parse("2025-08-04T00:00").toString());
            final ItemDto itemFound = ItemObjectMother.buildItemDto(itemSampleData);
            when(this.mockRestaurantController.retrieveItemsByRestaurantId(UUID.fromString(RESTAURANT_ID)))
                    .thenReturn(List.of(itemFound));
            final ItemResponseDto itemResponseDto = ItemObjectMother.buildItemResponseDto(itemSampleData);
            mockedStaticItemMapper.when(() -> ItemMapper.toItemResponseDto(any(ItemDto.class)))
                    .thenReturn(itemResponseDto);

            // When & Then
            final MvcResult mvcResult = this.mockMvc
                    .perform(get("/restaurant/{id}/menu", RESTAURANT_ID))
                    .andExpect(status().isOk())
                    .andReturn();
            LinkedHashMap<String, Object> response = (LinkedHashMap<String, Object>) this.objectMapper
                    .readValue(mvcResult.getResponse().getContentAsString(), List.class)
                    .getFirst();
            assertThat(response.get("id")).isEqualTo(itemSampleData.get("id"));
            assertThat(response.get("restaurantId")).isEqualTo(itemSampleData.get("restaurantId"));
            assertThat(response.get("name")).isEqualTo(itemSampleData.get("name"));
            assertThat(response.get("description")).isEqualTo(itemSampleData.get("description"));
            assertThat(response.get("price").toString()).isEqualTo(itemSampleData.get("price").toString());
            assertThat((Boolean) response.get("availableOnlyAtRestaurant")).isEqualTo(itemSampleData.get("availableOnlyAtRestaurant"));
            verify(this.mockRestaurantController, times(1)).retrieveItemsByRestaurantId(UUID.fromString(RESTAURANT_ID));
        }
    }
}
