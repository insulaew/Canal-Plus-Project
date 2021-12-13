package com.meetingplanner.dto;

import com.meetingplanner.model.User;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*Classe du DTO de l'entité User*/
public class UserDto {

    private long id;

    @NotNull(message="Vous devez indiquer un prénom")
    @NotEmpty(message="Le prénom ne peut pas être vide")
    private String firstName;

    @NotNull(message="Vous devez indiquer un nom")
    @NotEmpty(message="Le nom ne peut pas être vide")
    private String lastName;

    @NotNull(message="Vous devez indiquer un email")
    @NotEmpty(message="L'email ne peut pas être vide")
    @Email(message="Le format de votre email n'est pas valide", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    private String email;

    @NotNull(message="Vous devez indiquer un mot de passe")
    @NotEmpty(message="Le mot de passe ne peut pas être vide")
    @Size(min = 6, message = "Votre mot de passe doit contenir au moins 6 caractères")
    private String password;

    private Set<Long> meetingsIds;

    public UserDto(long id, String firstName, String lastName, String email, String password, Set<Long> meetingsIds) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.meetingsIds = meetingsIds;
    }

    public UserDto() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Long> getMeetingsIds() {
        return meetingsIds;
    }

    public void setMeetingsIds(Set<Long> meetingsIds) {
        this.meetingsIds = meetingsIds;
    }

    /*Permet de convertir un DTO User en entité User*/
    public User toUser() {

        return new User(
                this.id,
                this.firstName,
                this.lastName,
                this.email,
                this.password
        );
    }

}
