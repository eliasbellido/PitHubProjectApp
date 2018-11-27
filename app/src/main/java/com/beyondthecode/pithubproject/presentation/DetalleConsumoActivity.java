package com.beyondthecode.pithubproject.presentation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beyondthecode.pithubproject.PitHubApp;
import com.beyondthecode.pithubproject.R;
import com.beyondthecode.pithubproject.data.WSData;
import com.beyondthecode.pithubproject.data.datasource.local.SqlHelper;
import com.beyondthecode.pithubproject.data.datasource.request.PedidoRequest;
import com.beyondthecode.pithubproject.data.datasource.response.PedidoResponse;
import com.beyondthecode.pithubproject.data.datasource.rest.api.IApiClient;
import com.beyondthecode.pithubproject.domain.PedidoDetalle;
import com.beyondthecode.pithubproject.presentation.adapters.CarritoAdapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleConsumoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    List<PedidoDetalle> carrito = new ArrayList<>();
    TextView txtPrecioTotal;
    //Button btnColocarOrden;

    CarritoAdapter adapter;

    @BindView(R.id.tbConsumoLista)
    Toolbar tbConsumo;

    @BindView(R.id.btnColocarOrden)
    CardView btnContinuarPedido;

    @BindView(R.id.txtConsumo_restaurante)
    TextView txtNomRest;

    String nomRest;
    int idRest;

    Double total;

    Editable direccionEntrega;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_consumo);
        ButterKnife.bind(this);
        if(getIntent()!=null){
            idRest = getIntent().getIntExtra("idRest",-1);
            nomRest = getIntent().getStringExtra("nomRest");
            txtNomRest.setText(nomRest);

        }
        recyclerView = findViewById(R.id.listarCarrito);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        txtPrecioTotal = findViewById(R.id.txtTotalPrecioEnCarrito);

        cargarListaDeOrdenes();
        initParams();



        //btnColocarOrden = findViewById(R.id.btnColocarOrden);


    }
    


    private void initParams(){
        setSupportActionBar(tbConsumo);
        tbConsumo.setTitle("Tu pedido");
        tbConsumo.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        tbConsumo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnContinuarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(DetalleConsumoActivity.this, "Se colocó la orden, pronto recibirás tu pedido", Toast.LENGTH_SHORT).show();
                mostrarDireccionDialog();
            }
        });




    }

    private void mostrarDireccionDialog(){

        if(!carrito.isEmpty()){
            Log.d("carrito","Entro al dialogo");

            AlertDialog.Builder dialogoDeAlerta = new AlertDialog.Builder(this);
            final EditText edtDireccion = new EditText(DetalleConsumoActivity.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            dialogoDeAlerta.setTitle("A un paso más");
            dialogoDeAlerta.setMessage("Ingrese su su dirección: ");

            dialogoDeAlerta.setPositiveButton("Solicitar Delivery", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    direccionEntrega = edtDireccion.getText();

                    if(!String.valueOf(direccionEntrega).isEmpty()){
                        generarPedido();
                    }else{
                        Toast.makeText(DetalleConsumoActivity.this, getString(R.string.errorDireccionEntrega), Toast.LENGTH_SHORT).show();
                    }



                }
            });
            dialogoDeAlerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog

                }
            });




            //direccionEntrega = edtDireccion.getText().toString();

            edtDireccion.setLayoutParams(layoutParams);
            dialogoDeAlerta.setView(edtDireccion);
            dialogoDeAlerta.setIcon(R.drawable.ic_shopping_cart_black_24dp);

            dialogoDeAlerta.show();
        }else{
            Toast.makeText(this, "Primero debe ingresar productos al carrito", Toast.LENGTH_SHORT).show();
        }



    }

    private void generarPedido(){

        SharedPreferences sp = DetalleConsumoActivity.this.getSharedPreferences(PitHubApp.PREF_FILE,Context.MODE_PRIVATE);
        int idUsuario = Integer.parseInt(sp.getString(PitHubApp.PREF_CLI_ID,"-1"));

        //Toast.makeText(DetalleConsumoActivity.this, "valores:"+idUsuario+"/"+idRest+"/"+total+"/"+direccionEntrega, Toast.LENGTH_SHORT).show();

        List<PedidoDetalle> pedidoDetalle = new SqlHelper(DetalleConsumoActivity.this).obtenerOrden();

        PedidoRequest pedidoRequest = new PedidoRequest(idRest,idUsuario,total,1,String.valueOf(direccionEntrega),pedidoDetalle);


        IApiClient mApiService = WSData.getInterfaceService();
        Call<PedidoResponse> mService = mApiService.generarPedido(pedidoRequest);
        mService.enqueue(new Callback<PedidoResponse>() {
            @Override
            public void onResponse(Call<PedidoResponse> call, Response<PedidoResponse> response) {

                if(response.isSuccessful()){
                    PedidoResponse pedidoResponse = response.body();

                    if(pedidoResponse.getMensaje().equals("ok")){
                        Intent _homeActivity = new Intent(DetalleConsumoActivity.this,MainActivity.class);
                        _homeActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(_homeActivity);
                        finish();

                        new SqlHelper(DetalleConsumoActivity.this).eliminarPedidoActual();
                        Toast.makeText(DetalleConsumoActivity.this, "Se generó el peddo exitosamente", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(DetalleConsumoActivity.this, "Surgió un error", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    String errorMensaje = response.message();
                    Toast.makeText(DetalleConsumoActivity.this, errorMensaje, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PedidoResponse> call, Throwable t) {
                Toast.makeText(DetalleConsumoActivity.this, getString(R.string.serviceFailure), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarListaDeOrdenes() {

        carrito = new SqlHelper(this).obtenerOrden();

        if(!carrito.isEmpty()){




            adapter = new CarritoAdapter(carrito,this);
            recyclerView.setAdapter(adapter);

            //calcular precio total)

            total = 0.0;

            for(PedidoDetalle pedidoDetalle :carrito){

                total+=(Double.parseDouble(pedidoDetalle.getPrecio()))*(Integer.parseInt(pedidoDetalle.getCantidad()));
            }

            Locale local = new Locale("ES","PE");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(local);

            txtPrecioTotal.setText(fmt.format(total));

        }else{

            Toast.makeText(this, "Tu carrito está vacío", Toast.LENGTH_SHORT).show();
        }






    }
}
