package us.nstro.javaauction.auction;

/**
 * An AuctionEvent is an event fired when an auction has changed its
 * AuctionStatus.
 *
 * @author brian
 */
public class AuctionEvent {
    private Auction auction;
    private AuctionStatus status;

    /**
     * Get the auction which this event is associated with.
     *
     * @return Auction which threw this event
     */
    public Auction getAuction() {
        return this.auction;
    }

    /**
     * Get the new status of this auction.
     *
     * @return AuctionStatus of auction
     */
    public AuctionStatus getStatus() {
        return this.status;
    }

    /**
     * Create a new Auction Event.
     *
     * @param auction Auction which has fired this event
     * @param status AuctionStatus of the auction
     */
    public AuctionEvent(Auction auction, AuctionStatus status) {
        this.auction = auction;
        this.status = status;
    }
}
