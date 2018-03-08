package com.webbitmax.bitmax;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.webbitmax.bitmax.helper.VerifyConnection;
import com.webbitmax.bitmax.model.Chamados;
import com.webbitmax.bitmax.model.ServerRequest;
import com.webbitmax.bitmax.model.User;
import com.webbitmax.bitmax.retrofit.ApiService;
import com.webbitmax.bitmax.retrofit.RequestInterface;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChamadosActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abertos);

        getSupportActionBar().setTitle("Chamados");

        realm = Realm.getDefaultInstance();

        updateChamados();

    }


    public void updateChamados(){

        realm = Realm.getDefaultInstance();

        RealmResults<Chamados> results = realm.where(Chamados.class)
                .equalTo("status", "fechado")
                .findAll();


        if(results != null && results.size() > 0){
            for (int i = 0; i < results.size(); i++){

                Chamados chamado = results.get(i);

                int id = chamado.getSuporte_id();
                int cliente = chamado.getCliente();
                String resolvido = chamado.getResolvido();
                String relatorio = chamado.getRelatorio();
                String iniciado = chamado.getIniciado();
                String datafechamento = chamado.getFechado();
                String mcabo = chamado.getMcabo();
                String qconector = chamado.getQconector();

                VerifyConnection verifyConnection = new VerifyConnection(ChamadosActivity.this);

                if(verifyConnection.checkConexao() == true) {

                    RequestInterface requestInterface = ApiService.getApiService();

                    Call<ServerRequest> call = requestInterface.updateFechar(id, cliente, resolvido, relatorio, iniciado, datafechamento, mcabo, qconector);

                    call.enqueue(new Callback<ServerRequest>() {
                        @Override
                        public void onResponse(Call<ServerRequest> call, Response<ServerRequest> response) {

                            if(response.isSuccessful()){

                                Snackbar.make(findViewById(R.id.content_main), "Update Sucesso", Snackbar.LENGTH_LONG).show();

                            }else{

                                Snackbar.make(findViewById(R.id.content_main), "Update Failhou", Snackbar.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ServerRequest> call, Throwable t) {

                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

                            Snackbar.make(findViewById(R.id.content_main), "Update onFailure", Snackbar.LENGTH_LONG).show();
                        }
                    });

                }
            }
            buscarChamados();

        }else{
            buscarChamados();
        }

    }


    public void buscarChamados(){

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        VerifyConnection verifyConnection = new VerifyConnection(this);

        if(verifyConnection.checkConexao() == true){

            realm = Realm.getDefaultInstance();

            User user = realm.where(User.class).equalTo("logged", true).findFirst();

            final String login = user.getUsuario_login();

            RequestInterface requestService = ApiService.getApiService();

            Call<ServerRequest> call = requestService.chamados(login);

            call.enqueue(new Callback<ServerRequest>() {

                @Override
                public void onResponse(Call<ServerRequest> call, Response<ServerRequest> response) {

                    ServerRequest lista = response.body();

                    realm = Realm.getDefaultInstance();

                    realm.beginTransaction();

                    realm.delete(Chamados.class);

                    for(Chamados c : lista.getChamados()){

                        realm.copyToRealmOrUpdate(c);
                    }

                    realm.commitTransaction();

                    progressDialog.dismiss();


                    realm = Realm.getDefaultInstance();

                    RealmResults<Chamados> results = realm.where(Chamados.class)
                            .findAll();

                    ///Log.e("TAG-VERIFICAR", results.toString());

                    listaChamados();
                }

                @Override
                public void onFailure(Call<ServerRequest> call, Throwable t) {

                    progressDialog.dismiss();

                    Snackbar.make(findViewById(R.id.main_content), "Error onFailure", Snackbar.LENGTH_LONG).show();

                }
            });

        }else{

            progressDialog.dismiss();

            listaChamados();

        }

        progressDialog.dismiss();

    }


    public void listaChamados(){

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    public void abrirChamado(final Chamados chamados){

        realm = Realm.getDefaultInstance();

        final int id = chamados.getSuporte_id();

        Chamados query = realm.where(Chamados.class).equalTo("suporte_id", id).findFirst();

        if(query.isInitiated()){

            chamarDetalhes(chamados);

        }else{

            AlertDialog.Builder builder = new AlertDialog.Builder(ChamadosActivity.this);
            builder.setMessage("Deseja iniciar esse chamado?")
                    .setCancelable(false)
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        iniciaChamado(chamados);

                        }
                    })
                    .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }

    }

    public void iniciaChamado(final Chamados chamados){

        realm = Realm.getDefaultInstance();

        final int id = chamados.getSuporte_id();

        String datainiciado = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        Chamados query = realm.where(Chamados.class).equalTo("suporte_id", id).findFirst();

        realm.beginTransaction();

        query.setInitiated(true);
        query.setIniciado(datainiciado);
        realm.copyToRealmOrUpdate(query);

        realm.commitTransaction();

        VerifyConnection verifyConnection = new VerifyConnection(ChamadosActivity.this);

        if(verifyConnection.checkConexao() == true) {

            realm = Realm.getDefaultInstance();

            User user = realm.where(User.class).equalTo("logged", true).findFirst();

            final String login = user.getUsuario_login();

            RequestInterface requestInterface = ApiService.getApiService();

            Call<ServerRequest> call = requestInterface.iniciar(id, login);

            call.enqueue(new Callback<ServerRequest>() {
                @Override
                public void onResponse(Call<ServerRequest> call, Response<ServerRequest> response) {

                    ServerRequest serverRequest = response.body();

                    if(response.isSuccessful()){

                        if(serverRequest.isResult()) {

                            Snackbar.make(findViewById(R.id.main_content), "Chamado iniciado", Snackbar.LENGTH_LONG).show();

                            chamarDetalhes(chamados);
                        }else{
                            Snackbar.make(findViewById(R.id.main_content), "Voçẽ já tem um chamado iniciado", Snackbar.LENGTH_LONG).show();
                        }
                    }else{
                       Snackbar.make(findViewById(R.id.main_content), "Erro not isSuccess", Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ServerRequest> call, Throwable t) {

                    Snackbar.make(findViewById(R.id.main_content), "Erro not isSuccess", Snackbar.LENGTH_LONG).show();
                }
            });

        }else{
            Snackbar.make(findViewById(R.id.main_content), "Chamado iniciado XXXX", Snackbar.LENGTH_LONG).show();
            chamarDetalhes(chamados);
        }
    }

    public void chamarDetalhes(final Chamados chamados){
        Intent intent = new Intent(getApplicationContext(), DetalhesActivity.class);
        intent.putExtra("chamadoId", chamados.getSuporte_id());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_atualizar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_atualizar:
                buscarChamados();
        }

        return super.onOptionsItemSelected(item);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    return new Abertos();
                case 1:
                    return new Pendencias();
                case 2:
                    return new Fechados();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Abertos";
                case 1:
                    return "Pendencias";
                case 2:
                    return "Fechados";
            }
            return null;
        }


    }

}
