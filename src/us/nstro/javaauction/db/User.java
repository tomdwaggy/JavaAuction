package us.nstro.javaauction.db;

import java.util.LinkedList;

/**
 * This class models an auction program user
 * @author Steven Bolin
 */
public class User {
  
  private int userId;
  private String firstName;
  private String lastName;
  private String title;
  private boolean isAdmin;
  private String login;
  private LinkedList<Bid> bidList;
  private LinkedList<Auction> auctionList;
//  private boolean loggedIn;
  


  /**
   * This constructor creates an instance of a logged in User
   * @require - a valid user id<br>
   *            name is an array of size 3 where: <br>
   *                name[0] = first name<br>
   *                name[1] = last name<br>
   *                name[2] = title<br>
   *            login is the users login name<br>
   *            isAdmin is true if the user is an admin, or false otherwise
   * @ensure - creates an instance of User
   * @param userId
   * @param name
   * @param login
   * @param isAdmin
   */
  public User(int userId, String[] name, String login, boolean isAdmin) {
      
      this.userId = userId;
      this.firstName = name[0];
      this.lastName = name[1];
      this.title = name[2];
      this.isAdmin = isAdmin;
      this.login = login;
      this.bidList = new LinkedList<Bid>();
      this.auctionList = new LinkedList<Auction>();
//      this.loggedIn = true;
  }
  
  /**
   * Constructor creates an instance of a User which is not logged in
   */
//  public User() {
//      
//      this.userId = -1;
//      this.firstName = null;
//      this.lastName = null;
//      this.title = null;
//      this.isAdmin = false;
//      this.login = "Guest";
//      this.bidList = new LinkedList();
//      this.auctionList = new LinkedList();
//      this.loggedIn = false;
//    
//  }
  
  /**
   * returns the number of auctions this user is owner of
   * @ensure returns the number of auctions this user is owner of
   * @return 
   */
  public int auctionCount() {
    return auctionList.size();
  }
  
  /**
   * Returns the number of bids this user has placed
   * @ensure returns the number of bids this user has placed
   * @return the number of bids this user has placed
   */
  public int bidCount() {
    return bidList.size();
  }
  
  /**
   * Returns whether the user is logged in.
   * @ensure Returns true if the user is logged in, false otherwise
   * @return True if the user is logged in, false if otherwise
   */
//  public boolean isLogged() {
//    return this.loggedIn;
//  }
  
  /**
   * Add a list of all of this users auctions to this user
   * @require userAuctionList != null
   *          loggedIn == true
   * @ensure this.getAuctionList.equals(userAuctionList)
   * @param userAuctionList 
   */
  public void addAuctionList(LinkedList<Auction> userAuctionList) {
    this.auctionList = userAuctionList;
  }
  
  /**
   * Add an auction to this users list of auctions
   * @require auction != null
   *          loggedIn == true
   * @ensure this.auctionList.size() == old.auctionList.size() + 1
   * @param auction 
   */
  public void addAuction(Auction auction) {
    this.auctionList.add(auction);
  }
  
  /**
   * Remove an auction from this users list of auctions
   * @require auction != null
   *          loggedIn == true
   * @ensure this.auctionList.size() == old.auctionlist.size() - 1
   * @param auction 
   */
  public void deleteAuction(Auction auction) {
    this.auctionList.remove(auction);
  }
  
  /**
   * Remove an auction from this users list of auctions
   * @require auction != null
   *          loggedIn == true
   * @ensure this.auctionList.size() == old.auctionlist.size() - 1
   * @param auction 
   */
  public void deleteAuction(int index) {
    this.auctionList.remove(index);
  }
  
  /**
   * Add a list of all of this users bids to this user
   * @require userBidList != null
   *          loggedIn == true
   * @ensure this.getBidList.equals(userBidList)
   * @param userBidList 
   */
  public void addBidList(LinkedList<Bid> userBidList) {
    this.bidList = userBidList;
  }
  
  /**
   * Add a bid to the bid list of this user
   * @require bid != null
   *          loggedIn == true
   * @ensure this.bid.size() == old.bid.size() + 1
   * @param bid 
   */
  public void addBid(Bid bid) {
    this.bidList.add(bid);
  }
  
