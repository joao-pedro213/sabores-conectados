package com.postech.saboresconectados.infrastructure.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.saboresconectados.core.user.controller.UserController;
import com.postech.saboresconectados.core.common.exception.BusinessException;
import com.postech.saboresconectados.core.user.dto.NewUserDto;
import com.postech.saboresconectados.core.user.dto.UpdateUserDto;
import com.postech.saboresconectados.core.user.dto.UserDto;
import com.postech.saboresconectados.helpers.JsonReaderUtil;
import com.postech.saboresconectados.helpers.UserObjectMother;
import com.postech.saboresconectados.infrastructure.api.config.SecurityConfig;
import com.postech.saboresconectados.infrastructure.api.exception.HttpStatusResolver;
import com.postech.saboresconectados.infrastructure.user.dto.NewUserRequestDto;
import com.postech.saboresconectados.infrastructure.user.dto.UpdateUserRequestDto;
import com.postech.saboresconectados.infrastructure.user.data.UserDataSourceJpa;
import com.postech.saboresconectados.infrastructure.user.data.mapper.UserMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
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
@WebMvcTest(UserRestController.class)
class UserRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserDataSourceJpa mockUserDataSourceJpa;

    @Mock
    private UserController mockUserController;

    private MockedStatic<UserController> mockedStaticUserController;

    private MockedStatic<HttpStatusResolver> mockedStaticHttpStatusResolver;

    private MockedStatic<UserMapper> mockedStaticUserMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private final JsonReaderUtil jsonReaderUtil = new JsonReaderUtil("infrastructure/user/controller");

    private final UserObjectMother userObjectMother = new UserObjectMother();

    private static final String ID = "dc92890a-a32c-43d9-adf1-9e9662825b77";

    private static final String LAST_UPDATED = "2025-07-28T21:28:53.624495217";

    @BeforeEach
    void setUp() {
        this.mockedStaticHttpStatusResolver = mockStatic(HttpStatusResolver.class);
        this.mockedStaticUserController = mockStatic(UserController.class);
        this.mockedStaticUserController
                .when(() -> UserController.build(any(UserDataSourceJpa.class)))
                .thenReturn(this.mockUserController);
        this.mockedStaticUserMapper = mockStatic(UserMapper.class);
    }

    @AfterEach
    void tearDown() {
        if (this.mockedStaticHttpStatusResolver != null) {
            this.mockedStaticHttpStatusResolver.close();
        }
        if (this.mockedStaticUserController != null) {
            this.mockedStaticUserController.close();
        }
        if (this.mockedStaticUserMapper != null) {
            this.mockedStaticUserMapper.close();
        }
    }

    @Test
    void shouldCreateUser() throws Exception {
        // Given
        final String requestBody = this.jsonReaderUtil.readJsonFromFile("new-user-request-body.json");
        final String expectedResponseBody = this.jsonReaderUtil.readJsonFromFile("new-user-response-body.json");
        final NewUserDto mappedNewUserDto = NewUserDto.builder().build();
        this.mockedStaticUserMapper
                .when(() -> UserMapper.toNewUserDto(any(NewUserRequestDto.class)))
                .thenReturn(mappedNewUserDto);
        final UserDto userDto = this.userObjectMother
                .createSampleUserDto(this.objectMapper.readValue(requestBody, LinkedHashMap.class))
                .toBuilder()
                .id(UUID.fromString(ID))
                .password("*********")
                .lastUpdated(LocalDateTime.parse(LAST_UPDATED))
                .build();
        when(this.mockUserController.createUser(mappedNewUserDto)).thenReturn(userDto);

        //When & Then
        final MvcResult mvcResult = this.mockMvc
                .perform(
                        post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();
        assertThat(this.objectMapper.readValue(mvcResult.getResponse().getContentAsString(), LinkedHashMap.class))
                .isEqualTo(this.objectMapper.readValue(expectedResponseBody, LinkedHashMap.class));
        verify(this.mockUserController, times(1)).createUser(mappedNewUserDto);
    }

    @Test
    void shouldReturnBadRequestWhenRequiredBodyParametersAreMissing() throws Exception {
        // Given
        final String requestBody = this.jsonReaderUtil
                .readJsonFromFile("new-user-request-body-without-required-parameters.json");
        final String expectedResponseBody = this.jsonReaderUtil
                .readJsonFromFile("response-body-when-required-parameters-are-missing.json");

        //When & Then
        final MvcResult mvcResult = this.mockMvc
                .perform(
                        post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertThat(this.objectMapper.readValue(mvcResult.getResponse().getContentAsString(), LinkedHashMap.class))
                .isEqualTo(this.objectMapper.readValue(expectedResponseBody, LinkedHashMap.class));
    }

    @Test
    void shouldReturnBadRequestWhenBusinessExceptionIsThrown() throws Exception {
        // Given
        final String requestBody = this.jsonReaderUtil.readJsonFromFile("new-user-request-body.json");
        final String expectedResponseBody = this.jsonReaderUtil
                .readJsonFromFile("response-body-when-business-exception-is-raised.json");
        final NewUserDto mappedNewUserDto = NewUserDto.builder().build();
        this.mockedStaticUserMapper
                .when(() -> UserMapper.toNewUserDto(any(NewUserRequestDto.class)))
                .thenReturn(mappedNewUserDto);
        when(this.mockUserController.createUser(mappedNewUserDto))
                .thenThrow(new BusinessException("Business rule violation"));
        this.mockedStaticHttpStatusResolver
                .when(() -> HttpStatusResolver.resolveHttpStatusForBusinessException(any(BusinessException.class)))
                .thenReturn(HttpStatus.BAD_REQUEST);

        //When & Then
        final MvcResult mvcResult = this.mockMvc
                .perform(
                        post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertThat(this.objectMapper.readValue(mvcResult.getResponse().getContentAsString(), LinkedHashMap.class))
                .isEqualTo(this.objectMapper.readValue(expectedResponseBody, LinkedHashMap.class));
    }

    @Test
    void shouldFindUserById() throws Exception {
        // Given
        final String expectedResponseBody = this.jsonReaderUtil.readJsonFromFile("new-user-response-body.json");
        final UserDto userDto = this.userObjectMother
                .createSampleUserDto(this.objectMapper.readValue(expectedResponseBody, LinkedHashMap.class))
                .toBuilder()
                .id(UUID.fromString(ID))
                .lastUpdated(LocalDateTime.parse(LAST_UPDATED))
                .build();
        when(this.mockUserController.retrieveUserById(UUID.fromString(ID))).thenReturn(userDto);

        //When & Then
        final MvcResult mvcResult = this.mockMvc
                .perform(get("/user/{id}", ID))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(this.objectMapper.readValue(mvcResult.getResponse().getContentAsString(), LinkedHashMap.class))
                .isEqualTo(this.objectMapper.readValue(expectedResponseBody, LinkedHashMap.class));
        verify(this.mockUserController, times(1)).retrieveUserById(UUID.fromString(ID));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        // Given
        final String requestBody = this.jsonReaderUtil.readJsonFromFile("update-user-request-body.json");
        final String expectedResponseBody = this.jsonReaderUtil.readJsonFromFile("update-user-response-body.json");
        final UpdateUserDto mappedUpdateUserDto = UpdateUserDto.builder().build();
        this.mockedStaticUserMapper
                .when(() -> UserMapper.toUpdateUserDto(any(UpdateUserRequestDto.class)))
                .thenReturn(mappedUpdateUserDto);
        final UserDto userDto = this.userObjectMother
                .createSampleUserDto(this.objectMapper.readValue(expectedResponseBody, LinkedHashMap.class))
                .toBuilder()
                .id(UUID.fromString(ID))
                .lastUpdated(LocalDateTime.parse(LAST_UPDATED))
                .build();
        when(this.mockUserController.updateUser(UUID.fromString(ID), mappedUpdateUserDto)).thenReturn(userDto);

        //When & Then
        final MvcResult mvcResult = this.mockMvc
                .perform(
                        put("/user/{id}", ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(this.objectMapper.readValue(mvcResult.getResponse().getContentAsString(), LinkedHashMap.class))
                .isEqualTo(this.objectMapper.readValue(expectedResponseBody, LinkedHashMap.class));
        verify(this.mockUserController, times(1))
                .updateUser(UUID.fromString(ID), mappedUpdateUserDto);
    }

    @Test
    void shouldDeleteUserById() throws Exception {
        //When & Then
        this.mockMvc
                .perform(delete("/user/{id}", ID))
                .andExpect(status().isNoContent());
        verify(this.mockUserController, times(1)).deleteUserById(UUID.fromString(ID));
    }

    @Test
    void shouldLoginUser() throws Exception {
        // Given
        final String requestBody = this.jsonReaderUtil.readJsonFromFile("login-user-request-body.json");

        //When & Then
        this.mockMvc
                .perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
        final Map<String, String> requestBodyMap = this.objectMapper.readValue(requestBody, LinkedHashMap.class);
        verify(this.mockUserController, times(1))
                .loginUser(requestBodyMap.get("login"), requestBodyMap.get("password"));
    }

    @Test
    void shouldChangeUserPassword() throws Exception {
        // Given
        final String requestBody = this.jsonReaderUtil.readJsonFromFile("change-user-password-request-body.json");

        //When & Then
        this.mockMvc
                .perform(post("/user/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNoContent());
        final Map<String, String> requestBodyMap = this.objectMapper.readValue(requestBody, LinkedHashMap.class);
        verify(this.mockUserController, times(1))
                .changeUserPassword(
                        requestBodyMap.get("login"),
                        requestBodyMap.get("oldPassword"),
                        requestBodyMap.get("newPassword"));
    }
}
