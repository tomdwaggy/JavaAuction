package auction.ascending;

/**
 *  The AscendingAuction implements an Auction which is a traditional open
 *  ascending auction, like E-Bay.
*/
public class AscendingAuction implements Auction {
    
    private Selection validPrices;
    
    /**
     *  Creates a new Ascending auction with a minimum bid of min.
    */
    public AscendingAuction(Price min) {
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
    public placeBid(Bid bid) {
        
    }
    
}