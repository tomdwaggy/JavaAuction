/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package us.nstro.javaauction.auction;

/**
 *
 * @author bbecker
 */
public abstract class AbstractAuction {

    private AuctionInfo info;

    /**
     * Create a new Abstract Auction.
     */
    public AbstractAuction(AuctionInfo info) {
        this.info = info;
    }

    /**
     * Get the auction information.
     */
    public AuctionInfo getInfo() {
        return this.info;
    }


}
