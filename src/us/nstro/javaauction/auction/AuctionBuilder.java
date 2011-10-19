package us.nstro.javaauction.auction;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import us.nstro.javaauction.bids.Item;
import us.nstro.javaauction.bids.Price;
import us.nstro.javaauction.timer.AuctionTimer;

/**
 *
 * @author bbecker
 */
public class AuctionBuilder {

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

    public final AbstractAuction createAscendingAuction(Price minimumBid) {
        AuctionInfo info = new AuctionInfo(this.auctionID, this.name, this.auctioneer, this.products);
        return new AscendingAuction(info, minimumBid);
    }

    public final AbstractAuction createDutchAuction(AuctionTimer timer, Price startingBid, Price decrement, Price lowest) {
        AuctionInfo info = new AuctionInfo(this.auctionID, this.name, this.auctioneer, this.products);
        return new DutchAuction(info, timer, startingBid, decrement, lowest);
    }

    public final AbstractAuction createSealedFirstBidAuction(Price minimumBid) {
        AuctionInfo info = new AuctionInfo(this.auctionID, this.name, this.auctioneer, this.products);
        return new SealedFirstBidAuction(info, minimumBid);
    }
    
}
