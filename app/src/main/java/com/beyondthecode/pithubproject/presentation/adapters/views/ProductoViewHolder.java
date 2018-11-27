package com.beyondthecode.pithubproject.presentation.adapters.views;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyondthecode.pithubproject.R;
import com.beyondthecode.pithubproject.data.datasource.PithubConfig;
import com.beyondthecode.pithubproject.domain.Producto;
import com.beyondthecode.pithubproject.presentation.adapters.interfaces.PlatoComidaItemClickListener;
import com.squareup.picasso.Picasso;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductoViewHolder extends ChildViewHolder {

    @BindView(R.id.txtPlatoComida)
    public TextView txtPlato;

    @BindView(R.id.txtIdPlatoComida)
    public TextView txtIdPlato;

    @BindView(R.id.imgPlato)
    public ImageView imgPlatoComida;

    @BindView(R.id.txtDescripcionPlato)
    public TextView txtDescPlato_calorias;

    @BindView(R.id.txtPrecioPlato)
    public TextView txtPrePlato;

    @BindView(R.id.imgSumarCant)
    public ImageView imgSumarCant;

    @BindView(R.id.imgRestarCant)
    public ImageView imgRestarCant;

    @BindView(R.id.txtContador)
    public TextView txtCantidad;



    private PlatoComidaItemClickListener itemEscogido;




    public ProductoViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);

        imgSumarCant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemEscogido.onPlatoComidaSumarCantidadClick(v,getAdapterPosition());
            }
        });

        imgRestarCant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemEscogido.onPlatoComidaRestarCantidadClick(v,getAdapterPosition());
            }
        });

        imgPlatoComida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemEscogido.onPlatoComidaImagenClick(v,getAdapterPosition());
            }
        });


    }

    public void bind(Producto producto){
        txtIdPlato.setText(""+producto.getIdProd());
        txtPlato.setText(producto.getNomProd());
        txtDescPlato_calorias.setText(producto.getCaloriaProd() + " Kcal");
        txtPrePlato.setText(""+producto.getPreProd());
        Picasso.get()
                .load(PithubConfig.getPathBaseImageWebClient() + producto.getImgProd())
                .into(imgPlatoComida);



    }

    public void setItemClicked(PlatoComidaItemClickListener itemEscogido){
        this.itemEscogido = itemEscogido;

    }

}
