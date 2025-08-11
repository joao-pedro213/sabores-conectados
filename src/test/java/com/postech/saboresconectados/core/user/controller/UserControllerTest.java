package com.postech.saboresconectados.core.user.controller;

import com.postech.saboresconectados.core.user.domain.entity.UserEntity;
import com.postech.saboresconectados.core.user.domain.entity.enumerator.UserType;
import com.postech.saboresconectados.core.user.domain.usecase.ChangeUserPasswordUseCase;
import com.postech.saboresconectados.core.user.domain.usecase.CreateUserUseCase;
import com.postech.saboresconectados.core.user.domain.usecase.DeleteUserByIdUseCase;
import com.postech.saboresconectados.core.user.domain.usecase.LoginUserUseCase;
import com.postech.saboresconectados.core.user.domain.usecase.RetrieveUserByIdUseCase;
import com.postech.saboresconectados.core.user.domain.usecase.UpdateUserUseCase;
import com.postech.saboresconectados.core.user.dto.NewUserDto;
import com.postech.saboresconectados.core.user.dto.UpdateUserDto;
import com.postech.saboresconectados.core.user.dto.UserDto;
import com.postech.saboresconectados.core.user.gateway.UserGateway;
import com.postech.saboresconectados.core.user.datasource.UserDataSource;
import com.postech.saboresconectados.core.user.presenter.UserPresenter;
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
                .when(() -> UserGateway.build(this.mockUserDataSource))
                .thenReturn(this.mockUserGateway);
        this.mockedStaticUserPresenter = mockStatic(UserPresenter.class);
        this.mockedStaticUserPresenter.when(UserPresenter::build).thenReturn(this.mockUserPresenter);
        this.mockedStaticCreateUserUseCase = mockStatic(CreateUserUseCase.class);
        this.mockedStaticCreateUserUseCase
                .when(() -> CreateUserUseCase.build(this.mockUserGateway))
                .thenReturn(this.mockCreateUserUseCase);
        this.mockedStaticRetrieveUserByIdUseCase = mockStatic(RetrieveUserByIdUseCase.class);
        this.mockedStaticRetrieveUserByIdUseCase
                .when(() -> RetrieveUserByIdUseCase.build(this.mockUserGateway))
                .thenReturn(this.mockRetrieveUserByIdUseCase);
        this.mockedStaticUpdateUserUseCase = mockStatic(UpdateUserUseCase.class);
        this.mockedStaticUpdateUserUseCase
                .when(() -> UpdateUserUseCase.build(this.mockUserGateway))
                .thenReturn(this.mockUpdateUserUseCase);
        this.mockedStaticDeleteUserByIdUseCase = mockStatic(DeleteUserByIdUseCase.class);
        this.mockedStaticDeleteUserByIdUseCase
                .when(() -> DeleteUserByIdUseCase.build(this.mockUserGateway))
                .thenReturn(this.mockDeleteUserByIdUseCase);
        this.mockedStaticLoginUserUseCase = mockStatic(LoginUserUseCase.class);
        this.mockedStaticLoginUserUseCase
                .when(() -> LoginUserUseCase.build(this.mockUserGateway))
                .thenReturn(this.mockLoginUserUseCase);
        this.mockedStaticChangeUserPasswordUseCase = mockStatic(ChangeUserPasswordUseCase.class);
        this.mockedStaticChangeUserPasswordUseCase
                .when(() -> ChangeUserPasswordUseCase.build(this.mockUserGateway))
                .thenReturn(this.mockChangeUserPasswordUseCase);
        this.userSampleData = new LinkedHashMap<>();
        this.userSampleData.put("name", "Marcos");
        this.userSampleData.put("userType", UserType.CUSTOMER.getValue());
        this.userSampleData.put("email", "marcos@mail.com");
        this.userSampleData.put("login", "marcos635");
        this.userSampleData.put("password", "09231s121!G");
        this.userSampleData.put("address", "82495 Xavier Keys, Emersonburgh, KS 65336-8213");
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

    @Test
    void shouldCreateUser() {
        // Given
        final NewUserDto newUserDto = this.userObjectMother.createSampleNewUserDto(this.userSampleData);
        final UserEntity createdUserEntity = this.userObjectMother.createSampleUser(this.userSampleData);
        when(this.mockCreateUserUseCase.execute(any(UserEntity.class))).thenReturn(createdUserEntity);
        final UserDto createdUserDto = UserDto.builder().build();
        when(this.mockUserPresenter.toDto(createdUserEntity)).thenReturn(createdUserDto);

        // When
        final UserDto userDto = this.controller.createUser(newUserDto);

        // Then
        final ArgumentCaptor<UserEntity> argument = ArgumentCaptor.forClass(UserEntity.class);
        this.mockedStaticUserGateway.verify(() -> UserGateway.build(this.mockUserDataSource), times(1));
        this.mockedStaticCreateUserUseCase.verify(() -> CreateUserUseCase.build(this.mockUserGateway), times(1));
        verify(this.mockCreateUserUseCase, times(1)).execute(argument.capture());
        this.mockedStaticUserPresenter.verify(UserPresenter::build, times(1));
        verify(this.mockUserPresenter, times(1)).toDto(createdUserEntity);
        final UserEntity capturedUserEntity = argument.getValue();
        final UserEntity expectedUserEntity = createdUserEntity
                .toBuilder()
                .id(null)
                .lastUpdated(null)
                .build();
        assertThat(capturedUserEntity).usingRecursiveComparison().isEqualTo(expectedUserEntity);
        assertThat(userDto).isNotNull().isEqualTo(createdUserDto);
    }

    @Test
    void shouldRetrieveUserById() {
        // Given
        final UserEntity foundUserEntity = this.userObjectMother
                .createSampleUser(this.userSampleData)
                .toBuilder()
                .id(ID)
                .lastUpdated(LAST_UPDATED)
                .build();
        when(this.mockRetrieveUserByIdUseCase.execute(ID)).thenReturn(foundUserEntity);
        final UserDto foundUserDto = UserDto.builder().build();
        when(this.mockUserPresenter.toDto(foundUserEntity)).thenReturn(foundUserDto);

        // When
        final UserDto userDto = this.controller.retrieveUserById(ID);

        // Then
        this.mockedStaticUserGateway.verify(() -> UserGateway.build(this.mockUserDataSource), times(1));
        this.mockedStaticRetrieveUserByIdUseCase
                .verify(() -> RetrieveUserByIdUseCase.build(this.mockUserGateway), times(1));
        verify(this.mockRetrieveUserByIdUseCase, times(1)).execute(ID);
        this.mockedStaticUserPresenter.verify(UserPresenter::build, times(1));
        verify(this.mockUserPresenter, times(1)).toDto(foundUserEntity);
        assertThat(userDto).isNotNull().isEqualTo(foundUserDto);
    }

    @Test
    void shouldUpdateUser() {
        // Given
        final UpdateUserDto updateUserDto = this.userObjectMother.createSampleUpdateUserDto(this.userSampleData);
        final UserEntity updatedUserEntity = this.userObjectMother.createSampleUser(this.userSampleData);
        when(
                this.mockUpdateUserUseCase
                        .execute(
                                ID,
                                updateUserDto.getName(),
                                updateUserDto.getEmail(),
                                updateUserDto.getAddress()))
                .thenReturn(updatedUserEntity);
        final UserDto updatedUserDto = UserDto.builder().build();
        when(this.mockUserPresenter.toDto(updatedUserEntity)).thenReturn(updatedUserDto);

        // When
        final UserDto userDto = this.controller.updateUser(ID, updateUserDto);

        // Then
        this.mockedStaticUserGateway.verify(() -> UserGateway.build(this.mockUserDataSource), times(1));
        this.mockedStaticUpdateUserUseCase.verify(() -> UpdateUserUseCase.build(this.mockUserGateway), times(1));
        verify(this.mockUpdateUserUseCase, times(1))
                .execute(
                        ID,
                        updateUserDto.getName(),
                        updateUserDto.getEmail(),
                        updateUserDto.getAddress());
        this.mockedStaticUserPresenter.verify(UserPresenter::build, times(1));
        verify(this.mockUserPresenter, times(1)).toDto(updatedUserEntity);
        assertThat(userDto).isNotNull().isEqualTo(updatedUserDto);
    }

    @Test
    void shouldDeleteUserById() {
        // When
        this.controller.deleteUserById(ID);

        // Then
        this.mockedStaticUserGateway.verify(() -> UserGateway.build(this.mockUserDataSource), times(1));
        this.mockedStaticDeleteUserByIdUseCase
                .verify(() -> DeleteUserByIdUseCase.build(this.mockUserGateway), times(1));
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
        this.mockedStaticUserGateway.verify(() -> UserGateway.build(this.mockUserDataSource), times(1));
        this.mockedStaticLoginUserUseCase
                .verify(() -> LoginUserUseCase.build(this.mockUserGateway), times(1));
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
        this.mockedStaticUserGateway.verify(() -> UserGateway.build(this.mockUserDataSource), times(1));
        this.mockedStaticChangeUserPasswordUseCase
                .verify(() -> ChangeUserPasswordUseCase.build(this.mockUserGateway), times(1));
        verify(this.mockChangeUserPasswordUseCase, times(1)).execute(login, oldPassword, newPassword);
    }
}
