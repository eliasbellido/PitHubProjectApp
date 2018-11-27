package com.beyondthecode.pithubproject.presentation.adapters.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyondthecode.pithubproject.R;
import com.beyondthecode.pithubproject.data.datasource.PithubConfig;
import com.beyondthecode.pithubproject.domain.CategoriaPlato;
import com.squareup.picasso.Picasso;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriaPlatoViewHolder extends GroupViewHolder {

    @BindView(R.id.txtCategoriaComida)
    TextView txtCategoriaPlato;


    public CategoriaPlatoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bind(CategoriaPlato categoriaPlato){
        txtCategoriaPlato.setText(categoriaPlato.getTitle());

    }


}
