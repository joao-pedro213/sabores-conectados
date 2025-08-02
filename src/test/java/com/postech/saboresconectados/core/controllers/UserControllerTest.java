package com.postech.saboresconectados.core.controllers;

import com.postech.saboresconectados.core.controller.UserController;
import com.postech.saboresconectados.core.domain.entities.User;
import com.postech.saboresconectados.core.domain.entities.enumerators.UserType;
import com.postech.saboresconectados.core.domain.usecases.ChangeUserPasswordUseCase;
import com.postech.saboresconectados.core.domain.usecases.CreateUserUseCase;
import com.postech.saboresconectados.core.domain.usecases.DeleteUserByIdUseCase;
import com.postech.saboresconectados.core.domain.usecases.LoginUserUseCase;
import com.postech.saboresconectados.core.domain.usecases.RetrieveUserByIdUseCase;
import com.postech.saboresconectados.core.domain.usecases.UpdateUserUseCase;
import com.postech.saboresconectados.core.dtos.NewUserDto;
import com.postech.saboresconectados.core.dtos.UpdateUserDto;
import com.postech.saboresconectados.core.dtos.UserOutputDto;
import com.postech.saboresconectados.core.gateways.UserGateway;
import com.postech.saboresconectados.core.interfaces.UserDataSource;
import com.postech.saboresconectados.core.presenters.UserPresenter;
import com.postech.saboresconectados.helpers.UserObjectMother;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

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

class UserControllerTest {
    @Mock
    private UserDataSource mockUserDataSource;

    @Mock
    private UserGateway mockUserGateway;

    private MockedStatic<UserGateway> mockedStaticUserGateway;

    @Mock
    private UserPresenter mockUserPresenter;

    private MockedStatic<UserPresenter> mockedStaticUserPresenter;

    @Mock
    private CreateUserUseCase mockCreateUserUseCase;

    private MockedStatic<CreateUserUseCase> mockedStaticCreateUserUseCase;

    @Mock
    private RetrieveUserByIdUseCase mockRetrieveUserByIdUseCase;

    private MockedStatic<RetrieveUserByIdUseCase> mockedStaticRetrieveUserByIdUseCase;

    @Mock
    private UpdateUserUseCase mockUpdateUserUseCase;

    private MockedStatic<UpdateUserUseCase> mockedStaticUpdateUserUseCase;

    @Mock
    private DeleteUserByIdUseCase mockDeleteUserByIdUseCase;

    private MockedStatic<DeleteUserByIdUseCase> mockedStaticDeleteUserByIdUseCase;

    @Mock
    private LoginUserUseCase mockLoginUserUseCase;

    private MockedStatic<LoginUserUseCase> mockedStaticLoginUserUseCase;

    @Mock
    private ChangeUserPasswordUseCase mockChangeUserPasswordUseCase;

    private MockedStatic<ChangeUserPasswordUseCase> mockedStaticChangeUserPasswordUseCase;

    @InjectMocks
    private UserController controller;

    private Map<String, Object> userSampleData;

    private final UserObjectMother userObjectMother = new UserObjectMother();

    private static final UUID ID = UUID.randomUUID();

