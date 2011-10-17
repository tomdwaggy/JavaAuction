/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package us.nstro.javaauction.auction;

import us.nstro.javaauction.bids.Bid;

/**
 * A class for representing the status of an auction, such as whether an
 * auction is has been closed, whether there is a winner, and who won the
 * auction.
 *
 * @author bbecker
 */
public class AuctionStatus {

    private Bid winningBid;
    private boolean open;
    private boolean aborted;

    /**
     * Create a new AuctionStatus in the default open status.
     */
    public AuctionStatus() {
        this.open = true;
    }

    /**
     * Set the auction's status to closed without specifying a winner. This
     * is to represent an auction which has been closed prematurely.
     *
     * @ensure: this.isClosed() && this.isAborted()
     */
    public void abort() {
        this.open = false;
        this.aborted = true;
    }

    /**
     * Set the auction's status to closed and specify who won the auction. This
     * represents a normally closed auction.
     *
     * @require: winner != null
     * @ensure: this.isClosed() && this.isAborted() && this.hasWinningBid()
     *              && this.getWinningBid() != null
     *
     * @param winningBid the bid which won the auction
     */
    public void close(Bid winningBid) {
        this.open = false;
        this.winningBid = winningBid;
    }

    /**
     * Determine if the auction has been closed.
     *
     * @return whether the auction has been closed or not.
     */
    public boolean isClosed() {
        return !this.open;
    }

    /**
     * Determine if the auction has been aborted.
     *
     * @return whether the auction has been aborted with no winner.
     */
    public boolean isAborted() {
        return this.aborted;
    }

    /**
     * Determine if the auction has a winner.
     *
     * @return whether the auction has a winner.
     */
    public boolean hasWinningBid() {
        return this.winningBid != null;
    }

    /**
     * Get the winner of this auction.
     *
     * @require: this.hasWinner()
     * 
     * @return the bid which won the auction.
     */
    public Bid getWinningBid() {
        return this.winningBid;
    }

}
