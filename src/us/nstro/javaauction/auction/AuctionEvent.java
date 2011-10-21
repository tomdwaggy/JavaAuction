/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package us.nstro.javaauction.auction;

/**
 *
 * @author brian
 */
public class AuctionEvent {
    private Auction auction;
    private AuctionStatus status;
    
    public Auction getAuction() {
        return this.auction;
    }

    public AuctionStatus getStatus() {
        return this.status;
    }
    
    public AuctionEvent(Auction auction, AuctionStatus status) {
        this.auction = auction;
        this.status = status;
    }
}
