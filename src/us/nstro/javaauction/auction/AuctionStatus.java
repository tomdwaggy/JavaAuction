/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package us.nstro.javaauction.auction;

/**
 * A class for representing the status of an auction, such as whether an
 * auction is has been closed, whether there is a winner, and who won the
 * auction.
 *
 * @author bbecker
 */
public class AuctionStatus {

    private Bid win;
    private boolean open;

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
     * @ensure: this.isClosed()
     */
    public void close() {
        this.open = false;
    }

    /**
     * Set the auction's status to closed and specify who won the auction. This
     * represents a normally closed auction.
     *
     * @require: winner != null
     * @ensure: this.isClosed()
     *
     * @param winner the winning bid
     */
    public void close(Bid win) {
        this.close();
        this.win = win;
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
     * Determine if the auction has a winner.
     *
     * @return whether the auction has a winner.
     */
    public boolean hasWinningBid() {
        return this.win != null;
    }

    /**
     * Get the winner of this auction.
     *
     * @require: this.hasWinner()
     * 
     * @return the bid which won the auction.
     */
    public Bid getWinningBid() {
        return this.win;
    }

}