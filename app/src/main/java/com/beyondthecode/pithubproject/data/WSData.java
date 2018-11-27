package com.beyondthecode.pithubproject.data;

import android.content.Context;
import android.content.Intent;

import com.beyondthecode.pithubproject.PitHubApp;
import com.beyondthecode.pithubproject.data.datasource.PithubConfig;
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
            if(response.code() == 401){

                Context context = PitHubApp.getInstance().getApplicationContext();

                Intent login = new Intent(context,LoginActivity.class);
                context.startActivity(login);

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
