package com.tictactwo.daniel.brian.tictactwo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Algernon on 5/6/16.
 */
public class ConnectThread extends Thread{
    private final BluetoothSocket mSocket;
    private final BluetoothDevice mmDevice;
    private final BluetoothAdapter mAdapter;
    private static ConnectThread instance = null;
    private final String LOG_TAG = "Connect";
    private final static UUID MY_UUID = UUID.fromString("067e6162-3b6f-4ae2-a171-2470b63dff00");

    protected ConnectThread(BluetoothDevice device, BluetoothAdapter adapter) {
        BluetoothSocket tmp = null;
        mmDevice = device;
        mAdapter = adapter;
        try {
            tmp = mmDevice.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, "Created Socket!");
        mSocket = tmp;
    }

    public static ConnectThread getInstance(BluetoothDevice device, BluetoothAdapter adapter) {
        if (instance == null) {
            instance = new ConnectThread(device, adapter);
        }
        return instance;
    }


    public void run() {
        mAdapter.cancelDiscovery();
        Log.d(LOG_TAG, "cancel dics");
        try {
            mSocket.connect();
        } catch (IOException e) {
            Log.d(LOG_TAG, "COULDN't Connect");
            Log.d(LOG_TAG, e.toString());
            try {
                mSocket.close();
            } catch (IOException closeException) {
                Log.d(LOG_TAG, "COULDNT CLOSE");
                closeException.printStackTrace();
            }
            return;
        }
        Log.d(LOG_TAG, "SCORE!");
        // TODO: this is the connecting client. Start Game activity here.
    }

    public void cancel() {
        try {
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
