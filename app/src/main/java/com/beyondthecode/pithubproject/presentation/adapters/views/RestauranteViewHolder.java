package com.beyondthecode.pithubproject.presentation.adapters.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyondthecode.pithubproject.R;
import com.beyondthecode.pithubproject.presentation.adapters.interfaces.RestauranteClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestauranteViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.img_restaurante)
    public ImageView imgRest;

    @BindView(R.id.txtNomRestaurante)
    public TextView txtNomRest;

    @BindView(R.id.txtDistrito)
    public TextView txtDistrito;

    private RestauranteClickListener restauranteSelected;

    public RestauranteViewHolder(@NonNull final View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restauranteSelected.onRestauranteClick(itemView,getAdapterPosition());
            }
        });
    }

    public void setRestauranteItemClick(RestauranteClickListener restauranteSelected) {
        this.restauranteSelected = restauranteSelected;
    }
}
