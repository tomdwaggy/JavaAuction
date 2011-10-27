/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package us.nstro.javaauction.bids;

import java.util.Random;

/**
 * The Item class represents a single item which is to be sold in an auction.
 * This is generally unique but does not have to be, multiple auctions may
 * sell copies of the same item.
 *
 * @author bbecker
 */
public class Item {

    private Integer itemID;
    private String itemName;

    /**
     * Create a new Item.
     */
    public Item(Integer itemID, String itemName) {
        this.itemID = itemID;
        this.itemName = itemName;
    }

    /**
     * Creates a new item with the given item name.
     *
     * @param name
     * @return
     */
    public static Item createItem(String itemName) {
        Integer itemID = new Random().nextInt();
        return new Item(itemID, itemName);
    }

    /**
     * Get the item id.
     */
    public Integer getID() {
        return this.itemID;
    }

    /**
     * Get the item name.
     */
    public String getName() {
        return this.itemName;
    }
    
}
