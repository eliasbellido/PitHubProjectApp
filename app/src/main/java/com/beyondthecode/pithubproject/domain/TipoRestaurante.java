package com.beyondthecode.pithubproject.domain;

import com.google.gson.annotations.SerializedName;

public class TipoRestaurante {

    @SerializedName("idtipo_restaurante")
    private int idtiporestaurante;

    @SerializedName("tipo_restaurante")
    private String tiporestaurante;

    @SerializedName("imagendefault")
    private String imgtiporestaurante;

    public int getIdtiporestaurante() {
        return idtiporestaurante;
    }

    public void setIdtiporestaurante(int idtiporestaurante) {
        this.idtiporestaurante = idtiporestaurante;
    }

    public String getTiporestaurante() {
        return tiporestaurante;
    }

    public void setTiporestaurante(String tiporestaurante) {
        this.tiporestaurante = tiporestaurante;
    }

    public String getImgtiporestaurante() {
        return imgtiporestaurante;
    }

    public void setImgtiporestaurante(String imgtiporestaurante) {
        this.imgtiporestaurante = imgtiporestaurante;
    }
}
