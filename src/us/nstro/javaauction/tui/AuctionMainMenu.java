package us.nstro.javaauction.tui;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import us.nstro.javaauction.auction.AscendingAuctionBuilder;
import us.nstro.javaauction.auction.Auction;
import us.nstro.javaauction.auction.AuctionManager;

import us.nstro.javaauction.bids.Item;
import us.nstro.javaauction.bids.Price;
import us.nstro.javaauction.db.DatabaseException;
import us.nstro.javaauction.db.DatabaseInterface;
import us.nstro.javaauction.db.SQLiteConnection;
import us.nstro.javaauction.db.UserManager;

import us.nstro.javaauction.db.User;

/**
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

       String login = this.prompt.getString("Enter the user login name");
       String password =  this.prompt.getString("Enter the user password");

       try {

          name[0] = this.prompt.getString("Enter first name");
          name[1] = this.prompt.getString("Enter last name");
          name[2] = this.prompt.getString("Enter title");

          userId = this.dbi.addUser(login, password);
          userManager.addUser(userId, name, login);

          this.dbi.addUser(login, password);

        } catch (DatabaseException ex) {
          Logger.getLogger(AuctionMainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void doLogin() {

      int again = 0;

      while(userId < 0 && again != 1) {
      String login = this.prompt.getString("Enter login");
      String password = this.prompt.getString("Enter password");


        try {

          userId = this.dbi.login(login, password);

          if(userId < 0) {
            System.out.println("Username/password combination was not found. Please try again.");
            again = this.prompt.getInteger("Enter 0 to exit, or any other number to try again:");
          }

        } catch (DatabaseException ex) {
          Logger.getLogger(AuctionMainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

        } //  end of while

    } // end of method

    private User readAuctioneerFor(int auctionID) throws DatabaseException {
        int auctioneerID = this.dbi.getAuctionOwner(auctionID);
        this.userManager.addUser(auctioneerID, new String[] {"", "", ""}, "NILL");
        return this.userManager.getUserById(auctioneerID);
    }

    private void readAuctionsDatabase() {
        try {
            AuctionDatabaseLink loader = new AuctionDatabaseLink(this.dbi);
            for(int auctionID : this.dbi.getAuctionAllIds()) {
                if(this.dbi.getAuctionEnabled(auctionID)) {
                    User auctioneer = this.readAuctioneerFor(auctionID);
                    this.auctionManager.createAuction(auctionID, loader.getAuctionBuilder(auctionID, auctioneer));
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
            float minimumBid = this.prompt.getFloat("Minimum bid");
            builder.setMinimumBid(new Price(minimumBid));
            int auctionID = this.dbi.addAuction(userId);
            Auction auction = this.auctionManager.createAuction(auctionID, builder);

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
        System.out.println("Product(s): ");

        for(Item i : auction.getInfo().getProducts())
            System.out.println(i.getID() + " - " + i.getName());

        System.out.println("Closes: " + auction.getInfo().getEndDate());
    }

    private void doBidAuction() {
        
    }

    private void showAuctionMenu() {
        System.out.println("Welcome " + userManager.getUserById(userId).getfirstName());
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
