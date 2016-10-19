package com.visionsoft.raezproducciones;

/**
 * Created by Planeacion-PC on 13/10/2016.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hp1 on 21-01-2015.
 */
public class Tab2 extends Fragment implements GetJSONAbonos.ListenerAbonos {
    private ListView aListView;
    public static final String URL = "http://raezproducciones.com/sistema/php/get_pagos.php?email="+HomeActivity.email_id;
    private List<HashMap<String, String>> aAndroidMapList = new ArrayList<>();
    private static final String KEY_ABONO = "abono";
    private static final String KEY_FECHA = "fecha";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_2,container,false);
        aListView = (ListView) v.findViewById(R.id.list_abonos);
        new GetJSONAbonos(this).execute(URL);
        return v;
    }

    @Override
    public void onLoaded(List<abonos> abonosList) {
        for (abonos a : abonosList) {
            HashMap<String, String> map = new HashMap<>();
            map.put(KEY_ABONO, a.getAbono());
            map.put(KEY_FECHA, a.getFecha());
            aAndroidMapList.add(map);
        }
        loadListView();
    }

    @Override
    public void onError() {

        Toast.makeText(getContext(), "Error ups !", Toast.LENGTH_SHORT).show();
    }

    private void loadListView() {
        // Keys used in Hashmap
        String[] from = { KEY_ABONO, KEY_FECHA };
        // Ids of views in listview_layout
        int[] to = { R.id.abono,R.id.fecha};
        AbonosAdapter adapter = new AbonosAdapter(getContext(), aAndroidMapList, R.layout.list_abonos, from, to);
        aListView.setAdapter(adapter);

    }
}