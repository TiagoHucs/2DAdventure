package org.hucs.entity;

import org.hucs.GamePanel;
import org.hucs.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyHandler;
    int imageCounter = 0;
    int imageNumber = 1;

    public Player(GamePanel gp, KeyHandler keyHandler){
        this.gp = gp;
        this.keyHandler = keyHandler;

        setDefaultValues();
        configurePlayerImages();
    }

    public void configurePlayerImages(){
        try {
            up1 = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/player/walk/boy_up_1.png"));
            up2 = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/player/walk/boy_up_2.png"));
            down1 = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/player/walk/boy_down_1.png"));
            down2 = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/player/walk/boy_down_2.png"));
            left1 = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/player/walk/boy_left_1.png"));
            left2 = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/player/walk/boy_left_2.png"));
            right1 = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/player/walk/boy_right_1.png"));
            right2 = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/player/walk/boy_right_2.png"));

            stop = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/player/walk/boy_down_1.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setDefaultValues(){
        this.x = 100;
        this.y = 100;
        this.speed = 4;
        direction = Direction.DOWN;
    }

    public void update() {
        if(keyHandler.upPressed){
            direction = Direction.UP;
            y -= speed;
        } else if (keyHandler.downPressed){
            direction = Direction.DOWN;
            y += speed;
        } else if (keyHandler.leftPressed){
            direction = Direction.LEFT;
            x -= speed;
        } else if (keyHandler.rightPressed){
            direction = Direction.RIGHT;
            x += speed;
        } else {
            direction = Direction.STOP;
        }
    }

    public void draw(Graphics2D g2){
        //g2.setColor(Color.WHITE);
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize );

        BufferedImage image = null;

        switch (direction){
            case UP:
                image = getImageAnimated(Direction.UP);
                break;
            case DOWN:
                image = getImageAnimated(Direction.DOWN);
                break;
            case LEFT:
                image = getImageAnimated(Direction.LEFT);
                break;
            case RIGHT:
                image = getImageAnimated(Direction.RIGHT);
                break;
            case STOP:
                image = getImageAnimated(Direction.STOP);
                break;
        }

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

    }

    private BufferedImage getImageAnimated(Direction direction) {

        if (imageCounter++ >= 10){
            imageNumber = imageNumber == 1 ? 2 : 1;
            imageCounter = 0;
        }
        
        if(Direction.UP.equals(direction)){
            return imageNumber == 1 ? up1 : up2;
        } else if(Direction.DOWN.equals(direction)){
            return imageNumber == 1 ? down1 : down2;
        }else if(Direction.LEFT.equals(direction)){
            return imageNumber == 1 ? left1 : left2;
        }else if(Direction.RIGHT.equals(direction)){
            return imageNumber == 1 ? right1 : right2;
        }

        return stop;

    }


}
