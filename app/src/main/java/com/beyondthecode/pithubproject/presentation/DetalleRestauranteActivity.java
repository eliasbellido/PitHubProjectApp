package com.beyondthecode.pithubproject.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beyondthecode.pithubproject.R;
import com.beyondthecode.pithubproject.data.WSData;
import com.beyondthecode.pithubproject.data.datasource.PithubConfig;
import com.beyondthecode.pithubproject.data.datasource.local.SqlHelper;
import com.beyondthecode.pithubproject.data.datasource.response.ProductosResponse;
import com.beyondthecode.pithubproject.data.datasource.rest.api.IApiClient;
import com.beyondthecode.pithubproject.domain.CategoriaPlato;
import com.beyondthecode.pithubproject.domain.PedidoDetalle;
import com.beyondthecode.pithubproject.domain.Producto;
import com.beyondthecode.pithubproject.presentation.adapters.ProductoAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleRestauranteActivity extends AppCompatActivity {

    private final String TAG = "DetalleRestaurante";

    @BindView(R.id.ctl_detallerest_collapse)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.tbarDetalleRest)
    Toolbar toolbar;

    @BindView(R.id.imgLogoRest)
    ImageView imgLogoRest;

    @BindView(R.id.txtNomRestaurante)
    TextView txtNomRest;

    @BindView(R.id.txtCategoriaRestaurante)
    TextView txtCatRest;

    @BindView(R.id.rcvMenuRestaurante)
    RecyclerView rcvMenu;



    RecyclerView.LayoutManager layoutManager;

    int idRest;
    String nomRest;

    List<Producto>lstProd;

    List<Producto>lstCat;

    //List<CategoriaPlato> mCatPlatosList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_restaurante);
        ButterKnife.bind(this);

        if(getIntent()!=null){
            idRest = getIntent().getIntExtra("idRest",-1);
            nomRest = getIntent().getStringExtra("nomRest");
            /*ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(""+idRest);*/

            initParams();

            fetchProductos();

            populateRecycler();




        }
    }

    @Override
    public void onBackPressed() {

        List<PedidoDetalle>carrito = new SqlHelper(DetalleRestauranteActivity.this).obtenerOrden();

        if(!carrito.isEmpty()){
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Carrito de compras")
                    .setMessage("Si sales de esta pantalla perderás los productos que están en tu carrito. " +
                            "¿Deseas continuar?")
                    .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {



                            new SqlHelper(DetalleRestauranteActivity.this).eliminarPedidoActual();
                            DetalleRestauranteActivity.super.onBackPressed();
                        }
                    }).setNegativeButton("Cancelar", null).show();
        }else{
            super.onBackPressed();
        }




    }

    private void initParams(){
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        collapsingToolbarLayout = findViewById(R.id.ctl_detallerest_collapse);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        collapsingToolbarLayout.setTitle(""+nomRest);
    }


    private void fetchProductos(){
        IApiClient mApiService = WSData.getInterfaceService();
        Call<ProductosResponse> mService = mApiService.obtenerProductosxRestaurante(idRest);
        mService.enqueue(new Callback<ProductosResponse>() {
            @Override
            public void onResponse(Call<ProductosResponse> call, Response<ProductosResponse> response) {

                if(response.isSuccessful()){

                    ProductosResponse tipoRestResponse = response.body();
                    lstProd = tipoRestResponse.getData();
                    lstCat = new ArrayList<>();



                    for(int i=0; i<lstProd.size();i++){

                        //agregar solo 1 vez las categorias si existiera duplicados
                        if( !lstCat.containsAll(lstProd) ){
                            lstCat.add(new Producto(lstProd.get(i).getNomCat()));
                        }

                    }

                    ArrayList<CategoriaPlato> catplato = new ArrayList<>();

                    ArrayList<Producto> comida; //= new ArrayList<>();

                    CategoriaPlato cp;

                    Log.d(TAG,"tamaño de la lista:"+lstProd.size());

                    for(int i=0; i<lstCat.size();i++){


                        comida = new ArrayList<>();

                        for(int j=0; j<lstProd.size();j++){

                            if(lstCat.get(i).getNomCat().equals(lstProd.get(j).getNomCat())){
                                Log.d(TAG,"product"+j+" "+lstProd.get(j).getNomCat() + "/ categoria"+i+" "+ lstCat.get(i).getNomCat());

                                comida.add(new Producto(lstProd.get(i).getIdProd(),lstProd.get(j).getNomProd(),lstProd.get(j).getImgProd(),lstProd.get(j).getPreProd(),lstProd.get(j).getCaloriaProd()));

                            }
                        }


                        cp = new CategoriaPlato("kknero",comida);

                        cp.setTitle(lstCat.get(i).getNomCat());
                        //Log.d(TAG,"debug->"+cp.getTitle());
                        catplato.add(cp);


                    }

                    layoutManager = new LinearLayoutManager(DetalleRestauranteActivity.this);
                    rcvMenu.setLayoutManager(layoutManager);

                    ProductoAdapter adapter = new ProductoAdapter(catplato);
                    rcvMenu.setAdapter(adapter);



                    /*
                    //Para debugear quitar el bloque de comentario
                    for(Producto p : lstCat){
                         Log.d(TAG,"lista:"+p.getNomCat());

                    }*/

                    initValues();


                    //populateRestaurantes(tipoRestList);


                }
            }

            @Override
            public void onFailure(Call<ProductosResponse> call, Throwable t) {

                call.cancel();
                Log.d(TAG,"error en ->"+t);
                Toast.makeText(DetalleRestauranteActivity.this, R.string.serviceFailure, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.fabCarritoDetalleRestaurante)
    public void clicFab(){

        Intent intent = new Intent(this,DetalleConsumoActivity.class);
        intent.putExtra("nomRest",nomRest);
        intent.putExtra("idRest",idRest);
        startActivity(intent);

    }



    private void populateRecycler(){
        layoutManager = new LinearLayoutManager(this);
        rcvMenu.setLayoutManager(layoutManager);
       // rcvMenu.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        ArrayList<CategoriaPlato> catplato = new ArrayList<>();

        ArrayList<Producto> plato = new ArrayList<>();
        plato.add(new Producto(1,"kk","imagen1",12.5,4));
        plato.add(new Producto(2,"qqwe","iamgen2",12.5,4));

        CategoriaPlato cat1 = new CategoriaPlato("holi padre",plato);
        catplato.add(cat1);

        ArrayList<Producto> plato2 = new ArrayList<>();
        plato2.add(new Producto(3,"aveasd","ix",12.5,4));
        plato2.add(new Producto(4,"jojoas","iy",12.5,4));

        CategoriaPlato cat2 = new CategoriaPlato("segundo padre",plato2);
        catplato.add(cat2);

        ProductoAdapter adapter = new ProductoAdapter(catplato);
        rcvMenu.setAdapter(adapter);


    }



    private void initValues(){
        Log.d(TAG,"img->"+PithubConfig.getPathBaseImageWebClient() + lstProd.get(0).getLogoRest());

        Picasso.get()
                .load(PithubConfig.getPathBaseImageWebClient() + lstProd.get(0).getLogoRest())
                .into(imgLogoRest, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d(TAG,"picasso error->" + e);
                    }
                });

        txtNomRest.setText(lstProd.get(0).getNomrest());
        txtCatRest.setText(lstProd.get(0).getTipoRest());

    }
}
