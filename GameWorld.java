/************************************************************/
/* Author: Arianna Childs								    */
/* Major: Computer Science 									*/
/* Creation Date: April 1st, 2026						 	*/
/* Due Date: April 24th, 2-26							  	*/
/* Course: CS211-01											*/ 
/* VERSION 2												*/
/* Filename: GameWorld.java 								*/
/* Purpose: This program builds the entire map the player   */
/* 			will be playing on and interacting with.		*/
/************************************************************/
package application;

import javafx.scene.image.Image;  
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class GameWorld {
	
	//Connections to Player and Inventory classes
	private Inventory playerInventory;
	private Player player;
	
	private final int TILE_SIZE = 64;
	private final int ROWS = 80;
	private final int COLS = 80;
	
	//water = 0, land = 1
	private int[][] map = new int[ROWS][COLS];
	
	private final Pane worldLayer;
	
	//base tiles
	private Image water;
	private Image grass;
	
	//water outer corners
	private Image waterCornerBotLeft;
	private Image waterCornerBotRight;
	private Image waterCornerTopLeft;
	private Image waterCornerTopRight;
	
	//water inner corners
	private Image waterEdgeBotLeft;
	private Image waterEdgeBotRight;
	private Image waterEdgeTopLeft;
	private Image waterEdgeTopRight;
	
	//water straight edges
	private Image waterEdgeMidTop;
	private Image waterEdgeMidBot;
	private Image waterEdgeMidLeft;
	private Image waterEdgeMidRight;
	
	//grass outer corners
	private Image grassEdgeTopLeft;
	private Image grassEdgeTopRight;
	private Image grassEdgeBotLeft;
	private Image grassEdgeBotRight;
	
	//grass inner corners
	private Image grassEdgeCornerTopLeft;
	private Image grassEdgeCornerTopRight;
	private Image grassEdgeCornerBotLeft;
	private Image grassEdgeCornerBotRight;

	//grass straight edges
	private Image grassEdgeTopMid;
	private Image grassEdgeBotMid;
	private Image grassEdgeMidLeft;
	private Image grassEdgeMidRight;
	
	//Paths
	private Image pathTopLeft;
	private Image pathTopMid;
	private Image pathTopRight;
	private Image pathLeftMid;
	private Image pathMid;
	private Image pathMidRight;
	private Image pathBotLeft;
	private Image pathBotMid;
	private Image pathBotRight;
	private Image pathCornerTop;
	private Image pathCornerBot;
	
	//Buildings
	private Image house;
	private Image market;
	private Image stairs;
	private Image vertBridge;
	private Image horiBridge;
	private Image bridgeSupport;
	private Image fenceBotEnd;
	private Image fenceMid;
	private Image fenceRightEnd;
	private Image fenceTopLeft;
	private Image fenceVertMid;
	
	//Decorations
	private Image miniHay;
	
	//plots
	private Plot[][] plots;
	private Image emptyPlot;;
	private Image plantedPlot;
	
	
	public GameWorld(Player player, Inventory inventory)
	{
		this.player = player;
		this.playerInventory = inventory;
		
		worldLayer = new Pane();
		
		plots = new Plot[3][3];
		
		loadImages();
		buildWater();
		buildLand();
		drawMap();
		buildRaisedTerrain();
		buildPath();
		buildBuildings();
		
		int startX = 1845;
		int startY = 3335;
		
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				double x = startX + c * TILE_SIZE;
				double y = startY + r * TILE_SIZE;
				
				plots[r][c] = new Plot(emptyPlot, plantedPlot, x, y);
				worldLayer.getChildren().add(plots[r][c].getView());
			}
		}
	}

	public void loadImages()
	{
		water = new Image(getClass().getResource("/assets/tiles/ground/water.png").toExternalForm());
		grass = new Image(getClass().getResource("/assets/tiles/ground/grass.png").toExternalForm());
		
		//WATER EDGES
		waterCornerBotLeft = new Image(getClass().getResource("/assets/tiles/ground/water_corner_bot_left.png").toExternalForm());
		waterCornerBotRight = new Image(getClass().getResource("/assets/tiles/ground/water_corner_bot_right.png").toExternalForm());
		waterCornerTopLeft = new Image(getClass().getResource("/assets/tiles/ground/water_corner_top_left.png").toExternalForm());
		waterCornerTopRight = new Image(getClass().getResource("/assets/tiles/ground/water_corner_top_right.png").toExternalForm());
		
		waterEdgeBotLeft = new Image(getClass().getResource("/assets/tiles/ground/water_edge_bot_left.png").toExternalForm());
		waterEdgeBotRight = new Image(getClass().getResource("/assets/tiles/ground/water_edge_bot_right.png").toExternalForm());
		waterEdgeTopLeft = new Image(getClass().getResource("/assets/tiles/ground/water_edge_top_left.png").toExternalForm());
		waterEdgeTopRight = new Image(getClass().getResource("/assets/tiles/ground/water_edge_top_right.png").toExternalForm());
		
		waterEdgeMidTop = new Image(getClass().getResource("/assets/tiles/ground/water_edge_top_mid.png").toExternalForm());
		waterEdgeMidBot = new Image(getClass().getResource("/assets/tiles/ground/water_edge_bot_mid.png").toExternalForm());
		waterEdgeMidLeft = new Image(getClass().getResource("/assets/tiles/ground/water_edge_mid_left.png").toExternalForm());
		waterEdgeMidRight = new Image(getClass().getResource("/assets/tiles/ground/water_edge_mid_right.png").toExternalForm());
		
		//GRASS EDGES
		grassEdgeTopLeft = new Image(getClass().getResource("/assets/tiles/ground/grass_edge_top_left.png").toExternalForm());
		grassEdgeTopRight = new Image(getClass().getResource("/assets/tiles/ground/grass_edge_top_right.png").toExternalForm());
		grassEdgeBotLeft = new Image(getClass().getResource("/assets/tiles/ground/grass_edge_bot_left.png").toExternalForm());
		grassEdgeBotRight = new Image(getClass().getResource("/assets/tiles/ground/grass_edge_bot_right.png").toExternalForm());
		
		grassEdgeCornerTopLeft = new Image(getClass().getResource("/assets/tiles/ground/grass_edge_corner_top_left.png").toExternalForm());
		grassEdgeCornerTopRight = new Image(getClass().getResource("/assets/tiles/ground/grass_edge_corner_top_right.png").toExternalForm());
		grassEdgeCornerBotLeft = new Image(getClass().getResource("/assets/tiles/ground/grass_edge_corner_bot_left.png").toExternalForm());
		grassEdgeCornerBotRight = new Image(getClass().getResource("/assets/tiles/ground/grass_edge_corner_bot_right.png").toExternalForm());
		
		grassEdgeTopMid = new Image(getClass().getResource("/assets/tiles/ground/grass_edge_top_mid.png").toExternalForm());
		grassEdgeBotMid = new Image(getClass().getResource("/assets/tiles/ground/grass_edge_bot_mid.png").toExternalForm());
		grassEdgeMidLeft = new Image(getClass().getResource("/assets/tiles/ground/grass_edge_mid_left.png").toExternalForm());
		grassEdgeMidRight = new Image(getClass().getResource("/assets/tiles/ground/grass_edge_mid_right.png").toExternalForm());
		
		//Paths
		pathTopLeft = new Image(getClass().getResource("/assets/tiles/ground/path_top_left.png").toExternalForm());
		pathTopMid = new Image(getClass().getResource("/assets/tiles/ground/path_top_mid.png").toExternalForm());
		pathTopRight = new Image(getClass().getResource("/assets/tiles/ground/path_top_right.png").toExternalForm());
		pathLeftMid = new Image(getClass().getResource("/assets/tiles/ground/path_left_mid.png").toExternalForm());
		pathMid = new Image(getClass().getResource("/assets/tiles/ground/path_mid.png").toExternalForm());
		pathMidRight = new Image(getClass().getResource("/assets/tiles/ground/path_mid_right.png").toExternalForm());
		pathBotLeft = new Image(getClass().getResource("/assets/tiles/ground/path_bot_Left.png").toExternalForm());
		pathBotMid = new Image(getClass().getResource("/assets/tiles/ground/path_bot_mid.png").toExternalForm());
		pathBotRight = new Image(getClass().getResource("/assets/tiles/ground/path_bot_right.png").toExternalForm());
		pathCornerTop = new Image(getClass().getResource("/assets/tiles/ground/path_corner_top.png").toExternalForm());
		pathCornerBot = new Image(getClass().getResource("/assets/tiles/ground/path_corner_bot.png").toExternalForm());
		
		//Buildings
		house = new Image(getClass().getResource("/assets/tiles/buildings/house.png").toExternalForm());
		market = new Image(getClass().getResource("/assets/tiles/buildings/market.png").toExternalForm());
		stairs = new Image(getClass().getResource("/assets/tiles/ground/stairs.png").toExternalForm());
		vertBridge = new Image(getClass().getResource("/assets/tiles/buildings/bridge_vert.png").toExternalForm());
		horiBridge = new Image(getClass().getResource("/assets/tiles/buildings/bridge_hori.png").toExternalForm());
		bridgeSupport = new Image(getClass().getResource("/assets/tiles/buildings/bridge_support.png").toExternalForm());
		
		fenceBotEnd = new Image(getClass().getResource("/assets/tiles/buildings/fence_bot_end.png").toExternalForm());
		fenceMid = new Image(getClass().getResource("/assets/tiles/buildings/fence_mid.png").toExternalForm());
		fenceRightEnd = new Image(getClass().getResource("/assets/tiles/buildings/fence_right_end.png").toExternalForm());
		fenceTopLeft = new Image(getClass().getResource("/assets/tiles/buildings/fence_top_left_corner.png").toExternalForm());
		fenceVertMid = new Image(getClass().getResource("/assets/tiles/buildings/fence_vert_mid.png").toExternalForm());
	
		//Decorations/Foliage?
		miniHay = new Image(getClass().getResource("/assets/tiles/buildings/mini_hay.png").toExternalForm());
		
		//plots
		emptyPlot = new Image(getClass().getResource("/assets/tiles/crops/plot_empty.png").toExternalForm());
		plantedPlot = new Image(getClass().getResource("/assets/tiles/crops/plot_full.png").toExternalForm());
	}
	
	public void buildWater()
	{
		for (int r = 0; r < 80; r++) {
			for (int c = 0; c < 80; c++)
			{
				 map[r][c] = 0;
			}
		}
	}
	
	public void buildLand() 
	{
		 for (int r = 20; r <= 24; r++) {
		        for (int c = 30; c <= 44; c++) {
		            map[r][c] = 1;
		        }
		    }

		    for (int r = 24; r <= 30; r++) {
		        for (int c = 17; c <= 55; c++) {
		            map[r][c] = 1;
		        }
		    }

		    for (int r = 30; r <= 38; r++) {
		        for (int c = 12; c <= 55; c++) {
		            map[r][c] = 1;
		        }
		    }

		    for (int r = 38; r <= 46; r++) {
		        for (int c = 12; c <= 53; c++) {
		            map[r][c] = 1;
		        }
		    }

		    for (int r = 46; r <= 50; r++) {
		        for (int c = 16; c <= 53; c++) {
		            map[r][c] = 1;
		        }
		    }

		    for (int r = 50; r <= 52; r++) {
		        for (int c = 16; c <= 60; c++) {
		            map[r][c] = 1;
		        }
		    }

		    for (int r = 52; r <= 54; r++) {
		        for (int c = 16; c <= 64; c++) {
		            map[r][c] = 1;
		        }
		    }

		    for (int r = 54; r <= 61; r++) {
		        for (int c = 22; c <= 64; c++) {
		            map[r][c] = 1;
		        }
		    }

		    for (int r = 61; r <= 62; r++) {
		        for (int c = 33; c <= 64; c++) {
		            map[r][c] = 1;
		        }
		    }
	}
	
	private void drawMap()
	{
		 for (int r = 0; r < ROWS; r++) {
	            for (int c = 0; c < COLS; c++) {
	                Image selectedImage;
	                // If this tile is water, use water
	                if (map[r][c] == 0) {
	                    selectedImage = water;
	                } else {
	                    // Neighbor checks for land tiles
	                    boolean waterAbove = (r == 0 || map[r - 1][c] == 0);
	                    boolean waterBelow = (r == ROWS - 1 || map[r + 1][c] == 0);
	                    boolean waterLeft = (c == 0 || map[r][c - 1] == 0);
	                    boolean waterRight = (c == COLS - 1 || map[r][c + 1] == 0);

	                    boolean waterTopLeft = (r == 0 || c == 0 || map[r - 1][c - 1] == 0);
	                    boolean waterTopRight = (r == 0 || c == COLS - 1 || map[r - 1][c + 1] == 0);
	                    boolean waterBottomLeft = (r == ROWS - 1 || c == 0 || map[r + 1][c - 1] == 0);
	                    boolean waterBottomRight = (r == ROWS - 1 || c == COLS - 1 || map[r + 1][c + 1] == 0);
	                    // OUTER CORNERS first
	                    if (waterAbove && waterLeft) {
	                        selectedImage = waterCornerTopLeft;
	                    }
	                    else if (waterAbove && waterRight) {
	                        selectedImage = waterCornerTopRight;
	                    }
	                    else if (waterBelow && waterLeft) {
	                        selectedImage = waterCornerBotLeft;
	                    }
	                    else if (waterBelow && waterRight) {
	                        selectedImage = waterCornerBotRight;
	                    }
	                    // INNER CORNERS next
	                    else if (!waterAbove && !waterLeft && waterTopLeft) {
	                        selectedImage = waterEdgeBotRight;
	                    }
	                    else if (!waterAbove && !waterRight && waterTopRight) {
	                        selectedImage = waterEdgeBotLeft;
	                    }
	                    else if (!waterBelow && !waterLeft && waterBottomLeft) {
	                        selectedImage = waterEdgeTopRight;
	                    }
	                    else if (!waterBelow && !waterRight && waterBottomRight) {
	                        selectedImage = waterEdgeTopLeft;
	                    }
	                    // STRAIGHT EDGES
	                    else if (waterAbove) {
	                        selectedImage = waterEdgeMidBot;
	                    }
	                    else if (waterBelow) {
	                        selectedImage = waterEdgeMidTop;
	                    }
	                    else if (waterLeft) {
	                        selectedImage = waterEdgeMidRight;
	                    }
	                    else if (waterRight) {
	                       selectedImage = waterEdgeMidLeft;
	                    }
	                    // INSIDE LAND
	                    else {
	                        selectedImage = grass;
	                    }
	                }
	                ImageView tile = new ImageView(selectedImage);
	                tile.setFitWidth(TILE_SIZE);
	                tile.setFitHeight(TILE_SIZE);
	                tile.setX(c * TILE_SIZE);
	                tile.setY(r * TILE_SIZE);
	                tile.setSmooth(false);

	                worldLayer.getChildren().add(tile);
	            }      
		 }
	}
	
	public void buildRaisedTerrain()
	{
		Image selectedImage;
		
		for (int r = 30; r < 31; r++)
		{
			for (int c = 27; c < 48; c++) {
				
				if (c == 27) {
					selectedImage = grassEdgeTopLeft;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 47) {
					selectedImage = grassEdgeTopRight;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else {
					selectedImage = grassEdgeTopMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
			}
		}
	
		for (int r = 31; r < 32; r++)
		{
			for (int c = 27; c < 48; c++) {
				
				if (c == 27) 
				{
				 selectedImage = grassEdgeMidLeft;
				 ImageView tile = new ImageView(selectedImage);
	                tile.setFitWidth(TILE_SIZE);
	                tile.setFitHeight(TILE_SIZE);
	                tile.setX(c * TILE_SIZE);
	                tile.setY(r * TILE_SIZE);
	                tile.setSmooth(false);

	                worldLayer.getChildren().add(tile);
				}
				else if (c == 47) 
				{
					 selectedImage = grassEdgeMidRight;
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);

		                worldLayer.getChildren().add(tile);
					}
					
			}
			
		}
		
		for (int r = 32; r < 33; r++)
		{
			for (int c = 27; c < 49; c++) {
				
				if (c == 27) 
				{
				 selectedImage = grassEdgeMidLeft;
				 ImageView tile = new ImageView(selectedImage);
	                tile.setFitWidth(TILE_SIZE);
	                tile.setFitHeight(TILE_SIZE);
	                tile.setX(c * TILE_SIZE);
	                tile.setY(r * TILE_SIZE);
	                tile.setSmooth(false);

	                worldLayer.getChildren().add(tile);
				}
				else if (c == 47) 
				{
					 selectedImage = grassEdgeCornerTopRight;
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);

		                worldLayer.getChildren().add(tile);
				}
				else if (c == 48)
				{
					selectedImage = grassEdgeTopRight;
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);

		                worldLayer.getChildren().add(tile);
				}
					
			}
			
		}
		
		for (int r = 33; r < 34; r++)
		{
			for (int c = 25; c < 49; c++) {
				
				if (c == 25) {
					selectedImage = grassEdgeTopLeft;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 26)
				{
					selectedImage = grassEdgeTopMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 27) 
				{
				 selectedImage = grassEdgeCornerTopLeft;
				 ImageView tile = new ImageView(selectedImage);
	                tile.setFitWidth(TILE_SIZE);
	                tile.setFitHeight(TILE_SIZE);
	                tile.setX(c * TILE_SIZE);
	                tile.setY(r * TILE_SIZE);
	                tile.setSmooth(false);

	                worldLayer.getChildren().add(tile);
				}
				else if (c == 48) 
				{
					 selectedImage = grassEdgeMidRight;
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);

		                worldLayer.getChildren().add(tile);
				}
					
			}
			
		}
		
		for (int r = 34; r < 42; r++) {
			for (int c = 25; c < 49; c++)
			{
				if (c == 25) {
					selectedImage = grassEdgeMidLeft;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 48) {
					selectedImage = grassEdgeMidRight;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
			}
		}
		
		for (int r = 42; r < 43; r++) {
			for (int c = 25; c < 49; c++)
			{
				if (c == 25) {
					selectedImage = grassEdgeMidLeft;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 43) {
					selectedImage = grassEdgeCornerBotRight;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c >= 44 && c < 48)
				{
					selectedImage = grassEdgeBotMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
	
				else if (c == 48) {
					selectedImage = grassEdgeBotRight;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
			}
		}
		
		for (int r = 43; r < 46; r++) {
			for (int c = 25; c < 44; c++)
			{
				if (c == 25) {
					selectedImage = grassEdgeMidLeft;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				if (c == 43)
				{
					selectedImage = grassEdgeMidRight;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				
			}
		}
		
		for (int r = 46; r < 47; r++) {
			for (int c = 25; c < 44; c++)
			{
				if (c == 25)
				{
					selectedImage = grassEdgeMidLeft;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 42) {
					selectedImage = grassEdgeCornerBotRight;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 43) {
					selectedImage = grassEdgeBotRight;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
			}
		}
		
		for (int r = 47; r < 48; r++) {
			for (int c = 25; c < 43; c++)
			{
				if (c == 25)
				{
					selectedImage = grassEdgeBotLeft;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c >= 26 && c < 32) {
					selectedImage = grassEdgeBotMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 32) {
					selectedImage = grassEdgeCornerBotLeft;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 42) {
					selectedImage = grassEdgeMidRight;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
			}
		}
		for (int r = 48; r < 49; r++) {
			for (int c = 32; c < 43; c++)
			{
				if (c == 32) {
					selectedImage = grassEdgeMidLeft;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				if (c == 42) {
					selectedImage = grassEdgeMidRight;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				
				}
				
			}
		}
		for (int r = 49; r < 50; r++) {
			for (int c = 32; c < 43; c++)
			{
				if(c == 32) {
					selectedImage = grassEdgeBotLeft;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if(c > 32 && c < 36) {
					selectedImage = grassEdgeBotMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c >= 39 && c < 42) {
					selectedImage = grassEdgeBotMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 42)
				{
					selectedImage = grassEdgeBotRight;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
			}
		}
	}
	
	public void buildPath() 
	{
		Image selectedImage;
		
		//Raised Terrain paths
		for (int r = 37; r < 45; r++) {
			for (int c = 33; c < 42; c++) {
				selectedImage = pathMid;
				
				 ImageView tile = new ImageView(selectedImage);
	                tile.setFitWidth(TILE_SIZE);
	                tile.setFitHeight(TILE_SIZE);
	                tile.setX(c * TILE_SIZE);
	                tile.setY(r * TILE_SIZE);
	                tile.setSmooth(false);

	                worldLayer.getChildren().add(tile);
			}
		}
		
		for (int r = 36; r < 37; r++) 
		{
			for (int c = 32; c < 43; c++)
			{
				if(c == 32) {
					selectedImage = pathTopLeft;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c > 32 && c < 42)
				{
					selectedImage = pathTopMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 42)
				{
					selectedImage = pathTopRight;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
			}
		}
		for (int r = 37; r < 45; r++) {
			for (int c = 32; c < 43; c++) {
				if (c == 32)
				{
					selectedImage = pathLeftMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 42)
				{
					selectedImage = pathMidRight;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
			}
		}
		for (int r = 45; r < 46; r++) 
		{
			for (int c = 32; c < 43; c++)
			{
				if (c == 32) {
					selectedImage = pathBotLeft;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c > 32 && c < 42)
				{
					selectedImage = pathBotMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 42)
				{
					selectedImage = pathBotRight;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				
			}
		}
		//Path Trails
		for (int r = 50; r < 51; r++)
		{
			for (int c = 36; c < 39; c++)
			{
				if (c == 36) {
					selectedImage = pathTopLeft;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 37)
				{
					selectedImage = pathTopMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 38)
				{
					selectedImage = pathTopRight;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
			}
		}
		
		for (int r = 51; r < 54; r++)
		{
			for (int c = 36; c < 39; c++)
			{
				if (c == 36)
				{
					selectedImage = pathLeftMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 37) {
					selectedImage = pathMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 38)
				{
					selectedImage = pathMidRight;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
			}
		}
		for (int r = 54; r < 55; r++) {
			for (int c = 36; c < 65; c++)
			{
				if (c == 36)
				{
					selectedImage = pathLeftMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 37)
				{
					selectedImage = pathMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 38) {
					selectedImage = pathCornerTop;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c > 38 && c < 63) {
					selectedImage = pathTopMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 63) {
					selectedImage = pathTopRight;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
			}
		}
		
		for (int r = 55; r < 56; r++) {
			for (int c = 36; c < 65; c++)
			{
				if (c == 36)
				{
					selectedImage = pathLeftMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c > 36 && c < 63) {
					selectedImage = pathMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 63) {
					selectedImage = pathMidRight;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
			}
		}
		
		for (int r = 56; r < 57; r++) {
			for (int c = 36; c < 65; c++)
			{
				if (c == 36)
				{
					selectedImage = pathLeftMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 37)
				{
					selectedImage = pathMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 38) {
					selectedImage = pathCornerBot;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c > 38 && c < 63) {
					selectedImage = pathBotMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 63) {
					selectedImage = pathBotRight;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
			}
		}
		for (int r = 57; r < 61; r++) {
			for (int c = 36; c < 39; c++)
			{
				if (c == 36)
				{
					selectedImage = pathLeftMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 37) {
					selectedImage = pathMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 38)
				{
					selectedImage = pathMidRight;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
			}
		}
		for (int r = 61; r < 62; r++) {
			for (int c = 36; c < 39; c++)
			{
				if (c == 36)
				{
					selectedImage = pathBotLeft;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 37) {
					selectedImage = pathBotMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 38)
				{
					selectedImage = pathBotRight;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				
				}
			}
		}
		//Path Crop Land
		for (int r = 51; r < 52; r++) {
			for (int c = 24; c < 33; c++)
			{
				if (c == 24)
				{
					selectedImage = pathTopLeft;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c > 24 && c < 32) {
					selectedImage = pathTopMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 32) {
					selectedImage = pathTopRight;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
			}
		}
		
		for (int r = 52; r < 59; r++) {
			for (int c = 24; c < 33; c++)
			{
				if (c == 24) {
					selectedImage = pathLeftMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 32)
				{
					selectedImage = pathMidRight;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else
				{
					selectedImage = pathMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
			}
		}
		for (int r = 59; r < 60; r++) {
			for (int c = 24; c < 33; c++)
			{
				if (c == 24)
				{
					selectedImage = pathBotLeft;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c > 24 && c < 32) {
					selectedImage = pathBotMid;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
				else if (c == 32) {
					selectedImage = pathBotRight;
					
					 ImageView tile = new ImageView(selectedImage);
		                tile.setFitWidth(TILE_SIZE);
		                tile.setFitHeight(TILE_SIZE);
		                tile.setX(c * TILE_SIZE);
		                tile.setY(r * TILE_SIZE);
		                tile.setSmooth(false);
	
		                worldLayer.getChildren().add(tile);
				}
			}
		}
	}
	
	public void buildBuildings()
	{
		Image selectedImage;
		
		for (int r = 49; r < 50; r++) {
			for (int c = 36; c < 39; c++)
			{
				selectedImage = stairs;
				ImageView tile = new ImageView(selectedImage);
                tile.setFitWidth(TILE_SIZE);
                tile.setFitHeight(TILE_SIZE);
                tile.setX(c * TILE_SIZE);
                tile.setY(r * TILE_SIZE);
                tile.setSmooth(false);

                worldLayer.getChildren().add(tile);
			}
		}
		
		for (int r = 38; r < 39; r++)
		{
			for (int c = 35; c < 36; c++)
			{
				selectedImage = house;
				
				ImageView obj = new ImageView(selectedImage);
				obj.setPreserveRatio(true);
				obj.setFitWidth(selectedImage.getWidth() * 2);
				
				obj.setX(c * TILE_SIZE);
				obj.setY(r * TILE_SIZE - obj.getFitHeight() + TILE_SIZE);
				
				worldLayer.getChildren().add(obj);
			}
		}
		for (int r = 55; r < 56; r++) {
			for (int c = 64; c < 80; c++)
			{
				selectedImage = bridgeSupport;
				
				ImageView obj = new ImageView(selectedImage);
				obj.setPreserveRatio(true);
				obj.setFitWidth(selectedImage.getWidth() * 2);
				
				obj.setX(c * TILE_SIZE);
				obj.setY(r * TILE_SIZE - obj.getFitHeight() + TILE_SIZE);
				
				worldLayer.getChildren().add(obj);
			}
		}
		for (int r = 53; r < 54; r++) {
			for (int c = 64; c < 80; c++)
			{
				selectedImage = horiBridge;
				
				ImageView obj = new ImageView(selectedImage);
				obj.setPreserveRatio(true);
				obj.setFitWidth(selectedImage.getWidth() * 2);
				
				obj.setX(c * TILE_SIZE);
				obj.setY(r * TILE_SIZE - obj.getFitHeight() + TILE_SIZE);
				
				worldLayer.getChildren().add(obj);
			}
		}
		for (int r = 61; r < 80 ; r++) {
			for (int c = 36; c < 37; c++)
			{
				selectedImage = vertBridge;
				
				ImageView obj = new ImageView(selectedImage);
				obj.setPreserveRatio(true);
				obj.setFitWidth(selectedImage.getWidth() * 2);
				
				obj.setX(c * TILE_SIZE);
				obj.setY(r * TILE_SIZE - obj.getFitHeight() + TILE_SIZE);
				
				worldLayer.getChildren().add(obj);
			}
		}
		
		for (int r = 47; r < 48; r++) {
			for (int c = 46; c < 47; c++)
			{
				selectedImage = market;
				
				ImageView obj = new ImageView(selectedImage);
				obj.setPreserveRatio(true);
				obj.setFitWidth(selectedImage.getWidth() * 2);
				
				obj.setX(c * TILE_SIZE);
				obj.setY(r * TILE_SIZE - obj.getFitHeight() + TILE_SIZE);
				
				worldLayer.getChildren().add(obj);
			}
		}
		for (int r = 48; r < 49; r++) {
			for (int c = 50; c < 51; c++)
			{
				selectedImage = miniHay;
				
				ImageView tile = new ImageView(selectedImage);
                tile.setFitWidth(TILE_SIZE);
                tile.setFitHeight(TILE_SIZE);
                tile.setX(c * TILE_SIZE - 5);
                tile.setY(r * TILE_SIZE - 10);
                tile.setSmooth(false);

                worldLayer.getChildren().add(tile);
			}
		}
		
		for (int r = 51; r < 60; r++) {
			for (int c = 23; c < 24; c++)
			{
				if (c == 23) {
					selectedImage = fenceVertMid;
					
					ImageView tile = new ImageView(selectedImage);
	                tile.setFitWidth(TILE_SIZE);
	                tile.setFitHeight(TILE_SIZE);
	                tile.setX(c * TILE_SIZE);
	                tile.setY(r * TILE_SIZE);
	                tile.setSmooth(false);

	                worldLayer.getChildren().add(tile);

				}
			}
		}
		for (int r = 50; r < 51; r++) {
			for (int c = 23; c < 33; c++)
			{
				if(c == 23) {
					selectedImage = fenceTopLeft;
					
					ImageView tile = new ImageView(selectedImage);
	                tile.setFitWidth(TILE_SIZE);
	                tile.setFitHeight(TILE_SIZE);
	                tile.setX(c * TILE_SIZE);
	                tile.setY(r * TILE_SIZE);
	                tile.setSmooth(false);

	                worldLayer.getChildren().add(tile);
				}
				else if (c > 23 && c < 32) {
					selectedImage = fenceMid;
					
					ImageView tile = new ImageView(selectedImage);
	                tile.setFitWidth(TILE_SIZE);
	                tile.setFitHeight(TILE_SIZE);
	                tile.setX(c * TILE_SIZE);
	                tile.setY(r * TILE_SIZE);
	                tile.setSmooth(false);

	                worldLayer.getChildren().add(tile);
				}
				else
				{
					selectedImage = fenceRightEnd;
					
					ImageView tile = new ImageView(selectedImage);
	                tile.setFitWidth(TILE_SIZE);
	                tile.setFitHeight(TILE_SIZE);
	                tile.setX(c * TILE_SIZE);
	                tile.setY(r * TILE_SIZE);
	                tile.setSmooth(false);

	                worldLayer.getChildren().add(tile);
				}
			}
			
		}
		
		for (int r = 60; r < 61; r++) {
			for (int c = 23; c < 24; c++)
			{
			
				selectedImage = fenceBotEnd;
				
				ImageView tile = new ImageView(selectedImage);
                tile.setFitWidth(TILE_SIZE);
                tile.setFitHeight(TILE_SIZE);
                tile.setX(c * TILE_SIZE);
                tile.setY(r * TILE_SIZE);
                tile.setSmooth(false);

                worldLayer.getChildren().add(tile);
			}
		}
	}
	
	//plot interaction;
	public void tryPlot() {
		for (int r = 0; r < plots.length; r++) {
			for (int c = 0; c < plots[r].length; c++) {
				Plot plot = plots[r][c];
				
				if (player.getSprite().getBoundsInParent().intersects(plot.getBounds())) {
					if (!plot.isOccupied() && playerInventory.getSelectedSeed() != null) {
						plot.plant(playerInventory.getSelectedSeed());
						playerInventory.useSelectedSeed();
						
						System.out.println("Planted " + playerInventory.getSelectedSeed());
						return;
					}
				}
			}
		}
	}
	
	public Pane getView()
	{
		return worldLayer;
	}
}
