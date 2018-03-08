package com.webbitmax.bitmax;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.webbitmax.bitmax.helper.VerifyConnection;
import com.webbitmax.bitmax.model.ServerRequest;
import com.webbitmax.bitmax.model.User;
import com.webbitmax.bitmax.retrofit.ApiService;
import com.webbitmax.bitmax.retrofit.RequestInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText et_email, et_password;
    private  Button btn_login;
    private Realm realm;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        realm = Realm.getDefaultInstance();

        et_email = (EditText)findViewById(R.id.et_email);
        et_password = (EditText)findViewById(R.id.et_password);
        btn_login = (Button)findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(et_email.length() == 0){
                    Snackbar.make(findViewById(R.id.activity_login), "Preencha Login", Snackbar.LENGTH_LONG).show();
                    et_email.requestFocus();
                }else if(et_password.length() == 0){
                    Snackbar.make(findViewById(R.id.activity_login), "Preencha Senha", Snackbar.LENGTH_LONG).show();
                    et_password.requestFocus();
                }else{

                   final String login = et_email.getText().toString();
                   final String senha = et_password.getText().toString();

                    processarLogin(login, senha);

                }
            }
        });

    }

    private void processarLogin(String login, String senha) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        VerifyConnection verifyConnection = new VerifyConnection(getApplicationContext());

        if (verifyConnection.checkConexao() == true) {

            RequestInterface requestInterface = ApiService.getApiService();

            Call<ServerRequest> call = requestInterface.login(login,senha);

            call.enqueue(new Callback<ServerRequest>() {
                @Override
                public void onResponse(Call<ServerRequest> call, Response<ServerRequest> response) {

                    ServerRequest serverRequest = response.body();

                    if(response.isSuccessful()){

                            if (serverRequest.isResult()) {

                                User user = serverRequest.getUser();

                                realm = Realm.getDefaultInstance();

                                realm.beginTransaction();

                                user.setLogged(true);
                                realm.copyToRealmOrUpdate(user);

                                realm.commitTransaction();

                                progressDialog.dismiss();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                        } else {

                                progressDialog.dismiss();

                                AlertDialog alertDialog;
                                alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                                alertDialog.setTitle(" Atenção!");
                                alertDialog.setCancelable(true);
                                alertDialog.setMessage("Login ou senha invalidio...");
                                alertDialog.show();

                        }

                }else{
                        progressDialog.dismiss();

                        Snackbar.make(findViewById(R.id.activity_login), "not isSuccess como o servidor", Snackbar.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<ServerRequest> call, Throwable t) {

                    progressDialog.dismiss();

                    Snackbar.make(findViewById(R.id.activity_login), "Error onFailure", Snackbar.LENGTH_LONG).show();

                }

            });

        }else {

            User user = realm.where(User.class).equalTo("usuario_login", login).equalTo("usuario_senha", md5(senha)).findFirst();

            if(realm.where(User.class).equalTo("usuario_login", login).equalTo("usuario_senha", md5(senha)).count() > 0){

                realm.beginTransaction();

                user.setLogged(true);
                realm.copyToRealmOrUpdate(user);
                realm.commitTransaction();

                progressDialog.dismiss();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }else{

                progressDialog.dismiss();

                AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                alertDialog.setTitle(" Atenção!");
                alertDialog.setCancelable(true);
                alertDialog.setMessage("Login ou senha invalidio...");
                alertDialog.show();
            }

        }
    }

    public static final String md5(String toEncrypt) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] array = digest.digest(toEncrypt.getBytes());
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < array.length; i++) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (Exception exc) {
            return "";
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
