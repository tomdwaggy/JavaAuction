package auction;

/**
 *  Bid, which represents a user's bid in a specified auction.
*/
public class Bid {

    private User user;
    private Auction auction;
    private Price price;

    /**
     *  Create a new bid by user for auction at the price amount.
    */
    public Bid(User user, Auction auction, Price price) {
        this.user = user;
        this.auction = auction;
        this.amount = amount;
    }
    
    /**
     *  Get the User who has posted this bid.
    */
    public User getUser() {
        return this.user;
    }
    
    /**
     *  Get the Auction which this bid is for.
    */
    public Auction getAuction() {
        return this.auction;
    }
    
    /**
     *  Get the Price which this bid specifies.
    */
    public Price getPrice() {
        return this.price;
    }
    
}