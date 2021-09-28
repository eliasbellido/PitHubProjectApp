package com.beyondthecode.pithubproject.data;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.beyondthecode.pithubproject.PitHubApp;
import com.beyondthecode.pithubproject.data.datasource.PithubConfig;
import com.beyondthecode.pithubproject.data.datasource.PreferenceManager;
import com.beyondthecode.pithubproject.data.datasource.rest.api.IApiClient;
import com.beyondthecode.pithubproject.presentation.LoginActivity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WSData {

    private static OkHttpClient getOkHttpClient(){
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);
        logger.redactHeader("x-access-token");


        return new OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor(unauthorizedInterceptor)
                .build();
    }

    //controlar los accesos no permitidos
    private static Interceptor unauthorizedInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            if(response.code() == 403){

                final Context context = PitHubApp.getInstance().getApplicationContext();

                PreferenceManager.getInstancia().logoutUser();

                Intent login = new Intent(context,LoginActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(login);


                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "No tiene los permisos necesarios para usar nuestra app.", Toast.LENGTH_SHORT).show();
                    }
                };

                new Handler(Looper.getMainLooper()).post(runnable);

            }
            return response;
        }
    };

    public static IApiClient getInterfaceService(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PithubConfig.getPathAPI())
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(IApiClient.class);
    }
}
