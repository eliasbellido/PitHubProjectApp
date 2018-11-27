package com.beyondthecode.pithubproject.data.datasource.response;

import com.beyondthecode.pithubproject.domain.Producto;
import com.beyondthecode.pithubproject.domain.Restaurante;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProductosResponse {

    @SerializedName("data")
    @Expose
    private List<Producto> data = new ArrayList<>();

    public List<Producto> getData() {

        return data;
    }

    public void setData(List<Producto> data) {
        this.data = data;
    }

}
