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
    public Selection<Price> getValidPrices();

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
     * Gets the auction status. This is dynamic data, such as whether the
     * auction is closed or not, and who the winner is (if closed).
     *
     * @ensure: getInfo() != null
     */
    public AuctionInfo getInfo();

    /**
     * Has the auction been started?
     */
    public boolean hasStarted();

    /**
     * Start the auction.
     *
     * @require: !hasStarted()
     * @ensure: hasStarted()
     */
    public void start();

    /**
     * Is the auction open?
     */
    public boolean isOpen();

    /**
     * Is the auction aborted?
     */
    public boolean isAborted();

    /**
     * Aborts the auction, not committing any current winners.
     *
     * @require: isClosed() == false
     * @ensure: isClosed() == true &&
     *      hasWinner() == false
     */
    public void abortAuction();

    /**
     * Closes the auction normally, committing the winning bid.
     *
     * @require: isClosed() == false
     * @ensure: isClosed() == true
     */
    public void closeAuction();
}
