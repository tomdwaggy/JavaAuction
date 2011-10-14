package us.nstro.javaauction.auction.ascending;

import us.nstro.javaauction.types.selection.Selection;
import us.nstro.javaauction.types.selection.BoundedSelection;
import us.nstro.javaauction.auction.Price;
import us.nstro.javaauction.auction.Bid;
import us.nstro.javaauction.auction.AbstractAuction;
import us.nstro.javaauction.auction.AuctionInfo;

/**
 *  The AscendingAuction implements an Auction which is a traditional open
 *  ascending auction, like E-Bay.
*/
public class AscendingAuction extends AbstractAuction {
    
    private Selection<Price> validPrices;
    
    /**
     *  Creates a new Ascending auction with a minimum bid of min.
    */
    public AscendingAuction(AuctionInfo info, Price min) {
        super(info);
        this.validPrices = new BoundedSelection<Price>(min);
    }
    
    /**
     *  Gets a selection of valid bid prices for this auction.
     *
     *  @ensure: getValidBids().contains(i) for all i which is a valid
     *      bid.
    */
    public Selection<Price> getValidPrices() {
        return this.validPrices;
    }
    
    /**
     *  Places the bid 'bid' on this auction. After bidding, the
     *
     *  @require: getValidBids().contains(bid)
    */
    public void placeBid(Bid bid) {
        
    }
    
}