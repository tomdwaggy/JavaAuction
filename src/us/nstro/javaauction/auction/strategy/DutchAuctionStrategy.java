package us.nstro.javaauction.auction.strategy;

import java.util.Timer;
import java.util.TimerTask;
import us.nstro.javaauction.bids.Bid;
import us.nstro.javaauction.bids.Price;
import us.nstro.javaauction.types.selection.Selection;

/**
 * The Dutch Auction implements a simple auction, where the auctioneer starts
 * at a certain price, and continues to lower the price until the users decide
 * they are willing to bid. The first bidder is the winner.
 * 
 * @author bbecker
 */
public class DutchAuctionStrategy implements AuctionStrategy {

    private Timer priceDropTimer;
    private long decrementInterval;

    private Selection<Price> validPrices;

    private Price currentPrice;
    private Price decrementPrice;
    private Price lowestPrice;

    private Bid winningBid;

    /**
     * Set an initial bid for the Dutch auction.
     *
     * @param initialPrice
     */
    public void setInitialPrice(Price initialPrice) {
        this.currentPrice = initialPrice;
        this.validPrices = new Selection<Price>(initialPrice, initialPrice);
    }

    /**
     * Set a decrement value for the Dutch auction.
     *
     * @param decrementPrice
     */
    public void setDecrementPrice(Price decrementPrice) {
        this.decrementPrice = decrementPrice;
    }

    /**
     * Set a decrement interval in seconds.
     *
     * @param decrementInterval interval in seconds
     */
    public void setDecrementInterval(long seconds) {
        this.decrementInterval = seconds;
    }

    /**
     * Set the lowest price accepted.
     *
     * @param lowestPrice
     */
    public void setLowestPrice(Price lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    /**
     * Starts the auction timer for the Dutch auction, which automatically
     * lowers the price when the interval is reached.
     */
    public void startAuctionTimer() {
        long delay = 1000 * this.decrementInterval; // 1000 ms, as interval is in s

        TimerTask priceDrop = new TimerTask() {
            public void run() {
                currentPrice = currentPrice.prev(decrementPrice);
                validPrices = new Selection<Price>(currentPrice, currentPrice);
                
                if(currentPrice.compareTo(lowestPrice) < 0)
                    priceDropTimer.cancel();
            }
        };

        this.priceDropTimer = new Timer();
        this.priceDropTimer.schedule(priceDrop, delay, delay);
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