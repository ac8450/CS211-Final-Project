/************************************************************/
/* Author: Arianna Childs								    */
/* Major: Computer Science 									*/
/* Creation Date: April 1st, 2026						 	*/
/* Due Date: April 27th, 2026							  	*/
/* Course: CS211-01											*/ 
/* VERSION 3												*/
/* Filename: InteractionManager.java 						*/
/* Purpose: This program will control all player			*
/* 			interactions between farming plots and the shop.*/
/* 			This program will also control world barriers	*/
/* 			and where the player can and cannot walk on		*/
/************************************************************/

package application;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class InteractionManager {

	private Rectangle shopZone;
	private Rectangle plotBuyZone;
	
	
	/**
	 * Constructs the interaction manager and initializes
	 * player interaction zones.
	 */
	public InteractionManager() {
		buildShopZone();
		buildPlotBuyZone();
	}
	
	/**
	 * Builds the collision zone used to detect when the
	 * player is near the shop.
	 */
	public void buildShopZone() {
		shopZone = new Rectangle(500,500,100,100);
		shopZone.setFill(Color.BLACK);
		shopZone.setOpacity(0);
		shopZone.setX(2975);
		shopZone.setY(3350);
	}
	
	/**
	 * Builds the interaction zone used for purchasing
	 * the second farming plot.
	 */
	public void buildPlotBuyZone() {
		plotBuyZone = new Rectangle(500,500,100,100);
		plotBuyZone.setFill(Color.BLACK);
		plotBuyZone.setOpacity(0);
		plotBuyZone.setX(1675);
		plotBuyZone.setY(3375);
	}
	
	/**
	 * Checks whether the player is within the shop
	 * interaction zone.
	 *
	 * @param player player being checked
	 * @return true if the player is near the shop zone,
	 * false otherwise
	 */
	public boolean isNearShop(Player player) {
		return player.getSprite().getBoundsInParent().intersects(shopZone.getBoundsInParent());
	}
	
	/**
	 * Checks whether the player is within the plot
	 * purchase interaction zone.
	 *
	 * @param player player being checked
	 * @return true if the player is near the plot buy zone,
	 * false otherwise
	 */
	public boolean isNearPlotShopZone(Player player) {
		return player.getSprite().getBoundsInParent().intersects(plotBuyZone.getBoundsInParent());
	}
	
	/**
	 * Returns the shop interaction zone rectangle.
	 *
	 * @return shop zone rectangle
	 */
	public Rectangle getShopZone() {
		return shopZone;
	}

	/**
	 * Returns the plot purchase interaction zone rectangle.
	 *
	 * @return plot buy zone rectangle
	 */
	public Rectangle getPlotBuyZone() {
		return plotBuyZone;
	}
	
	/**
	 * Disables the plot purchase interaction zone after
	 * the second plot has been purchased.
	 */
	public void disablePlotBuyZone() {
		plotBuyZone.setX(-1000);
		plotBuyZone.setY(-1000);
	}
	
}
