package com.tictactwo.daniel.brian.tictactwo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Algernon on 5/5/16.
 */
public class DevicesAdapter extends ArrayAdapter<String> {
    private static final String LOG_TAG = "Adapter";
    private BluetoothAdapter adapter = null;
    private final Context context;
    public DevicesAdapter(Context c, ArrayList<String> devices, BluetoothAdapter bAdapter) {
        super(c, 0, devices);
        adapter = bAdapter;
        context = c;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(LOG_TAG, "Getting view");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        String device[] = getItem(position).split("\n");
        Log.d(LOG_TAG, device[0]);
        Log.d(LOG_TAG, device[1]);
        View rowView = inflater.inflate(R.layout.content_main, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.text);
        textView.setText(device[0] + "\n" + device[1]);

        return rowView;
    }


}
