package com.beyondthecode.pithubproject.presentation.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beyondthecode.pithubproject.PitHubApp;
import com.beyondthecode.pithubproject.R;
import com.beyondthecode.pithubproject.data.WSData;
import com.beyondthecode.pithubproject.data.datasource.response.TipoRestaurantesResponse;
import com.beyondthecode.pithubproject.data.datasource.rest.api.IApiClient;
import com.beyondthecode.pithubproject.domain.TipoRestaurante;
import com.beyondthecode.pithubproject.presentation.DetalleConsumoActivity;
import com.beyondthecode.pithubproject.presentation.adapters.CategoriaRestauranteAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RestaurantesFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    View view;

    @BindView(R.id.rcv_frgmentRestauranteCategorias)
    RecyclerView rcvCategoriaRest;

    RecyclerView.LayoutManager layoutManager;

    public RestaurantesFragment() {
        // Required empty public constructor
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Restaurantes");
        obtenerTipoRestaurantes();


    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_restaurantes, container, false);

        ButterKnife.bind(this,view);

        return view;
    }




    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;


    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void obtenerTipoRestaurantes(){
        SharedPreferences sp = getContext().getSharedPreferences(PitHubApp.PREF_FILE,Context.MODE_PRIVATE);
        String token = sp.getString(PitHubApp.PREF_TOKEN,"null");
        IApiClient mApiService = WSData.getInterfaceService();


        Call<TipoRestaurantesResponse> mService = mApiService.obtenerCategoriasRestaurantes(token);
        mService.enqueue(new Callback<TipoRestaurantesResponse>() {
            @Override
            public void onResponse(Call<TipoRestaurantesResponse> call, Response<TipoRestaurantesResponse> response) {

                if(response.isSuccessful()){

                    TipoRestaurantesResponse tipoRestResponse = response.body();
                    List<TipoRestaurante> tipoRestList = tipoRestResponse.getData();

                    cargarCategoriaRestaurante(tipoRestList);
                    //Toast.makeText(getContext(), ""+tipoRestList, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TipoRestaurantesResponse> call, Throwable t) {

                call.cancel();
                //Toast.makeText(getContext(), R.string.serviceFailure, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void cargarCategoriaRestaurante(List<TipoRestaurante> lstRestCategoria){


        rcvCategoriaRest.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(getActivity());
        rcvCategoriaRest.setLayoutManager(layoutManager);


        CategoriaRestauranteAdapter mAdapter = new CategoriaRestauranteAdapter(getContext(),lstRestCategoria);


        Log.d("list","tamaño de la lista para cargar" + mAdapter.getItemCount());

        rcvCategoriaRest.setAdapter(mAdapter);
    }


}
