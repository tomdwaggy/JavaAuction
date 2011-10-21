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

    public void auctionEventOccurred(AuctionEvent event);
    
}
