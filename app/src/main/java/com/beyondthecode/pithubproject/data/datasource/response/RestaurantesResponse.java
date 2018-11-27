package com.beyondthecode.pithubproject.data.datasource.response;

import com.beyondthecode.pithubproject.domain.Restaurante;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RestaurantesResponse {

    @SerializedName("data")
    @Expose
    private List<Restaurante> data = new ArrayList<>();

    public List<Restaurante> getData() {
        return data;
    }

    public void setData(List<Restaurante> data) {
        this.data = data;
    }
    
}
