package com.beyondthecode.pithubproject.data.datasource.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PedidoResponse {

    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
