package us.nstro.javaauction.auction;

import us.nstro.javaauction.auction.strategy.AuctionStrategy;
import us.nstro.javaauction.bids.Bid;
import us.nstro.javaauction.bids.Price;
import us.nstro.javaauction.types.selection.Selection;

/**
 * The AbstractAuction class implements the base functionality of storing and
 * retrieving the auction information, auction status, and current winning
 * bid. It also implements the close and abort auction functionality.
 *
 * @author bbecker
 */
public abstract class Auction {

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
     * Gets the auction status. This is dynamic data, such as whether the
     * auction is closed or not, and who the winner is (if closed).
     *
     * @ensure: getInfo() != null
     */
    public final AuctionInfo getInfo() {
        return this.info;
    }

    /**
     * Get the auction information. This is static information about an
     * auction, such as who the auctioneer is, what is being auctioned off,
     * the auction name and ID, etc.
     *
     * @ensure: getInfo() != null
     */
    public final AuctionStatus getStatus() {
        return this.status;
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
        this.status.close(this.auctionStrategy.getWinningBid());
    }

}
