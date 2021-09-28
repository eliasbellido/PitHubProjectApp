package com.beyondthecode.pithubproject.di;

import android.content.Context;
import android.content.Intent;

import com.beyondthecode.pithubproject.PitHubApp;
import com.beyondthecode.pithubproject.data.datasource.PithubConfig;
import com.beyondthecode.pithubproject.data.datasource.rest.api.IApiClient;
import com.beyondthecode.pithubproject.presentation.LoginActivity;
import com.beyondthecode.pithubproject.util.rx.PitHubAppRxSchedulers;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {


    @Provides
    @Singleton
    public static OkHttpClient provideOkHttpClient(){
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor(unauthorizedInterceptor)
                .build();
    }


    @Provides
    @Singleton
    Cache provideCache(File file){
        return new Cache(file,10*10*1000);
    }


    @Provides
    @Singleton
    File provideCacheFile(Context context){
        return context.getFilesDir();
    }


    @Provides
    @Singleton
    RxJava2CallAdapterFactory provideRxAdapter(){
        return RxJava2CallAdapterFactory.createWithScheduler(PitHubAppRxSchedulers.INTERNET_SCHEDULERS);
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
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(IApiClient.class);
    }
}
