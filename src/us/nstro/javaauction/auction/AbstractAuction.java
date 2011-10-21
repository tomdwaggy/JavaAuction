package us.nstro.javaauction.auction;

/**
 * The AbstractAuction class implements the base functionality of storing and
 * retrieving the auction information, auction status, and current winning
 * bid. It also implements the close and abort auction functionality.
 *
 * @author bbecker
 */
public abstract class AbstractAuction implements Auction {

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
     * Gets the auction status. This is dynamic data, such as whether the
     * auction is closed or not, and who the winner is (if closed).
     *
     * @ensure: getInfo() != null
     */
    public AuctionInfo getInfo() {
        return this.info;
    }

    /**
     * Start the auction.
     *
     * @require: !isOpen() && !hasWinner() && !isAborted
     * @ensure: isOpen()
     */
    public void startAuction() {
        if(this.status == AbstractAuction.Status.NOT_STARTED)
            this.status = AbstractAuction.Status.OPEN;
    }

    /**
     * Is the auction open?
     */
    public boolean isOpen() {
        return this.status == AbstractAuction.Status.OPEN;
    }

    /**
     * Is the auction aborted?
     */
    public boolean isAborted() {
        return this.status == AbstractAuction.Status.ABORTED;
    }

    /**
     * Aborts the auction, not committing any current winners.
     *
     * @require: isClosed() == false
     * @ensure: isClosed() == true &&
     *      hasWinner() == false
     */
    public void abortAuction() {
        if(this.isOpen())
            this.status = AbstractAuction.Status.ABORTED;
    }

    /**
     * Closes the auction normally, committing the winning bid.
     *
     * @require: isClosed() == false
     * @ensure: isClosed() == true
     */
    public void closeAuction() {
        if(this.isOpen())
            this.status = AbstractAuction.Status.CLOSED;
    }

}
