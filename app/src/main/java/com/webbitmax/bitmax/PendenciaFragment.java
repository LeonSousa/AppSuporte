package com.webbitmax.bitmax;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PendenciaFragment extends DialogFragment {

    EditText  et_relatorio;
    Button btn_pendencia;
    Chamados chamados;
    Realm realm;

    public static PendenciaFragment newInstance(Chamados chamados){
        PendenciaFragment f = new PendenciaFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        f.chamados = chamados;

        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_pendencia, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        getDialog().setTitle("Pendencia chamados");
        getDialog().setCancelable(false);

        et_relatorio = (EditText) view.findViewById(R.id.text_relatorio);
        btn_pendencia = (Button) view.findViewById(R.id.btn_pendencia);

        btn_pendencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                realm = Realm.getDefaultInstance();

                User user = realm.where(User.class).equalTo("logged", true).findFirst();

                final String login = user.getUsuario_login();

                final int id = chamados.getSuporte_id();
                final String notapendencia = et_relatorio.getText().toString();
                final String datapendencia = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                VerifyConnection verifyConnection = new VerifyConnection(getActivity());

                if (verifyConnection.checkConexao() == true) {

                    RequestInterface requestService = ApiService.getApiService();

                    Call<ServerRequest> call = requestService.pendencia(id, notapendencia, datapendencia, login);

                    call.enqueue(new Callback<ServerRequest>() {
                        @Override
                        public void onResponse(Call<ServerRequest> call, Response<ServerRequest> response) {
                            getDialog().cancel();

                            if (response.isSuccessful()) {

                                Toast.makeText(getActivity().getApplicationContext(), "Pendencia Adicionanda", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(getActivity(), ChamadosActivity.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "Error not isSuccess", Toast.LENGTH_LONG).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<ServerRequest> call, Throwable t) {
                            getDialog().cancel();
                            Toast.makeText(getActivity().getApplicationContext(), "Erro na comunicação com o servidor", Toast.LENGTH_LONG).show();
                        }
                    });

                }else{

                    Chamados query = realm.where(Chamados.class).equalTo("suporte_id", id).findFirst();

                    realm.beginTransaction();

                    query.setExecucao(false);
                    query.setPendencia("1");
                    query.setDatapendencia(datapendencia);
                    query.setNotapendencia(notapendencia);
                    realm.copyToRealmOrUpdate(query);

                    realm.commitTransaction();
                    realm.close();

                    Toast.makeText(getActivity().getApplicationContext(), "Pendencia adicioanada", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getActivity(), ChamadosActivity.class);
                    startActivity(intent);

                }
            }
        });
    }
}
