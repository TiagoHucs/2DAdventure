package org.hucs;

import org.hucs.entity.Player;
import org.hucs.tiles.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    final int originalTileSize = 16; // 16 x 16
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16; // ajuste quantas colunas quer
    public final int maxScreenRow = 12; // ajuste quantas linhas quer
    public final int screenWidth = tileSize * maxScreenCol; //768px
    public final int screenHeight = tileSize * maxScreenRow; //576px
    public final int fps = 60;

    TileManager tm = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

    //create player
    Player player = new Player(this,keyHandler);

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

        tm.draw(g2);
        player.draw(g2);

        g2.dispose();
    }
}
