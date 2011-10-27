/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package us.nstro.javaauction.auction;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import us.nstro.javaauction.bids.Item;

/**
 *
 * @author bbecker
 */
public abstract class AuctionBuilder {

    private String name;
    private User auctioneer;
    private Collection<Item> products;
    private Date ends;

    public final AuctionBuilder setAuctionName(String name) {
        this.name = name;
        return this;
    }

    public final AuctionBuilder setAuctioneer(User auctioneer) {
        this.auctioneer = auctioneer;
        return this;
    }

    public final AuctionBuilder setProduct(Item product) {
        this.products = Collections.singleton(product);
        return this;
    }

    public final AuctionBuilder setProducts(Collection<Item> products) {
        this.products = products;
        return this;
    }

    public final AuctionBuilder setEndDate(Date ends) {
        this.ends = ends;
        return this;
    }

    protected AuctionInfo createAuctionInfo() {
        return new AuctionInfo(this.name, this.auctioneer, this.products, this.ends);
    }

    public abstract Auction build(Integer auctionID);

}
