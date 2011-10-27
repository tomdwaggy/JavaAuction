/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package us.nstro.javaauction.auction;

import java.util.Collection;
import us.nstro.javaauction.bids.Item;

/**
 * AuctionInfo represents the "static" information that all auctions will
 * have, such as the auction identifier, name, auctioneer who is trying to
 * sell the product, and the product in question.
 *
 * @author bbecker
 */
public class AuctionInfo {

    private String name;
    private User auctioneer;
    private Collection<Item> products;

    /**
     * Create a new Auction Information object.
     */
    public AuctionInfo(String name, User auctioneer, Collection<Item> products) {
        this.name = name;
        this.auctioneer = auctioneer;
        this.products = products;
    }

    /**
     * Get the auction name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the auctioneer who posted this auction.
     */
    public User getAuctioneer() {
        return this.auctioneer;
    }

    /**
     * Get the product which is being sold in this auction.
     */
    public Collection<Item> getProducts() {
        return this.products;
    }

}
