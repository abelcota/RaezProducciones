package com.visionsoft.raezproducciones;

/**
 * Created by Planeacion-PC on 13/10/2016.
 */
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
public class Tab1 extends Fragment implements LoadJSONTask.Listener {
    private ListView mListView;
    public static final String URL = "http://raezproducciones.com/sistema/php/get_paquetes.php?email="+HomeActivity.email_id;
    private List<HashMap<String, String>> mAndroidMapList = new ArrayList<>();
    private static final String KEY_ID = "id_paquetes";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_DESC = "descripcion";
    private static final String KEY_PRECIO = "precio";
    private static final String KEY_IMG = "img";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_1,container,false);
        mListView = (ListView) v.findViewById(R.id.list_view);
        new LoadJSONTask(this).execute(URL);
        return v;
    }

    @Override
    public void onLoaded(List<paquetes> paquetesList) {
        for (paquetes paq : paquetesList) {
            HashMap<String, String> map = new HashMap<>();
            map.put(KEY_ID, paq.getId_paquetes());
            map.put(KEY_NOMBRE, paq.getNombre());
            map.put(KEY_DESC, paq.getDescripcion());
            map.put(KEY_PRECIO, paq.getPrecio());
            map.put(KEY_IMG, "http://raezproducciones.com/sistema/assets/img/paquetes/"+paq.getImg());
            mAndroidMapList.add(map);
        }
        loadListView();
    }


    @Override
    public void onError() {

        Toast.makeText(getContext(), "Error ups !", Toast.LENGTH_SHORT).show();
    }

    private void loadListView() {
        // Keys used in Hashmap
        String[] from = { KEY_NOMBRE, KEY_DESC, KEY_PRECIO, KEY_IMG };
        // Ids of views in listview_layout
        int[] to = { R.id.nombre,R.id.descripcion, R.id.precio, R.id.profile_pic};
        PaquetesAdapter adapter = new PaquetesAdapter(getContext(), mAndroidMapList, R.layout.list_item, from, to);
        mListView.setAdapter(adapter);

    }

}