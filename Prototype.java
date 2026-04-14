package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

public class Prototype extends Application {

    private final Set<KeyCode> keysPressed = new HashSet<>();
    
    private final int TILE_SIZE = 64;
    private final int ROWS = 80;
    private final int COLS = 80;
    
    private final double WINDOW_WIDTH = 900;
    private final double WINDOW_HEIGHT = 700;
    

    @Override
    public void start(Stage stage) {
    	try {

    		
	        Pane root = new Pane();
	        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
	        
	        
	        
	        GameWorld world = new GameWorld();
	        
	        
	        double startX = 36 * TILE_SIZE;
	        double startY = 43 * TILE_SIZE;
	        
	        Player player = new Player("/assets/sprites/farmer.png", startX, startY);
	        
	        world.getView().getChildren().add(player.getSprite());
	        root.getChildren().add(world.getView());
	
	        MenuUI menu = new MenuUI();
	        root.getChildren().add(menu.getRoot());
	        
	        menu.getRoot().prefWidthProperty().bind(scene.widthProperty());
	        menu.getRoot().prefHeightProperty().bind(scene.heightProperty());
	        
	        scene.setOnKeyPressed(e -> keysPressed.add(e.getCode()));
	        scene.setOnKeyReleased(e -> keysPressed.remove(e.getCode()));
	
	        AnimationTimer timer = new AnimationTimer() {
	            @Override
	            public void handle(long now) {
	                player.update(keysPressed, now, scene);
	                
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
