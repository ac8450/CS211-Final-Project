/************************************************************/
/* Author: Halden Kerzner, Arianna Childs                  */
/* Major: Mathematics / Computer Science                   */
/* Creation Date: April 13th, 2026                         */
/* Due Date: April 27th, 2026                              */
/* Course: CS211-01                                        */
/* VERSION 3                                              */
/* Professor Name: Professor Shimkanon                     */
/* Assignment: Final Project                               */
/* Filename: Inventory.java                                */
/* Purpose: This class manages player seeds, crops,        */
/*          hotbar data, and selling mechanics. Initial    */
/*          class concept provided by Halden Kerzner and   */
/*          expanded and integrated into final gameplay    */
/*          implementation by Arianna Childs.              */
/************************************************************/

package application;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Random;

public class Inventory {
	//player inventory: crop assigned to a number of seeds given to the player when purchased
	private final Map<String, Integer> seedInventory;
	private final Map<String, Integer> cropInventory;
	private String selectedSeed;
	private int coins;
	private String selectedCropIcon;
	
	//hotbar
	private String[] hotbar;
	private int selectedSlot;
	
	/**
	 * Constructs the player inventory and initializes seeds,
	 * crops, coins, and hotbar storage.
	 */
	public Inventory(){
		seedInventory = new HashMap<>();
		cropInventory = new HashMap<>();
		selectedSeed = null;
		selectedCropIcon = null;
		coins = 20;
		selectedSlot = 0;
		hotbar = new String[4];
	}
	
	/**
	 * Returns the player's current coin amount.
	 *
	 * @return current number of coins
	 */
	public int getCoins() {
		return coins;
	}
	
	/**
	 * Adds seeds of a crop type to the inventory.
	 *
	 * @param crop crop seed type
	 * @param amount number of seeds to add
	 */
	public void addSeeds(String crop, int amount) {
		if (crop == null || amount <= 0) {
			return;
		}
								//checks if crop exists. if so, add 3 to its initial amount
		seedInventory.put(crop, seedInventory.getOrDefault(crop, 0) + amount);
		
		if (!isInHotbar(crop)) {
			addToHotbar(crop);
		}
		
		if (selectedSeed == null) {
			selectedSeed = crop;
		}
	}
	
	/**
	 * Adds harvested crops to the inventory.
	 *
	 * @param crop crop type to add
	 * @param amount number of crops to add
	 */
	public void addCrops(String crop, int amount) {
		if (crop == null || amount <= 0) {
			return;
		}
		
		cropInventory.put(crop, cropInventory.getOrDefault(crop, 0) + amount);
		
		if (!isInHotbar(crop)) {
			addToHotbar(crop);
		}
		
		if (selectedCropIcon == null) {
			selectedCropIcon = crop;
		}	
	}
	
