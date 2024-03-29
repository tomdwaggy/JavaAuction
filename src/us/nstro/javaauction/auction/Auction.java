/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package us.nstro.javaauction.auction;

import java.util.Collection;
import us.nstro.javaauction.bids.Bid;
import us.nstro.javaauction.bids.Price;
import us.nstro.javaauction.type.Selection;

/**
 *
 * @author bbecker
 */
public interface Auction {

    /**
     * Get the valid prices for a bid in this auction.
     *
     * @return a Selection of valid prices
     */
    public Selection<Price> getValidPrices();

    /**
     * Adds an Auction Event Listener to the given auction, in order to be
     * notified when an event occurs.
     * @param listener
     */
    public void addAuctionEventListener(AuctionEventListener listener);

    /**
     * Removes an Auction Event Listener from the given auction, in order to be
     * notified when an event occurs.
     * @param listener
     */
    public void removeAuctionEventListener(AuctionEventListener listener);

    /**
     * Get a list of all Auction Event Listeners.
     */
    public Collection<AuctionEventListener> getAuctionEventListeners();

    /**
     * Places the bid 'bid' on this auction. After bidding, the ascending
     * auction is defined to accept the bid so long as it is higher than any
     * other bid.
     *
     * @require: getValidPrices().contains(bid.getPrice())
     */
    public void placeBid(Bid bid);

    /**
     * Get the winning bid(s).
     */
    public Collection<Bid> getWinningBids();

    /**
     * Gets the unique auction identifier for this auction.
     */
    public Integer getID();

    /**
     * Gets the auction status. This is dynamic data, such as whether the
     * auction is closed or not, and who the winner is (if closed).
     *
     * @ensure: getInfo() != null
     */
    public AuctionInfo getInfo();

    /**
     * Start the auction.
     *
     * @require: !hasStarted()
     * @ensure: hasStarted()
     */
    public void startAuction();

    /**
     * Determine if the auction is currently open.
     */
    public boolean isOpen();

    /**
     * Determine if the auction has been aborted.
     */
    public boolean isAborted();

    /**
     * Aborts the auction, not committing any current winners.
     *
     * @require: isOpen() == true
     * @ensure: isOpen() == false && isAborted() == false && hasWinner() == false
     */
    public void abortAuction();

    /**
     * Closes the auction normally, committing the winning bid.
     *
     * @require: isOpen() == true
     * @ensure: isOpen() == false && isAborted() == false
     */
    public void closeAuction();
}
