package com.beyondthecode.pithubproject.data.datasource;

import android.content.Context;
import android.content.SharedPreferences;

import com.beyondthecode.pithubproject.PitHubApp;
import com.beyondthecode.pithubproject.domain.Cliente;

public class PreferenceManager {

    private final SharedPreferences prefs;

    private static PreferenceManager instancia = null;

    public static void init (Context context){
        if(instancia==null){
            instancia = new PreferenceManager(context);
        }
    }

    public static PreferenceManager getInstancia(){
        if(instancia == null){
            throw new RuntimeException("PreferenceManager no fue inicializado en el App");
        }
        return instancia;
    }

    private PreferenceManager (Context context){
        prefs = context.getSharedPreferences(PitHubApp.PREF_FILE, Context.MODE_PRIVATE);
    }

    public void setUser(Cliente cliente){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(PitHubApp.PREF_LOGGED,true);
        editor.putString(PitHubApp.PREF_CLI_EMAIL,cliente.getEmailcli());
        editor.putString(PitHubApp.PREF_CLI_ID,cliente.getIdcli());
        editor.apply();
    }

    public boolean isUserLogged(){
        return prefs.getBoolean(PitHubApp.PREF_LOGGED,false);
    }

    public void logoutUser(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(PitHubApp.PREF_LOGGED,false);
        editor.putString(PitHubApp.PREF_CLI_EMAIL,"");
        editor.putString(PitHubApp.PREF_CLI_ID,"");
        editor.apply();
    }

}
