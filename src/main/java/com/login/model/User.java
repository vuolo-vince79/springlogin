package com.login.model;

import com.login.enum_message.ResponseMessage;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    long idUser;
    @NotNull(message = "USERNAME_NULL")
    String username;
    @NotNull(message = "EMAIL_NULL")
    @Email(message = "INVALID_EMAIL")
    String email;
    @NotNull(message = "PSW_NULL")
    @Size(min = 8, message = "SHORT_PSW")
    String psw;
    String ruolo;
    @Column(name = "refresh_token")
    String refreshToken;

    public User(){}

    public User(String username, String psw) {
        this.username = username;
        this.psw = psw;
    }

    public User(String username, String email, String psw) {
        this.username = username;
        this.email = email;
        this.psw = psw;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
