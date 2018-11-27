package com.beyondthecode.pithubproject.data.datasource.request;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("email")
    String email;

    @SerializedName("clave")
    String clave;

    public LoginRequest(String email, String clave) {
        this.email = email;
        this.clave = clave;
    }

}
