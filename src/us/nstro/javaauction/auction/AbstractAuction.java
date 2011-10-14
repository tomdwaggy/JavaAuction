package us.nstro.javaauction.auction;

import us.nstro.javaauction.types.selection.Selection;

/**
 * The AbstractAuction class implements the base functionality of storing and
 * retrieving the auction information, auction status, and current winning
 * bid. It also implements the close and abort auction functionality.
 *
 * @author bbecker
 */
public abstract class AbstractAuction implements Auction {

    private AuctionInfo info;
    private AuctionStatus status;

    private Bid currentWinningBid;

    private Selection<Price> validPrices;

    /**
     * Create a new Abstract Auction.
     */
    public AbstractAuction(AuctionInfo info) {
        this.info = info;
        this.status = new AuctionStatus();
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
     *  Updates the selection of valid bid prices for this auction.
    */
    public final void updateValidPrices(Selection<Price> prices) {
        this.validPrices = prices;
        if(prices.isEmpty())
            this.closeAuction();
    }

    /**
     * Sets the current winning bidder.
     */
    public final void setCurrentWinningBid(Bid bid) {
        this.currentWinningBid = bid;
    }

    /**
     * Get the auction information.
     */
    public final AuctionInfo getInfo() {
        return this.info;
    }

    /**
     * Get the auction information.
     */
    public final AuctionStatus getStatus() {
        return this.status;
    }

    /**
     * Aborts the auction.
     */
    public final void abortAuction() {
        this.status.close();
    }

    /**
     * Closes the auction normally.
     */
    public final void closeAuction() {
        this.status.close(this.currentWinningBid);
    }

}
