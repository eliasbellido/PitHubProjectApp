package com.beyondthecode.pithubproject.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.beyondthecode.pithubproject.R;
import com.beyondthecode.pithubproject.data.datasource.local.SqlHelper;
import com.beyondthecode.pithubproject.domain.CategoriaPlato;
import com.beyondthecode.pithubproject.domain.PedidoDetalle;
import com.beyondthecode.pithubproject.domain.Producto;
import com.beyondthecode.pithubproject.presentation.adapters.interfaces.PlatoComidaItemClickListener;
import com.beyondthecode.pithubproject.presentation.adapters.views.CategoriaPlatoViewHolder;
import com.beyondthecode.pithubproject.presentation.adapters.views.ProductoViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class ProductoAdapter extends ExpandableRecyclerViewAdapter<CategoriaPlatoViewHolder,ProductoViewHolder> {


    public ProductoAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public CategoriaPlatoViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoriacomida_item,parent,false);

        return new CategoriaPlatoViewHolder(view);
    }

    @Override
    public ProductoViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plato_item,parent,false);

        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(final ProductoViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {

        Producto producto = (Producto) group.getItems().get(childIndex);
        holder.setItemClicked(new PlatoComidaItemClickListener() {
            @Override
            public void onItemClick(View view, int posicion) {
                Toast.makeText(view.getContext(), "click en item", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPlatoComidaImagenClick(View view, int position) {
                int contador = Integer.parseInt((String) holder.txtCantidad.getText());


                if(contador > 0){
                    //Toast.makeText(view.getContext(), "se agregó al carrito" +position, Toast.LENGTH_SHORT).show();

                    new SqlHelper(view.getContext()).agregarAlcarro(
                            new PedidoDetalle(
                                    (String) holder.txtIdPlato.getText(),
                                    (String) holder.txtPlato.getText(),
                                    (String) holder.txtCantidad.getText(),
                                    (String) holder.txtPrePlato.getText()


                            ),view.getContext()
                    );
                }else{
                    Toast.makeText(view.getContext(), "seleccione una cantidad mayor a 0", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onPlatoComidaSumarCantidadClick(View view, int position) {
                //Toast.makeText(view.getContext(), "click en sumar", Toast.LENGTH_SHORT).show();

                int contador = Integer.parseInt((String) holder.txtCantidad.getText()) + 1;
                holder.txtCantidad.setText(""+contador);


            }

            @Override
            public void onPlatoComidaRestarCantidadClick(View view, int position) {
                //Toast.makeText(view.getContext(), "click en restar", Toast.LENGTH_SHORT).show();


                if(Integer.parseInt((String) holder.txtCantidad.getText())>0){
                    int contador = Integer.parseInt((String) holder.txtCantidad.getText()) - 1;

                    holder.txtCantidad.setText(""+contador);
                }






            }
        });
        holder.bind(producto);



    }

    @Override
    public void onBindGroupViewHolder(CategoriaPlatoViewHolder holder, int flatPosition, ExpandableGroup group) {

        CategoriaPlato categoriaPlato = (CategoriaPlato) group;
        holder.bind(categoriaPlato);

    }
}
