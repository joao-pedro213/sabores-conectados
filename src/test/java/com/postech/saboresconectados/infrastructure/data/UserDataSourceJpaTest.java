package com.postech.saboresconectados.infrastructure.data;

import com.postech.saboresconectados.core.dtos.UserDto;
import com.postech.saboresconectados.infrastructure.data.datamappers.UserMapper;
import com.postech.saboresconectados.infrastructure.data.models.UserModel;
import com.postech.saboresconectados.infrastructure.data.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserDataSourceJpaTest {
    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserDataSourceJpa dataSource;

    private MockedStatic<UserMapper> mockedStaticUserMapper;

    private static final UUID ID = UUID.randomUUID();

    private static final String LOGIN = "testLogin7";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockedStaticUserMapper = mockStatic(UserMapper.class);
    }

    @AfterEach
    void tearDown() {
        if (this.mockedStaticUserMapper != null) {
            this.mockedStaticUserMapper.close();
        }
    }

    @Test
    void shouldSaveUser() {
        // Given
        final UserDto userToSaveDto = UserDto.builder().build();
        final UserModel userToSave = UserModel.builder().build();
        final UserDto expectedSavedUserDto = UserDto.builder().build();
        this.mockedStaticUserMapper.when(() -> UserMapper.toUserModel(userToSaveDto)).thenReturn(userToSave);
        when(this.repository.save(userToSave)).thenReturn(userToSave);
        this.mockedStaticUserMapper.when(() -> UserMapper.toUserDto(userToSave)).thenReturn(expectedSavedUserDto);

        // When
        final UserDto savedUserDto = this.dataSource.save(userToSaveDto);

        // Then
        this.mockedStaticUserMapper.verify(() -> UserMapper.toUserModel(userToSaveDto), times(1));
        verify(this.repository, times(1)).save(userToSave);
        this.mockedStaticUserMapper.verify(() -> UserMapper.toUserDto(userToSave), times(1));
        assertThat(savedUserDto).isNotNull().isEqualTo(expectedSavedUserDto);
    }

    @Test
    void shouldFindUserById() {
        // Given
        final UserModel foundUser = UserModel.builder().build();
        when(this.repository.findById(ID)).thenReturn(Optional.of(foundUser));
        final UserDto mappedUserDto = UserDto.builder().build();
        this.mockedStaticUserMapper.when(() -> UserMapper.toUserDto(foundUser)).thenReturn(mappedUserDto);

        // When
        Optional<UserDto> foundUserDto = this.dataSource.findById(ID);

        // Then
        verify(this.repository, times(1)).findById(ID);
        this.mockedStaticUserMapper.verify(() -> UserMapper.toUserDto(foundUser), times(1));
        assertThat(foundUserDto).isPresent().contains(mappedUserDto);
    }

    @Test
    void shouldFindUserByIdWhenRepositoryReturnsEmpty() {
        // Given
        final UserModel foundUser = UserModel.builder().build();
        when(this.repository.findById(ID)).thenReturn(Optional.empty());

        // When
        Optional<UserDto> foundUserDto = this.dataSource.findById(ID);

        // Then
        verify(this.repository, times(1)).findById(ID);
        this.mockedStaticUserMapper.verify(() -> UserMapper.toUserDto(foundUser), times(0));
        assertThat(foundUserDto).isNotPresent();
    }

    @Test
    void shouldFindUserByLogin() {
        // Given
        final UserModel foundUser = UserModel.builder().build();
        when(this.repository.findByLogin(LOGIN)).thenReturn(Optional.of(foundUser));
        final UserDto mappedUserDto = UserDto.builder().build();
        this.mockedStaticUserMapper.when(() -> UserMapper.toUserDto(foundUser)).thenReturn(mappedUserDto);

        // When
        Optional<UserDto> foundUserDto = this.dataSource.findByLogin(LOGIN);

        // Then
        verify(this.repository, times(1)).findByLogin(LOGIN);
        this.mockedStaticUserMapper.verify(() -> UserMapper.toUserDto(foundUser), times(1));
        assertThat(foundUserDto).isPresent().contains(mappedUserDto);
    }

    @Test
    void shouldFindUserByLoginWhenRepositoryReturnsEmpty() {
        // Given
        final UserModel foundUser = UserModel.builder().build();
        when(this.repository.findByLogin(LOGIN)).thenReturn(Optional.empty());

        // When
        Optional<UserDto> foundUserDto = this.dataSource.findByLogin(LOGIN);

        // Then
        verify(this.repository, times(1)).findByLogin(LOGIN);
        this.mockedStaticUserMapper.verify(() -> UserMapper.toUserDto(foundUser), times(0));
        assertThat(foundUserDto).isNotPresent();
    }

    @Test
    void shouldDeleteUserById() {
        // When
        this.dataSource.deleteById(ID);

        // Then
        verify(this.repository, times(1)).deleteById(ID);
    }
}
