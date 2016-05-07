package com.tictactwo.daniel.brian.tictactwo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by brian on 5/5/16.
 */
public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "Game";
    public StringBuilder flattenedBoard = new StringBuilder("000000000");
    private  int isXPlayer;
    private ManagerThread managerThread;

    private String[][] board = {{"","",""},{"","",""},{"","",""}};

    public ImageButton imageButton1, imageButton2, imageButton3,
            imageButton4, imageButton5, imageButton6,
            imageButton7, imageButton8, imageButton9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_view);

        managerThread = ManagerThread.getInstance(null);

        Intent intent = getIntent();
        isXPlayer = intent.getIntExtra("isXPlayer", -1);

        Log.v(LOG_TAG, String.valueOf(isXPlayer));

        imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
        imageButton5 = (ImageButton) findViewById(R.id.imageButton5);
        imageButton6 = (ImageButton) findViewById(R.id.imageButton6);
        imageButton7 = (ImageButton) findViewById(R.id.imageButton7);
        imageButton8 = (ImageButton) findViewById(R.id.imageButton8);
        imageButton9 = (ImageButton) findViewById(R.id.imageButton9);


        imageButton1.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        imageButton3.setOnClickListener(this);
        imageButton4.setOnClickListener(this);
        imageButton5.setOnClickListener(this);
        imageButton6.setOnClickListener(this);
        imageButton7.setOnClickListener(this);
        imageButton8.setOnClickListener(this);
        imageButton9.setOnClickListener(this);
    }


    public void rowAndColonFlattenBoard(int row, int col) {
        int position = (3 * row) + col;
        if (isXPlayer == 1) {
            flattenedBoard.setCharAt(position, 'X');
        } else {
            flattenedBoard.setCharAt(position, 'O');
        }
    }

    public void updateGameBoard(int row, int col) {
        if (isXPlayer == 0) {
            board[row][col] = "X";
        } else {
            board[row][col] = "O";
        }
    }

    @Override
    public void onClick(View v) {
        int row = 0, col = 0;
        Log.d(LOG_TAG, "onClick");
        switch (v.getId()) {
            case R.id.imageButton1:
                col = 0;
                row = 0;
                imageButton1.setEnabled(false);
                break;

            case R.id.imageButton2:
                col = 1;
                row = 0;
                imageButton1.setEnabled(false);
                break;

            case R.id.imageButton3:
                col = 2;
                row = 0;
                imageButton1.setEnabled(false);
                break;

            case R.id.imageButton4:
                col = 0;
                row = 1;
                imageButton1.setEnabled(false);
                break;

            case R.id.imageButton5:
                col = 1;
                row = 1;
                imageButton1.setEnabled(false);
                break;

            case R.id.imageButton6:
                col = 2;
                row = 1;
                imageButton1.setEnabled(false);
                break;

            case R.id.imageButton7:
                col = 0;
                row = 2;
                imageButton1.setEnabled(false);
                break;

            case R.id.imageButton8:
                col = 1;
                row = 2;
                imageButton1.setEnabled(false);
                break;

            case R.id.imageButton9:
                col = 2;
                row = 2;
                imageButton1.setEnabled(false);
                break;

            default:
                break;
        }

        rowAndColonFlattenBoard(row, col);
        updateGameBoard(row, col);
        boolean hasWon = hasGameWon(board, row, col);
        Log.d(LOG_TAG, "hasWon");
        Log.d(LOG_TAG, String.valueOf(hasWon));

        if (!hasWon) {
            Log.d(LOG_TAG, "Sending moves!");
            Log.d(LOG_TAG, "Flattened Board");
            Log.d(LOG_TAG, flattenedBoard.toString());
            managerThread.sendMoves(flattenedBoard.toString());
        }
        /*if(client)
            board[row][col] = "x";
        else if(server)
            board[row][col] = "o";

        hasGameWon(board, row, col);

        */
    }

    // Takes the row and column coordinates of the last move made
    // and checks to see if that move causes the player to win
    public boolean hasGameWon(String[][] gameBoard, int row, int col){
        String Player = gameBoard[row][col];

        int r = row;
        int c = col;

        boolean onDiagonal = (row == col) || (col == -1 * row + (gameBoard.length-1));
        boolean HorizontalWin = true, VerticalWin = true;
        boolean DiagonalWinOne = true, DiagonalWinTwo = true;

        // Check the rows and columns
        for(int n = 0; n < gameBoard.length; n++){
            if(!gameBoard[r][n].equals(Player))
                HorizontalWin = false;
            if(!gameBoard[n][c].equals(Player))
                VerticalWin = false;
        }

        // Only check diagonals if the move is on a diagonal
        if(onDiagonal){
            // Check the diagonals
            for(int n = 0; n < gameBoard.length; n++){
                if(!gameBoard[n][n].equals(Player))
                    DiagonalWinOne = false;
                if(!gameBoard[n][-1*n+(gameBoard.length-1)].equals(Player))
                    DiagonalWinTwo = false;
            }
        }
        else{
            DiagonalWinOne = false;
            DiagonalWinTwo = false;
        }

        boolean hasWon = (HorizontalWin || VerticalWin || DiagonalWinOne || DiagonalWinTwo);

        return hasWon;

    }

    public static Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(LOG_TAG, "Received message");
            Log.d(LOG_TAG, Integer.toString(msg.what));
            Log.d(LOG_TAG, msg.toString());
            byte[] a = (byte[]) msg.obj;
            String s = new String(a);
            Log.d(LOG_TAG, s);

            // Update game board

        }
    };

}
