package com.tictactwo.daniel.brian.tictactwo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest{



    @Test
    public void test1() throws Exception {
        GameActivity game = new GameActivity();
        String[][] arra= {
                          {"x","o","x"},
                          {"x","x","o"},
                          {"x","x","o"}
                         };

        assertEquals(false, game.hasGameWon(arra, 0, 1));
    }

    @Test
    public void test2() throws Exception {
        GameActivity game = new GameActivity();
        String[][] arra= {
                {"x","o","x"},
                {"x","x","o"},
                {"x","x","o"}
        };

        assertEquals(true, game.hasGameWon(arra, 1, 1));
    }

    @Test
    public void test3() throws Exception {
        GameActivity game = new GameActivity();
        String[][] arra= {
                {"","",""},
                {"","x",""},
                {"","",""}
        };

        assertEquals(false, game.hasGameWon(arra, 1, 1));
    }

    @Test
    public void test4() throws Exception {
        GameActivity game = new GameActivity();
        String[][] arra= {
                {"","o",""},
                {"","x",""},
                {"","o",""}
        };

        assertEquals(false, game.hasGameWon(arra, 1, 1));
    }

    @Test
    public void test5() throws Exception {
        GameActivity game = new GameActivity();
        String[][] arra= {
                {"x","",""},
                {"x","x","o"},
                {"x","o","o"}
        };

        assertEquals(true, game.hasGameWon(arra, 0, 0));
    }

}