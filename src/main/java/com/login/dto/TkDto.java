package com.login.dto;

public class TkDto {

    private final String email;
    private final String siteName;
    private final String siteUrl;
    private final boolean isPublic;

    public TkDto(String email, String siteName, String siteUrl, boolean isPublic) {
        this.email = email;
        this.siteName = siteName;
        this.siteUrl = siteUrl;
        this.isPublic = isPublic;
    }


    public String getEmail() {
        return email;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public boolean isPublic() {
        return isPublic;
    }
}
