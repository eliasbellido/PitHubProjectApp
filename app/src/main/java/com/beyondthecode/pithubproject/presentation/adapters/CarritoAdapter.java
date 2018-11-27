package com.beyondthecode.pithubproject.presentation.adapters;

import android.content.Context;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.amulyakhare.textdrawable.TextDrawable;
import com.beyondthecode.pithubproject.R;
import com.beyondthecode.pithubproject.domain.PedidoDetalle;
import com.beyondthecode.pithubproject.presentation.adapters.interfaces.ItemClickListener;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Computer on 1/02/2018.
 */


class CarritoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txt_carrito_nombre, txt_precio;
    public ImageView img_carrito_contador;


    private ItemClickListener itemClickListener;

    public void setTxt_carrito_nombre(TextView txt_carrito_nombre){
        this.txt_carrito_nombre = txt_carrito_nombre;
    }


    public CarritoViewHolder(View itemView) {

        super(itemView);

        txt_carrito_nombre = itemView.findViewById(R.id.txt_nombreItemCarrito);
        txt_precio = itemView.findViewById(R.id.txt_precioItemCarrito);
        img_carrito_contador = itemView.findViewById(R.id.img_contadorItemCarrito);
    }

    @Override
    public void onClick(View v) {

    }
}

public class CarritoAdapter extends RecyclerView.Adapter<CarritoViewHolder> {

    private List<PedidoDetalle> listarData = new ArrayList<>();
    private Context context;

    public CarritoAdapter(List<PedidoDetalle> listarData, Context context) {
        this.listarData = listarData;
        this.context = context;
    }

    @Override
    public CarritoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.carrito_item,parent,false);


        return new CarritoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CarritoViewHolder holder, int position) {

        TextDrawable drawable = TextDrawable.builder().buildRound
                (""+listarData.get(position).getCantidad(), Color.RED);


        holder.img_carrito_contador.setImageDrawable(drawable);


        Locale local = new Locale("ES","PE");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(local);

        Double precio = (Double.parseDouble(listarData.get(position).getPrecio()))*(Integer.parseInt(listarData.get(position).getCantidad()));
        holder.txt_precio.setText(fmt.format(precio));
        holder.txt_carrito_nombre.setText(listarData.get(position).getProductNombre());

    }

    @Override
    public int getItemCount() {

        return listarData.size();
    }
}
