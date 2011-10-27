/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package us.nstro.javaauction.auction;

/**
 * Listener interface for the Auction Event system, so that the brain of the
 * program can be notified when things happen in the auction.
 *
 * @author brian
 */
public interface AuctionEventListener {

    /**
     * An auction event has occurred on the Auction on which this object
     * is listening.
     *
     * @param event The auction event containing the new status of the auction.
     */
    public void auctionEventOccurred(AuctionEvent event);
    
}
