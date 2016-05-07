package com.tictactwo.daniel.brian.tictactwo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by brian on 5/5/16.
 */
public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String LOG_TAG = "Game";
    public static StringBuilder flattenedBoard;
    private static int isXPlayer;
    public static Context context;
    private ManagerThread managerThread;

    private static String[][] board;
    public static ProgressDialog d;

    
    public ImageButton imageButton1, imageButton2, imageButton3,
            imageButton4, imageButton5, imageButton6,
            imageButton7, imageButton8, imageButton9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_view);

        flattenedBoard = new StringBuilder("000000000");
        board = new String[][]{{"0", "0", "0"}, {"0", "0", "0"}, {"0", "0", "0"}};

        context = this;
        d = new ProgressDialog(context);
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

    public static void updateGameBoard(int row, int col) {
        if (isXPlayer == 0) {
            board[row][col] = "O";
        } else {
            board[row][col] = "X";
        }
    }

    public static void updateGameBoard(String flatBoard){
        int index = 0;
        int newRow = 0, newCol = 0;

        if (d.isIndeterminate()) {
            d.dismiss();
        }

        outerloop:
        for(int row=0; row<3; row++){
            for(int col=0; col<3; col++){
                if(flatBoard.charAt(index) != '0') {
                    char icon = flatBoard.charAt(index);

                    Log.d(LOG_TAG, "board: "+board[row][col]+" icon: "+Character.toString(icon) );
                    if(!board[row][col].equals(Character.toString(icon))){

                        board[row][col] = Character.toString(icon);
                        flattenedBoard.setCharAt(index, icon);


                        Log.d(LOG_TAG, "UPDATE board: "+board[row][col]+" icon: "+Character.toString(icon) );
                        int buttonIndex = index+1;
                        String buttonID = "imageButton"+Integer.toString(buttonIndex);
                        Log.d(LOG_TAG, "Flattened character: "+Character.toString(icon));
                        Log.d(LOG_TAG, buttonID);
                        int resID = context.getResources().getIdentifier(buttonID, "id", "com.tictactwo.daniel.brian.tictactwo");
                        Activity activity = (Activity) context;
                        ImageButton button = (ImageButton) activity.findViewById(resID);
                        updateSquareReceived(button);
                        button.setEnabled(false);
                        newRow = row;
                        newCol = col;
                        break outerloop;
                    }
//                    else{
//                        board[row][col] = Character.toString(icon);
//                        flattenedBoard.setCharAt(index, icon);
//                    }
                }
                index++;
            }
        }

        Log.d(LOG_TAG, "newRow: "+Integer.toString(newRow)+" newCol: "+Integer.toString(newCol) );
        Log.d(LOG_TAG, "GameWon: " + Boolean.toString(hasGameWon(board, newRow, newRow)));
        printBoard();
        boolean hasWon = hasGameWon(board, newRow, newCol);
        if (hasWon) {
            CharSequence text;
            if(isXPlayer == 0)
                text = "X has won!";
            else
                text = "O has won!";

            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    public static void printBoard(){
        String flat="";
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                flat = flat+" "+board[i][j];
            }
            flat=flat+"\n";
        }

        Log.d(LOG_TAG, flat);
    }

    @Override
    public void onClick(View v) {
        int row = 0, col = 0;
        Log.d(LOG_TAG, "onClick");
        switch (v.getId()) {
            case R.id.imageButton1:
                col = 0;
                row = 0;
                updateSquareLocal(imageButton1);
                imageButton1.setEnabled(false);
                break;

            case R.id.imageButton2:
                col = 1;
                row = 0;
                updateSquareLocal(imageButton2);
                imageButton2.setEnabled(false);
                break;

            case R.id.imageButton3:
                col = 2;
                row = 0;
                updateSquareLocal(imageButton3);
                imageButton3.setEnabled(false);
                break;

            case R.id.imageButton4:
                col = 0;
                row = 1;
                updateSquareLocal(imageButton4);
                imageButton4.setEnabled(false);
                break;

            case R.id.imageButton5:
                col = 1;
                row = 1;
                updateSquareLocal(imageButton5);
                imageButton5.setEnabled(false);
                break;

            case R.id.imageButton6:
                col = 2;
                row = 1;
                updateSquareLocal(imageButton6);
                imageButton6.setEnabled(false);
                break;

            case R.id.imageButton7:
                col = 0;
                row = 2;
                updateSquareLocal(imageButton7);
                imageButton7.setEnabled(false);
                break;

            case R.id.imageButton8:
                col = 1;
                row = 2;
                updateSquareLocal(imageButton8);
                imageButton8.setEnabled(false);
                break;

            case R.id.imageButton9:
                col = 2;
                row = 2;
                updateSquareLocal(imageButton9);
                imageButton9.setEnabled(false);
                break;

            default:
                break;
        }

        rowAndColonFlattenBoard(row, col);
        updateGameBoard(row, col);
        boolean hasWon = hasGameWon(board, row, col);
        Log.d(LOG_TAG, "hasWon");
        Log.d(LOG_TAG, String.valueOf(hasWon));


        Log.d(LOG_TAG, "Sending moves!");
        Log.d(LOG_TAG, "Flattened Board");
        Log.d(LOG_TAG, flattenedBoard.toString());
        managerThread.sendMoves(flattenedBoard.toString());
        if (hasWon) {
            CharSequence text;
            if(isXPlayer == 0)
                text = "O has won!";
            else
                text = "X has won!";

            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {

            d.setMessage("Waiting for other player's move...");
            d.show();
        }

    }


    public static void updateSquareLocal(ImageButton button) {
        if(isXPlayer == 0)
            button.setImageResource(R.drawable.o);
        else
            button.setImageResource(R.drawable.x);

    }

    public static void updateSquareReceived(ImageButton button) {
        if(isXPlayer != 0)
            button.setImageResource(R.drawable.o);
        else
            button.setImageResource(R.drawable.x);
    }

    // Takes the row and column coordinates of the last move made
    // and checks to see if that move causes the player to win
    public static boolean hasGameWon(String[][] gameBoard, int row, int col){
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
            updateGameBoard(s.substring(0,9));
        }
    };

}
