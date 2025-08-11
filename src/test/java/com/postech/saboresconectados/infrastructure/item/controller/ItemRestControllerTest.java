package com.postech.saboresconectados.infrastructure.item.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.saboresconectados.core.item.controller.ItemController;
import com.postech.saboresconectados.core.item.dto.ItemDto;
import com.postech.saboresconectados.core.item.dto.NewItemDto;
import com.postech.saboresconectados.core.item.dto.UpdateItemDto;
import com.postech.saboresconectados.core.item.datasource.ItemDataSource;
import com.postech.saboresconectados.helpers.ItemObjectMother;
import com.postech.saboresconectados.helpers.JsonReaderUtil;
import com.postech.saboresconectados.infrastructure.api.config.SecurityConfig;
import com.postech.saboresconectados.infrastructure.api.exception.HttpStatusResolver;
import com.postech.saboresconectados.infrastructure.item.dto.ItemResponseDto;
import com.postech.saboresconectados.infrastructure.item.dto.NewItemRequestDto;
import com.postech.saboresconectados.infrastructure.item.dto.UpdateItemRequestDto;
import com.postech.saboresconectados.infrastructure.item.data.ItemDataSourceJpa;
import com.postech.saboresconectados.infrastructure.restaurant.data.RestaurantDataSourceJpa;
import com.postech.saboresconectados.infrastructure.item.data.mapper.ItemMapper;
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(ItemRestController.class)
class ItemRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemDataSourceJpa mockItemDataSourceJpa;

    @MockitoBean
    private RestaurantDataSourceJpa mockRestaurantDataSourceJpa;

    @Mock
    private ItemController mockItemController;

    private MockedStatic<ItemController> mockedStaticItemController;

    private MockedStatic<HttpStatusResolver> mockedStaticHttpStatusResolver;

    private MockedStatic<ItemMapper> mockedStaticItemMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private final JsonReaderUtil jsonReaderUtil = new JsonReaderUtil("infrastructure/item/controller");

    private static final UUID ITEM_ID = UUID.fromString("f954b6dd-71bf-4fc0-b7b2-4cf88eaccfce");

    private static final LocalDateTime ITEM_LAST_UPDATED = LocalDateTime.parse("2025-08-04T22:56:49.388455306");

    @BeforeEach
    void setUp() {
        this.mockedStaticHttpStatusResolver = mockStatic(HttpStatusResolver.class);
        this.mockedStaticItemController = mockStatic(ItemController.class);
        this.mockedStaticItemController
                .when(() -> ItemController.build(any(ItemDataSource.class), any(RestaurantDataSourceJpa.class)))
                .thenReturn(this.mockItemController);
        this.mockedStaticItemMapper = mockStatic(ItemMapper.class);
    }

    @AfterEach
    void tearDown() {
        if (this.mockedStaticItemController != null) {
            this.mockedStaticItemController.close();
        }
        if (this.mockedStaticHttpStatusResolver != null) {
            this.mockedStaticHttpStatusResolver.close();
        }
        if (this.mockedStaticItemMapper != null) {
            this.mockedStaticItemMapper.close();
        }
    }

    @Test
    void shouldCreateItem() throws Exception {
        // Given
        final String requestBody = this.jsonReaderUtil.readJsonFromFile("new-item-request-body.json");
        final String expectedResponseBody = this.jsonReaderUtil.readJsonFromFile("new-item-response-body.json");
        final NewItemDto mappedNewItemDto = NewItemDto.builder().build();
        this.mockedStaticItemMapper
                .when(() -> ItemMapper.toNewItemDto(any(NewItemRequestDto.class)))
                .thenReturn(mappedNewItemDto);
        final ItemDto createdItemDto = ItemDto.builder().build();
        when(this.mockItemController.createItem(mappedNewItemDto)).thenReturn(createdItemDto);
        final ItemResponseDto itemResponseDto = ItemObjectMother
                .buildItemResponseDto(this.objectMapper.readValue(requestBody, HashMap.class))
                .toBuilder()
                .id(ITEM_ID)
                .lastUpdated(ITEM_LAST_UPDATED)
                .build();
        this.mockedStaticItemMapper
                .when(() -> ItemMapper.toItemResponseDto(any(ItemDto.class)))
                .thenReturn(itemResponseDto);

        //When & Then
        final MvcResult mvcResult = this.mockMvc
                .perform(
                        post("/item")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();
        assertThat(this.objectMapper.readValue(mvcResult.getResponse().getContentAsString(), LinkedHashMap.class))
                .isEqualTo(this.objectMapper.readValue(expectedResponseBody, LinkedHashMap.class));
        verify(this.mockItemController, times(1)).createItem(any(NewItemDto.class));
    }

    @Test
    void shouldUpdateRestaurant() throws Exception {
        // Given
        final String requestBody = this.jsonReaderUtil.readJsonFromFile("update-item-request-body.json");
        final String expectedResponseBody = this.jsonReaderUtil.readJsonFromFile("update-item-response-body.json");
        final UpdateItemDto mappedUpdateItemDto = UpdateItemDto.builder().build();
        this.mockedStaticItemMapper
                .when(() -> ItemMapper.toUpdateItemDto(any(UpdateItemRequestDto.class)))
                .thenReturn(mappedUpdateItemDto);
        final ItemDto updatedItemDto = ItemDto.builder().build();
        when(this.mockItemController.updateItem(ITEM_ID, mappedUpdateItemDto)).thenReturn(updatedItemDto);
        final ItemResponseDto itemResponseDto = ItemObjectMother
                .buildItemResponseDto(this.objectMapper.readValue(expectedResponseBody, LinkedHashMap.class))
                .toBuilder()
                .id(ITEM_ID)
                .lastUpdated(ITEM_LAST_UPDATED)
                .build();
        this.mockedStaticItemMapper
                .when(() -> ItemMapper.toItemResponseDto(any(ItemDto.class)))
                .thenReturn(itemResponseDto);

        //When & Then
        final MvcResult mvcResult = this.mockMvc
                .perform(
                        put("/item/{id}", ITEM_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(this.objectMapper.readValue(mvcResult.getResponse().getContentAsString(), LinkedHashMap.class))
                .isEqualTo(this.objectMapper.readValue(expectedResponseBody, LinkedHashMap.class));
        verify(this.mockItemController, times(1)).updateItem(ITEM_ID, mappedUpdateItemDto);
    }

    @Test
    void shouldDeleteItemById() throws Exception {
        //When & Then
        this.mockMvc
                .perform(delete("/item/{id}", ITEM_ID))
                .andExpect(status().isNoContent());
        verify(this.mockItemController, times(1)).deleteItemById(ITEM_ID);
    }
}
