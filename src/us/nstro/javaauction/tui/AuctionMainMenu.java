package us.nstro.javaauction.tui;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import us.nstro.javaauction.auction.AscendingAuctionBuilder;
import us.nstro.javaauction.auction.Auction;
import us.nstro.javaauction.auction.AuctionManager;
import us.nstro.javaauction.bids.Bid;

import us.nstro.javaauction.bids.Item;
import us.nstro.javaauction.bids.Price;
import us.nstro.javaauction.db.DatabaseException;
import us.nstro.javaauction.db.DatabaseInterface;
import us.nstro.javaauction.db.SQLiteConnection;
import us.nstro.javaauction.db.UserManager;

import us.nstro.javaauction.db.User;

/**
 * Main Text User Interface of the Auction client.
 *
 * @author bbecker
 */
public class AuctionMainMenu {

    private Prompt prompt;

    private int userId;
    private DatabaseInterface dbi;

    private AuctionManager auctionManager;
    private UserManager userManager;

    public AuctionMainMenu() {
        this.prompt = new Prompt();
        this.userId = -1;
        this.auctionManager = new AuctionManager();
        this.userManager = new UserManager();
        this.dbi = new SQLiteConnection();
        this.readAuctionsDatabase();
    }

    public void start() {
        while(true) {
            while(this.userId <= 0)
                this.showLoginMenu();
            this.showAuctionMenu();
        }
    }

    private void showLoginMenu() {
        System.out.println("[1] Create User Account");
        System.out.println("[2] Login");
        System.out.println("[0] End the Program");
        int choice = this.prompt.getInteger("Enter your selection");
        switch(choice) {
            case 1:
                doCreateUserAccount(); break;
            case 2:
                doLogin(); break;
            case 0:
                doQuit(); break;
        }
    }

