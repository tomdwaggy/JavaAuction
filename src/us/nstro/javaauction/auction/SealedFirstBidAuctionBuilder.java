package us.nstro.javaauction.auction;

import us.nstro.javaauction.bids.Price;

/**
 *
 * @author bbecker
 */
public class SealedFirstBidAuctionBuilder extends AuctionBuilder {

    private Price minimumBid;

    public SealedFirstBidAuctionBuilder(Price minimumBid) {
        this.minimumBid = minimumBid;
    }

    public Auction build(Integer auctionID) {
        return new SealedFirstBidAuction(auctionID, this.createAuctionInfo(), this.minimumBid);
    }

}