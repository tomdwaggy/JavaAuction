package us.nstro.javaauction.auction;

import us.nstro.javaauction.types.selection.Selection;

/**
 * The Auction interface allows clients to determine what bids are valid, and
 * to place bids. It also contains AuctionInfo and AuctionStatus which provide
 * information about the auction in a general case (what is being sold, etc).
*/
public interface Auction {

    /**
     * Get the auction information. This is static information about an
     * auction, such as who the auctioneer is, what is being auctioned off,
     * the auction name and ID, etc.
     *
     * @ensure: getInfo() != null
     */
    public AuctionInfo getInfo();

    /**
     * Gets the auction status. This is dynamic data, such as whether the
     * auction is closed or not, and who the winner is (if closed).
     *
     * @ensure: getInfo() != null
     */
    public AuctionStatus getStatus();

    /**
     * Gets a selection of valid bid prices for this auction.
     *
     * @require: getStatus.isClosed() == false
     * @ensure: getValidBids().contains(i) for all i which is a valid
     *     bid.
    */
    public Selection<Price> getValidPrices();
    
    /**
     *  Places the bid 'bid' on this auction. After bidding, the
     *
     *  @require: getStatus.isClosed() == false &&
     *      getValidBids().contains(bid)
    */
    public void placeBid(Bid bid);

    /**
     * Closes the auction normally, committing the winning bid.
     *
     * @require: getSTatus().isClosed() == false
     * @ensure: getStatus().isClosed() == true
     */
    public void closeAuction();

    /**
     * Aborts the auction, not committing any current winners.
     *
     * @require: getStatus().isClosed() == false
     * @ensure: getStatus().isClosed() == true &&
     *      getStatus().hasWinner() == false
     */
    public void abortAuction();
    
}