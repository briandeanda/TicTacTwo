package com.tictactwo.daniel.brian.tictactwo;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Algernon on 5/5/16.
 */
public class ListDevicesActivity extends ListActivity{

    private BluetoothAdapter  mBluetoothAdapter= null;
    private static Context c;
    private static final int REQUEST_ENABLE_BT = 1;
    private static final String LOG_TAG = "LIST";
    private ArrayList<String> devices = new ArrayList<>(10);
    private DevicesAdapter devicesAdapter = null;
    private final UUID MY_UUID = UUID.fromString("067e6162-3b6f-4ae2-a171-2470b63dff00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c = this;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(ListDevicesActivity.this, "You need bluetooth to play our game ):", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BT);
        } else {
            Log.d(LOG_TAG, "Starting discovery");
            if (mBluetoothAdapter.isDiscovering()) {
                Log.v(LOG_TAG, "CANCEL DISC");
                mBluetoothAdapter.cancelDiscovery();
            }
            mBluetoothAdapter.startDiscovery();
        }

        devicesAdapter = new DevicesAdapter(this, devices, mBluetoothAdapter);


        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
        setListAdapter(devicesAdapter);

        AcceptThread aT = AcceptThread.getInstance(mBluetoothAdapter);
        Thread t = new Thread(aT);
        t.start();

        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, "CLICKED");
                Log.d(LOG_TAG, devices.get(position));
                String address = devices.get(position).split("\n")[1];
                Log.d(LOG_TAG, address);
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                ConnectThread t = ConnectThread.getInstance(device, mBluetoothAdapter);
                t.start();
            }
        });
    }

    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(LOG_TAG, "Received message");
            Log.d(LOG_TAG, Integer.toString(msg.what));

            ManagerThread.getInstance((BluetoothSocket) msg.obj).start();

            Intent i = new Intent(c, GameActivity.class);
            i.putExtra("isXPlayer", msg.what);
            c.startActivity(i);
        }
    };

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(LOG_TAG, action);
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                Log.d(LOG_TAG, device.getAddress());
                devices.add(device.getName() + "\n" + device.getAddress());
                devicesAdapter.notifyDataSetChanged();
                for (String d : devices) {
                    Log.d(LOG_TAG, "Loop");
                    Log.d(LOG_TAG, d);
                }
                Log.d(LOG_TAG, Integer.toString(devices.size()));
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode != RESULT_OK) {
                Toast.makeText(ListDevicesActivity.this, "You need to activate BT!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Log.d(LOG_TAG, "Starting discovery after BT");
                if (mBluetoothAdapter.isDiscovering()) {
                    Log.d(LOG_TAG, "CANCEL DISC");
                    mBluetoothAdapter.cancelDiscovery();
                }
                mBluetoothAdapter.startDiscovery();
            }
        }
    }
}
