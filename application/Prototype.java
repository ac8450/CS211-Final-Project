/************************************************************/
/* Author: Arianna Childs								    */
/* Major: Computer Science 									*/
/* Creation Date: April 1st, 2026						 	*/
/* Due Date: April 27th, 2026							  	*/
/* Course: CS211-01											*/ 
/* VERSION 3												*/
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
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.AudioClip;

import javafx.animation.FadeTransition;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.application.Platform;

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
 
    
    private long lastTime = 0;
    
    private boolean cutscenePlaying = false;
    private double cutsceneSpeed = 0.75;
    
    //music
    private MediaPlayer titleMusic;
    private MediaPlayer gameMusic;
    private AudioClip playSound;
    
    private double musicVolume = 0.15;
    
    //fade transitions
    private Rectangle fadeOverlay;
    
    private Player player;
    private GameWorld world;
    private Shop shop;
    private Inventory inventory;
    private InteractionManager interactions;
    
    /**
     * Launches the game, initializes the stage, scenes, game objects,
     * input handling, and starts the main game loop.
     *
     * @param stage primary application window
     */
    @Override
    public void start(Stage stage) {
    	try {

    		
	        Pane root = new Pane();
	        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
	        
	        fadeOverlay = new Rectangle(2000, 2000);
	        fadeOverlay.setFill(Color.BLACK);
	        fadeOverlay.setOpacity(0);
	        fadeOverlay.setVisible(false);
	        
	        
	        inventory = new Inventory();
	        shop = new Shop();
	        
	        
	        double startX = 36 * TILE_SIZE;
	        double startY = 43 * TILE_SIZE;
	        
	        player = new Player("/assets/sprites/farmer.png", startX, startY);
	        interactions = new InteractionManager();
	        
	        world = new GameWorld(player, inventory);
	        
	        world.getView().getChildren().add(player.getSprite());
	        world.getView().getChildren().addAll(interactions.getShopZone(), interactions.getPlotBuyZone());
	        
	        
	        root.getChildren().add(world.getView());
	
	        MenuUI menu = new MenuUI(this, inventory, shop);
	        root.getChildren().add(menu.getRoot());
	        
	        menu.getRoot().prefWidthProperty().bind(scene.widthProperty());
	        menu.getRoot().prefHeightProperty().bind(scene.heightProperty());
	        
	        root.getChildren().add(fadeOverlay);
	        fadeOverlay.toFront();
	        
	        loadAudio();
	        startTitleMusic();
	        
	    
	        
	        scene.setOnKeyPressed(e -> {
	        	
	        	if (cutscenePlaying) {
	        		return;
	        	}
	        	
                keysPressed.add(e.getCode());
                
                
                
                //Open shop
                if (keysPressed.contains(KeyCode.E)) {
                	if(interactions.isNearShop(player)) {
                		openShop(menu);
                	}
                	if(interactions.isNearPlotShopZone(player)) {
                		openBuyPlot(menu);
                	}
                	
                	boolean harvested = world.tryHarvest();
                	
                	if(!harvested) {
                		world.tryPlant();
                	}
                	menu.updateHotbar();
                	
                }
                
                //selecting hotbar slots
                if (keysPressed.contains(KeyCode.DIGIT1)) {
                	inventory.selectSlot(0);
                    menu.updateHotbar();
                }
                if (keysPressed.contains(KeyCode.DIGIT2)) {
                	inventory.selectSlot(1);
                    menu.updateHotbar();
                }
                if (keysPressed.contains(KeyCode.DIGIT3)) {
                	inventory.selectSlot(2);
                    menu.updateHotbar();
                }
                if (keysPressed.contains(KeyCode.DIGIT4)) {
                	inventory.selectSlot(3);
                    menu.updateHotbar();
                }
                
                //Pause the game
                if (e.getCode() == KeyCode.ESCAPE && gameStarted) {
                    if (!paused) {
                        pauseGame(menu);
                    } 
                    else {
                        resumeGame(menu);
                    }
                }
            });


            scene.setOnKeyReleased(e -> {
            if (cutscenePlaying) {
            	keysPressed.clear();
            	return;
            }
            keysPressed.remove(e.getCode());
            });

            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                	if (lastTime == 0) {
                		lastTime = now;
                		return;
                	}
                	
                	double time = (now - lastTime) / 1_000_000_000.0;
                	lastTime = now;
                	
                    if (gameStarted && !paused) {
                        if(cutscenePlaying) {
	                    	keysPressed.clear();
	                    	player.cutsceneMove(0, cutsceneSpeed);
	                    	player.cutsceneWalkAnimation(now);
                        }
                        else {
                        	player.update(keysPressed, now, scene, world);
                        }
                        world.updateCrops(time);
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
    /**
     * Starts gameplay and changes the game state from the title screen
     * into the active playable game.
     *
     * @param menu user interface menu object
     */
    public void startGame(MenuUI menu) {
        gameStarted = true;
        paused = false;
        
        menu.hideTitleScreen();
    }

    /**
     * Pauses gameplay, player movement, and crop growth while displaying
     * the pause menu.
     *
     * @param menu user interface menu object
     */
    public void pauseGame(MenuUI menu) {
        if (gameStarted && !paused) {
            paused = true;
            if(paused && gameMusic != null) {
            	gameMusic.stop();
            }
            menu.openPauseMenu();
        }
    }

    /**
     * Resumes gameplay after being paused and closes the pause menu.
     *
     * @param menu user interface menu object
     */
    public void resumeGame(MenuUI menu) {
        paused = false;
        if (!paused && gameMusic != null) {
        	startGameMusic();
        }
        menu.closePauseMenu();
    }

    /**
     * Opens the seed shop interface and pauses gameplay.
     *
     * @param menu user interface menu object
     */
    public void openShop(MenuUI menu) {
    	 if (gameStarted && !paused) {
             paused = true;
             menu.openShop();
         }
    }
    
    /**
     * Closes the seed shop interface and resumes gameplay.
     *
     * @param menu user interface menu object
     */
    public void closeShop(MenuUI menu)
    {
    	paused = false;
    	menu.closeShop();
    }
    
    /**
     * Opens the plot purchase menu and pauses gameplay.
     *
     * @param menu user interface menu object
     */
    public void openBuyPlot(MenuUI menu) {
    	if (gameStarted && !paused) {
            paused = true;
            menu.openPlotBuy();
        }
    }
    
    /**
     * Closes the plot purchase menu and resumes gameplay.
     *
     * @param menu user interface menu object
     */
    public void closeBuyPlot(MenuUI menu) {
    	paused = false;
    	menu.closePlotBuy();
    }
    
    /**
     * Returns the player from gameplay back to the title screen.
     *
     * @param menu user interface menu object
     */
    public void returnToTitle(MenuUI menu) {
        gameStarted = false;
        paused = false;


        menu.closePauseMenu();
        menu.showTitleScreen();
    }

    /**
     * Updates the camera position to follow the player through the world.
     *
     * @param world game world being displayed
     * @param player player object being followed
     * @param scene scene containing the game window
     */
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

    
    //muisc
    
    /**
     * Loads audio files used for title music, gameplay music,
     * and sound effects.
     */
    private void loadAudio() {
    	Media title = new Media(getClass().getResource("/audio/title.mp3").toExternalForm());
    	Media game = new Media(getClass().getResource("/audio/game.wav").toExternalForm());
    	
    	titleMusic = new MediaPlayer(title);
    	gameMusic = new MediaPlayer(game);
    	
    	titleMusic.setCycleCount(MediaPlayer.INDEFINITE);
    	gameMusic.setCycleCount(MediaPlayer.INDEFINITE);
    	
    	titleMusic.setVolume(musicVolume);
    	gameMusic.setVolume(musicVolume);
    	
    	
    	playSound = new AudioClip(getClass().getResource("/audio/play.wav").toExternalForm());
    	playSound.setVolume(0.55);
    	
    }
    
    /**
     * Plays and loops the title screen background music.
     */
    private void startTitleMusic() {
    	if(gameMusic != null) {
    		gameMusic.stop();
    	}
    	
    	if(titleMusic != null) {
    		titleMusic.play();
    	}
    }
    
    /**
     * Plays and loops the in-game background music.
     */
    private void startGameMusic() {
    	if (titleMusic != null) {
    		titleMusic.stop();
    	}
    	if(gameMusic != null) {
    		gameMusic.play ();
    	}
    }
    
    /**
     * Plays the sound effect used when starting the game
     * from the title screen.
     */
    public void playStartSound() {
    	if(playSound != null) {
    		playSound.play();
    	}
    }
    
    /**
     * Starts the game using a fade transition and intro cutscene.
     *
     * @param menu user interface menu object
     */
    public void startGameWithFade(MenuUI menu) {
    	playStartSound();
    
    	if (titleMusic != null) {
    		titleMusic.stop();
    	}
    	playStartSound();
    	
    	fadeOverlay.setVisible(true);
    	fadeOverlay.setOpacity(1);
    	fadeOverlay.toFront();
    	
    	cutscenePlaying = true;
       
        Platform.runLater(() -> {
        	startGame(menu);
        	
        	 FadeTransition fadeIn = new FadeTransition(Duration.seconds(5), fadeOverlay);
        	 
             fadeIn.setFromValue(1);
             fadeIn.setToValue(0);

             fadeIn.setOnFinished(e -> {
                	fadeOverlay.setVisible(false);
                	cutscenePlaying = false;
                	keysPressed.clear();
                	player.stopCutsceneWalk();
                	startGameMusic();
                });
             fadeIn.play();
        });
    	
    }
    
    /**
     * Disables the plot purchase interaction zone after the second
     * plot has been purchased.
     */
    public void disablePlotBuyZone() {
    	interactions.disablePlotBuyZone();
    }
    
    /**
     * Attempts to purchase and unlock the second farming plot.
     *
     * @return true if the plot was successfully purchased,
     * false otherwise
     */
    public boolean buySecondPlot() {
    	return world.tryBuySecondPlot(inventory, shop);
    }
    
    /**
     * Mutes or unmutes the title and gameplay music.
     *
     * @param muted true if music should be muted, false otherwise
     */
    public void setMusicMuted(boolean muted) {
    	if(gameMusic != null) {
    		gameMusic.setMute(muted);
    	}
    	
    	if(titleMusic != null) {
    		titleMusic.setMute(muted);
    	}
    }
    
    /**
     * Launches the JavaFX application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
