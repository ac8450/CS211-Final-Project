/************************************************************/
/* Author: Arianna Childs								    */
/* Major: Computer Science 									*/
/* Creation Date: April 1st, 2026						 	*/
/* Due Date: April 24th, 2-26							  	*/
/* Course: CS211-01											*/ 
/* VERSION 2												*/
/* Filename: InteractionManager.java 						*/
/* Purpose: This program will control all player			*/
/* 			interactions between farming plots and the shop.*/
/* 			This program will also control world barriers	*/
/* 			and where the player can and cannot walk on		*/
/************************************************************/

package application;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class InteractionManager {

	private Rectangle shopZone;
	
	public InteractionManager() {
		buildShopZone();
		
	}
	
	public void buildShopZone() {
		shopZone = new Rectangle(500,500,100,100);
		shopZone.setFill(Color.TRANSPARENT);
		shopZone.setX(2975);
		shopZone.setY(3350);
	}
	
	public boolean isNearShop(Player player) {
		return player.getSprite().getBoundsInParent().intersects(shopZone.getBoundsInParent());
	}
	 
	
	public Rectangle getShopZone() {
		return shopZone;
	}
}
