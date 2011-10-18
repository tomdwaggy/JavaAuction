package us.nstro.javaauction.auction;

import java.util.Collection;
import us.nstro.javaauction.auction.strategy.AuctionStrategy;
import us.nstro.javaauction.bids.Bid;
import us.nstro.javaauction.bids.Price;
import us.nstro.javaauction.type.Selection;

/**
 * The AbstractAuction class implements the base functionality of storing and
 * retrieving the auction information, auction status, and current winning
 * bid. It also implements the close and abort auction functionality.
 *
 * @author bbecker
 */
public class Auction {

    private AuctionInfo info;
    private AuctionStatus status;
    private AuctionStrategy auctionStrategy;

    /**
     * Create a new Auction with the given auction information and the given
     * auction strategy.
     *
     * @param info Auction information
     * @param strategy Auction strategy, the kind of auction this is
     *          for instance, ascending or descending
     */
    public Auction(AuctionInfo info, AuctionStrategy strategy) {
        this.info = info;
        this.status = new AuctionStatus();
        this.auctionStrategy = strategy;
    }

    /**
     *  Gets a selection of valid bid prices for this auction.
     *
     *  @ensure: getValidBids().contains(i) for all i which is a valid
     *      bid.
     */
    public final Selection<Price> getValidPrices() {
        return this.auctionStrategy.getValidPrices();
    }

    /**
     * Places the bid 'bid' on this auction. After bidding, the ascending
     * auction is defined to accept the bid so long as it is higher than any
     * other bid.
     *
     * @require: getValidPrices().contains(bid.getPrice())
     */
    public void placeBid(Bid bid) {
        this.auctionStrategy.placeBid(bid);
    }

    /**
     * Get the winning bid(s).
     */
    public Collection<Bid> getWinningBids() {
        return this.auctionStrategy.getWinningBids();
    }

    /**
     * Gets the auction status. This is dynamic data, such as whether the
     * auction is closed or not, and who the winner is (if closed).
     *
     * @ensure: getInfo() != null
     */
    public final AuctionInfo getInfo() {
        return this.info;
    }

    /**
     * Is the auction open?
     */
    public final boolean isOpen() {
        return this.status.isOpen();
    }

    /**
     * Is the auction aborted?
     */
    public final boolean isAborted() {
        return this.status.isAborted();
    }

    /**
     * Aborts the auction, not committing any current winners.
     *
     * @require: getStatus().isClosed() == false
     * @ensure: getStatus().isClosed() == true &&
     *      getStatus().hasWinner() == false
     */
    public final void abortAuction() {
        this.status.abort();
    }

    /**
     * Closes the auction normally, committing the winning bid.
     *
     * @require: getSTatus().isClosed() == false
     * @ensure: getStatus().isClosed() == true
     */
    public final void closeAuction() {
        this.status.close();
    }

}
