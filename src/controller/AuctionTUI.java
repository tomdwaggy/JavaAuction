package controller;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import us.nstro.javaauction.auction.AscendingAuctionBuilder;
import us.nstro.javaauction.auction.Auction;
import us.nstro.javaauction.auction.AuctionManager;

import us.nstro.javaauction.auction.User;
import us.nstro.javaauction.bids.Item;
import us.nstro.javaauction.bids.Price;
import us.nstro.javaauction.db.DatabaseException;
import us.nstro.javaauction.db.DatabaseInterface;
import us.nstro.javaauction.db.SQLiteConnection;

/**
 *
 * @author bbecker
 */
public class AuctionTUI {

    private DatabaseInterface dbi;

    private User testUser;
    private BufferedReader read;
    private AuctionManager auctionManager;

    public AuctionTUI(User user) {
        this.testUser = user;
        this.read = new BufferedReader(new InputStreamReader(System.in));
        this.auctionManager = new AuctionManager();
        this.dbi = new SQLiteConnection();
        this.readAuctionsDatabase();
    }

    private void readAuctionsDatabase() {
        try {
            for(int auctionID : this.dbi.getAuctionAllIds()) {
                if(this.dbi.getAuctionEnabled(auctionID)) {
                    // stub, add stuff from the database which is going to
                    // require handler for DB
                }
            }
        } catch (DatabaseException dbe) {
            // Failed to read database
            System.out.println(dbe.toString());
        }
    }

    public String getStringFromPrompt(String prompt) throws IOException {
        System.out.print(prompt + ": ");
        return this.read.readLine();
    }

    public Integer getIntegerFromPrompt(String prompt) throws IOException {
        System.out.print(prompt + ": ");
        return Integer.parseInt(this.read.readLine());
    }

    public Float getFloatFromPrompt(String prompt) throws IOException {
        System.out.print(prompt + " (e.g. 1.92): ");
        return Float.parseFloat(this.read.readLine());
    }

    public Date getDateFromPrompt(String prompt) throws IOException {
        try {
            String format = "MM-dd-yyyy";
            System.out.print(prompt + " (" + format + "): ");
            Date date = new SimpleDateFormat(format, Locale.ENGLISH).parse(this.read.readLine());
            return date;
        } catch(ParseException pe) {
            return new Date();
        }
    }

    public void doCreateAscendingAuction() {
        AscendingAuctionBuilder builder = new AscendingAuctionBuilder();
        try {
            builder.setAuctioneer(this.testUser);
            builder.setAuctionName(this.getStringFromPrompt("Auction name"));
            builder.setAuctionDescription(this.getStringFromPrompt("Auction description"));
            builder.setProduct(Item.createItem(this.getStringFromPrompt("Item name")));
            builder.setEndDate(this.getDateFromPrompt("Ending Date"));
            float minimumBid = this.getFloatFromPrompt("Minimum bid");
            builder.setMinimumBid(new Price(minimumBid));
            int auctionID = this.dbi.addAuction(this.testUser.getUserID());
            Auction auction = this.auctionManager.createAuction(auctionID, builder);
            //this.dbi.updateAuctionType(auctionID, auctionID);
            this.dbi.updateAuctionTitle(auctionID, auction.getInfo().getName());
            this.dbi.updateAuctionDescription(auctionID, auction.getInfo().getDescription());
            this.dbi.updateAuctionCurrentBid(auctionID, Math.round(minimumBid * 100));
            this.dbi.updateAuctionStartTime(auctionID, new Date().getTime());
            this.dbi.updateAuctionStopTime(auctionID, auction.getInfo().getEndDate().getTime());
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        } catch (DatabaseException dbe) {
            System.out.println(dbe.toString());
        }
    }

    public void doCreateAuction() {
        try {
            System.out.println("What type of Auction?");
            System.out.println("[1] Ascending Auction");
            System.out.println("[0] Go back");
            int choice = getIntegerFromPrompt("Enter your selection");
        if(choice == 1)
            doCreateAscendingAuction();
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }
    }

    public void doListOpenAuctions() {
        System.out.println("Showing open Auctions");
        System.out.println("---------------------");
        for(Auction auction : this.auctionManager.listOpenAuctions()) {
            System.out.println(auction.getID() + "\t" + auction.getInfo().getName());
        }
    }

    public void doBidAuction() {
        
    }

    public void doMainMenu() {
        int choice;
        try {
            do  {
                System.out.println("[3] Create a new Auction");
                System.out.println("[4] List open Auctions");
                System.out.println("[5] Bid on an Auction");
                System.out.println("[0] End the Program");
                choice = getIntegerFromPrompt("Enter your selection");
                switch(choice) {
                    case 3:
                        doCreateAuction(); break;
                    case 4:
                        doListOpenAuctions(); break;
                }
            } while (choice != 0);
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }
    }

    public static void main(String[] args) {
        AuctionTUI tui = new AuctionTUI(User.createUser("Arven"));
        System.out.println("Welcome to AQUEOUS Auction Application.");
        tui.doMainMenu();
        System.out.println("Program Terminating...");
        System.exit(0);
    }

}
