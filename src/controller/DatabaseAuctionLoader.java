package controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import us.nstro.javaauction.auction.AscendingAuctionBuilder;
import us.nstro.javaauction.auction.AuctionBuilder;
import us.nstro.javaauction.auction.DutchAuctionBuilder;
import us.nstro.javaauction.auction.SealedFirstBidAuctionBuilder;
import us.nstro.javaauction.bids.Item;
import us.nstro.javaauction.db.DatabaseException;
import us.nstro.javaauction.db.DatabaseInterface;

/**
 *
 * @author bbecker
 */
public class DatabaseAuctionLoader {

    private DatabaseInterface db;

    public DatabaseAuctionLoader(DatabaseInterface db) {
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

    public AuctionBuilder getAuctionBuilder(Integer auctionID) throws DatabaseException {
        int auctionType = this.db.getAuctionTypeId(auctionID);
        if(auctionType == 0)
            return this.getAscendingAuctionBuilder(auctionID);
        else if(auctionType == 1)
            return this.getDutchAuctionBuilder(auctionID);
        else if(auctionType == 2)
            return this.getSealedFirstBidAuctionBuilder(auctionID);
        else
            throw new UnsupportedOperationException("No builder for Auction Type " + auctionType);
    }

}
