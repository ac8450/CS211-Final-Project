/************************************************************/
/* Author: Mackenzie Mann, Arianna Childs                  */
/* Major: Computer Science                                 */
/* Creation Date: April 13th, 2026                         */
/* Due Date: April 27th, 2026                              */
/* Course: CS211-01                                        */
/* VERSION 3                                               */
/* Professor Name: Professor Shimkanon                     */
/* Assignment: Final Project                               */
/* Filename: Plot.java                                     */
/* Purpose: This class manages plot occupancy, planting,   */
/*          harvesting, and crop interactions. Initial     */
/*          class concept provided by Mackenzie Mann and   */
/*          expanded and integrated into final gameplay    */
/*          implementation by Arianna Childs.              */
/************************************************************/

package application;

import javafx.scene.image.ImageView;  
import javafx.scene.image.Image;

import javafx.geometry.Bounds;

public class Plot {
	private boolean occupied;
	private ImageView plotView;
	private Image emptyPlot;
	private Image plantedPlot;
	
	private Crop crop;
	
	/**
	 * Constructs a plot with empty and planted images at a set position.
	 *
	 * @param emptyPlot image used when the plot is empty
	 * @param plantedPlot image used when the plot is planted
	 * @param x x-coordinate of the plot
	 * @param y y-coordinate of the plot
	 */
	public Plot(Image emptyPlot, Image plantedPlot, double x, double y)
	{
		this.emptyPlot = emptyPlot;
		this.plantedPlot = plantedPlot;
		
		occupied = false;
		
		plotView = new ImageView(emptyPlot);
		plotView.setX(x);
		plotView.setY(y);
		plotView.setFitHeight(64);
		plotView.setFitWidth(64);
			
	}
	
	/**
	 * Updates the plot image based on whether the plot is occupied.
	 */
	public void updateImage() {
		if (occupied) {
			plotView.setImage(plantedPlot);
		}
		else {
			plotView.setImage(emptyPlot);
		}
	}
	
	/**
	 * Attempts to plant the selected seed from the player's inventory.
	 *
	 * @param inventory player inventory used to get and remove seeds
	 * @return true if planting was successful, false otherwise
	 */
	public boolean plant(Inventory inventory) {
		if (occupied) {
			return false;
		}
		
		String selectedSeed = inventory.getSelectedSeed();
		
		if(selectedSeed == null) {
			return false;
		}
		
		if (inventory.useSelectedSeed()) {
			occupied = true;
			updateImage();
			String cropType = selectedSeed.replace(" seeds", "");
			crop = new Crop(cropType, plotView.getX(), plotView.getY());
			return true;
		}
		
		return false;
	}
	
	/**
	 * Clears the plot and removes its current crop.
	 */
	public void clearPlot() {
		occupied = false;
		crop = null;
		updateImage();
	}
	
	/**
	 * Checks whether the plot currently has a planted crop.
	 *
	 * @return true if the plot is occupied, false otherwise
	 */
	public boolean isOccupied() {
		return occupied;
	}
	
	//Crop stuff
	/**
	 * Returns the crop currently planted in the plot.
	 *
	 * @return current crop object
	 */
	public Crop getCrop() {
		return crop;
	}
	
	/**
	 * Updates the crop growing in the plot.
	 *
	 * @param time elapsed time since the last update
	 */
	public void updateCrop(double time) {
		if(crop != null) {
			crop.update(time);
		}
	}
	
	/**
	 * Checks whether the plot currently has a crop.
	 *
	 * @return true if a crop exists, false otherwise
	 */
	public boolean hasCrop() {
		return crop != null;
	}
	
	/**
	 * Attempts to harvest the crop in the plot.
	 *
	 * @param inventory player inventory used to store harvested crops
	 * @return true if the crop was harvested, false otherwise
	 */
	public boolean canHarvest(Inventory inventory) {
		
		
		if (crop !=  null && crop.isHarvestable()) {
			String harvestedCrop = crop.getCropType();
			clearPlot();
			inventory.addCrops(harvestedCrop, 1);
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the plot's bounds for collision and interaction checks.
	 *
	 * @return plot bounds
	 */
	public Bounds getBounds() {
		return plotView.getBoundsInParent();
	}
	
	/**
	 * Returns the plot image view.
	 *
	 * @return plot image view
	 */
	public ImageView getView() {
		return plotView;
	}
}
