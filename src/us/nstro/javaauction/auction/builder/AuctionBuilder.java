/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package us.nstro.javaauction.auction.builder;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import us.nstro.javaauction.auction.AuctionInfo;
import us.nstro.javaauction.auction.User;
import us.nstro.javaauction.bids.Item;

/**
 *
 * @author bbecker
 */
public class AuctionBuilder {

    private UUID auctionID;
    private String name;
    private User auctioneer;
    private Collection<Item> products;

    public final void setAuctionID(UUID auctionId) {
        this.auctionID = auctionId;
    }

    public final void setAuctionName(String name) {
        this.name = name;
    }

    public final void setAuctioneer(User auctioneer) {
        this.auctioneer = auctioneer;
    }

    public final void setProduct(Item product) {
        this.products = Collections.singleton(product);
    }

    public final AuctionInfo createAuctionInfo() {
        return new AuctionInfo(this.auctionID, this.name, this.auctioneer, this.products);
    }

}
