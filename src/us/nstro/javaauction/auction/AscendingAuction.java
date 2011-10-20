package us.nstro.javaauction.auction;

import java.util.Collection;
import java.util.Collections;
import us.nstro.javaauction.bids.Bid;
import us.nstro.javaauction.bids.Price;
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
    public AscendingAuction(AuctionInfo info, Price minimumBid) {
        super(info);
        this.validPrices = new Selection<Price>(minimumBid);
    }

    /**
     * Gets the valid prices for a bid in this auction.
     */
    @Override
    public Selection<Price> getValidPrices() {
        return validPrices;
    }

    /**
     * Calculates the winning bid for this auction.
     *
     * @require: hasWinner()
     * @ensure: getWinningBids().size() > 0
     */
    @Override
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
    @Override
    public void placeBid(Bid bid) {
        if(this.getValidPrices().contains(bid.getPrice()))
            this.validPrices = new Selection<Price>(bid.getPrice().next(new Price(1)));
            this.winningBid = bid;
    }
    
}