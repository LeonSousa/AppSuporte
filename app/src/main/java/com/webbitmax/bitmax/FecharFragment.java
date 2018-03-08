package com.webbitmax.bitmax;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.webbitmax.bitmax.helper.VerifyConnection;
import com.webbitmax.bitmax.model.Chamados;
import com.webbitmax.bitmax.model.ServerRequest;
import com.webbitmax.bitmax.model.User;
import com.webbitmax.bitmax.retrofit.ApiService;
import com.webbitmax.bitmax.retrofit.RequestInterface;

import java.util.Date;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wargas on 24/08/17.
 */

public class FecharFragment extends DialogFragment {

    EditText et_mcabo, et_qconectores, et_relatorio;
    CheckBox check_resolvido;
    Button btn_fechar;
    Chamados chamados;
    Realm realm;

   public static FecharFragment newInstance(Chamados chamados){
       FecharFragment f = new FecharFragment();
       Bundle args = new Bundle();
       f.setArguments(args);
       f.chamados = chamados;

       return f;
   }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_fecharchamado, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

       super.onViewCreated(view, savedInstanceState);
        getDialog().setTitle("Fechar chamados");
        getDialog().setCancelable(false);

        et_mcabo = (EditText) view.findViewById(R.id.text_mcabos);
        et_qconectores = (EditText) view.findViewById(R.id.text_qconectores);
        et_relatorio = (EditText) view.findViewById(R.id.text_relatorio);
        check_resolvido = (CheckBox) view.findViewById(R.id.check_resolvido);
        btn_fechar = (Button) view.findViewById(R.id.btn_fechar);

        btn_fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                realm = Realm.getDefaultInstance();

                User user = realm.where(User.class).equalTo("logged", true).findFirst();

                final String login = user.getUsuario_login();

                int  id  =     chamados.getSuporte_id();
                int cliente = chamados.getCliente();
                String resolvido = "";
                if(check_resolvido.isChecked()){
                    resolvido = "sim";
                } else {
                    resolvido = "nao";
                }
                String datafechamento = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                String  relatorio =   et_relatorio.getText().toString();
                String  mcabo =   et_mcabo.getText().toString();
                String  qconector =   et_qconectores.getText().toString();


                VerifyConnection verifyConnection = new VerifyConnection(getActivity());

                if (verifyConnection.checkConexao() == true) {

                    RequestInterface requestService = ApiService.getApiService();

                  final  Call<ServerRequest> call = requestService.fechar(id, cliente, resolvido, relatorio, mcabo, qconector, login);

                    call.enqueue(new Callback<ServerRequest>() {
                        @Override
                        public void onResponse(Call<ServerRequest> call, Response<ServerRequest> response) {
                            getDialog().cancel();

                            if(response.isSuccessful()){

                                if(response.body().isResult()){

                                    Toast.makeText(getActivity().getApplicationContext(), response.body().getMensagem() , Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(getActivity(), ChamadosActivity.class);
                                    startActivity(intent);

                                }else{

                                    Toast.makeText(getActivity().getApplicationContext(), response.body().getMensagem() , Toast.LENGTH_LONG).show();
                                }

                            }else{

                                Toast.makeText(getActivity().getApplicationContext(), "Erro not isSuccess", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<ServerRequest> call, Throwable t) {
                            getDialog().cancel();
                            Toast.makeText(getActivity().getApplicationContext(), "Erro de comunicação, tente novamente", Toast.LENGTH_LONG).show();
                        }
                    });

                }else{

                    Chamados chamado = realm.where(Chamados.class).equalTo("suporte_id", id).findFirst();

                    int suporteID = chamado.getSuporte_id();

                    realm.beginTransaction();

                    chamado.setStatus("fechado");
                    chamado.setResolvido(resolvido);
                    chamado.setFechado(datafechamento);
                    chamado.setRelatorio(relatorio);
                    chamado.setExecucao(false);
                    chamado.setMcabo(mcabo);
                    chamado.setQconector(qconector);

                    realm.copyToRealmOrUpdate(chamado);

                    realm.commitTransaction();

                    realm.close();

                    Toast.makeText(getActivity().getApplicationContext(), "Chamado " + suporteID + " fechado", Toast.LENGTH_LONG).show();

                    getDialog().cancel();

                    Intent intent = new Intent(getActivity(), ChamadosActivity.class);
                    startActivity(intent);

                }

            }
        });
    }
}
