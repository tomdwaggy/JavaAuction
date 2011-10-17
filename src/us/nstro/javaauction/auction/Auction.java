package us.nstro.javaauction.auction;

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

    private Bid currentWinningBid;

    private Selection<Price> validPrices;

    /**
     * Create a new Abstract Auction.
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
        return this.validPrices;
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
        this.status.close();
    }

    /**
     * Closes the auction normally, committing the winning bid.
     *
     * @require: getSTatus().isClosed() == false
     * @ensure: getStatus().isClosed() == true
     */
    public final void closeAuction() {
        this.status.close(this.currentWinningBid);
    }

    /**
     * Places the bid 'bid' on this auction. After bidding, the ascending
     * auction is defined to accept the bid so long as it is higher than any
     * other bid.
     *
     * @require: getValidBids().contains(bid)
     */
    public final void placeBid(Bid bid) {
        this.currentWinningBid = this.auctionStrategy.getWinningBid();
        this.validPrices = this.auctionStrategy.getValidPrices();
        if(this.validPrices.isEmpty())
            this.closeAuction();
    }

}
