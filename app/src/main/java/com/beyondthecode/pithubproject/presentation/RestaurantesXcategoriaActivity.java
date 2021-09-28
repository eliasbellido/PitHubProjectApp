package com.beyondthecode.pithubproject.presentation;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.beyondthecode.pithubproject.PitHubApp;
import com.beyondthecode.pithubproject.R;
import com.beyondthecode.pithubproject.data.WSData;
import com.beyondthecode.pithubproject.data.datasource.response.RestaurantesResponse;
import com.beyondthecode.pithubproject.data.datasource.rest.api.IApiClient;
import com.beyondthecode.pithubproject.domain.Restaurante;
import com.beyondthecode.pithubproject.presentation.adapters.RestauranteAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantesXcategoriaActivity extends AppCompatActivity {

    @BindView(R.id.rcv_restaurantesxcategoria)
    RecyclerView rcvRest;

    RecyclerView.LayoutManager layoutManager;

    private static final String TAG = "RestauranteXcategoria";

    private int idcategoria;
    private String categoria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantes_xcategoria);
        ButterKnife.bind(this);

        if(getIntent()!=null){
            idcategoria = getIntent().getIntExtra("idcategoriaRest",-1);
            categoria = getIntent().getStringExtra("categoria");
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(""+categoria);

            obtenerRestaurantes(idcategoria);




        }
    }

    private void obtenerRestaurantes(int idcategoria){
        SharedPreferences sp = this.getSharedPreferences(PitHubApp.PREF_FILE,Context.MODE_PRIVATE);
        String token = sp.getString(PitHubApp.PREF_TOKEN,"null");
        IApiClient mApiService = WSData.getInterfaceService();
        Call<RestaurantesResponse> mService = mApiService.obtenerRestaurantesxCategoria(token, idcategoria);
        mService.enqueue(new Callback<RestaurantesResponse>() {
            @Override
            public void onResponse(Call<RestaurantesResponse> call, Response<RestaurantesResponse> response) {

                if(response.isSuccessful()){

                    RestaurantesResponse tipoRestResponse = response.body();
                    List<Restaurante> tipoRestList = tipoRestResponse.getData();

                    for(Restaurante tr : tipoRestList){
                        Log.d(TAG,""+tr.getNomrest());
                        Log.d(TAG,""+tr.getDistrito());

                    }

                    populateRestaurantes(tipoRestList);


                }
            }

            @Override
            public void onFailure(Call<RestaurantesResponse> call, Throwable t) {

                call.cancel();
                Log.d(TAG,"error en ->"+t);
                Toast.makeText(RestaurantesXcategoriaActivity.this, R.string.serviceFailure, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void populateRestaurantes(List<Restaurante> lstRest){
        rcvRest.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        rcvRest.setLayoutManager(layoutManager);
        rcvRest.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        RestauranteAdapter mAdapter = new RestauranteAdapter(this,lstRest);
        mAdapter.notifyDataSetChanged();


        Log.d("list","tamaño de la lista para cargar" + mAdapter.getItemCount());

        rcvRest.setAdapter(mAdapter);
    }
}
