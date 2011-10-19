package us.nstro.javaauction.auction;

import us.nstro.javaauction.timer.AuctionTimer;
import java.util.Collection;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import us.nstro.javaauction.bids.Bid;
import us.nstro.javaauction.bids.Price;
import us.nstro.javaauction.type.Selection;

/**
 * The Dutch Auction implements a simple auction, where the auctioneer starts
 * at a certain price, and continues to lower the price until the users decide
 * they are willing to bid. The first bidder is the winner.
 * 
 * @author bbecker
 */
public class DutchAuction extends AbstractAuction {

    private AuctionTimer decrementTimer;

    private Selection<Price> validPrices;

    private Price currentPrice;
    private Price decrementPrice;
    private Price lowestPrice;

    private Bid winningBid;

    /**
     * Creates a new Dutch auction strategy.
     *
     * @param initial the starting bid
     * @param decrement the price the offers are lowered each interval
     * @param interval how often the price offer is lowered
     * @param lowest the lowest possible price which will be accepted
     * 
     */
    public DutchAuction(AuctionInfo info, AuctionTimer timer, Price initial, Price decrement, Price lowest) {
        super(info);
        this.currentPrice = initial;
        this.decrementPrice = decrement;
        this.decrementTimer = timer;
        this.lowestPrice = lowest;

        this.validPrices = new Selection<Price>(this.currentPrice, this.currentPrice);
    }

    /**
     * Starts the auction timer for the Dutch auction, which automatically
     * lowers the price when the interval is reached.
     */
    public void startAuctionTimer() {
        TimerTask priceDrop = new TimerTask() {
            public void run() {
                currentPrice = currentPrice.prev(decrementPrice);
                validPrices = new Selection<Price>(currentPrice, currentPrice);
                
                if(currentPrice.compareTo(lowestPrice) < 0)
                    decrementTimer.cancel();
            }
        };

        this.decrementTimer.schedule(priceDrop);
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
        if(this.getValidPrices().contains(bid.getPrice())) {
            this.validPrices = new Selection<Price>(bid.getPrice().next(new Price(1)));
            this.winningBid = bid;
        }
    }

}