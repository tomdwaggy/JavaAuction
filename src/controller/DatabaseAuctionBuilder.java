/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import us.nstro.javaauction.auction.Auction;
import us.nstro.javaauction.auction.AuctionBuilder;
import us.nstro.javaauction.db.DatabaseInterface;

/**
 *
 * @author bbecker
 */
public class DatabaseAuctionBuilder extends AuctionBuilder {

    public AuctionBuilder readAuctionFromDatabase(Integer auctionID, DatabaseInterface db) {
        
    }

    public Auction build() {
        AuctionBuilder builder;
        return new Auction();
    }

}
