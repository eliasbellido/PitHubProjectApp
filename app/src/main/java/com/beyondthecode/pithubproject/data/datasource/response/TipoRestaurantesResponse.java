package com.beyondthecode.pithubproject.data.datasource.response;

import com.beyondthecode.pithubproject.domain.TipoRestaurante;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TipoRestaurantesResponse {

    @SerializedName("data")
    @Expose
    private List<TipoRestaurante> data = new ArrayList<>();

    public List<TipoRestaurante> getData() {
        return data;
    }

    public void setData(List<TipoRestaurante> data) {
        this.data = data;
    }
}
