package com.beyondthecode.pithubproject.data.datasource;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class PithubConfig {

    public static PithubConfig instancia = null;
    private Context context;

    //Beta pathservice
    //private static String pathBase = "http://localhost:8081";
    private static String pathBase = "http://localhost:8080";
    private static String pathBaseImageWebClient = "http://localhost:8093/pitrestaurant/";

    //private static String pathAPI =  pathBase + "/api/rest/";
    private static String pathAPI =  pathBase + "/finalSemana13ServiciosWeb2_/rest/";


    public static PithubConfig getInstancia(Context context){
        if(instancia == null){
            instancia = new PithubConfig(context);
        }
        return instancia;
    }

    private PithubConfig(Context context){ this.context = context;}

    public static String getPathAPI() {
        return pathAPI;
    }

    public static String getPathBaseImageWebClient() {
        return pathBaseImageWebClient;
    }

    public boolean checkConnectivity(){

        boolean state_Network = true;

        ConnectivityManager connectivityManager;
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager != null)
        {

            try{

                NetworkInfo result = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                NetworkInfo resultWIFI = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                if (result != null || resultWIFI != null)
                {
                    if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                        Log.v("AL_NETWORK", "MOBILE-WIFI");
                        state_Network = true;

                    }else{
                        Log.v("AL_NETWORK", "NO - MOBILE-WIFI");
                        state_Network = false;
                    }
                }
                else
                {
                    Log.v("AL_NETWORK", "NO MOBILE && MOBILE-WIFI");
                    state_Network = false;
                }

            }catch (Exception e){
                e.printStackTrace();
            }


        }else{
            Log.v("AL_NETWORK", "NO NETWORK");
            state_Network = false;
        }

        return state_Network;

    }
}
