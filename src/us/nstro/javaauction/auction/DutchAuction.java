package us.nstro.javaauction.auction;

import java.util.Timer;
import java.util.TimerTask;
import us.nstro.javaauction.types.selection.Selection;

/**
 * The Dutch Auction implements a simple auction, where the auctioneer starts
 * at a certain price, and continues to lower the price until the users decide
 * they are willing to bid. The first bidder is the winner.
 * @author bbecker
 */
public class DutchAuction extends AbstractAuction {

    private Price current;
    private Timer priceDropTimer;

    /**
     * Creates a new Dutch auction, or descending auction, with the options
     * for a Dutch auction. The time for the Dutch auction which simulates an
     * auctioneer manually lowering the price every 'interval' seconds is
     * started.
     */
    public DutchAuction(AuctionInfo info, Price start, Price drop, Price min, long interval) {

        super(info);
        this.updateValidPrices(new Selection<Price>(start, start));

        this.startAuctionTimer(start, drop, min, interval);

    }

    /**
     * Starts the auction timer for the Dutch auction, which automatically
     * lowers the price when the interval is reached.
     */
    private void startAuctionTimer(final Price start, final Price drop, final Price min, final long interval) {
        this.current = start;
        
        long delay = 1000 * interval; // 1000 ms, as interval is in s
        
        TimerTask priceDrop = new TimerTask() {
            public void run() {
                current = current.prev(drop);
                if(current.compareTo(min) < 0)
                    closeAuction();
            }
        };

        this.priceDropTimer = new Timer();
        this.priceDropTimer.schedule(priceDrop, delay, delay);
    }

    /**
     * Places the bid 'bid' on this auction. After bidding, the bidder should
     * automatically win given that it was a valid bid.
     *
     * @require: getValidBids().contains(bid)
     */
    public void placeBid(Bid bid) {
        this.setCurrentWinningBid(bid);
        this.closeAuction();
    }
}
