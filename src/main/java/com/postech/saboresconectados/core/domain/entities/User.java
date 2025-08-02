package com.postech.saboresconectados.core.domain.entities;

import com.postech.saboresconectados.core.domain.entities.enumerators.UserType;
import com.postech.saboresconectados.core.domain.exceptions.InvalidEmailException;
import com.postech.saboresconectados.core.domain.exceptions.InvalidLoginException;
import com.postech.saboresconectados.core.domain.exceptions.InvalidPasswordException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Getter
@Setter
@Builder(toBuilder = true)
public class User {
    private UUID id;
    private String name;
    private UserType userType;
    private String email;
    private String login;
    private String password;
    private String address;
    private LocalDateTime lastUpdated;

    private User(
            UUID id, String name, UserType userType,
            String email, String login, String password,
            String address, LocalDateTime lastUpdated) {
        this.id = id;
        this.name = name;
        this.userType = userType;
        this.setEmail(email);
        this.setLogin(login);
        this.setPassword(password);
        this.address = address;
        this.lastUpdated = lastUpdated;
    }

    public void setEmail(String email) {
        if (!this.isEmailValid(email)) {
            throw new InvalidEmailException();
        }
        this.email = email;
    }

    private boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        return EmailValidator.getInstance().isValid(email);
    }

    public void setLogin(String login) {
        if (!this.isLoginValid(login)) {
            throw new InvalidLoginException();
        }
        this.login = login;
    }

    private boolean isLoginValid(String login) {
        if (login == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._-]{5,}+$");
        Matcher matcher = pattern.matcher(login);
        return matcher.matches();
    }

    public void setPassword(String password) {
        if (!this.isPasswordValid(password)) {
            throw new InvalidPasswordException();
        }
        this.password = password;
    }

    private boolean isPasswordValid(String password) {
        if (password == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static class UserBuilder {
        public User build() {
            return new User(
                    this.id, this.name, this.userType,
                    this.email, this.login, this.password,
                    this.address, this.lastUpdated);
        }
    }

}
