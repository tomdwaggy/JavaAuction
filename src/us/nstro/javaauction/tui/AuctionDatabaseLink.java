package us.nstro.javaauction.tui;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import us.nstro.javaauction.auction.AscendingAuctionBuilder;
import us.nstro.javaauction.auction.AuctionBuilder;
import us.nstro.javaauction.auction.DutchAuctionBuilder;
import us.nstro.javaauction.auction.SealedFirstBidAuctionBuilder;
import us.nstro.javaauction.bids.Item;
import us.nstro.javaauction.bids.Price;
import us.nstro.javaauction.db.DatabaseException;
import us.nstro.javaauction.db.DatabaseInterface;
import us.nstro.javaauction.db.User;

/**
 *
 * @author bbecker
 */
public class AuctionDatabaseLink {

    private DatabaseInterface db;

    public enum AuctionType {
        
        ASCENDING(0), DUTCH(1), SEALED(2);

        private int index;

        private AuctionType(int index) {
            this.index = index;
        }

        public boolean matches(int other) {
            return this.index == other;
        }

    }

    public AuctionDatabaseLink(DatabaseInterface db) {
        this.db = db;
    }

    private void setCommonAuctionParams(Integer auctionID, AuctionBuilder builder) throws DatabaseException {
        builder.setAuctionID(auctionID);
        builder.setAuctionDescription(this.db.getAuctionDescription(auctionID));
        builder.setAuctionName(this.db.getAuctionTitle(auctionID));
        List<Item> products = new LinkedList<Item>();
        builder.setProducts(products);
        builder.setEndDate(new Date(this.db.getAuctionStopTime(auctionID)));
    }

    private AuctionBuilder getAscendingAuctionBuilder(Integer auctionID) throws DatabaseException {
        AscendingAuctionBuilder builder = new AscendingAuctionBuilder();
        this.setCommonAuctionParams(auctionID, builder);
        builder.setMinimumBid(new Price(this.db.getAuctionCurrentBid(auctionID)));
        return builder;
    }

    private AuctionBuilder getDutchAuctionBuilder(Integer auctionID) throws DatabaseException {
        DutchAuctionBuilder builder = new DutchAuctionBuilder();
        this.setCommonAuctionParams(auctionID, builder);
        return builder;
    }

    private AuctionBuilder getSealedFirstBidAuctionBuilder(Integer auctionID) throws DatabaseException {
        SealedFirstBidAuctionBuilder builder = new SealedFirstBidAuctionBuilder();
        this.setCommonAuctionParams(auctionID, builder);
        return builder;
    }

    public AuctionBuilder getAuctionBuilder(Integer auctionID, User auctioneer) throws DatabaseException {
        int auctionType = this.db.getAuctionTypeId(auctionID);
        AuctionBuilder builder;
        
        if(AuctionType.ASCENDING.matches(auctionType))
            builder = this.getAscendingAuctionBuilder(auctionID);
        else if(AuctionType.DUTCH.matches(auctionType))
            builder = this.getDutchAuctionBuilder(auctionID);
        else if(AuctionType.SEALED.matches(auctionType))
            builder = this.getSealedFirstBidAuctionBuilder(auctionID);
        else
            throw new UnsupportedOperationException("Invalid Auction Type: " + auctionType);

        builder.setAuctioneer(auctioneer);
        return builder;
    }

}
