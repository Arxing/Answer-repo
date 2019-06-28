package org.arxing;

public class Main {

    public static void main(String[] arg) {
        ScanLooper game;
        //玩猜數字遊戲
        //        game = new GuessNumberGame(System.in);
        //玩猜拳遊戲
        game = new GuessBoneGame(System.in);
        game.startLoop();
    }
}
