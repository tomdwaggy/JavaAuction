package us.nstro.javaauction.db;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * This class models a user manager for the auction program
 * @author Steven Bolin
 */
public final class UserManager {
  
  private LinkedList<User> userList;

  /**
   * Constructor
   *          Creates a new instance of the user manager
   */
  public UserManager() {
    userList = new LinkedList<User>();
    
  }
  
  /**
   * Create a logged in user adding user to the list of users and the list of logged
   * in users
   * @require a valid user id<br>
   *            name is an array of size 3 where: <br>
   *                name[0] = first name<br>
   *                name[1] = last name<br>
   *                name[2] = title<br>
   *            login is the users login name<br>
   *            isAdmin is true if the user is an admin, or false otherwise
   * @ensure 
   *          this.userList.size() == old.user.size() + 1
   *          this.loggedIn.size() == old.loggedIn.size() + 1
   */
  public void addUser(int userId, String[] name, String login) {
    User user = new User(userId, name, login, true);
    userList.add(user);
  }
  
  /**
   * Removes a user from the lsit of users registered with the User Manager
   * @require user != null
   * @ensure this.numberOfUsers() = old.numberOfUsers() - 1
   * @param user the User object to remove from the User Manager
   */
  public void removeUser(User user) {
    userList.remove(user);
  }
  
  
  /**
   * Returns the number of users registered with the User Manager
   * @ensure returns the number of users
   * @return 
   */
  public int numberOfUsers() {
    return this.userList.size();
  }
  
  
  /**
   * Returns a list of users which are logged in
   * @require userList.size > 0
   * @ensure returns a list of all users which are logged in
   * @return 
   */
  private LinkedList<User> getUserList() {
    
    LinkedList<User> result = new LinkedList<User>();
    Iterator<User> iter = userList.iterator();
    User user = null;
    while(iter.hasNext()) {
      user = iter.next();
//      if(user.isLogged())
        result.add(user);
    }
    return result;
  }
  
  /**
   * Returns a User specified by userId
   * @require userId > 0
   * @ensure Returns User specified by userId
   * @param userId the user id for the user being requested
   * @return user specified by userId
   */
  public User getUserById(int userId) {
    
    Iterator<User> iter = userList.iterator();
    User result = null;
    boolean foundIt = false;
    
    while(iter.hasNext() && !foundIt) {
      result = iter.next();
      
      if(result.getUserId() == userId)
        foundIt = true;
    }
    return result;
  }
  
  /**
   * Returns the User who is the owner of the auction specified by auctionId
   * @require auction != null
   * @ensure returns the User who is the owner of the auction specified by auctionId
   * @param auction the auction for which to find the user
   * @return the User who is the owner of the auction specified by auctionId
   */
  public User getUserByAuction(Auction auction) {
    
    Iterator<User> iter = userList.iterator();
    User result = null;
    User user = null;
    boolean foundIt = false;
    LinkedList<Auction> aList = null;
    
    while(iter.hasNext() && !foundIt) {
      user = iter.next();
      aList = user.getAuctionList();
      
      if(aList.contains(auction)) {
        result = user;
        foundIt = true;
      }
    }
    
    return result;
  }
  
  /**
   * Returns the user who placed the bid
   * @require bid != null
   * @ensure returns the user who placed the bid
   * @param bid a bid object for which to find the user who placed it
   * @return returns the user who placed the bid
   */
  public User getUserByBid(Bid bid) {
    
    Iterator<User> iter = userList.iterator();
    User user = null;
    User result = null;
    boolean foundIt = false;
    LinkedList<Bid> bList = null;
    
    while(iter.hasNext()) {
      user = iter.next();
      bList = user.getBidList();
      
      if(bList.contains(bid)) {
        result = user;
        foundIt = true;
      }
    }
    return result;
  }
  
} // end of class
