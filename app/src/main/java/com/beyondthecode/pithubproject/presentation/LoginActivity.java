package com.beyondthecode.pithubproject.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.beyondthecode.pithubproject.BuildConfig;
import com.beyondthecode.pithubproject.PitHubApp;
import com.beyondthecode.pithubproject.R;
import com.beyondthecode.pithubproject.data.WSData;
import com.beyondthecode.pithubproject.data.datasource.PithubConfig;
import com.beyondthecode.pithubproject.data.datasource.PreferenceManager;
import com.beyondthecode.pithubproject.data.datasource.request.LoginRequest;
import com.beyondthecode.pithubproject.data.datasource.response.LoginResponse;
import com.beyondthecode.pithubproject.data.datasource.rest.api.IApiClient;
import com.beyondthecode.pithubproject.domain.Cliente;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edtUser)
    EditText edtusuario;

    @BindView(R.id.edtClave)
    EditText edtclave;

    @BindView(R.id.btnIngresar)
    Button btnLogin;
    @BindView(R.id.pgbLogin)
    ProgressBar pgbLogin;

    private static AppCompatActivity mActivity;
    private Context mContext;
    private static final String TAG = "LoginActivity";

    PithubConfig pithubConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        mActivity = this;
        ButterKnife.bind(this);

        pithubConfig = PithubConfig.getInstancia(mContext);

        initParams();

    }

    private void initParams(){
        if(BuildConfig.IS_DEBUGGING){
            edtusuario.setText(R.string.user4defaulttesting);
            edtclave.setText(R.string.clave4defaulttesting);
        }
    }

    @OnClick(R.id.btnIngresar)
    public void ingresar(){
        //Toast.makeText(mActivity, getString(R.string.msgBienvenida), Toast.LENGTH_SHORT).show();
        pgbLogin.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.INVISIBLE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                verifyCredentials(edtusuario.getText().toString().trim(),edtclave.getText().toString().trim());
            }
        }, 2500);


    }

    /*
    * Servicios con Retrofit*/

    private void verifyCredentials(final String email, final String clave){
        Log.d(TAG,"En metodo verifyCredentials()");

        IApiClient mApiService = WSData.getInterfaceService();
        Call<LoginResponse> mService = mApiService.autenticar(new LoginRequest(email,clave));
        mService.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pgbLogin.setVisibility(View.INVISIBLE);
                btnLogin.setVisibility(View.VISIBLE);


                if(response.isSuccessful()){

                    LoginResponse loginResponse = response.body();

                    //revisar si los datos son los correctos
                    if(loginResponse.getMensaje().equals("ok")){
                        Cliente cliente = loginResponse.getData();

                        PreferenceManager.getInstancia().setUser(cliente);



                        Intent loginIntent = new Intent(mContext,MainActivity.class);
                        startActivity(loginIntent);
                        finish();


                        Toast.makeText(LoginActivity.this, "se obtuvo: " + cliente.getEmailcli() + "/" + cliente.getIdcli(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(mContext, getString(R.string.credentialsFailedMsg), Toast.LENGTH_SHORT).show();
                    }


                }else{
                    String errorMensaje = response.message();
                    Toast.makeText(LoginActivity.this, errorMensaje, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                call.cancel();
                //Log.d(TAG,getString(R.string.onfailureService));
                Log.d(TAG,"acà: " + t);
                Toast.makeText(mContext, getString(R.string.serviceFailure), Toast.LENGTH_SHORT).show();
                pgbLogin.setVisibility(View.INVISIBLE);
                btnLogin.setVisibility(View.VISIBLE);

            }
        });
    }

    
    @OnClick(R.id.txtRegistrar)
    public void registrar(){
        SharedPreferences sp = mContext.getSharedPreferences(PitHubApp.PREF_FILE,Context.MODE_PRIVATE);
        String email = sp.getString(PitHubApp.PREF_CLI_EMAIL,"");
        Toast.makeText(mActivity, "tocaste en registrar "+email, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        verificarUsuarioLogueado();
    }

    private void verificarUsuarioLogueado(){
        if (PreferenceManager.getInstancia().isUserLogged()){
            Intent redirigir = new Intent(mContext,MainActivity.class);
            startActivity(redirigir);
            finish();
            Toast.makeText(mActivity, getString(R.string.userLogged), Toast.LENGTH_SHORT).show();
        }
    }
}
