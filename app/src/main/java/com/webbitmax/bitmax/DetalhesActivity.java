package com.webbitmax.bitmax;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.webbitmax.bitmax.helper.VerifyConnection;
import com.webbitmax.bitmax.model.Chamados;
import com.webbitmax.bitmax.model.ServerRequest;
import com.webbitmax.bitmax.model.User;
import com.webbitmax.bitmax.retrofit.ApiService;
import com.webbitmax.bitmax.retrofit.RequestInterface;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalhesActivity extends AppCompatActivity {

    Chamados chamados;
    TextView tv_tecnico, tv_tipo, tv_prazo, tv_nome, tv_endereco, tv_complem,
            tv_bairro, tv_cidade, tv_telefone, tv_celular, tv_login, tv_senha, tv_plano, tv_mensagem;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tv_tipo  = (TextView) findViewById(R.id.value_tipo);
        tv_prazo  = (TextView) findViewById(R.id.value_prazo);
        tv_nome  = (TextView) findViewById(R.id.value_nome);
        tv_endereco = (TextView) findViewById(R.id.value_endereco);
        tv_complem  = (TextView) findViewById(R.id.value_complemtno);
        tv_bairro = (TextView) findViewById(R.id.value_bairro);
        tv_cidade  = (TextView) findViewById(R.id.value_cidade);
        tv_telefone  = (TextView) findViewById(R.id.value_telefone);
        tv_celular  = (TextView) findViewById(R.id.value_celular);
        tv_login = (TextView) findViewById(R.id.value_login);
        tv_senha = (TextView) findViewById(R.id.value_senha);
        tv_plano  = (TextView) findViewById(R.id.value_plano);
        tv_mensagem  = (TextView) findViewById(R.id.value_mensagem);

        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("chamadoId");

        Realm realm = Realm.getDefaultInstance();

        chamados = realm.where(Chamados.class).equalTo("suporte_id", id).findFirst();


        getSupportActionBar().setTitle("Detalhes " + id);

        tv_tipo.setText(chamados.getTipo());
        tv_prazo.setText(chamados.getPrazodata());
        tv_nome.setText(chamados.getCliente_nome());
        tv_endereco.setText(chamados.getCliente_endereco());
        tv_complem.setText(chamados.getCliente_complemento());
        tv_bairro.setText(chamados.getCliente_bairro());
        tv_cidade.setText(chamados.getCliente_cidade());
        tv_telefone.setText(chamados.getTelefone());
        tv_celular.setText(chamados.getCelular());
        tv_login.setText(chamados.getCliente_login());
        tv_senha.setText(chamados.getCliente_senha());
        tv_mensagem.setText(chamados.getMensagem());

    }

    public void fecharChamado(Chamados chamados){
        FragmentManager fm = getFragmentManager();
        FecharFragment fecharChamadoFragment = FecharFragment.newInstance(chamados);
        fecharChamadoFragment.show(fm, "fechar_chamado");
    }


    public void pendenciaChamado(Chamados chamados){
        FragmentManager fm = getFragmentManager();
        PendenciaFragment pendenciaFragment = PendenciaFragment.newInstance(chamados);
        pendenciaFragment.show(fm, "Colocar_pendencia");
    }


    public void resetMAC(Chamados chamados){

        VerifyConnection verifyConnection = new VerifyConnection(this);

        if(verifyConnection.checkConexao()){

            final String clienteLogin = chamados.getCliente_login();

            realm = Realm.getDefaultInstance();

            User user = realm.where(User.class).equalTo("logged", true).findFirst();

            final String login = user.getUsuario_login();

            RequestInterface requestInterface = ApiService.getApiService();

            Call<ServerRequest> call = requestInterface.resetarmac(clienteLogin, login);

            call.enqueue(new Callback<ServerRequest>() {
                @Override
                public void onResponse(Call<ServerRequest> call, Response<ServerRequest> response) {

                    if(response.isSuccessful()){

                        if(response.body().isResult()){

                            Snackbar.make(findViewById(R.id.activity_detalhes), response.body().getMensagem(), Snackbar.LENGTH_LONG).show();
                        }else{

                            Snackbar.make(findViewById(R.id.activity_detalhes), response.body().getMensagem(), Snackbar.LENGTH_LONG).show();
                        }

                    }else{

                        Snackbar.make(findViewById(R.id.activity_detalhes), "Problema ao resetar o mac", Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ServerRequest> call, Throwable t) {

                    Snackbar.make(findViewById(R.id.activity_detalhes), t.getMessage(), Snackbar.LENGTH_LONG).show();

                }
            });

        }else{

            Snackbar.make(findViewById(R.id.activity_detalhes), "Não é possivel resetar o MAC em modo Offiline", Snackbar.LENGTH_LONG).show();
        }


    }

    public void marcarLocation(Chamados chamados){

        Intent intent = new Intent(getApplicationContext(), MapaActivity.class);
        intent.putExtra("codigo", chamados.getCodigo());
        startActivity(intent);
        /*

        VerifyConnection verifyConnection = new VerifyConnection(this);

        if(verifyConnection.checkConexao()){

            final int clienteCodigo = chamados.getCliente();

            RequestInterface requestInterface = ApiService.getApiService();

            Call<ServerRequest> call = requestInterface.marcarPonto(clienteCodigo);

            call.enqueue(new Callback<ServerRequest>() {
                @Override
                public void onResponse(Call<ServerRequest> call, Response<ServerRequest> response) {

                    if(response.isSuccessful()){

                        if(response.body().isResult()){

                            ////Snackbar.make(findViewById(R.id.activity_detalhes), response.body().getMensagem(), Snackbar.LENGTH_LONG).show();
                        }else{

                            ////Snackbar.make(findViewById(R.id.activity_detalhes), response.body().getMensagem(), Snackbar.LENGTH_LONG).show();
                        }

                    }else{

                        Snackbar.make(findViewById(R.id.activity_detalhes), "Problema ao resetar o mac", Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ServerRequest> call, Throwable t) {

                    Snackbar.make(findViewById(R.id.activity_detalhes), t.getMessage(), Snackbar.LENGTH_LONG).show();

                }
            });

        }else{

            Snackbar.make(findViewById(R.id.activity_detalhes), "Não é possivel resetar o MAC em modo Offiline", Snackbar.LENGTH_LONG).show();
        }

        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalhes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.menu_fechar){

                    AlertDialog.Builder builder = new AlertDialog.Builder(DetalhesActivity.this);
                    builder.setCancelable(false);
                    builder.setMessage("Deseja fechar este chamado?");
                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fecharChamado(chamados);

                        }
                    }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();


        }else if(id == R.id.menu_pendencia){

            AlertDialog.Builder builder2 = new AlertDialog.Builder(DetalhesActivity.this);
            builder2.setCancelable(false);
            builder2.setMessage("Deseja colocar pendencia?");
            builder2.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    pendenciaChamado(chamados);

                }
            }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).show();
        }else if(id == R.id.menu_mac){
            AlertDialog.Builder builder2 = new AlertDialog.Builder(DetalhesActivity.this);
            builder2.setCancelable(false);
            builder2.setMessage("Resetar o MAC do cliente?");
            builder2.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    resetMAC(chamados);

                }
            }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).show();

        }else if(id == R.id.menu_location){
            AlertDialog.Builder builder2 = new AlertDialog.Builder(DetalhesActivity.this);
            builder2.setCancelable(false);
            builder2.setMessage("Deseja MAC do cliente?");
            builder2.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    marcarLocation(chamados);

                }
            }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).show();

        }

        return super.onOptionsItemSelected(item);
    }
}
