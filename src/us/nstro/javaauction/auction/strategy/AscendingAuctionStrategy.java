package us.nstro.javaauction.auction.strategy;

import java.util.Collection;
import java.util.Collections;
import us.nstro.javaauction.bids.Bid;
import us.nstro.javaauction.bids.Price;
import us.nstro.javaauction.type.Selection;

/**
 *  The AscendingAuction implements an Auction which is a traditional open
 *  ascending auction, like E-Bay.
 */
public class AscendingAuctionStrategy implements AuctionStrategy {

    private Selection<Price> validPrices;
    private Bid winningBid;

    /**
     * Creates a new ascending auction with the given initial price selection.
     *
     * @param initial initial valid prices selection
     */
    public AscendingAuctionStrategy(Selection<Price> initial) {
        this.validPrices = initial;
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
    public Collection<Bid> getWinningBids() {
        return Collections.singleton(this.winningBid);
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