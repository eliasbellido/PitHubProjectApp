package com.beyondthecode.pithubproject.presentation.adapters.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyondthecode.pithubproject.R;
import com.beyondthecode.pithubproject.presentation.adapters.interfaces.CategoriaRestauranteClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoriaRestauranteViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.img_categoriaRestaurante)
    public ImageView imgCategoriaRest;

    @BindView(R.id.txtCategoriaRestaurante)
    public TextView txtCategoriaRest;

    private CategoriaRestauranteClickListener itemSelected;

    public CategoriaRestauranteViewHolder(@NonNull final View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemSelected.onCategoriaRestauranteClick(itemView,getAdapterPosition());
            }
        });

    }



    public void setRestaurantCategoryItemClick(CategoriaRestauranteClickListener itemSelected){
        this.itemSelected = itemSelected;
    }


}
