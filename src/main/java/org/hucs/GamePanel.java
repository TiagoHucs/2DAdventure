package org.hucs;

import org.hucs.entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    final int originalTileSize = 16; // 16 x 16
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16; // ajuste quantas colunas quer
    final int maxScreenRow = 12; // ajuste quantas linhas quer
    final int screenWidth = tileSize * maxScreenCol; //768px
    final int screenHeight = tileSize * maxScreenRow; //576px
    final int fps = 60;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

    //create player
    Player player = new Player(this,keyHandler);

    // player default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/fps; //0.01666
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null){
            long currentTime = System.nanoTime();

            update();
            repaint();

            try {
                double remainTime = nextDrawTime - System.nanoTime();
                remainTime = remainTime/1000000;

                if(remainTime < 0){
                    remainTime = 0;
                }

                Thread.sleep((long) remainTime) ;

                nextDrawTime += drawInterval;

            } catch (InterruptedException e){
                e.printStackTrace();
            }

        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        g2.dispose();
    }
}
