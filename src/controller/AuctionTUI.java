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

/**
 *
 * @author bbecker
 */
public class AuctionTUI {

    private User testUser;
    private BufferedReader read;
    private AuctionManager auctionManager;

    public AuctionTUI(User user) {
        this.testUser = user;
        this.read = new BufferedReader(new InputStreamReader(System.in));
        this.auctionManager = new AuctionManager();
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
            builder.setProduct(Item.createItem(this.getStringFromPrompt("Item name")));
            builder.setEndDate(this.getDateFromPrompt("Ending Date"));
            builder.setMinimumBid(new Price(this.getFloatFromPrompt("Minimum bid")));
            this.auctionManager.createAuction(Integer.MIN_VALUE, builder);
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
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
            System.out.println(auction.getID() + "\t\t" + auction.getInfo().getName());
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
