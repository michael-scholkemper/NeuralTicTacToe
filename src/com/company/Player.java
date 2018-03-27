package com.company;

public class Player {
    public char symbol;

    public Player(char s1) {
        symbol = s1;
    }

    public int play() {
        //TODO
        return (int) (Math.random() * 9);
    }
}