    private static final LocalDateTime LAST_UPDATED = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockedStaticUserGateway = mockStatic(UserGateway.class);
        this.mockedStaticUserGateway
                .when(() -> UserGateway.create(this.mockUserDataSource))
                .thenReturn(this.mockUserGateway);
        this.mockedStaticUserPresenter = mockStatic(UserPresenter.class);
        this.mockedStaticUserPresenter.when(UserPresenter::create).thenReturn(this.mockUserPresenter);
        this.mockedStaticCreateUserUseCase = mockStatic(CreateUserUseCase.class);
        this.mockedStaticCreateUserUseCase
                .when(() -> CreateUserUseCase.create(this.mockUserGateway))
                .thenReturn(this.mockCreateUserUseCase);
        this.mockedStaticRetrieveUserByIdUseCase = mockStatic(RetrieveUserByIdUseCase.class);
        this.mockedStaticRetrieveUserByIdUseCase
                .when(() -> RetrieveUserByIdUseCase.create(this.mockUserGateway))
                .thenReturn(this.mockRetrieveUserByIdUseCase);
        this.mockedStaticUpdateUserUseCase = mockStatic(UpdateUserUseCase.class);
        this.mockedStaticUpdateUserUseCase
                .when(() -> UpdateUserUseCase.create(this.mockUserGateway))
                .thenReturn(this.mockUpdateUserUseCase);
        this.mockedStaticDeleteUserByIdUseCase = mockStatic(DeleteUserByIdUseCase.class);
        this.mockedStaticDeleteUserByIdUseCase
                .when(() -> DeleteUserByIdUseCase.create(this.mockUserGateway))
                .thenReturn(this.mockDeleteUserByIdUseCase);
        this.userSampleData = new LinkedHashMap<>();
        this.mockedStaticLoginUserUseCase = mockStatic(LoginUserUseCase.class);
        this.mockedStaticLoginUserUseCase
                .when(() -> LoginUserUseCase.create(this.mockUserGateway))
                .thenReturn(this.mockLoginUserUseCase);
        this.mockedStaticChangeUserPasswordUseCase = mockStatic(ChangeUserPasswordUseCase.class);
        this.mockedStaticChangeUserPasswordUseCase
                .when(() -> ChangeUserPasswordUseCase.create(this.mockUserGateway))
                .thenReturn(this.mockChangeUserPasswordUseCase);
        this.userSampleData.put("name", "Marcos");
        this.userSampleData.put("userType", UserType.CUSTOMER.getValue());
        this.userSampleData.put("email", "marcos@mail.com");
        this.userSampleData.put("login", "marcos635");
        this.userSampleData.put("password", "09231s121!G");
        this.userSampleData.put("address", "82495 Xavier Keys, Emersonburgh, KS 65336-8213");
    }

    @Test
    void shouldCreateUser() {
        // Given
        final NewUserDto newUserDto = this.userObjectMother.createSampleNewUserDto(this.userSampleData);
        final User createdUser = this.userObjectMother.createSampleUser(this.userSampleData);
        when(this.mockCreateUserUseCase.execute(any(User.class))).thenReturn(createdUser);
        final UserOutputDto createdUserOutputDto = UserOutputDto.builder().build();
        when(this.mockUserPresenter.toDto(createdUser)).thenReturn(createdUserOutputDto);

        // When
        final UserOutputDto userOutputDto = this.controller.createUser(newUserDto);

        // Then
        final ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        this.mockedStaticUserGateway.verify(() -> UserGateway.create(this.mockUserDataSource), times(1));
        this.mockedStaticCreateUserUseCase.verify(() -> CreateUserUseCase.create(this.mockUserGateway), times(1));
        verify(this.mockCreateUserUseCase, times(1)).execute(argument.capture());
        this.mockedStaticUserPresenter.verify(UserPresenter::create, times(1));
        verify(this.mockUserPresenter, times(1)).toDto(createdUser);
        final User capturedUser = argument.getValue();
        final User expectedUser = this.userObjectMother
                .createSampleUser(this.userSampleData)
                .toBuilder()
                .id(null)
                .lastUpdated(null)
                .build();
        assertThat(capturedUser)
                .usingRecursiveComparison()
                .isEqualTo(expectedUser);
        assertThat(userOutputDto).isNotNull().isEqualTo(createdUserOutputDto);
    }

    @Test
    void shouldRetrieveUserById() {
        // Given
        final User foundUser = this.userObjectMother
                .createSampleUser(this.userSampleData)
                .toBuilder()
                .id(ID)
                .lastUpdated(LAST_UPDATED)
                .build();
        when(this.mockRetrieveUserByIdUseCase.execute(ID)).thenReturn(foundUser);
        final UserOutputDto foundUserOutputDto = UserOutputDto.builder().build();
        when(this.mockUserPresenter.toDto(foundUser)).thenReturn(foundUserOutputDto);

        // When
        final UserOutputDto userOutputDto = this.controller.retrieveUserById(ID);

        // Then
        this.mockedStaticUserGateway.verify(() -> UserGateway.create(this.mockUserDataSource), times(1));
        this.mockedStaticRetrieveUserByIdUseCase
                .verify(() -> RetrieveUserByIdUseCase.create(this.mockUserGateway), times(1));
        verify(this.mockRetrieveUserByIdUseCase, times(1)).execute(ID);
        this.mockedStaticUserPresenter.verify(UserPresenter::create, times(1));
        verify(this.mockUserPresenter, times(1)).toDto(foundUser);
        assertThat(userOutputDto).isNotNull().isEqualTo(foundUserOutputDto);
    }

    @Test
    void shouldUpdateUser() {
        // Given
        final UpdateUserDto updateUserDto = this.userObjectMother.createSampleUpdateUserDto(this.userSampleData);
        final User updatedUser = this.userObjectMother.createSampleUser(this.userSampleData);
        when(
                this.mockUpdateUserUseCase
                        .execute(
                                ID,
                                updateUserDto.getName(),
                                updateUserDto.getEmail(),
                                updateUserDto.getAddress()))
                .thenReturn(updatedUser);
        final UserOutputDto updatedUserOutputDto = UserOutputDto.builder().build();
        when(this.mockUserPresenter.toDto(updatedUser)).thenReturn(updatedUserOutputDto);

        // When
        final UserOutputDto userOutputDto = this.controller.updateUser(ID, updateUserDto);

        // Then
        this.mockedStaticUserGateway.verify(() -> UserGateway.create(this.mockUserDataSource), times(1));
        this.mockedStaticUpdateUserUseCase.verify(() -> UpdateUserUseCase.create(this.mockUserGateway), times(1));
        verify(this.mockUpdateUserUseCase, times(1))
                .execute(
                        ID,
                        updateUserDto.getName(),
                        updateUserDto.getEmail(),
                        updateUserDto.getAddress());
        this.mockedStaticUserPresenter.verify(UserPresenter::create, times(1));
        verify(this.mockUserPresenter, times(1)).toDto(updatedUser);
        assertThat(userOutputDto).isNotNull().isEqualTo(updatedUserOutputDto);
    }

    @Test
    void shouldDeleteUserById() {
        // When
        this.controller.deleteUserById(ID);

        // Then
        this.mockedStaticUserGateway.verify(() -> UserGateway.create(this.mockUserDataSource), times(1));
        this.mockedStaticDeleteUserByIdUseCase
                .verify(() -> DeleteUserByIdUseCase.create(this.mockUserGateway), times(1));
        verify(this.mockDeleteUserByIdUseCase, times(1)).execute(ID);
    }

    @Test
    void shouldLoginUser() {
        // Given
        final String login = this.userSampleData.get("login").toString();
        final String password = this.userSampleData.get("password").toString();

        // When
        this.controller.loginUser(login, password);

        // Then
        this.mockedStaticUserGateway.verify(() -> UserGateway.create(this.mockUserDataSource), times(1));
        this.mockedStaticLoginUserUseCase
                .verify(() -> LoginUserUseCase.create(this.mockUserGateway), times(1));
        verify(this.mockLoginUserUseCase, times(1)).execute(login, password);
    }

    @Test
    void shouldChangeUserPassword() {
        // Given
        final String login = this.userSampleData.get("login").toString();
        final String oldPassword = this.userSampleData.get("password").toString();
        final String newPassword = "MyN3wP4s$";

        // When
        this.controller.changeUserPassword(login, oldPassword, newPassword);

        // Then
        this.mockedStaticUserGateway.verify(() -> UserGateway.create(this.mockUserDataSource), times(1));
        this.mockedStaticChangeUserPasswordUseCase
                .verify(() -> ChangeUserPasswordUseCase.create(this.mockUserGateway), times(1));
        verify(this.mockChangeUserPasswordUseCase, times(1)).execute(login, oldPassword, newPassword);
    }

    @AfterEach
    void tearDown() {
        if (this.mockedStaticUserGateway != null) {
            this.mockedStaticUserGateway.close();
        }
        if (this.mockedStaticUserPresenter != null) {
            this.mockedStaticUserPresenter.close();
        }
        if (this.mockedStaticCreateUserUseCase != null) {
            this.mockedStaticCreateUserUseCase.close();
        }
        if (this.mockedStaticRetrieveUserByIdUseCase != null) {
            this.mockedStaticRetrieveUserByIdUseCase.close();
        }
        if (this.mockedStaticUpdateUserUseCase != null) {
            this.mockedStaticUpdateUserUseCase.close();
        }
        if (this.mockedStaticDeleteUserByIdUseCase != null) {
            this.mockedStaticDeleteUserByIdUseCase.close();
        }
        if (this.mockedStaticLoginUserUseCase != null) {
            this.mockedStaticLoginUserUseCase.close();
        }
        if (this.mockedStaticChangeUserPasswordUseCase != null) {
            this.mockedStaticChangeUserPasswordUseCase.close();
        }
    }
}
