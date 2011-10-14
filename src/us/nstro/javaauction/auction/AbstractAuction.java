/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package us.nstro.javaauction.auction;

/**
 *
 * @author bbecker
 */
public abstract class AbstractAuction implements Auction {

    private AuctionInfo info;
    private AuctionStatus status;

    /**
     * Create a new Abstract Auction.
     */
    public AbstractAuction(AuctionInfo info) {
        this.info = info;
        this.status = new AuctionStatus();
    }

    /**
     * Get the auction information.
     */
    public AuctionInfo getInfo() {
        return this.info;
    }

    /**
     * Get the auction information.
     */
    public AuctionStatus getStatus() {
        return this.status;
    }


}