    private void doCreateUserAccount() {
      String[] name = {"FirstName", "LastName", "Title"};
      String login = "";
      boolean unique = false;

      try {

        while(!unique) {
            login = this.prompt.getString("Enter the user login name");
            unique = this.dbi.uniqueLogin(login);
            if(!unique) {
                System.out.println("Sorry, but the name " + login + " has been taken.");
                boolean tryAgain = this.prompt.getYesNo("Try again?");
                if(!tryAgain)
                    return;
            }
        }

        String password =  this.prompt.getString("Enter the user password");

        name[0] = this.prompt.getString("Enter first name");
        name[1] = this.prompt.getString("Enter last name");
        name[2] = this.prompt.getString("Enter title");

        userId = this.dbi.addUser(login, password);
        this.dbi.updateUserFirstName(userId, name[0]);
        this.dbi.updateUserLastName(userId, name[1]);
        this.dbi.updateUserTitle(userId, name[2]);

        userManager.addUser(userId, name, login);

      } catch (DatabaseException ex) {
        Logger.getLogger(AuctionMainMenu.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

    private void doLogin() {

      boolean again = true;

      while(userId < 0 && again) {
      String login = this.prompt.getString("Enter login");
      String password = this.prompt.getString("Enter password");

        try {

          userId = this.dbi.login(login, password);

          if(userId < 0) {
            System.out.println("Username or Password not valid.");
            again = this.prompt.getYesNo("Try again?");
          } else {
            String name[] = this.dbi.getUserName(userId);
            this.userManager.addUser(userId, name, login);
            System.out.println("Welcome " + userManager.getUserById(userId).getFirstName());
          }

        } catch (DatabaseException ex) {
          Logger.getLogger(AuctionMainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

      } //  end of while

    } // end of method

    private User getOrReadUser(int userID) throws DatabaseException {
        if(!this.userManager.containsUserId(userID)) {
            String login = this.dbi.getUserLogin(userID);
            String[] name = this.dbi.getUserName(userID);
            this.userManager.addUser(userID, name, login);
        }
        return this.userManager.getUserById(userID);
    }

    private User readAuctioneerFor(int auctionID) throws DatabaseException {
        int auctioneerID = this.dbi.getAuctionOwner(auctionID);
        return this.getOrReadUser(auctioneerID);
    }

    private User readBidderFor(int bidID) throws DatabaseException {
        int bidderID = this.dbi.getBidUserId(bidID);
        return this.getOrReadUser(bidderID);
    }

    private void readBidsForAuction(Auction auction) throws DatabaseException {
        int auctionID = auction.getID();
        if(this.dbi.auctionHasBids(auctionID)) {
            for(int bidID : this.dbi.getBidsForAuction(auctionID)) {
                User bidder = this.readBidderFor(bidID);
                Price price = Price.fromInteger(this.dbi.getBidAmount(bidID));
                Bid bid = new Bid(bidder, auction, price);
                if(auction.getValidPrices().contains(price)) {
                    auction.placeBid(bid);
                }
            }
        }
    }

    private void readAuctionsDatabase() {
        try {
            AuctionDatabaseLink loader = new AuctionDatabaseLink(this.dbi);
            for(int auctionID : this.dbi.getAuctionAllIds()) {
                if(this.dbi.getAuctionEnabled(auctionID)) {
                    User auctioneer = this.readAuctioneerFor(auctionID);
                    Auction auction = this.auctionManager.createAuction(auctionID, loader.getAuctionBuilder(auctionID, auctioneer));
                    if(auction.isOpen())
                        this.readBidsForAuction(auction);
                }
            }
        } catch (DatabaseException dbe) {
            // Failed to read database
            System.out.println(dbe.getWrappedException().toString());
        }
    }

    private void doLogout() {
      this.userId = -1;
    }

    private void doCreateAscendingAuction() {
        AscendingAuctionBuilder builder = new AscendingAuctionBuilder();
        try {
            builder.setAuctioneer(this.userManager.getUserById(userId));
            builder.setAuctionName(this.prompt.getString("Auction name"));
            builder.setAuctionDescription(this.prompt.getString("Auction description"));
            builder.setProduct(Item.createItem(this.prompt.getString("Item name")));
            builder.setEndDate(this.prompt.getDate("Ending Date"));
            Price minimumBid = Price.fromFloat(this.prompt.getFloat("Minimum bid"));
            builder.setMinimumBid(minimumBid);
            int auctionID = this.dbi.addAuction(userId);
            Auction auction = this.auctionManager.createAuction(auctionID, builder);

            this.dbi.updateAuctionType(auctionID, 0);
            this.dbi.updateAuctionTitle(auctionID, auction.getInfo().getName());
            this.dbi.updateAuctionDescription(auctionID, auction.getInfo().getDescription());
            this.dbi.updateAuctionCurrentAsking(auctionID, minimumBid.cents());
            this.dbi.updateAuctionCurrentBid(auctionID, minimumBid.cents());
            this.dbi.updateAuctionStartTime(auctionID, new Date().getTime());
            this.dbi.updateAuctionStopTime(auctionID, auction.getInfo().getEndDate().getTime());
        } catch (DatabaseException dbe) {
            System.out.println(dbe.toString());
        }
    }

    private void doCreateAuction() {
        System.out.println("What type of Auction?");
        System.out.println("[1] Ascending Auction");
        System.out.println("[0] Go back");
        int choice = this.prompt.getInteger("Enter your selection");
        if(choice == 1)
            doCreateAscendingAuction();
    }

    private void doListOpenAuctions() {
        System.out.println("Showing open Auctions");
        System.out.println("---------------------");
        for(Auction auction : this.auctionManager.listOpenAuctions()) {
            System.out.println(auction.getID() + "\t" + auction.getInfo().getName());
        }
    }

    private void doAuctionInfo() {
        int choice = this.prompt.getInteger("Auction ID to get info for");
        Auction auction = this.auctionManager.getAuction(choice);

        if(auction == null) {
            System.out.println("That is not a valid auction ID.");
            return;
        }

        System.out.println("Auction name: " + auction.getInfo().getName());
        System.out.println("Auction description: " + auction.getInfo().getDescription());
        System.out.println("Auctioneer: " + auction.getInfo().getAuctioneer().getLogin());
        // System.out.println("Product(s): ");
        
        if(auction.getWinningBids().size() == 1) {
            Bid winning = auction.getWinningBids().iterator().next();
            System.out.println("Current Winner: " + winning.getUser().getLogin());
        } else if(auction.getWinningBids().size() > 1) {
            System.out.println("Current Winner(s): ");
            for(Bid winning : auction.getWinningBids())
                System.out.println(winning.getUser().getLogin());
        }

        System.out.println("Current Valid Bids: " + auction.getValidPrices().toString());

        for(Item i : auction.getInfo().getProducts())
            System.out.println(i.getID() + " - " + i.getName());

        System.out.println("Closes: " + auction.getInfo().getEndDate());
    }

    private void doBidAuction() {
        int choice = this.prompt.getInteger("Auction ID to bid on");
        Auction auction = this.auctionManager.getAuction(choice);

        if(auction == null) {
            System.out.println("That is not a valid auction ID.");
            return;
        }

        System.out.println("Valid bidding prices: " + auction.getValidPrices().toString());
        Price price = Price.fromFloat(this.prompt.getFloat("How much do you want to bid?"));

        try {
            if(auction.getValidPrices().contains(price)) {
                System.out.println("Placing bid of " + price.toString() + " on auction " + choice + ".");
                boolean yesno = this.prompt.getYesNo("Really place the bid?");
                if(yesno) {
                    auction.placeBid(new Bid(this.userManager.getUserById(this.userId), auction, price));
                    this.dbi.addBid(choice, this.userId, price.cents());
                }
            } else {
                System.out.println("That is not a valid bid for this auction.");
            }
        } catch(DatabaseException dbe) {
            dbe.getWrappedException().printStackTrace(System.out);
        }
    }

    private void showAuctionMenu() {
        System.out.println("[1] Create a new Auction");
        System.out.println("[2] List open Auctions");
        System.out.println("[3] Auction Information");
        System.out.println("[4] Bid on an Auction");
        System.out.println("[5] Logout");
        System.out.println("[0] End the Program");
        int choice = this.prompt.getInteger("Enter your selection");
        switch(choice) {
            case 1:
                doCreateAuction(); break;
            case 2:
                doListOpenAuctions(); break;
            case 3:
                doAuctionInfo(); break;
            case 4:
                doBidAuction(); break;
            case 5:
                doLogout(); break;
            case 0:
                doQuit(); break;
        }
    }

    private void doQuit() {
        System.exit(0);
    }

}
