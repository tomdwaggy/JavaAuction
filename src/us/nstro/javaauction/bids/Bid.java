package us.nstro.javaauction.bids;

import us.nstro.javaauction.auction.Auction;
import us.nstro.javaauction.auction.User;

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
        this.price = price;
    }
    
    /**
     *  Get the User who has posted this bid.
    */
    public User getUser() {
        return this.user;
    }
    
    /**
     *  Get the AbstractAuction which this bid is for.
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

    @Override
    public String toString() {
        return "Bid { By: " + this.user.getName() + " At: " + this.price.toString() + "}";
    }
    
}