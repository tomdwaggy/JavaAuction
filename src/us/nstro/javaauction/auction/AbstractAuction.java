package us.nstro.javaauction.auction;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
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
public abstract class AbstractAuction {

    public enum Status {
        NOT_STARTED,    // AbstractAuction has not started yet
        OPEN,           // AbstractAuction has started, not been closed
        CLOSED,         // AbstractAuction has been closed normally
        ABORTED         // AbstractAuction has been aborted forcefully (no winner)
    }

    private AuctionInfo info;
    private AbstractAuction.Status status;

    /**
     * Create a new AbstractAuction with the given auction information and the given
     * auction strategy.
     *
     * @param info AbstractAuction information
     * @param strategy AbstractAuction strategy, the kind of auction this is
     *          for instance, ascending or descending
     */
    public AbstractAuction(AuctionInfo info) {
        this.info = info;
        this.status = AbstractAuction.Status.NOT_STARTED;
    }

    /**
     *  Gets a selection of valid bid prices for this auction.
     *
     *  @ensure: getValidBids().contains(i) for all i which is a valid
     *      bid.
     */
    public Selection<Price> getValidPrices() {
        return new Selection<Price>(new Price(50), new Price(100));
    }

    /**
     * Places the bid 'bid' on this auction. After bidding, the ascending
     * auction is defined to accept the bid so long as it is higher than any
     * other bid.
     *
     * @require: getValidPrices().contains(bid.getPrice())
     */
    public void placeBid(Bid bid) {
        //this.auctionStrategy.placeBid(bid);
    }

    /**
     * Get the winning bid(s).
     */
    public Collection<Bid> getWinningBids() {
        return Collections.singleton(new Bid(new User(UUID.randomUUID(), "TEST"), this, new Price(500)));
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
        return this.status != AbstractAuction.Status.NOT_STARTED;
    }

    /**
     * Start the auction.
     *
     * @require: !hasStarted()
     * @ensure: hasStarted()
     */
    public final void start() {
        if(!this.hasStarted())
            this.status = AbstractAuction.Status.OPEN;
    }

    /**
     * Is the auction open?
     */
    public final boolean isOpen() {
        return this.status == AbstractAuction.Status.OPEN;
    }

    /**
     * Is the auction aborted?
     */
    public final boolean isAborted() {
        return this.status == AbstractAuction.Status.ABORTED;
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
            this.status = AbstractAuction.Status.ABORTED;
    }

    /**
     * Closes the auction normally, committing the winning bid.
     *
     * @require: isClosed() == false
     * @ensure: isClosed() == true
     */
    public final void closeAuction() {
        if(this.isOpen())
            this.status = AbstractAuction.Status.CLOSED;
    }

}
