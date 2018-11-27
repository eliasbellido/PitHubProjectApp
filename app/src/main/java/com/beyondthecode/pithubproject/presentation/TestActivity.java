package com.beyondthecode.pithubproject.presentation;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.beyondthecode.pithubproject.R;
import com.beyondthecode.pithubproject.data.WSData;
import com.beyondthecode.pithubproject.data.datasource.response.RestaurantesResponse;
import com.beyondthecode.pithubproject.data.datasource.response.TipoRestaurantesResponse;
import com.beyondthecode.pithubproject.data.datasource.rest.api.IApiClient;
import com.beyondthecode.pithubproject.domain.Restaurante;
import com.beyondthecode.pithubproject.domain.TipoRestaurante;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";

    private int idcategoria;
    private String categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        if(getIntent()!=null){
            idcategoria = getIntent().getIntExtra("idcategoriaRest",-1);
            categoria = getIntent().getStringExtra("categoria");
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(""+categoria);

            obtenerTipoRestaurantes(idcategoria);


        }


    }

    private void obtenerTipoRestaurantes(int idcategoria){
        IApiClient mApiService = WSData.getInterfaceService();
        Call<RestaurantesResponse> mService = mApiService.obtenerRestaurantesxCategoria(idcategoria);
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

                    Toast.makeText(TestActivity.this, ""+tipoRestList, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestaurantesResponse> call, Throwable t) {

                call.cancel();
                Toast.makeText(TestActivity.this, R.string.serviceFailure, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
