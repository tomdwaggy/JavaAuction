package us.nstro.javaauction.auction;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import us.nstro.javaauction.auction.strategy.AuctionStrategy;
import us.nstro.javaauction.bids.Item;

/**
 *
 * @author bbecker
 */
public class AuctionBuilder {

    private AuctionStrategy strategy;

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

    public final void setProducts(Collection<Item> products) {
        this.products = products;
    }

    public final void setStrategy(AuctionStrategy strategy) {
        this.strategy = strategy;
    }

    public final Auction createAuction() {
        AuctionInfo info = new AuctionInfo(this.auctionID, this.name, this.auctioneer, this.products);
        return new Auction(info, this.strategy);
    }
    
}
