package us.nstro.javaauction.auction;

import java.util.Collection;
import java.util.LinkedList;

/**
 * The AbstractAuction class implements the base functionality of storing and
 * retrieving the auction information, auction status, and current winning
 * bid. It also implements the close and abort auction functionality.
 *
 * @author bbecker
 */
public abstract class AbstractAuction implements Auction {

    private AuctionInfo info;
    private AuctionStatus status;
    private Collection<AuctionEventListener> listeners;

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
        this.status = AuctionStatus.NOT_STARTED;
        this.listeners = new LinkedList<AuctionEventListener>();
    }

    /**
     * Adds an Auction Event Listener to the given auction, in order to be
     * notified when an event occurs.
     * @param listener
     */
    public void addAuctionEventListener(AuctionEventListener listener) {
        this.listeners.add(listener);
    }

    /**
     * Removes an Auction Event Listener from the given auction, in order to be
     * notified when an event occurs.
     * @param listener
     */
    public void removeAuctionEventListener(AuctionEventListener listener) {
        this.listeners.remove(listener);
    }

    /**
     * Get a list of all Auction Event Listeners.
     */
    public Collection<AuctionEventListener> getAuctionEventListeners() {
        return this.listeners;
    }

    /**
     * Fire an Auction Event to all of the Listeners
     */
    protected void fireAuctionEvent(AuctionEvent evt) {
        for(AuctionEventListener listener : this.listeners)
            listener.auctionEventOccurred(evt);
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
        if(this.status == AuctionStatus.NOT_STARTED)
            this.status = AuctionStatus.OPEN;
    }

    /**
     * Is the auction open?
     */
    public boolean isOpen() {
        return this.status == AuctionStatus.OPEN;
    }

    /**
     * Is the auction aborted?
     */
    public boolean isAborted() {
        return this.status == AuctionStatus.ABORTED;
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
            this.status = AuctionStatus.ABORTED;
    }

    /**
     * Closes the auction normally, committing the winning bid.
     *
     * @require: isClosed() == false
     * @ensure: isClosed() == true
     */
    public void closeAuction() {
        if(this.isOpen())
            this.status = AuctionStatus.CLOSED;
    }

}
