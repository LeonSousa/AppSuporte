package com.webbitmax.bitmax;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.webbitmax.bitmax.helper.NetworkStateChangeReceiver;
import com.webbitmax.bitmax.helper.VerifyConnection;
import com.webbitmax.bitmax.model.Chamados;
import com.webbitmax.bitmax.model.ServerRequest;
import com.webbitmax.bitmax.model.User;
import com.webbitmax.bitmax.retrofit.ApiService;
import com.webbitmax.bitmax.retrofit.RequestInterface;

import static com.webbitmax.bitmax.helper.NetworkStateChangeReceiver.IS_NETWORK_AVAILABLE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView nome, email;
    Realm realm;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";
    private BroadcastReceiver broadcastReceiver;
    private ProgressBar progresso;
    private TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /// progresso = (ProgressBar)findViewById(R.id.progressBar2);

        ////////////////////

       // tvMsg = (TextView) findViewById(R.id.tvMsg);
     /*

        if (verificarPlayServices()) {
            Intent intent = new Intent(this, RegistroIntentService.class);
            startService(intent);
        }

        */
            /////////////////////

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        IntentFilter intentFilter = new IntentFilter(NetworkStateChangeReceiver.NETWORK_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false);
                String networkStatus = isNetworkAvailable ? "conectada" : "desconectada";

                Snackbar.make(findViewById(R.id.content_main), "Network Status: " + networkStatus, Snackbar.LENGTH_LONG).show();

                if(networkStatus == "conectada"){
                    updateChamados();
                }

            }
        }, intentFilter);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        realm = Realm.getDefaultInstance();

            if(realm.where(User.class).equalTo("logged", true).count() == 0){

               Intent intent = new Intent(MainActivity.this, SplashScreenActivity.class);
               startActivity(intent);

            }else{

            User user = realm.where(User.class).equalTo("logged", true).findFirst();

            View header=navigationView.getHeaderView(0);
            nome = (TextView)header.findViewById(R.id.tv_login);
            email = (TextView)header.findViewById(R.id.tv_email);
            nome.setText(user.getUsuario_nome());
            email.setText(user.getUsuario_email());

            nome = (TextView) findViewById(R.id.userName);
            nome.setText(user.getUsuario_nome());

        }

        updateChamados();

    }

/*
    private boolean verificarPlayServices() {
        int codigo = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (codigo != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(codigo)) {
                GooglePlayServicesUtil.getErrorDialog(codigo, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "Este dispositivo não permite usar o recurso.");
                finish();
            }
            return false;
        }
        return true;
    }
*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_aberto) {

            Intent intent = new Intent(MainActivity.this, ChamadosActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_pendencia) {



        }else if(id == R.id.nav_fechados){



        }else if(id == R.id.nav_maps){

        Intent intent = new Intent(MainActivity.this, MapaActivity.class);
        startActivity(intent);

        }else if (id == R.id.nav_sair) {

            realm = Realm.getDefaultInstance();

            User user = realm.where(User.class).equalTo("logged", true).findFirst();

            if(realm.where(User.class).equalTo("logged", true).count() >= 1){

                realm.beginTransaction();

                user.setLogged(false);
                realm.copyToRealmOrUpdate(user);

                realm.commitTransaction();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
                finish();

            }else{

                Snackbar.make(findViewById(R.id.content_main), "Erro ao Sair", Snackbar.LENGTH_LONG).show();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

                VerifyConnection verifyConnection = new VerifyConnection(MainActivity.this);

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

        }

    }

/*
    public void updatePendencias(View view){

        realm = Realm.getDefaultInstance();

        RealmResults<Chamados> results2 = realm.where(Chamados.class)
                .equalTo("pendencia", "1")
                .findAll();


        ////Log.e("TAG-OR", results2.);

        if(results2 != null && results2.size() > 0){
            for (int i = 0; i < results2.size(); i++){

                Chamados chamado = results2.get(i);

                Log.e("TAG-ENVIO", results2.toString());

                int id = chamado.getSuporte_id();
                String status = chamado.getStatus();
                String resolvido = chamado.getResolvido();
                String iniciado = chamado.getIniciado();
                String datafechamento = chamado.getFechado();
                String relatorio = chamado.getRelatorio();
                String mcabo = chamado.getMcabo();
                String qconector = chamado.getQconector();
                String pendencia = chamado.getPendencia();
                String datapendencia = chamado.getDatapendencia();
                String notapendencia = chamado.getNotapendencia();

                VerifyConnection verifyConnection = new VerifyConnection(MainActivity.this);

                if(verifyConnection.checkConexao() == true) {

                    RequestInterface requestInterface = ApiService.getApiService();

                  ///  Call<ServerRequest> call = requestInterface.updateFechar(id, status, resolvido, iniciado, datafechamento, relatorio, mcabo, qconector, pendencia, datapendencia, notapendencia);

                  /*  call.enqueue(new Callback<ServerRequest>() {
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

                            Snackbar.make(findViewById(R.id.content_main), "Update onFailure 1", Snackbar.LENGTH_LONG).show();
                        }
                    });
                    *//*

                }
            }

        }

    }
*/
/*

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter("registrationComplete"));
    }
    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onPause();
    }
*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
