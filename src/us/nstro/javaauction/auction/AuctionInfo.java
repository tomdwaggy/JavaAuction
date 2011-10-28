/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package us.nstro.javaauction.auction;

import java.util.Collection;
import java.util.Date;
import us.nstro.javaauction.bids.Item;

/**
 * AuctionInfo represents the "static" information that all auctions will
 * have, such as the auction identifier, name, auctioneer who is trying to
 * sell the product, and the product in question.
 *
 * @author bbecker
 */
public class AuctionInfo {

    private String name, description;
    private User auctioneer;
    private Collection<Item> products;
    private Date ends;

    /**
     * Create a new Auction Information object.
     */
    public AuctionInfo(String name, String description, User auctioneer, Collection<Item> products, Date ends) {
        this.name = name;
        this.description = description;
        this.auctioneer = auctioneer;
        this.products = products;
        this.ends = ends;
    }

    /**
     * Get the auction name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the auction description.
     */
    public String getDescription() {
        return this.description;
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

    /**
     * Get the ending date.
     */
    public Date getEndDate() {
        return this.ends;
    }

}
