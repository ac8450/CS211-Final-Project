/************************************************************/
/* Author: Arianna Childs								    */
/* Major: Computer Science 									*/
/* Creation Date: April 1st, 2026						 	*/
/* Due Date: April 24th, 2-26							  	*/
/* Course: CS211-01											*/ 
/* VERSION 2												*/
/* Professor Name: Professor Shimkanon				 		*/
/* Assignment: Final Project 								*/
/* Filename: Prototype.java 								*/
/* Purpose: This program will connect all frontend classes	*/
/* 			to the backend classes to create a fully 		*/
/* 			playable game loop. This class also creates     */
/* 			a camera to follow the player and a title screen*/
/* 			and it controls the gamestates for when player  */
/* 			movement and crop growth must be paused			*/
/************************************************************/

package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.HashSet;
import java.util.Set;

public class Prototype extends Application {

    private final Set<KeyCode> keysPressed = new HashSet<>();
    
    private final int TILE_SIZE = 64;
    private final int ROWS = 80;
    private final int COLS = 80;
    
    private final double WINDOW_WIDTH = 900;
    private final double WINDOW_HEIGHT = 700;
    

    private boolean gameStarted = false;
    private boolean paused = false;
    
    @Override
    public void start(Stage stage) {
    	try {

    		
	        Pane root = new Pane();
	        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
	        
	        
	        Inventory inventory = new Inventory();
	        Shop shop = new Shop();
	        
	        
	        double startX = 36 * TILE_SIZE;
	        double startY = 43 * TILE_SIZE;
	        
	        Player player = new Player("/assets/sprites/farmer.png", startX, startY);
	        InteractionManager interactions = new InteractionManager();
	        
	        GameWorld world = new GameWorld(player, inventory);
	        
	        world.getView().getChildren().add(player.getSprite());
	        world.getView().getChildren().add(interactions.getShopZone());
	        
	        
	        
	        root.getChildren().add(world.getView());
	
	        MenuUI menu = new MenuUI(this, inventory, shop);
	        root.getChildren().add(menu.getRoot());
	        
	        menu.getRoot().prefWidthProperty().bind(scene.widthProperty());
	        menu.getRoot().prefHeightProperty().bind(scene.heightProperty());
	        
	    
	        
	        scene.setOnKeyPressed(e -> {
                keysPressed.add(e.getCode());
                
                //Open shop
                if (keysPressed.contains(KeyCode.E)) {
                	if(interactions.isNearShop(player)) {
                		openShop(menu);
                	}
                	else {
                		world.tryPlot();
                	}
                }
                
                //Pause the game
                if (e.getCode() == KeyCode.ESCAPE && gameStarted) {
                    if (!paused) {
                        pauseGame(menu);
                    } else {
                        resumeGame(menu);
                    }
                }
            });


            scene.setOnKeyReleased(e -> keysPressed.remove(e.getCode()));


            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (gameStarted && !paused) {
                        player.update(keysPressed, now, scene);
                    }


                    updateCamera(world, player, scene);
                }
            };

	        
	        timer.start();
	
	        stage.setScene(scene);
	        stage.setTitle("Prototype");
	        stage.show();
	
	        root.requestFocus();
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    public void startGame(MenuUI menu) {
        gameStarted = true;
        paused = false;


        menu.hideTitleScreen();
        //menu.getRoot().setMouseTransparent(true);
    }


    public void pauseGame(MenuUI menu) {
        if (gameStarted && !paused) {
            paused = true;
            menu.openPauseMenu();
        }
    }


    public void resumeGame(MenuUI menu) {
        paused = false;
        menu.closePauseMenu();
    }

    public void openShop(MenuUI menu) {
    	 if (gameStarted && !paused) {
             paused = true;
             menu.openShop();
         }
    }
    
    public void closeShop(MenuUI menu)
    {
    	paused = false;
    	menu.closeShop();
    }
    public void returnToTitle(MenuUI menu) {
        gameStarted = false;
        paused = false;


        menu.closePauseMenu();
        menu.showTitleScreen();
    }

    
    private void updateCamera(GameWorld world, Player player, Scene scene)
    {
    	double worldWidth = COLS * TILE_SIZE;
    	double worldHeight = ROWS * TILE_SIZE;
    	
    	double playerX = player.getPlayerX();
    	double playerY = player.getPlayerY();
    	
    	double playerWidth = player.getSprite().getFitWidth();
    	double playerHeight = player.getSprite().getFitHeight();
    	
    	double sceneWidth = scene.getWidth();
    	double sceneHeight = scene.getHeight();
    	
    	double cameraX = (sceneWidth / 2) - (playerX + playerWidth / 2);
    	double cameraY = (sceneHeight / 2) - (playerY + playerHeight / 2);
    	
    	double minX = sceneWidth - worldWidth;
    	double minY = sceneHeight - worldHeight;
    	
    	if (cameraX > 0)
    	{
    		cameraX = 0;
    	}
    	if (cameraY > 0)
    	{
    		cameraY = 0;
    	}
    	if (cameraX < minX)
    	{
    		cameraX = minX;
    	}
    	if (cameraY < minY)
    	{
    		cameraY = minY;
    	}
    	
    	world.getView().setLayoutX(cameraX);
    	world.getView().setLayoutY(cameraY);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
