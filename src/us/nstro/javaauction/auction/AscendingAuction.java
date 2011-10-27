package us.nstro.javaauction.auction;

import java.util.Collection;
import java.util.Collections;
import us.nstro.javaauction.bids.Bid;
import us.nstro.javaauction.bids.Price;
import us.nstro.javaauction.type.Minimum;
import us.nstro.javaauction.type.Selection;

/**
 *  The AscendingAuction implements an Auction which is a traditional open
 *  ascending auction, like E-Bay.
 */
public class AscendingAuction extends AbstractAuction {

    private Selection<Price> validPrices;
    private Bid winningBid;

    /**
     * Creates a new ascending auction with the given initial price selection.
     *
     * @param minimumBid minimum bid for this auction
     */
    public AscendingAuction(Integer id, AuctionInfo info, Price minimumBid) {
        super(id, info);
        this.validPrices = new Minimum<Price>(minimumBid);
    }

    /**
     * Gets the valid prices for a bid in this auction.
     */
    public Selection<Price> getValidPrices() {
        return this.validPrices;
    }

    /**
     * Calculates the winning bid for this auction.
     *
     * @require: hasWinner()
     * @ensure: getWinningBids().size() > 0
     */
    public Collection<Bid> getWinningBids() {
        if(this.winningBid != null)
            return Collections.singleton(this.winningBid);
        else
            return Collections.emptyList();
    }

    /**
     * Places the bid 'bid' on this auction. After bidding, the ascending
     * auction is defined to accept the bid so long as it is higher than any
     * other bid.
     *
     * @require: getValidPrices().contains(bid.getPrice())
     */
    public void placeBid(Bid bid) {
        if(this.getValidPrices().contains(bid.getPrice())) {
            this.validPrices = new Minimum<Price>(bid.getPrice().next(new Price(1)));
            this.winningBid = bid;
        }
    }
    
}