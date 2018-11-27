package com.beyondthecode.pithubproject.domain;


import android.os.Parcel;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class CategoriaPlato extends ExpandableGroup<Producto> {

    private String title;

    public CategoriaPlato(String title, List<Producto> items) {

        super(title, items);
    }



    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
