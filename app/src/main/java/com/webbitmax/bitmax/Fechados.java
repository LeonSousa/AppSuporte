package com.webbitmax.bitmax;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webbitmax.bitmax.adapters.AdapterChamados;
import com.webbitmax.bitmax.model.Chamados;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by leonardo on 17/09/17.
 */

public class Fechados extends Fragment {

    RecyclerView recyclerView;
    AdapterChamados adapterChamados;
    Realm realm;

    public Fechados(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_fechados, container, false);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        realm = Realm.getDefaultInstance();

        RealmResults<Chamados> fechados = realm.where(Chamados.class)
                .equalTo("status", "fechado")
                .findAll();

        //((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(fechados.size() + " Fechados");



        adapterChamados = new AdapterChamados(fechados);
        recyclerView.setAdapter(adapterChamados);


        Log.e("TAG",  "FECHADOSSSSS");
      ///  ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Fechados");

        return rootView;
    }
}
