package us.nstro.javaauction.auction;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import us.nstro.javaauction.bids.Item;
import us.nstro.javaauction.bids.Price;
import us.nstro.javaauction.handler.Ticker;

/**
 *
 * @author bbecker
 */
public class AuctionBuilder {

    private String name;
    private User auctioneer;
    private Collection<Item> products;

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

    private AuctionInfo createAuctionInfo() {
        UUID auctionID = UUID.randomUUID();
        return new AuctionInfo(auctionID, this.name, this.auctioneer, this.products);
    }

    public final AscendingAuction createAscendingAuction(Price minimumBid) {
        return new AscendingAuction(this.createAuctionInfo(), minimumBid);
    }

    public final DutchAuction createDutchAuction(Ticker timer, Price startingBid, Price decrement, Price lowest) {
        return new DutchAuction(this.createAuctionInfo(), timer, startingBid, decrement, lowest);
    }

    public final SealedFirstBidAuction createSealedFirstBidAuction(Price minimumBid) {
        return new SealedFirstBidAuction(this.createAuctionInfo(), minimumBid);
    }
    
}
