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

public class Abertos extends Fragment {

    RecyclerView recyclerView;
    AdapterChamados adapterChamados;
    Realm realm;

    public Abertos(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main_abertos, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        realm = Realm.getDefaultInstance();

        RealmResults<Chamados> abertos = realm.where(Chamados.class)
                .equalTo("status", "aberto").notEqualTo("pendencia", "1")
                .findAll();

        ///((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(abertos.size() + " Abertos");


        adapterChamados = new AdapterChamados(abertos);
        recyclerView.setAdapter(adapterChamados);

        Log.e("TAG",  "ABERTSSS");
       /// ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Abertos");

        return rootView;

    }

}