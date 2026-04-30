/************************************************************/
/* Author: Mackenzie Mann, Arianna Childs                  */
/* Major: Computer Science                                 */
/* Creation Date: April 13th, 2026                         */
/* Due Date: April 27th, 2026                              */
/* Course: CS211-01                                        */
/* VERSION 3                                              */
/* Professor Name: Professor Shimkanon                     */
/* Assignment: Final Project                               */
/* Filename: Crop.java                                     */
/* Purpose: This class manages crop growth stages and      */
/*          harvest readiness. Initial class concept       */
/*          provided by Mackenzie Mann and expanded and    */
/*          integrated into final gameplay implementation  */
/*          by Arianna Childs.                             */
/************************************************************/
package application;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image; 
import javafx.scene.image.ImageView;

public class Crop {
	// Fields 
	private String cropType;
    private int growthStage; 
	private double timeSinceLastStage;
	private double timePerStage;
	
	private Image growth;
	private ImageView cropView;
	
	
	
	
	// Constructor
	/**
	 * Constructs a crop object, initializes its type,
	 * position, and growth sprite.
	 *
	 * @param cropType type of crop being created
	 * @param x x-coordinate of the crop
	 * @param y y-coordinate of the crop
	 */
	public Crop(String cropType, double x, double y)
	{
		this.cropType = cropType;
		this.growthStage = 0;
		this.timePerStage = 10.0;
		this.timeSinceLastStage = 0;
		
		loadCropImage();
		
		cropView = new ImageView(growth);
		
		double offsetX = 0;
		double offsetY = 0;
		switch(cropType) {
			case "carrot":
				offsetX = 7;
				offsetY = -5;
				break;
			case "corn":
				offsetX = 10;
				offsetY = -35;
				break;
			case "strawberry":
				offsetX = 7;
				offsetY = 15;
				break;
			case "tomato":
				offsetX = 7;
				offsetY = -5;
				break;
		}
		
		cropView.setY(y + offsetY);
		cropView.setX(x + offsetX);
		
		updateViewport();
	}
	
	/**
	 * Loads the sprite sheet associated with the crop type.
	 */
	public void loadCropImage() {
		switch(cropType) {
			case "carrot":
				growth = new Image(getClass().getResource("/assets/tiles/crops/carrot_growth.png").toExternalForm());
				break;
			case "corn":
				growth = new Image(getClass().getResource("/assets/tiles/crops/corn_growth.png").toExternalForm());
				break;
			case "strawberry":
				growth = new Image(getClass().getResource("/assets/tiles/crops/strawberry_growth.png").toExternalForm());
				break;
			case "tomato":
				growth = new Image(getClass().getResource("/assets/tiles/crops/tomato_growth.png").toExternalForm());
				break;
		}
	}
	
	/**
	 * Updates the crop sprite viewport to reflect
	 * the current growth stage.
	 */
	public void updateViewport() {
		double frameWidth = growth.getWidth() / 4.0;
		double frameHeight = growth.getHeight();
		
		cropView.setViewport(new Rectangle2D(
				growthStage  * frameWidth,
				0,
				frameWidth,
				frameHeight));
	}
	
	/**
	 * Updates crop growth based on elapsed time.
	 *
	 * @param time elapsed time since the last update
	 */
	public void update(double time)
	{
		if (growthStage >= 3) {
			return;
		}
		
		timeSinceLastStage += time;
		
		if(timeSinceLastStage >= timePerStage) {
			growthStage++;
			timeSinceLastStage = 0;
			updateViewport();
		}
	
	}
	
	/**
	 * Checks whether the crop has fully grown and can be harvested.
	 *
	 * @return true if the crop is harvestable, false otherwise
	 */
	public boolean isHarvestable()
	{
		return growthStage >= 3;
	}
	
	// getters 
	/**
	 * Returns the crop type.
	 *
	 * @return crop type name
	 */
	public String getCropType() {
		return cropType;
	}
	/**
	 * Returns the crop image view.
	 *
	 * @return crop image view
	 */
	public ImageView getView() {
		return cropView;
	}
	
}
