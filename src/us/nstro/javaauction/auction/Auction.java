package us.nstro.javaauction.auction;

import us.nstro.javaauction.types.selection.Selection;

/**
 *  The Auction interface allows clients to determine what bids are valid, and
 *  to place bids.
*/
public interface Auction {
    
    /**
     *  Gets a selection of valid bid prices for this auction.
     *
     *  @ensure: getValidBids().contains(i) for all i which is a valid
     *      bid.
    */
    public Selection<Price> getValidPrices();
    
    /**
     *  Places the bid 'bid' on this auction. After bidding, the
     *
     *  @require: getValidBids().contains(bid)
    */
    public void placeBid(Bid bid);
    
}