  /**
   * Remove a bid form this users list of bids
   * @require bid != null
   *          loggedIn == true
   * @ensure this.bidList.size() == old.bidList.size() + 1
   * @param bid 
   */
  public void removeBid(Bid bid) {
    this.bidList.remove(bid);
  }
  
  /**
   * Remove a bid form this users list of bids
   * @require bid != null
   *          loggedIn == true
   * @ensure this.bidList.size() == old.bidList.size() + 1
   * @param bid 
   */
  public void removeBid(int index) {
    this.bidList.remove(index);
  }
  
  /**
   * Get the list of this users bids
   * @require 
   *          loggedIn == true
   * @ensure returns the list of this users bids
   * @return 
   */
  public LinkedList<Bid> getBidList() {
    return this.bidList;
  }
  
  /**
   * get the list of this users auctions
   * @require 
   *          loggedIn == true
   * @ensure returns the list of this users auctions
   * @return 
   */
  public LinkedList<Auction> getAuctionList() {
    return this.auctionList;
  }
  
  
  /**
   * this users user id
   * @require 
   *          loggedIn == true
   * @ensure Returns this users user id
   * @return this users user id
   */
  public int getUserId() {
    
    return this.userId;
  }
  
  /**
   * this users first name
   * @require
   *          loggedIn == true
   * @ensure returns this users first name
   * @return this users first name
   */
  public String getfirstName() {
    return this.firstName;
  }
  
  /**
   * this users last name
   * @require 
   *          loggedIn == true
   * @ensure returns this users last name
   * @return returns this users last name
   */
  public String getLastName() {
    return this.lastName;
  }
  
  /**
   * this users title
   * @require
   *          loggedIn == true
   * @ensure returns this users title, such as Mr. Mrs. Ms. Dr. etc
   * @return returns this users title, such as Mr. Mrs. Ms. Dr. etc
   */
  public String getTitle() {
    return this.title;
  }
  
  /**
   * get the users admin status
   * @require 
   *          loggedIn == true
   * @ensure returns true if this user is an admin, false otherwise
   * @return returns true if this user is an admin, false otherwise
   */
  public boolean isAdmin() {
    return this.isAdmin;
  }
  
  /**
   * get the users login id
   * @ensure returns this users login, or "Guest" if loggedIn == false
   * @return returns this users login
   */
  public String getLogin() {
    return this.login;
  }
  
  /**
   * Updates the users first name
   * @require fName != null
   *          loggedIn == true
   * @ensure  This users first name updated to the String given by fName.
   * @param fName A String representing the new value for this users first name
   */
  public void updateFirstName(String fName) {
    this.firstName = fName;
  }
  
  /**
   * Updates the users last name
   * @require lName != null
   *          loggedIn == true
   * @ensure  This users last name updated to the String given by lName.
   * @param fName A String representing the new value for this users last name
   */
  public void updateLastName(String lName) {
    this.lastName = lName;
  }
  
  /**
   * Updates the users title
   * @require title != null
   *          loggedIn == true
   * @ensure This users title updated to the String given by title 
   * @param title
   */
  public void updateTitle(String title) {
    this.title = title;
  }
  
  /**
   * Updates the users isAdmin status
   * @require status != null
   *          loggedIn == true
   * @ensure This users admin status updated to the value given by status 
   * @param status true if has admin status, false otherwise
   */
  public void updateIsAdmin(boolean status) {
    this.isAdmin = status;
  }
  
  /**
   * Updates the users login id
   * @require login != null
   *          loggedIn == true
   * @ensure This users login id updated to the value given by login <
   * @param login
   */
  public void updateLogin(String login) {
    this.login = login;
  }

      /**
     *  Determine if two users are the same
     *
     *  @require: Object to be compared with must be a User
     *  @ensure:
     *      if o.userID = this.userID, this.equals(o) == true
     *      if o.userID != this.userID, this.equals(o) == false
    */
    @Override
    public boolean equals(Object o) {
        if(o instanceof User)
            return this.userId == ((User)o).userId;
        else
            return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.userId;
        return hash;
    }
    
} // end of class
