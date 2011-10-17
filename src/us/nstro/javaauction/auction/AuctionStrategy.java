package us.nstro.javaauction.auction;

import us.nstro.javaauction.types.selection.Selection;

/**
 * The AuctionStrategy determines the valid prices for a bid, and how to
 * calculate the winning bid.
 */
public interface AuctionStrategy {

    /**
     * Gets the valid prices for a bid in this auction.
     * 
     */
    public Selection<Price> getValidPrices();

    /**

    /**
     * Calculates the winning bid for this auction.
     *
     * @ensure: bid != null
     */
    public Bid getWinningBid();

}