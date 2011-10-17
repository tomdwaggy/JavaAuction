package us.nstro.javaauction.auction.strategy;

import us.nstro.javaauction.auction.AuctionStrategy;
import us.nstro.javaauction.auction.Bid;
import us.nstro.javaauction.auction.Price;
import us.nstro.javaauction.types.selection.Selection;

/**
 *  The AscendingAuction implements an Auction which is a traditional open
 *  ascending auction, like E-Bay.
 */
public class AscendingAuctionStrategy implements AuctionStrategy {

    private Selection<Price> validPrices;
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
        return validPrices;
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
     * Places the bid 'bid' on this auction. After bidding, the ascending
     * auction is defined to accept the bid so long as it is higher than any
     * other bid.
     *
     * @require: getValidPrices().contains(bid.getPrice())
     */
    public void placeBid(Bid bid) {
        if(this.getValidPrices().contains(bid.getPrice()))
            this.validPrices = new Selection<Price>(bid.getPrice().next(new Price(1)));
            this.winningBid = bid;
    }
    
}