package us.nstro.javaauction.auction;

import us.nstro.javaauction.bids.Price;

/**
 *
 * @author bbecker
 */
public class AscendingAuctionBuilder extends AuctionBuilder {

    private Price minimumBid;

    /**
     * Creates a new Ascending Auction Builder with the following
     * default parameters:
     *      minimum bid = $1.00
     * These should be changed using the setter methods.
     */
    public AscendingAuctionBuilder() {
        this.minimumBid = Price.fromFloat(1.00f);
    }

    /**
     * Sets the Minimum Bid for this Ascending Auction.
     *
     * @require Price is positive
     * @param minimumBid
     */
    public void setMinimumBid(Price minimumBid) {
        this.minimumBid = minimumBid;
    }

    /**
     * Build the new Ascending Auction.
     *
     * @return a new, valid auction
     */
    public Auction build() {
        return new AscendingAuction(this.getAuctionID(), this.createAuctionInfo(), this.minimumBid);
    }

}
