package com.sphinxcodes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import static java.awt.Color.*;

public class Gamer extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;

    private int ballX = 120;
    private int ballY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private MapGenerator map;

    public Gamer() {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }
    public void paint(Graphics g){
        //background
        g.setColor(black);
        g.fillRect(1,1,692,592);

        //map Drawing
        map.draw((Graphics2D) g);

        //border
        g.setColor(Color.green);
        g.fillRect(0,0,5,592);
        g.fillRect(0,0,692,3);
        g.fillRect(692,0,5,592);

        //scores
        g.setColor(Color.white);
        g.setFont(new Font("georgia", Font.BOLD, 20));
        g.drawString("" + score, 590, 30);

        //the paddle
        g.setColor(Color.yellow);
        g.fillRect(playerX, 550, 100,8);

        //the ball
        g.setColor(white);
        g.fillOval(ballX, ballY, 20,20);

        if(totalBricks <= 0){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("georgia", Font.BOLD, 30));
            g.drawString("Weldone Vintage Player!! " , 190, 300);

            g.setFont(new Font("georgia", Font.BOLD, 20));
            g.drawString("Press Enter to restart" , 230, 350);
        }

        if(ballY > 570){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("georgia", Font.BOLD, 30));
            g.drawString("Game Over,Player " , 190, 300);

            g.setFont(new Font("georgia", Font.BOLD, 20));
            g.drawString("Press Enter to restart" , 230, 350);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
            if(new Rectangle(ballX, ballY, 20,20).intersects(new Rectangle(playerX, 550,100,8))){
                ballYdir = -ballYdir;
            }
            A: for(int i = 0; i < map.map.length; i ++){
                for(int j = 0; j < map.map[0].length; j++){
                    if(map.map[i][j] > 0){
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
                        Rectangle ballRect = new Rectangle(ballX,ballY, 20, 20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0,i,j);
                            totalBricks --;
                            score +=5;

                            if(ballX + 19 <= brickRect.x || ballX + 1 >= brickRect.x + brickRect.width){
                                ballXdir = - ballYdir;
                            }else{
                                ballYdir = - ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }
            ballX += ballXdir;
            ballY += ballYdir;
            if(ballX < 0){
                ballXdir = -ballXdir;
            }
            if(ballY < 0){
                ballYdir = -ballYdir;
            }
            if(ballX > 670){
                ballXdir = -ballXdir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerX >= 600){
                playerX = 600;
            }else{
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX < 10){
                playerX = 10;
            }else{
                moveLeft();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballX = 120;
                ballY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3, 7);

                repaint();
            }
        }
    }
    public void moveRight(){
        play = true;
        playerX += 20;
    }
    public void moveLeft(){
        play = true;
        playerX -= 20;
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