	/**
	 * Checks whether a crop already exists in the hotbar.
	 *
	 * @param crop crop type being checked
	 * @return true if the crop is already in the hotbar,
	 * false otherwise
	 */
	public boolean isInHotbar(String crop) {
		for (String slot : hotbar) {
			if(crop.equals(slot)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds a crop or seed type to the next available hotbar slot.
	 *
	 * @param crop crop type to add
	 */
	public void addToHotbar(String crop) {
		for(int i = 0; i < hotbar.length; i++) {
			if(hotbar[i] == null) {
				hotbar[i] = crop;
				return;
			}
		}
	}
	
	/**
	 * Removes a crop or seed type from the hotbar.
	 *
	 * @param crop crop type to remove
	 */
	public void removeFromHotbar(String crop) {
		for (int i = 0; i < hotbar.length; i++) {
			if (crop.equals(hotbar[i])) {
				hotbar[i] = null;
				return;
			}
		}
	}
	
	/**
	 * Removes coins from the player's inventory if enough
	 * coins are available.
	 *
	 * @param amount number of coins to remove
	 * @return true if coins were removed, false otherwise
	 */
	public boolean removeCoins(int amount) {
		if (amount <=0) {
			return false;
		}
		if (coins >= amount) {
			coins -= amount;
			return true;
		}
		return false;
	}
	
	/**
	 * Adds coins to the player's inventory.
	 *
	 * @param amount number of coins to add
	 */
	public void addCoins(int amount) {
		if(amount > 0) {
			coins += amount;
		}
	}
	
	/**
	 * Uses one unit of the currently selected seed.
	 *
	 * @return true if a seed was used, false otherwise
	 */
	public boolean useSelectedSeed() {
		//if seed does not exist, don't use seed
		if (selectedSeed == null) {
			return false;
		}
		//if crop exist -> count = values
		//if crop does not exist -> count = 0;
		int count = seedInventory.getOrDefault(selectedSeed, 0);
		//if count is less than or equal to 0. don't use crop
		if (count <= 0) {
			return false;
		}
		
		//if count is greater than 0 -> remove seed from the inventory
		//if count is zero, remove crop from the inventory
		//else, put seed back into inventory with one less amount
		count--;
		if (count == 0) {
			seedInventory.remove(selectedSeed);
			removeFromHotbar(selectedSeed);
			selectedSeed = hotbar[selectedSlot];
		}
		else {
			seedInventory.put(selectedSeed, count);
		}
		
		return true;
	}

	/**
	 * Selects an active hotbar slot.
	 *
	 * @param index hotbar slot index
	 */
	public void selectSlot(int index) {
		if (index < 0 || index >= hotbar.length) {
			return;
		}
		selectedSlot = index;
	    selectedSeed = hotbar[index];
	    selectedCropIcon = hotbar[index];
	}
	
	/**
	 * Returns the crop or seed assigned to a hotbar slot.
	 *
	 * @param index hotbar slot index
	 * @return crop in the selected slot
	 */
	public String getSlotCrop(int index) {
		if (index < 0 || index >= hotbar.length) {
			return null;
		}
		return hotbar[index];
	}
	
	/**
	 * Returns the currently selected hotbar slot.
	 *
	 * @return selected slot index
	 */
	public int getSelectedSlot() {
		return selectedSlot;
	}
	
	/**
	 * Sets the currently selected seed if available.
	 *
	 * @param crop seed type to select
	 * @return true if the seed was selected, false otherwise
	 */
	public boolean setSelectedSeed(String crop) {
		if (crop == null) {
			return false;
		}
		
		int count = seedInventory.getOrDefault(crop, 0);
		
		if (count > 0) {
			selectedSeed = crop;
			return true;
		}
		return false;	
	}
	
	/**
	 * Returns the currently selected seed.
	 *
	 * @return selected seed type
	 */
	public String getSelectedSeed() {
		return selectedSeed;
	}
	
	/**
	 * Sets the currently selected crop icon if available.
	 *
	 * @param crop crop type to select
	 * @return true if the crop icon was selected, false otherwise
	 */
	public boolean setSelectedCropIcon(String crop) {
		if (crop == null) {
			return false;
		}
		
		int count = cropInventory.getOrDefault(crop, 0);
		
		if (count > 0) {
			selectedCropIcon = crop;
			return true;
		}
		return false;	
	}
	
	/**
	 * Returns the currently selected crop icon.
	 *
	 * @return selected crop icon type
	 */
	public String getSelectedCropIcon() {
		return selectedCropIcon;
	}
	
	/**
	 * Returns the quantity of a selected seed in inventory.
	 *
	 * @param crop seed type being checked
	 * @return quantity of that seed
	 */
	public int getSelectedSeedCount(String crop) {
		if (selectedSeed == null) {
			return 0;
		}
		return seedInventory.getOrDefault(crop, 0);
	}
	
	/**
	 * Returns the quantity of a harvested crop in inventory.
	 *
	 * @param crop crop type being checked
	 * @return quantity of that crop
	 */
	public int getSelectedCropCount(String crop) {
		if (selectedCropIcon == null) {
			return 0;
		}
		return cropInventory.getOrDefault(crop, 0);
	}
	
	/**
	 * Checks whether the inventory contains a seed type.
	 *
	 * @param crop seed type being checked
	 * @return true if seeds exist, false otherwise
	 */
	public boolean hasSeeds(String crop) {
		return seedInventory.getOrDefault(crop, 0) > 0;
	}
	
	/**
	 * Checks whether the inventory contains a harvested crop.
	 *
	 * @param crop crop type being checked
	 * @return true if crops exist, false otherwise
	 */
	public boolean hasCrops(String crop) {
		return cropInventory.getOrDefault(crop, 0) > 0;
	}
	
	/**
	 * Checks whether the inventory contains any seeds.
	 *
	 * @return true if any seeds exist, false otherwise
	 */
	public boolean hasAnySeeds() {
		return !seedInventory.isEmpty();
	}
	
	/**
	 * Checks whether the inventory contains any harvested crops.
	 *
	 * @return true if any crops exist, false otherwise
	 */
	public boolean hasAnyCrops() {
		return !cropInventory.isEmpty();
	}
	
	//selling
	/**
	 * Sells all harvested crops in the inventory and adds earned coins.
	 *
	 * @return total coins earned from the sale
	 */
	public int sellAllCrops() {
		int totalEarned = 0;
		
		Random random = new Random();
		int randPrice = 0;

		ArrayList<String> crops = new ArrayList<>(cropInventory.keySet());
	    for (String crop : crops){
	        int amount = cropInventory.getOrDefault(crop, 0);

	        int pricePerCrop = 0;
	        switch (crop) {
	            case "carrot":
	                pricePerCrop = 5;
	                randPrice = random.nextInt(((pricePerCrop + 3) - pricePerCrop) + 1) + pricePerCrop;
	                break;
	            case "corn":
	                pricePerCrop = 7;
	                randPrice = random.nextInt(((pricePerCrop + 3) - pricePerCrop) + 1) + pricePerCrop;
	                break;
	            case "strawberry":
	                pricePerCrop = 2;
	                randPrice = random.nextInt(((pricePerCrop + 3) - pricePerCrop) + 1) + pricePerCrop;
	                break;
	            case "tomato":
	                pricePerCrop = 5;
	                randPrice = random.nextInt(((pricePerCrop + 3) - pricePerCrop) + 1) + pricePerCrop;
	                break;
	        }

	        totalEarned += amount * randPrice;

	        cropInventory.remove(crop);
	        removeFromHotbar(crop);
	    }

	    addCoins(totalEarned);

	    if (selectedCropIcon != null && !hasCrops(selectedCropIcon)) {
	        selectedCropIcon = null;
	    }

	    return totalEarned;

	}
	
	/**
	 * Returns the seed inventory map.
	 *
	 * @return map containing seed quantities
	 */
	public Map<String, Integer> getSeedInventory(){
		return seedInventory;
	}
	
	/**
	 * Returns the harvested crop inventory map.
	 *
	 * @return map containing crop quantities
	 */
	public Map<String, Integer> getCropInventory(){
		return cropInventory;
	}
	
}