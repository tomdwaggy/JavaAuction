package us.nstro.javaauction.auction;

import java.util.Collection;
import java.util.Collections;
import us.nstro.javaauction.bids.Bid;
import us.nstro.javaauction.bids.Price;
import us.nstro.javaauction.type.Minimum;
import us.nstro.javaauction.type.Selection;

/**
 * The Sealed First Bid Auction is an auction in which all of the bidders bid
 * without the value that they bid being released.
 *
 * @author bbecker
 */
public class SealedFirstBidAuction extends AbstractAuction {

    private Selection<Price> validPrices;
    private Selection<Price> sealedValidPrices;

    private Bid winningBid;

    /**
     * Create a new Sealed First Bid auction strategy.
     *
     * @param minimumBid the lowest bid which will be accepted
     */
    public SealedFirstBidAuction(Integer id, AuctionInfo info, Price minimumBid) {
        super(id, info);
        this.validPrices = new Minimum<Price>(minimumBid);
        this.sealedValidPrices = this.validPrices;
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
    public Collection<Bid> getWinningBids() {
        if(this.winningBid != null)
            return Collections.singleton(this.winningBid);
        else
            return Collections.emptyList();
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
            this.sealedValidPrices = new Minimum<Price>(bid.getPrice().next(new Price(1)));
        }
    }


}
