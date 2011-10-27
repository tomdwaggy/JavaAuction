package us.nstro.javaauction.auction;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This TimerTask simply closes the auction when it is executed. It is to
 * be scheduled for a single execution.
 *
 * @author bbecker
 */
class CloseAuction extends TimerTask {
    private Auction auction;

    public CloseAuction(Auction auction) {
        this.auction = auction;
    }

    @Override
    public void run() {
        this.auction.closeAuction();
    }
}

/**
 * The AuctionManager manages the mapping of auction id's to auctions, and
 * has a timer which stops auctions when they expire.
 *
 * @author bbecker
 */
public class AuctionManager {

    private Map<Integer, Auction> id_auction;

    private Timer closeTimer;

    /**
     * Create a new AuctionManager object.
     */
    public AuctionManager() {
        this.id_auction = new HashMap<Integer, Auction>();
        this.closeTimer = new Timer();
    }

    /**
     * Add a new Auction to the AuctionManager with the given ID and an
     * auction builder.
     *
     * @param auctionID a new auction ID
     * @param builder an auction builder
     * @return reference to the Auction which was built
     *
     * @require: this.getAuction(auctionID) is not valid
     * @ensure: this.getAuction(auctionID) != null
     */
    public Auction createAuction(Integer auctionID, AuctionBuilder builder) {
        Auction auction = builder.build(auctionID);
        this.id_auction.put(auctionID, auction);
        this.closeTimer.schedule(new CloseAuction(auction), auction.getInfo().getEndDate());
        auction.startAuction();
        return auction;
    }

    /**
     * Gets an already existing Auction from the AuctionManager.
     *
     * @param auctionID
     * @return
     */
    public Auction getAuction(Integer auctionID) {
        return this.id_auction.get(auctionID);
    }

    /**
     * Removes an auction from the AuctionManager.
     * 
     * @param auctionID
     */
    public void removeAuction(Integer auctionID) {
        this.id_auction.remove(auctionID);
    }
    
}
