package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HumanPlayer extends Player {
    public HumanPlayer(char s1) {
        super(s1);
    }

    public int play(char[][] board) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            return Integer.parseInt(br.readLine());
        } catch (Exception e) {
        }
        ;
        return 0;
    }
}
