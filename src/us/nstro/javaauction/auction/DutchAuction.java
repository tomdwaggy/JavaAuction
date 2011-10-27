package us.nstro.javaauction.auction;

import us.nstro.javaauction.handler.Ticker;
import java.util.Collection;
import java.util.Collections;
import us.nstro.javaauction.bids.Bid;
import us.nstro.javaauction.bids.Price;
import us.nstro.javaauction.type.EmptySelection;
import us.nstro.javaauction.type.Selection;
import us.nstro.javaauction.type.SingleValue;

/**
 * The Dutch Auction implements a simple auction, where the auctioneer starts
 * at a certain price, and continues to lower the price until the users decide
 * they are willing to bid. The first bidder is the winner.
 * 
 * @author bbecker
 */
public class DutchAuction extends AbstractAuction {

    private Ticker decrementTicker;

    private Selection<Price> validPrices;

    private Price currentPrice;
    private Price decrementPrice;
    private Price lowestPrice;

    private Bid winningBid;

    /**
     * Creates a new Dutch auction.
     *
     * @param initial the starting bid
     * @param decrement the price the offers are lowered each interval
     * @param interval how often the price offer is lowered
     * @param lowest the lowest possible price which will be accepted
     * 
     */
    public DutchAuction(Integer id, AuctionInfo info, Ticker ticker, Price initial, Price decrement, Price lowest) {
        super(id, info);
        
        this.currentPrice = initial;
        this.decrementPrice = decrement;
        this.decrementTicker = ticker;
        this.lowestPrice = lowest;

        this.validPrices = new SingleValue<Price>(this.currentPrice);
    }

    /**
     * Starts the auction timer for the Dutch auction, which automatically
     * lowers the price when the interval is reached.
     */
    private void startDutchAuctionTimer() {

        // This runnable drops the price by the given decrement that this dutch
        // auction was created with.
        Runnable priceDrop = new Runnable() {
            public void run() {
                if(currentPrice.prev(decrementPrice).compareTo(lowestPrice) < 0)
                    return;

                currentPrice = currentPrice.prev(decrementPrice);
                validPrices = new SingleValue<Price>(currentPrice);
            }
        };

        // Add our runnable to the ticker, to be called every tick.
        this.decrementTicker.addTickHandler(priceDrop);
    }

    /**
     * Start the auction, starting the auction timer. Starting the auction
     * will also start the dutch auction timer, which decrements the auction
     * value.
     */
    @Override
    public void startAuction() {
        super.startAuction();

        if(this.isOpen())
            this.startDutchAuctionTimer();
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
     * @ensure: A valid bid has been placed.
     */
    public Collection<Bid> getWinningBids() {
        // Return the winning bid as a singleton collection, if there is a
        // winning bid, otherwise return an empty list.
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
        // Does the valid prices contain the bid to be placed?
        if(this.getValidPrices().contains(bid.getPrice())) {
            // There are no longer any valid prices, bidding is over.
            this.validPrices = new EmptySelection<Price>();
            // Set the winning bid to the current bid.
            this.winningBid = bid;
            // Close the auction immediately, as the dutch auction is won by
            // the first valid bid.
            this.closeAuction();
        }
    }

}