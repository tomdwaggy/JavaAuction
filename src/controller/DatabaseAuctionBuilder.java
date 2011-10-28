/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import us.nstro.javaauction.auction.AscendingAuctionBuilder;
import us.nstro.javaauction.auction.Auction;
import us.nstro.javaauction.auction.AuctionBuilder;
import us.nstro.javaauction.db.DatabaseException;
import us.nstro.javaauction.db.DatabaseInterface;

/**
 *
 * @author bbecker
 */
public class DatabaseAuctionBuilder extends AscendingAuctionBuilder {

    private DatabaseInterface db;

    public DatabaseAuctionBuilder(DatabaseInterface db) {
        this.db = db;
    }

    public AuctionBuilder setFromDatabase(Integer auctionID) throws DatabaseException {
        this.setAuctionName(this.db.getAuctionTitle(auctionID));
        this.setAuctionDescription(this.db.getAuctionDescription(auctionID));
        return this;
    }

}
