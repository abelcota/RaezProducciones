package com.visionsoft.raezproducciones;

import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.SimpleAdapter;
import android.widget.TextView;

public class PaquetesAdapter extends SimpleAdapter {
    private Context mContext;
    public LayoutInflater inflater = null;

    public PaquetesAdapter(Context context,
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
            vi = inflater.inflate(R.layout.list_item, null);

        HashMap<String, String> data = (HashMap<String, String>) getItem(position);

        TextView nombre = (TextView) vi.findViewById(R.id.nombre);
        nombre.setText(data.get("nombre"));
        TextView descripcion = (TextView) vi.findViewById(R.id.descripcion);
        descripcion.setText(Html.fromHtml(data.get("descripcion")));
        TextView precio = (TextView) vi.findViewById(R.id.precio);
        precio.setText("$"+data.get("precio"));
        new DownloadTask((ImageView) vi.findViewById(R.id.profile_pic))
                .execute((String) data.get("img"));
        return vi;
    }

}