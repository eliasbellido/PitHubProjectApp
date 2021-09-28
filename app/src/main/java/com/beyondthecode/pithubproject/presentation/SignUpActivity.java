package com.beyondthecode.pithubproject.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.beyondthecode.pithubproject.PitHubApp;
import com.beyondthecode.pithubproject.R;
import com.beyondthecode.pithubproject.data.WSData;
import com.beyondthecode.pithubproject.data.datasource.PreferenceManager;
import com.beyondthecode.pithubproject.data.datasource.request.LoginRequest;
import com.beyondthecode.pithubproject.data.datasource.response.LoginResponse;
import com.beyondthecode.pithubproject.data.datasource.rest.api.IApiClient;
import com.beyondthecode.pithubproject.domain.Cliente;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.textInputLayoutEmail)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.edtsignup_email)
    EditText edtEmail;

    @BindView(R.id.textInputLayoutClave)
    TextInputLayout inputLayoutClave;
    @BindView(R.id.edtsignup_pass)
    EditText edtClave;


    @BindView(R.id.textInputLayoutConfirmarClave)
    TextInputLayout inputLayoutConfirmarClave;
    @BindView(R.id.edtsignup_confirmpass)
    EditText edtConfirmarClave;

    @BindView(R.id.pgbSignUp)
    ProgressBar pgbSignUp;

    @BindView(R.id.btnSignUp)
    Button btnSingUp;
    private static final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.txtIngresar)
    public void ingresar(){

        Intent loginActivity = new Intent(this, LoginActivity.class);
        loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(loginActivity);
        //finish();

        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @OnClick(R.id.btnSignUp)
    public void registrarNuevoUsuario(){

        submitFormNewUser(edtEmail.getText().toString().trim(),edtClave.getText().toString().trim());

    }

    public void submitFormNewUser(final String email, final String clave){

        if(!validarUsuario()){
            return;
        }else if(!validarClave()){
            return;
        }else if(!validarConfirmarClave()){
            return;
        }

        Toast.makeText(this, "Gracias por validar todo!", Toast.LENGTH_SHORT).show();
        pgbSignUp.setVisibility(View.VISIBLE);
        btnSingUp.setVisibility(View.INVISIBLE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                registrarUsuario(email,clave);
            }
        }, 2500);

    }


    private boolean validarUsuario() {

        if(TextUtils.isEmpty(edtEmail.getText())){

            inputLayoutEmail.setError(getString(R.string.msgerror_ingresarEmail));
            requestFocus(edtEmail);
            return false;
        }else{
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;

    }

    private boolean validarClave() {

        if(TextUtils.isEmpty(edtClave.getText())){
            inputLayoutClave.setError(getString(R.string.msgerror_ingresarClave));
            requestFocus(edtClave);
            return false;
        }else{
            inputLayoutClave.setErrorEnabled(false);
        }
        return true;

    }

    private boolean validarConfirmarClave() {

        if(TextUtils.isEmpty(edtConfirmarClave.getText())){
            inputLayoutConfirmarClave.setError(getString(R.string.msgerror_ingresarConfirmarClave));
            requestFocus(edtConfirmarClave);
            return false;
        }else if(!edtConfirmarClave.getText().toString().equals(edtClave.getText().toString())){
            inputLayoutConfirmarClave.setError(getString(R.string.msgerror_clavesNoCoinciden));
            requestFocus(edtConfirmarClave);
            return false;
        }
        else{
            inputLayoutConfirmarClave.setErrorEnabled(false);
        }
        return true;

    }

    private void requestFocus(View view){
        if(view.requestFocus()){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

    }

    private void registrarUsuario(final String email, final String clave){
        Log.d(TAG,"En metodo verifyCredentials()");

        IApiClient mApiService = WSData.getInterfaceService();
        Call<LoginResponse> mService = mApiService.registrarusuario(new LoginRequest(email,clave));
        mService.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pgbSignUp.setVisibility(View.INVISIBLE);
                btnSingUp.setVisibility(View.VISIBLE);


                if(response.isSuccessful()){

                    LoginResponse loginResponse = response.body();

                    //revisar si los datos son los correctos
                    if(loginResponse.getMensaje().equals("ok")){
                        Cliente cliente = new Cliente();
                        cliente.setIdcli(response.body().getData().getIdcli());
                        cliente.setEmailcli(edtEmail.getText().toString());

                        PreferenceManager.getInstancia().setUser(cliente,"");



                        Intent mainIntent = new Intent(SignUpActivity.this,MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(mainIntent);
                        finish();


                        Toast.makeText(SignUpActivity.this, "se obtuvo: " + cliente.getEmailcli() + "/" + cliente.getIdcli(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(SignUpActivity.this, getString(R.string.onSignUpError), Toast.LENGTH_SHORT).show();
                    }


                }else{
                    String errorMensaje = response.message();
                    Toast.makeText(SignUpActivity.this, errorMensaje, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                call.cancel();
                //Log.d(TAG,getString(R.string.onfailureService));
                Log.d(TAG,"acà: " + t);
                Toast.makeText(SignUpActivity.this, getString(R.string.serviceFailure), Toast.LENGTH_SHORT).show();
                pgbSignUp.setVisibility(View.INVISIBLE);
                btnSingUp.setVisibility(View.VISIBLE);

            }
        });
    }


}
