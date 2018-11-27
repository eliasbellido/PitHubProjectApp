package com.beyondthecode.pithubproject.presentation.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.beyondthecode.pithubproject.R;
import com.beyondthecode.pithubproject.data.datasource.PithubConfig;
import com.beyondthecode.pithubproject.domain.TipoRestaurante;
import com.beyondthecode.pithubproject.presentation.RestaurantesXcategoriaActivity;
import com.beyondthecode.pithubproject.presentation.TestActivity;
import com.beyondthecode.pithubproject.presentation.adapters.interfaces.CategoriaRestauranteClickListener;
import com.beyondthecode.pithubproject.presentation.adapters.views.CategoriaRestauranteViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoriaRestauranteAdapter extends RecyclerView.Adapter<CategoriaRestauranteViewHolder> {

    Context context;
    List<TipoRestaurante> tipoRestauranteList;

    public CategoriaRestauranteAdapter(Context context, List<TipoRestaurante> tipoRestauranteList) {
        this.context = context;
        this.tipoRestauranteList = tipoRestauranteList;
    }

    @NonNull
    @Override
    public CategoriaRestauranteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.categoriarestaurante_item,parent,false);
        CategoriaRestauranteViewHolder viewHolder = new CategoriaRestauranteViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaRestauranteViewHolder holder, int position) {

        final String upperString = tipoRestauranteList.get(position).getTiporestaurante().substring(0,1).toUpperCase() + tipoRestauranteList.get(position).getTiporestaurante().substring(1).toLowerCase();

        holder.txtCategoriaRest.setText(upperString);
        Picasso.get()
                .load(PithubConfig.getPathBaseImageWebClient() + tipoRestauranteList.get(position).getImgtiporestaurante())
                .into(holder.imgCategoriaRest);

        holder.setRestaurantCategoryItemClick(new CategoriaRestauranteClickListener() {
            @Override
            public void onCategoriaRestauranteClick(View view, int position) {
                Toast.makeText(context, "click en:" + tipoRestauranteList.get(position).getTiporestaurante(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context,RestaurantesXcategoriaActivity.class);
                intent.putExtra("idcategoriaRest",tipoRestauranteList.get(position).getIdtiporestaurante());
                intent.putExtra("categoria",upperString);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return tipoRestauranteList.size();
    }
}
