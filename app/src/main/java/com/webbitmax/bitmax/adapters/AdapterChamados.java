package com.webbitmax.bitmax.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.webbitmax.bitmax.ChamadosActivity;
import com.webbitmax.bitmax.R;
import com.webbitmax.bitmax.model.Chamados;

import io.realm.RealmResults;

/**
 * Created by leonardo on 08/09/17.
 */

public class AdapterChamados extends RecyclerView.Adapter<AdapterChamados.MyViewHolder>{

    RealmResults<Chamados> chamados;

    public AdapterChamados(RealmResults<Chamados> chamados) {

        this.chamados = chamados;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_abertos, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Chamados chamado = chamados.get(position);

        holder.tv_id.setText(chamado.getSuporte_id()+"");
        holder.tv_numero.setText(chamado.getNumero());
        holder.tv_tecnico.setText(chamado.getTecnico());
        holder.tv_local.setText(chamado.getCliente_bairro() + " - " + chamado.getCliente_cidade());

    }

    @Override
    public int getItemCount() {
        return chamados.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_id, tv_numero, tv_tecnico, tv_local;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_id = (TextView) itemView.findViewById(R.id.tv_id);
            tv_numero = (TextView) itemView.findViewById(R.id.tv_numero);
            tv_tecnico = (TextView) itemView.findViewById(R.id.tv_tecnico);
            tv_local = (TextView) itemView.findViewById(R.id.tv_local);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        ChamadosActivity activity = (ChamadosActivity) v.getContext();
                        int position = getAdapterPosition();

                    if(position == 0){

                        activity.abrirChamado(chamados.get(position));

                    }else{

                        Toast.makeText(v.getContext(), "Você só pode iniciar o primeiro chamado", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }
}
