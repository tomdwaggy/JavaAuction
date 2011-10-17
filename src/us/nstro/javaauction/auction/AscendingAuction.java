package us.nstro.javaauction.auction;

import us.nstro.javaauction.types.selection.Selection;

/**
 *  The AscendingAuction implements an Auction which is a traditional open
 *  ascending auction, like E-Bay.
 */
public class AscendingAuction extends AbstractAuction {
    
    /**
     *  Creates a new Ascending auction with a minimum bid of min.
     */
    public AscendingAuction(AuctionInfo info, Price min) {
        super(info);
        this.updateValidPrices(new Selection<Price>(min));
    }
    

    
}