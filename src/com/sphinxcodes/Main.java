package com.sphinxcodes;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame obj = new JFrame();
        Gamer game = new Gamer();
        obj.setBounds(10, 10, 700, 600);
        obj.setTitle("Ping Ball");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(game);
    }
}
