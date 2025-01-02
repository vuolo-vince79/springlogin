package com.login.dto;

public class SetupUserDto {

    private final String token;
    private String lang;
    private boolean isDark;

    public SetupUserDto(String token, String lang){
        this.token = token;
        this.lang = lang;
    }

    public SetupUserDto(String token, boolean isDark) {
        this.token = token;
        this.isDark = isDark;
    }

    public String getToken() {
        return token;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public boolean isDark() {
        return isDark;
    }

    public void setDark(boolean dark) {
        isDark = dark;
    }
}
