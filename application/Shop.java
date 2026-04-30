/************************************************************/
/* Author: Halden Kerzner, Arianna Childs                  */
/* Major: Mathematics / Computer Science                   */
/* Creation Date: April 13th, 2026                         */
/* Due Date: April 27th, 2026                              */
/* Course: CS211-01                                        */
/* VERSION 3                                               */
/* Professor Name: Professor Shimkanon                     */
/* Assignment: Final Project                               */
/* Filename: Shop.java                                     */
/* Purpose: This class manages purchasing, selling, and    */
/*          crop pricing. Initial class concept provided   */
/*          by Halden Kerzner and expanded and integrated  */
/*          into final gameplay implementation by          */
/*          Arianna Childs.                                */
/************************************************************/

package application;

public class Shop {
	private String selectedCrop;
	private int selectedPrice;

	
	/**
	 * Sets the currently selected crop and its purchase price.
	 *
	 * @param crop selected crop type
	 * @param price price of the selected crop
	 */
	public void setSelection(String crop, int price) {
		selectedCrop = crop;
		selectedPrice = price;
	}
	
	/**
	 * Attempts to purchase the currently selected crop.
	 *
	 * @param inventory player inventory used to remove coins and add seeds
	 * @return true if the purchase was successful, false otherwise
	 */
	public boolean buySelected(Inventory inventory) {
		if (selectedCrop == null) 
			{
				return false;
			}
		if (inventory.removeCoins(selectedPrice)) {
			inventory.addSeeds(selectedCrop, 3);
			return true;
		}
		return false;
	}
	
	/**
	 * Attempts to purchase and unlock the second farming plot.
	 *
	 * @param inventory player inventory used to remove coins
	 * @return true if the plot purchase was successful, false otherwise
	 */
	public boolean buyPlot(Inventory inventory) {
		if (inventory.removeCoins(250)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Sells all harvested crops currently in the player's inventory.
	 *
	 * @param inventory player inventory containing crops to sell
	 * @return total amount of coins earned from the sale
	 */
	public int sellInventory(Inventory inventory) {
		return inventory.sellAllCrops();
	}
	
	/**
	 * Returns the currently selected crop.
	 *
	 * @return selected crop name
	 */
	public String getSelectedCrop() {
		return selectedCrop;
	}
	
	/**
	 * Returns the purchase price of the selected crop.
	 *
	 * @return selected crop price
	 */
	public int getSelectedPrice() {
		return selectedPrice;
	}
}
