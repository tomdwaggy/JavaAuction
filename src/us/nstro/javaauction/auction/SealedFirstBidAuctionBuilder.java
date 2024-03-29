package us.nstro.javaauction.auction;

import us.nstro.javaauction.bids.Price;

/**
 *
 * @author bbecker
 */
public class SealedFirstBidAuctionBuilder extends AuctionBuilder {

    private Price minimumBid;

    public void setMinimumBid(Price minimumBid) {
        this.minimumBid = minimumBid;
    }

    public Auction build() {
        return new SealedFirstBidAuction(this.getAuctionID(), this.createAuctionInfo(), this.minimumBid);
    }

}