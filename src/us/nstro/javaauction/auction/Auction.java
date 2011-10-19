package us.nstro.javaauction.auction;

import java.util.Collection;
import us.nstro.javaauction.auction.strategy.AuctionStrategy;
import us.nstro.javaauction.bids.Bid;
import us.nstro.javaauction.bids.Price;
import us.nstro.javaauction.type.Selection;

/**
 * The AbstractAuction class implements the base functionality of storing and
 * retrieving the auction information, auction status, and current winning
 * bid. It also implements the close and abort auction functionality.
 *
 * @author bbecker
 */
public class Auction {

    public enum Status {
        NOT_STARTED,    // Auction has not started yet
        OPEN,           // Auction has started, not been closed
        CLOSED,         // Auction has been closed normally
        ABORTED         // Auction has been aborted forcefully (no winner)
    }

    private AuctionInfo info;
    private Auction.Status status;
    private AuctionStrategy auctionStrategy;

    /**
     * Create a new Auction with the given auction information and the given
     * auction strategy.
     *
     * @param info Auction information
     * @param strategy Auction strategy, the kind of auction this is
     *          for instance, ascending or descending
     */
    public Auction(AuctionInfo info, AuctionStrategy strategy) {
        this.info = info;
        this.status = Auction.Status.NOT_STARTED;
        this.auctionStrategy = strategy;
    }

    /**
     *  Gets a selection of valid bid prices for this auction.
     *
     *  @ensure: getValidBids().contains(i) for all i which is a valid
     *      bid.
     */
    public final Selection<Price> getValidPrices() {
        return this.auctionStrategy.getValidPrices();
    }

    /**
     * Places the bid 'bid' on this auction. After bidding, the ascending
     * auction is defined to accept the bid so long as it is higher than any
     * other bid.
     *
     * @require: getValidPrices().contains(bid.getPrice())
     */
    public void placeBid(Bid bid) {
        this.auctionStrategy.placeBid(bid);
    }

    /**
     * Get the winning bid(s).
     */
    public Collection<Bid> getWinningBids() {
        return this.auctionStrategy.getWinningBids();
    }

    /**
     * Gets the auction status. This is dynamic data, such as whether the
     * auction is closed or not, and who the winner is (if closed).
     *
     * @ensure: getInfo() != null
     */
    public final AuctionInfo getInfo() {
        return this.info;
    }

    /**
     * Has the auction been started?
     */
    public final boolean hasStarted() {
        return this.status != Auction.Status.NOT_STARTED;
    }

    /**
     * Start the auction.
     *
     * @require: !hasStarted()
     * @ensure: hasStarted()
     */
    public final void start() {
        if(!this.hasStarted())
            this.status = Auction.Status.OPEN;
    }

    /**
     * Is the auction open?
     */
    public final boolean isOpen() {
        return this.status == Auction.Status.OPEN;
    }

    /**
     * Is the auction aborted?
     */
    public final boolean isAborted() {
        return this.status == Auction.Status.ABORTED;
    }

    /**
     * Aborts the auction, not committing any current winners.
     *
     * @require: isClosed() == false
     * @ensure: isClosed() == true &&
     *      hasWinner() == false
     */
    public final void abortAuction() {
        if(this.isOpen())
            this.status = Auction.Status.ABORTED;
    }

    /**
     * Closes the auction normally, committing the winning bid.
     *
     * @require: isClosed() == false
     * @ensure: isClosed() == true
     */
    public final void closeAuction() {
        if(this.isOpen())
            this.status = Auction.Status.CLOSED;
    }

}
