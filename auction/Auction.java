package auction;

public interface Auction {
    
    /**
     *  Gets a range of valid bids for this particular auction.
     *
     *  @ensure: getValidBids().contains(i) for all i which is a valid
     *      bid.
    */
    public Range<Bid> getValidBids();
    
    /**
     *  Places a bid on this auction.
     *
     *  @require: getValidBids().contains(bid)
    */
    public placeBid(Bid bid);
    
}