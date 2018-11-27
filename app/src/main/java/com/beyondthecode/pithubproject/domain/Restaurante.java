package com.beyondthecode.pithubproject.domain;

import com.google.gson.annotations.SerializedName;

public class Restaurante {

    @SerializedName("idrestaurante")
    private int idrest;

    @SerializedName("nomrestaurante")
    private String nomrest;

    @SerializedName("descripcionrestaurante")
    private String descrest;

    @SerializedName("logorestaurante")
    private String imgtiporestaurante;

    @SerializedName("idtipo_restaurante")
    private int idtiporestaurante;

    @SerializedName("nomdistrito")
    private String distrito;


    public int getIdrest() {
        return idrest;
    }

    public void setIdrest(int idrest) {
        this.idrest = idrest;
    }

    public String getNomrest() {
        return nomrest;
    }

    public void setNomrest(String nomrest) {
        this.nomrest = nomrest;
    }

    public String getDescrest() {
        return descrest;
    }

    public void setDescrest(String descrest) {
        this.descrest = descrest;
    }

    public String getImgtiporestaurante() {
        return imgtiporestaurante;
    }

    public void setImgtiporestaurante(String imgtiporestaurante) {
        this.imgtiporestaurante = imgtiporestaurante;
    }

    public int getIdtiporestaurante() {
        return idtiporestaurante;
    }

    public void setIdtiporestaurante(int idtiporestaurante) {
        this.idtiporestaurante = idtiporestaurante;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }
}
