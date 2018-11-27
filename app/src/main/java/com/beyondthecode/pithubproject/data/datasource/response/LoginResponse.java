package com.beyondthecode.pithubproject.data.datasource.response;

import com.beyondthecode.pithubproject.domain.Cliente;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LoginResponse {

    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    @SerializedName("data")
    @Expose
    private Cliente data;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Cliente getData() {
        return data;
    }

    public void setData(Cliente data) {
        this.data = data;
    }
}
