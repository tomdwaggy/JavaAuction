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

    public final AuctionBuilder setAuctionID(UUID auctionId) {
        this.auctionID = auctionId;
        return this;
    }

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

    public final AuctionBuilder setStrategy(AuctionStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    public final Auction createAuction() {
        AuctionInfo info = new AuctionInfo(this.auctionID, this.name, this.auctioneer, this.products);
        return new Auction(info, this.strategy);
    }
    
}
