package com.tictactwo.daniel.brian.tictactwo;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.LogRecord;

/**
 * Created by Algernon on 5/6/16.
 */
public class ManagerThread extends Thread {
    private static  ManagerThread instance = null;
    private final InputStream mInputStream;
    private final OutputStream mOutputStream;
    private final BluetoothSocket mSocket;
    private final String LOG_TAG = "Manager";

    protected ManagerThread(BluetoothSocket socket) {
        mSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        try{
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, "Streams assigned");
        mInputStream = tmpIn;
        mOutputStream = tmpOut;
    }

    public static ManagerThread getInstance(BluetoothSocket socket) {
        if (instance == null) {
            instance = new ManagerThread(socket);
        }
        return instance;
    }

    public void run() {
        byte[] buffer = new byte[1024];
        int bytes;

        while (true) {
            try {
                bytes = mInputStream.read(buffer);
                // TODO: Send to activity handler.

            } catch (IOException e) {
                e.printStackTrace();
                Log.d(LOG_TAG, "Error listening to input stream");
            }
        }
    }

    public void write(byte[] bytes) {
        try {
            mOutputStream.write(bytes);
        } catch (IOException e) {
            Log.d(LOG_TAG, "Error writing bytes");
        }
    }

    public void cancel() {
        try {
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "Error closing socket");
        }
    }

    public void sendMoves(String moves) {
        if (!moves.equals(null) && !moves.equals("")) {
            Message msgObg = handler.obtainMessage();
            Bundle b = new Bundle();
            b.putString("message", moves);
            msgObg.setData(b);
            handler.sendMessage(msgObg);

        }
    }

    private final Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            String move = msg.getData().getString("message");

            if (move != null) {
                byte[] moveToBytes = move.getBytes();
                write(moveToBytes);
                Log.v(LOG_TAG, "Writing move to pipe");
            }
        }
    };
}
