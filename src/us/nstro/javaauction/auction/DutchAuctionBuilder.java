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

    public DutchAuctionBuilder(Ticker timer, Price initial, Price decrement, Price lowest) {
        this.ticker = timer;
        this.initial = initial;
        this.decrement = decrement;
        this.lowest = lowest;
    }

    public Auction build(Integer auctionID) {
        return new DutchAuction(auctionID, this.createAuctionInfo(), this.ticker, this.initial, this.decrement, this.lowest);
    }

}
