/************************************************************/
/* Author: Arianna Childs								    */
/* Major: Computer Science 									*/
/* Creation Date: April 1st, 2026						 	*/
/* Due Date: April 27th, 2026							  	*/
/* Course: CS211-01											*/ 
/* VERSION 3												*/	
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
 
    private long cutsceneLastFramChange = 0;
    private int stepFrame = 0;
    private final int[] frames = {0,1,2,1};

    /**
     * Constructs the player, initializes the sprite, and sets
     * the starting position.
     *
     * @param imagePath sprite sheet file path
     * @param startX starting x-coordinate
     * @param startY starting y-coordinate
     */
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

    /**
     * Updates player movement, animation, and collision checks.
     *
     * @param keysPressed currently pressed movement keys
     * @param now current animation timer value
     * @param scene active game scene
     * @param world game world used for collision detection
     */
    public void update(Set<KeyCode> keysPressed, long now, Scene scene, GameWorld world) {
        updateMovement(keysPressed, scene, world);
        updateAnimation(now);
        updateSpritePosition();
    }
    
    /**
     * Moves the player automatically during the intro cutscene.
     *
     * @param x horizontal movement amount
     * @param y vertical movement amount
     */
    public void cutsceneMove(double x, double y) {
    	playerX += x;
    	playerY += y;
    	updateSpritePosition();
    }
    
    /**
     * Updates player movement based on input and collision rules.
     *
     * @param keysPressed currently pressed movement keys
     * @param now current animation timer value
     * @param scene active game scene
     * @param world game world used for collision detection
     */
    private void updateMovement(Set<KeyCode> keysPressed, Scene scene, GameWorld world) {
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


        double nextX = playerX + velocityX;
        double nextY = playerY + velocityY;
        

        // check X movement first
        if (!isBlocked(nextX, playerY, world)) {
            playerX = nextX;
        }

        // check Y movement second
        if (!isBlocked(playerX, nextY, world)) {
            playerY = nextY;
        }
        
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
    
    
    /**
     * Checks whether a player position would collide with
     * blocked terrain.
     *
     * @param x x-coordinate to test
     * @param y y-coordinate to test
     * @param world game world used for collision detection
     * @return true if movement is blocked, false otherwise
     */
    private boolean isBlocked(double x, double y, GameWorld world) {
    	double leftFootX = x + 12;
    	double rightFootX = x + sprite.getFitWidth() - 12;
    	double footY = y + sprite.getFitHeight() - 6;
    	
    	int leftCol = (int)(leftFootX / GameWorld.TILE_SIZE);
        int rightCol = (int)(rightFootX / GameWorld.TILE_SIZE);
        int row = (int)(footY / GameWorld.TILE_SIZE);

        return world.isBlocked(row, leftCol) || world.isBlocked(row, rightCol);
    }

    /**
     * Updates the player's walking animation based on movement.
     *
     * @param now current animation timer value
     */
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
    
    /**
     * Updates the player's cutscene walking animation.
     *
     * @param now current animation timer value
     */
    public void cutsceneWalkAnimation(long now) {
    	currentRow = 0;
    	
    	if (cutsceneLastFramChange == 0) {
    		cutsceneLastFramChange = now;
    	}
    	if (now - cutsceneLastFramChange >= 500_000_000) {
    		stepFrame++;
    		if(stepFrame >= frames.length) {
    			stepFrame = 0;
    		}
    		
    		cutsceneLastFramChange = now;
    	}
    	
    	currentCol = frames[stepFrame];
    	
    	updateViewPort();
    	updateSpritePosition();
    }
    
    /**
     * Stops the cutscene walking animation and restores
     * the idle player frame.
     */
    public void stopCutsceneWalk() {
    	currentRow = 0;
    	currentCol = 1;
    	cutsceneLastFramChange = 0;
    	stepFrame = 0;
    	updateViewPort();
    }
    
    /**
     * Updates the sprite viewport used for animation frames.
     */
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

    /**
     * Updates the player's sprite position on the screen
     * to match the current player coordinates.
     */
    private void updateSpritePosition() {
        sprite.setX(playerX);
        sprite.setY(playerY);
    }
    
    /**
     * Returns the player's sprite image view.
     *
     * @return player sprite
     */
    public ImageView getSprite() {
        return sprite;
    }
    
    /**
     * Returns the player's current x-coordinate.
     *
     * @return player x position
     */
    public double getPlayerX() {
    	return playerX;
    }
    
    /**
     * Returns the player's current y-coordinate.
     *
     * @return player y position
     */
    public double getPlayerY()
    {
    	return playerY;
    }
}
