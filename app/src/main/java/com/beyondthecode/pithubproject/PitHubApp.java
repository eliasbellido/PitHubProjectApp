package com.beyondthecode.pithubproject;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;

import com.beyondthecode.pithubproject.data.datasource.PreferenceManager;
import com.beyondthecode.pithubproject.data.datasource.local.SqlHelper;
import com.beyondthecode.pithubproject.di.PitHubAppComponent;
import com.beyondthecode.pithubproject.domain.PedidoDetalle;

import java.util.List;




public class PitHubApp extends Application {

    private static PitHubApp singleton;

    public static SharedPreferences PREF_PITHUB_APP;


    public static synchronized PitHubApp getInstance(){
        return singleton;
    }

    private static PitHubAppComponent pitHubAppComponent;


    @Override
    public void onCreate() {
        super.onCreate();

        singleton = this;
        initPitHubComponent();



        PreferenceManager.init(getApplicationContext());
        PREF_PITHUB_APP = getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);

        List<PedidoDetalle> carrito = new SqlHelper(PitHubApp.this).obtenerOrden();

        if(!carrito.isEmpty()){
            new SqlHelper(PitHubApp.this).eliminarPedidoActual();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static PitHubAppComponent getPitHubAppComponent() {
        return pitHubAppComponent;
    }

    private void initPitHubComponent(){


    }
    /*
    * VARIABLES PREFERENCIAS*/

    public static final String PREF_FILE = "PITHUB_PREF";
    public static final String PREF_LOGGED = "PREF_LOGGED";
    public static final String PREF_CLI_EMAIL = "PREF_CLI_EMAIL";
    public static final String PREF_CLI_ID = "PREF_CLI_ID";
    public static final String PREF_TOKEN = "PREF_TOKEN";



}
