package us.nstro.javaauction.auction;

/**
 * AuctionStatus describes the status of an auction, whether it has not yet
 * been started, is currently open, has been closed normally, or whether it
 * has been aborted.
 *
 * @author brian
 */
public enum AuctionStatus {
    NOT_STARTED,    // AbstractAuction has not started yet
    OPEN,           // AbstractAuction has started, not been closed
    CLOSED,         // AbstractAuction has been closed normally
    ABORTED         // AbstractAuction has been aborted forcefully (no winner)
}
