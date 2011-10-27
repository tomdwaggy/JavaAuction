package us.nstro.javaauction.auction;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author bbecker
 */
public class AuctionManager {

    private Integer nextValid = 0;
    private Map<Integer, Auction> id_auction;

    public AuctionManager() {
        this.id_auction = new HashMap<Integer, Auction>();
    }

    private Integer getValidNewID() {
        Integer valid = this.nextValid;
        this.nextValid = this.nextValid + 1;
        return valid;
    }

    public Auction addAuction(AuctionBuilder builder) {
        Auction auction = builder.build(this.getValidNewID());
        this.id_auction.put(auction.getID(), auction);
        return auction;
    }

    public Auction getAuction(Integer auctionID) {
        return this.id_auction.get(auctionID);
    }

    public void removeAuction(Integer auctionID) {
        this.id_auction.remove(auctionID);
    }
    
}
