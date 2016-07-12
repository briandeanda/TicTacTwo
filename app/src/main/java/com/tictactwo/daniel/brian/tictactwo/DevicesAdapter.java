package com.tictactwo.daniel.brian.tictactwo;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Algernon on 5/5/16.
 */
public class DevicesAdapter extends BaseAdapter {
    private static final String LOG_TAG = "Adapter";
    private BluetoothAdapter adapter = null;
    private ArrayList<String> devices;
    private final Context context;
    public DevicesAdapter(Context c, ArrayList<String> devices, BluetoothAdapter bAdapter) {
        this.devices = devices;
        adapter = bAdapter;
        context = c;
    }


    @Override
    public int getCount() {
        return this.devices.size();
    }

    @Override
    public Object getItem(int position) {
        return this.devices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        CompleteListViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.list_layout, null);
            viewHolder = new CompleteListViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (CompleteListViewHolder) v.getTag();
        }
        String device[] = this.devices.get(position).split("\n");
        Log.d(LOG_TAG, device[0]);
        Log.d(LOG_TAG, device[1]);
        Log.d(LOG_TAG, "Getting view");

        viewHolder.mTvItem.setText(device[0] + "\n" + device[1]);


        return v;
    }
}

class CompleteListViewHolder {
    public TextView mTvItem;
    public CompleteListViewHolder(View base) {
        mTvItem = (TextView) base.findViewById(R.id.text);
    }
}
