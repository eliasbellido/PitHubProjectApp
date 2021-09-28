package com.beyondthecode.pithubproject.domain;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Producto implements Parcelable {

    @SerializedName("nomrestaurante")
    private String nomrest;

    @SerializedName("tipo_restaurante")
    private String tipoRest;

    @SerializedName("logorestaurante")
    private String logoRest;

    @SerializedName("nomproducto")
    private String nomProd;

    @SerializedName("id")
    private int idProd;

    @SerializedName("nomcategoria")
    private String nomCat;

    @SerializedName("calorias")
    private int caloriaProd;

    @SerializedName("precio")
    private Double preProd;

    @SerializedName("imagenproducto")
    private String imgProd;

    public Producto(String nomCat) {
        this.nomCat = nomCat;
    }

    public Producto(int idProd, String nomProd, String imgProd, Double preProd, int caloriaProd){
        this.idProd = idProd;
        this.nomProd = nomProd;
        this.imgProd = imgProd;
        this.preProd = preProd;
        this.caloriaProd = caloriaProd;
    }

    public Producto(String nomrest, String tipoRest, String logoRest, String nomProd, String nomCat, int caloriaProd, Double preProd, String imgProd) {
        this.nomrest = nomrest;
        this.tipoRest = tipoRest;
        this.logoRest = logoRest;
        this.nomProd = nomProd;
        this.nomCat = nomCat;
        this.caloriaProd = caloriaProd;
        this.preProd = preProd;
        this.imgProd = imgProd;
    }

    protected Producto(Parcel in) {
        nomrest = in.readString();
        tipoRest = in.readString();
        logoRest = in.readString();
        nomProd = in.readString();
        nomCat = in.readString();
        caloriaProd = in.readInt();
        if (in.readByte() == 0) {
            preProd = null;
        } else {
            preProd = in.readDouble();
        }
        imgProd = in.readString();
    }

    public static final Creator<Producto> CREATOR = new Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel in) {
            return new Producto(in);
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public String getNomrest() {
        return nomrest;
    }

    public void setNomrest(String nomrest) {
        this.nomrest = nomrest;
    }

    public String getTipoRest() {
        return tipoRest;
    }

    public void setTipoRest(String tipoRest) {
        this.tipoRest = tipoRest;
    }

    public String getLogoRest() {
        return logoRest;
    }

    public void setLogoRest(String logoRest) {
        this.logoRest = logoRest;
    }

    public String getNomProd() {
        return nomProd;
    }

    public void setNomProd(String nomProd) {
        this.nomProd = nomProd;
    }

    public String getNomCat() {
        return nomCat;
    }

    public void setNomCat(String nomCat) {
        this.nomCat = nomCat;
    }

    public int getCaloriaProd() {
        return caloriaProd;
    }

    public void setCaloriaProd(int caloriaProd) {
        this.caloriaProd = caloriaProd;
    }

    public Double getPreProd() {
        return preProd;
    }

    public void setPreProd(Double preProd) {
        this.preProd = preProd;
    }

    public String getImgProd() {
        return imgProd;
    }

    public void setImgProd(String imgProd) {
        this.imgProd = imgProd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nomProd);
    }

    @Override
    public boolean equals(Object o) {
        Log.d("Producto", "llamando a equals()");
        if (this == o) return true;
        if (!(o instanceof Producto)) return false;
        Producto producto = (Producto) o;

        return Objects.equals(nomCat, producto.nomCat);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nomCat);
    }

}
