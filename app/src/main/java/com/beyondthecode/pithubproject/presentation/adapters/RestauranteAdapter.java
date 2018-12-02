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
import com.beyondthecode.pithubproject.domain.Restaurante;
import com.beyondthecode.pithubproject.presentation.DetalleRestauranteActivity;
import com.beyondthecode.pithubproject.presentation.RestaurantesXcategoriaActivity;
import com.beyondthecode.pithubproject.presentation.adapters.interfaces.CategoriaRestauranteClickListener;
import com.beyondthecode.pithubproject.presentation.adapters.interfaces.RestauranteClickListener;
import com.beyondthecode.pithubproject.presentation.adapters.views.RestauranteViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestauranteAdapter extends RecyclerView.Adapter<RestauranteViewHolder> {

    Context context;
    List<Restaurante> restauranteList;

    public RestauranteAdapter(Context context, List<Restaurante> restauranteList) {
        this.context = context;
        this.restauranteList = restauranteList;
    }

    @NonNull
    @Override
    public RestauranteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.restaurante_item,parent,false);
        RestauranteViewHolder viewHolder = new RestauranteViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RestauranteViewHolder holder, int position) {
        Picasso.get()
                .load(PithubConfig.getPathBaseImageWebClient() + restauranteList.get(position).getImgtiporestaurante())
                .into(holder.imgRest);

        String upperString = restauranteList.get(position).getDistrito().substring(0,1).toUpperCase() + restauranteList.get(position).getDistrito().substring(1).toLowerCase();

        holder.txtNomRest.setText(restauranteList.get(position).getNomrest());
        holder.txtDistrito.setText(upperString);

        holder.setRestauranteItemClick(new RestauranteClickListener() {

            @Override
            public void onRestauranteClick(View view, int position) {
                Toast.makeText(context, "click en:" + restauranteList.get(position).getNomrest(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context,DetalleRestauranteActivity.class);
                intent.putExtra("idRest",restauranteList.get(position).getIdrest());
                intent.putExtra("nomRest",restauranteList.get(position).getNomrest());


                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(restauranteList != null){
            return restauranteList.size();
        }
        return 0;

    }
}
