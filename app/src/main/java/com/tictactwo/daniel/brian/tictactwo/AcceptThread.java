package com.tictactwo.daniel.brian.tictactwo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Algernon on 5/6/16.
 */
public class AcceptThread extends Thread{
    private static AcceptThread instance = null;
    private static BluetoothAdapter mBluetoothAdapter = null;
    private static BluetoothServerSocket mBluetoothServerSocket = null;
    private final static UUID MY_UUID = UUID.fromString("067e6162-3b6f-4ae2-a171-2470b63dff00");
    private static final String LOG_TAG = "Accept";

    protected AcceptThread(BluetoothAdapter adapter) {
        BluetoothServerSocket tmp = null;
        mBluetoothAdapter = adapter;
        try {
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("TicTacTwo", MY_UUID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mBluetoothServerSocket = tmp;
        Log.d(LOG_TAG, "Created socket!");
    }

    public static AcceptThread getInstance(BluetoothAdapter adapter) {
        if (instance == null) {
            instance = new AcceptThread(adapter);
        }
        return instance;
    }

    @Override
    public void run() {
        BluetoothSocket socket = null;
        while (true) {
            try {
                socket = mBluetoothServerSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            if (socket != null) {
                Log.d(LOG_TAG, "CONNECTED!");
                //TODO - this means this guy is the host.  Launch game activity with flag
                break;
            }
        }
    }

    public void cancel() {
        try {
            mBluetoothServerSocket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
