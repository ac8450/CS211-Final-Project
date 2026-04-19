/************************************************************/
/* Author: Arianna Childs								    */
/* Major: Computer Science 									*/
/* Creation Date: April 1st, 2026						 	*/
/* Due Date: April 24th, 2-26							  	*/
/* Course: CS211-01											*/ 
/* VERSION 2												*/	
/* Filename: Player.java 									*/
/* Purpose: This program creates the player sprite			*/
/* 			and controls all of its movement and animations.*/
/************************************************************/
package application;

import javafx.geometry.Rectangle2D; 
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.util.Set;

public class Player {

    private final ImageView sprite;

    private final double frameWidth = 32;
    private final double fullFrameHeight = 64;
    
    private final double visibleFrameHeight = 42;
    private final double yOffset = 22;

    private double playerX;
    private double playerY;

    private double velocityX = 0;
    private double velocityY = 0;

    private final double speed = 7.5;
    
    private final int WORLD_HEIGHT = 80 * 64;
    private final int WORLD_WIDTH = 80 * 64;

    // row = direction, col = frame
    // row order here assumes:
    // 0 = down, 1 = left, 2 = right, 3 = up
    private int currentRow = 0;
    private int currentCol = 1; // middle frame = idle

    private long lastFrameChange = 0;
    private final long FRAME_DELAY = 250_000_000;

    public Player(String imagePath, double startX, double startY) {
        Image sheet = new Image(getClass().getResource(imagePath).toExternalForm());

        this.playerX = startX;
        this.playerY = startY;
        
        this.sprite = new ImageView(sheet);
        
        updateViewPort();
        
        this.sprite.setFitWidth(frameWidth * 2);
        this.sprite.setFitHeight(visibleFrameHeight * 2);
        this.sprite.setX(playerX);
        this.sprite.setY(playerY);
        this.sprite.setSmooth(false);
    }

    public void update(Set<KeyCode> keysPressed, long now, Scene scene) {
        updateMovement(keysPressed, scene);
        updateAnimation(now);
        updateSpritePosition();
    }

    private void updateMovement(Set<KeyCode> keysPressed, Scene scene) {
        velocityX = 0;
        velocityY = 0;

        if (keysPressed.contains(KeyCode.W)) {
            velocityY = -speed;
            currentRow = 3;
        } else if (keysPressed.contains(KeyCode.S)) {
            velocityY = speed;
            currentRow = 0;
        } else if (keysPressed.contains(KeyCode.A)) {
            velocityX = -speed;
            currentRow = 1;
        } else if (keysPressed.contains(KeyCode.D)) {
            velocityX = speed;
            currentRow = 2;
        }

        playerX += velocityX;
        playerY += velocityY;

        double spriteWidth = sprite.getFitWidth();
        double spriteHeight = sprite.getFitHeight();

        if (playerX < 0) {
            playerX = 0;
        }
        if (playerY < 0) {
            playerY = 0;
        }
        if (playerX > WORLD_WIDTH - spriteWidth) {
            playerX = WORLD_WIDTH - spriteWidth;
        }
        if (playerY > WORLD_HEIGHT - spriteHeight) {
            playerY = WORLD_HEIGHT - spriteHeight;
        }
    }

    private void updateAnimation(long now) {
        boolean moving = (velocityX != 0 || velocityY != 0);

        if (moving) {
            if (now - lastFrameChange >= FRAME_DELAY) {
                if (currentCol == 0) {
                    currentCol = 2;
                } else {
                    currentCol = 0;
                }
                lastFrameChange = now;
            }
        } else {
            currentCol = 1;
        }
        
        updateViewPort();

    }
    
    private void updateViewPort()
    {
    	double x = currentCol * frameWidth;
    	double y = currentRow * fullFrameHeight + yOffset;
    	
    	  sprite.setViewport(new Rectangle2D(
                  x,
                  y,
                  frameWidth,
                  visibleFrameHeight
          ));
    }

    private void updateSpritePosition() {
        sprite.setX(playerX);
        sprite.setY(playerY);
    }
    
  

    public ImageView getSprite() {
        return sprite;
    }
    public double getPlayerX() {
    	return playerX;
    }
    public double getPlayerY()
    {
    	return playerY;
    }
}
