package com.webbitmax.bitmax;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;




import com.webbitmax.bitmax.helper.VerifyConnection;
import com.webbitmax.bitmax.model.ServerRequest;
import com.webbitmax.bitmax.model.User;
import com.webbitmax.bitmax.retrofit.ApiService;
import com.webbitmax.bitmax.retrofit.RequestInterface;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapaActivity {




/*

    public class MapaActivity extends Activity implements LocationListener {

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 100;

    private Realm realm;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    private LocationRequest mLocationRequest;
    private TextView latitude, longitude;
    private Button enviar;
    private EditText editcodigo;

    private double fusedLatitude = 0.0;
    private  double fusedLongitude = 0.0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        initializeViews();

        if (checkPlayServices()) {
            startFusedLocation();
            registerRequestUpdate(this);
        }

    }

    private void initializeViews() {

        editcodigo = (EditText) findViewById(R.id.editCodigo);
        latitude = (TextView) findViewById(R.id.textview_latitude);
        longitude = (TextView) findViewById(R.id.textview_longitude);
        enviar = (Button)findViewById(R.id.btn_enviar);

        //String codigo = null;

        Bundle bundle = getIntent().getExtras();

        final String codigo = bundle.getString("codigo");

        editcodigo.setText(codigo);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VerifyConnection verifyConnection = new VerifyConnection(getApplicationContext());

                if(verifyConnection.checkConexao()){

                    final double lati = getFusedLatitude();
                    final double longi = getFusedLongitude();

                    realm = Realm.getDefaultInstance();

                    User user = realm.where(User.class).equalTo("logged", true).findFirst();

                    final String login = user.getUsuario_login();

                    RequestInterface requestInterface = ApiService.getApiService();

                    Call<ServerRequest> call = requestInterface.marcarPonto(codigo, lati, longi, login);

                    call.enqueue(new Callback<ServerRequest>() {
                        @Override
                        public void onResponse(Call<ServerRequest> call, Response<ServerRequest> response) {

                            if(response.isSuccessful()){

                                    Toast.makeText(getApplicationContext(), "SUCESSO", Toast.LENGTH_LONG).show();

                            }else{

                                Toast.makeText(getApplicationContext(), "NOT SUCESSO", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ServerRequest> call, Throwable t) {

                            Toast.makeText(getApplicationContext(), "FAILHA", Toast.LENGTH_LONG).show();

                        }
                    });

                }else{

                    Toast.makeText(getApplicationContext(), "SEM INTERNET", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected void onStop() {
        stopFusedLocation();
        super.onStop();
    }


    // check if google play services is installed on the device
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                Toast.makeText(getApplicationContext(),
                        "This device is supported. Please download google play services", Toast.LENGTH_LONG)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }


    public void startFusedLocation() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnectionSuspended(int cause) {
                        }

                        @Override
                        public void onConnected(Bundle connectionHint) {

                        }
                    }).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {

                        @Override
                        public void onConnectionFailed(ConnectionResult result) {

                        }
                    }).build();
            mGoogleApiClient.connect();
        } else {
            mGoogleApiClient.connect();
        }
    }

    public void stopFusedLocation() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }


    public void registerRequestUpdate(final LocationListener listener) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000); // every second

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, listener);
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                    if (!isGoogleApiClientConnected()) {
                        mGoogleApiClient.connect();
                    }
                    registerRequestUpdate(listener);
                }
            }
        }, 1000);
    }

    public boolean isGoogleApiClientConnected() {
        return mGoogleApiClient != null && mGoogleApiClient.isConnected();
    }

    @Override
    public void onLocationChanged(Location location) {
        setFusedLatitude(location.getLatitude());
        setFusedLongitude(location.getLongitude());

        ///Toast.makeText(getApplicationContext(), "NEW LOCATION RECEIVED", Toast.LENGTH_LONG).show();

        latitude.setText(getString(R.string.latitude_string) +" "+ getFusedLatitude());
        longitude.setText(getString(R.string.longitude_string) +" "+ getFusedLongitude());

    }

    public void setFusedLatitude(double lat) {
        fusedLatitude = lat;
    }

    public void setFusedLongitude(double lon) {
        fusedLongitude = lon;
    }

    public double getFusedLatitude() {
        return fusedLatitude;
    }

    public double getFusedLongitude() {
        return fusedLongitude;
    }
}
*/

}