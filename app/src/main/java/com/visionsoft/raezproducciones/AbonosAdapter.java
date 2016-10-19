package com.visionsoft.raezproducciones;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yo on 18/10/2016.
 */

public class AbonosAdapter extends SimpleAdapter {
    private Context mContext;
    public LayoutInflater inflater = null;

    public AbonosAdapter(Context context,
                           List<? extends Map<String, ?>> data, int resource, String[] from,
                           int[] to) {
        super(context, data, resource, from, to);
        mContext = context;
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.list_abonos, null);

        HashMap<String, String> data = (HashMap<String, String>) getItem(position);

        TextView abono = (TextView) vi.findViewById(R.id.abono);
        abono.setText(data.get("abono"));
        TextView fecha = (TextView) vi.findViewById(R.id.fecha);
        fecha.setText(data.get("fecha"));
        TextView letras = (TextView) vi.findViewById(R.id.letras);
        Numero_a_letra NumLetra = new Numero_a_letra();
        letras.setText(NumLetra.Convertir(data.get("abono"), true));
        return vi;
    }

}
