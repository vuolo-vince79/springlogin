package com.login.model;

import jakarta.persistence.*;

@Entity
@Table(name = "setup")
public class SetupUser {

    @Id
    @Column(name = "id_setup")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idSetup;
    @Column(name = "id_user")
    long idUser;
    String lang;
    @Column(name = "dark_theme")
    boolean darkTheme;

    public SetupUser(){}

    public long getIdSetup() {
        return idSetup;
    }

    public void setIdSetup(long idSetup) {
        this.idSetup = idSetup;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public boolean isDarkTheme() {
        return darkTheme;
    }

    public void setDarkTheme(boolean darkTheme) {
        this.darkTheme = darkTheme;
    }
}
