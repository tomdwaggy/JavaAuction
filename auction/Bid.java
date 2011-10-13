package auction;

/**
 *  Bid, which represents a simple bid in an auction, for a set price and by
 *  a certain user.
*/
public class Bid implements Comparable<Bid> {
    
    private User user;
    private Auction auction;
    private Integer amount;
    
    /**
     *  Create a new bid by user for auction at the price amount.
    */
    public Bid(User user, Auction auction, Integer amount) {
        this.user = user;
        this.auction = auction;
        this.amount = amount;
    }
    
    /**
     *  Compare a bid with another bid, returning the highest bid object.
     *
     *  @require: Bids must be for the same item, by the same user.
     *  @ensure: compareTo(o) > 0 if bid is higher, compareTo(o) < 0 if bid
     *      is lower.
    */
    public boolean compareTo(Bid o) {
        if(this.user.equals(o.user) && this.auction.equals(o.auction) == 0)
            return this.amount.compareTo(o.amount);
        else
            return false;
    }
    
}