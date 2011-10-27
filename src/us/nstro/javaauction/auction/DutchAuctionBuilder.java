package us.nstro.javaauction.auction;

import us.nstro.javaauction.bids.Price;
import us.nstro.javaauction.handler.Ticker;

/**
 *
 * @author bbecker
 */
public class DutchAuctionBuilder extends AuctionBuilder {

    private Price initial, decrement, lowest;
    private Ticker ticker;

    public DutchAuctionBuilder setTicker(Ticker timer) {
        this.ticker = timer;
        return this;
    }

    public DutchAuctionBuilder setTicker(AuctionManager manager, long delay) {
        this.ticker = manager.createTicker(delay);
        return this;
    }

    public DutchAuctionBuilder setInitialPrice(Price initial) {
        this.initial = initial;
        return this;
    }

    public DutchAuctionBuilder setDecrement(Price decrement) {
        this.decrement = decrement;
        return this;
    }

    public DutchAuctionBuilder setLowestPrice(Price lowest) {
        this.lowest = lowest;
        return this;
    }

    public Auction build(Integer auctionID) {
        return new DutchAuction(auctionID, this.createAuctionInfo(), this.ticker, this.initial, this.decrement, this.lowest);
    }

}
