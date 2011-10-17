package us.nstro.javaauction.auction.strategy;

import us.nstro.javaauction.auction.Bid;
import us.nstro.javaauction.auction.Price;
import us.nstro.javaauction.types.selection.Selection;

/**
 * The Sealed First Bid Auction is an auction in which all of the bidders bid
 * without the value that they bid being released.
 *
 * @author bbecker
 */
public class SealedFirstBidAuctionStrategy implements AuctionStrategy {

    private Selection<Price> validPrices;
    private Selection<Price> sealedValidPrices;

    private Bid winningBid;

    /**
     * Set a minimum bid for the auction.
     *
     * @param minimumBid
     */
    public void setMinimumBid(Price minimumBid) {
        this.validPrices = new Selection<Price>(minimumBid);
    }

    /**
     * Gets the valid prices for a bid in this auction.
     *
     */
    public Selection<Price> getValidPrices() {
        return this.validPrices;
    }

    /**
     * Calculates the winning bid for this auction.
     *
     * @ensure: A valid bid has been placed.
     */
    public Bid getWinningBid() {
        return this.winningBid;
    }

    /**
     * Places the bid 'bid' on this auction. After bidding, the hidden
     * sealedBids attribute will be set to contain the actual valid bids,
     * and bids which are lower are silently dropped.
     *
     * @require: getValidBids().contains(bid)
     */
    public void placeBid(Bid bid) {
        if(this.sealedValidPrices.contains(bid.getPrice())) {
            this.winningBid = bid;
            this.sealedValidPrices = new Selection<Price>(bid.getPrice().next(new Price(1)));
        }
    }


}
