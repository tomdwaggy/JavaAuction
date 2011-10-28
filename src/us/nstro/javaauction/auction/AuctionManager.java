package us.nstro.javaauction.auction;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import us.nstro.javaauction.handler.Ticker;
import us.nstro.javaauction.handler.TimerTicker;

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

    private Timer timer;

    /**
     * Create a new AuctionManager object.
     */
    public AuctionManager() {
        this.id_auction = new HashMap<Integer, Auction>();
        this.timer = new Timer();
    }

    /**
     * Gets a list of all open Auctions.
     */
    public List<Auction> listOpenAuctions() {
        List<Auction> list = new LinkedList<Auction>();
        for(Auction auction : id_auction.values())
            if(auction.isOpen())
                list.add(auction);
        return list;
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
        builder.setAuctionID(auctionID);
        Auction auction = builder.build();

        this.id_auction.put(auctionID, auction);

        if(auction.getInfo().getEndDate() != null)
            this.timer.schedule(new CloseAuction(auction), auction.getInfo().getEndDate());

        auction.startAuction();
        
        return auction;
    }

    /**
     * Create a new Ticker for use in keeping time of auction events in
     * sync. It should only be used for auction events utilizing this particular
     * auction manager.
     *
     * @long millis milliseconds of delay to tick for
     *
     * @return a new Ticker implementation based on the Auction Manager
     */
    public Ticker createTicker(long millis) {
        TimerTicker tick = new TimerTicker();
        this.timer.schedule(tick, millis, millis);
        return tick;
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
