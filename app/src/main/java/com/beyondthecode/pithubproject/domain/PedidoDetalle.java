package com.beyondthecode.pithubproject.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Computer on 30/01/2018.
 */

public class PedidoDetalle {

    @SerializedName("idproducto")
    @Expose
    private String productId;

    private String productNombre;

    @SerializedName("cantidad")
    @Expose
    private String cantidad;

    private String precio;


    public PedidoDetalle() {
    }

    public PedidoDetalle(String productNombre, String cantidad) {
        this.productNombre = productNombre;
        this.cantidad = cantidad;
    }

    public PedidoDetalle(String productNombre, String cantidad, String precio) {
        this.productNombre = productNombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public PedidoDetalle(String productId, String productNombre, String cantidad, String precio) {
        this.productId = productId;
        this.productNombre = productNombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductNombre() {
        return productNombre;
    }

    public void setProductNombre(String productNombre) {
        this.productNombre = productNombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }


}


