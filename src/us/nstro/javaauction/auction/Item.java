/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package us.nstro.javaauction.auction;

import java.util.UUID;

/**
 * The Item class represents a single item which is to be sold in an auction.
 * This is generally unique but does not have to be, multiple auctions may
 * sell copies of the same item.
 *
 * @author bbecker
 */
public class Item {

    private UUID itemID;
    private String itemName;

    /**
     * Create a new Item.
     */
    public Item(UUID itemID, String itemName) {
        this.itemID = itemID;
        this.itemName = itemName;
    }

    /**
     * Get the item id.
     */
    public String getID() {
        return this.itemName;
    }

    /**
     * Get the item name.
     */
    public String getName() {
        return this.itemName;
    }
    
}