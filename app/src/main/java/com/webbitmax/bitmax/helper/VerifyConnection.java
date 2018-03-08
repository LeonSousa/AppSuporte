package com.webbitmax.bitmax.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.webbitmax.bitmax.model.Chamados;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by leonardo on 09/09/17.
 */

public class VerifyConnection extends BroadcastReceiver {


/*
    @Override
    public void onReceive(Context context, Intent intent) {

        //Log.d("TAG",""+intent.getAction());

        Log.d("API123",""+intent.getAction());

        if(intent.getAction().equals("com.webbitmax.bitmax.SOME_ACTION")){

            Toast.makeText(context, "SOME_ACTION is received", Toast.LENGTH_LONG).show();
        }

       else{
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            boolean isConnected = networkInfo != null &&
                    networkInfo.isConnectedOrConnecting();
            if (isConnected) {
                try {
                    Toast.makeText(context, "Network is connected", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(context, "Network is changed or reconnected", Toast.LENGTH_LONG).show();
            }
        }
    }

*/


    private Context context;

    public VerifyConnection(Context context) {
        this.context = context;
    }

    public boolean checkConexao() {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


        if (connectivityManager != null) {

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo == null) {

                return false;
            }

            int netType = networkInfo.getType();

            if (netType == ConnectivityManager.TYPE_WIFI
                    || netType == ConnectivityManager.TYPE_MOBILE) {

                // updateChamados();

                return networkInfo.isConnected();

            } else {
                return false;
            }


        } else {
            return false;
        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }
/*
    private void updateChamados() {

        Realm realm = Realm.getDefaultInstance();

        RealmResults<Chamados> results = realm.where(Chamados.class)
                .equalTo("status", "fechado")
                .findAll();



        if(results != null && results.size() > 0){
            for (int i = 0; i < results.size(); i++){

                Log.d("TAG-VERIFY", "result count row: " + results.size());

                Chamados id = results.get(i);

                Log.d("TAG-VERIFY", "row: " + id.getSuporte_id());
            }
        }else{
            Log.d("TAG-VERIFY", "quantidade " + results.size());
        }



    }
*/

}
