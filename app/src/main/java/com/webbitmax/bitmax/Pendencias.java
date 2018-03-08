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

public class Pendencias extends Fragment {

    RecyclerView recyclerView;
    AdapterChamados adapterChamados;
    Realm realm;

    public Pendencias(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main_pendencias, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        realm = Realm.getDefaultInstance();

        RealmResults<Chamados> pendencias = realm.where(Chamados.class)
                .equalTo("pendencia", "1")
                .findAll();

        //((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(pendencias.size() + " Pendencias");


        adapterChamados = new AdapterChamados(pendencias);
        recyclerView.setAdapter(adapterChamados);

        Log.e("TAG",  "PENDSSSS");
       /// ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Pendencias");

        return rootView;
    }

}