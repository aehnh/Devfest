package com.example.anders.devfest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by q on 2017-01-24.
 */

public class chatAdapter extends ArrayAdapter {
    private ArrayList<ChatData> items;
    Context context;

    public chatAdapter(Context context, int ResourceId, ArrayList<ChatData> items) {
        super(context,ResourceId,items);
        this.items = items;
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.lv_item, null);
        }

        ChatData in = items.get(position);

        if (in != null) {
            TextView w = (TextView) v.findViewById(R.id.writer);
            TextView b= (TextView) v.findViewById(R.id.blub);

            if (w != null){
                w.setText(in.getUserName()+": ");
            }

            if(b != null){
               b.setText(in.getMessage());
            }
        }
        return v;
    }
}
