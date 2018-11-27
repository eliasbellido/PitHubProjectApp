package com.beyondthecode.pithubproject.presentation.adapters.interfaces;

import android.view.View;

public interface PlatoComidaItemClickListener {
    void onItemClick(View view,int posicion);

    void onPlatoComidaImagenClick(View view, int position);
    void onPlatoComidaSumarCantidadClick(View view, int position);
    void onPlatoComidaRestarCantidadClick(View view, int position);

}
