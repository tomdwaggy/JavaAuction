package us.nstro.javaauction.db;

import java.sql.SQLException;

/**
 * This interface models a connection between the database for the Auction 
 * Application and any other process or object requiring dialog with the database.
 * @author Steven Bolin
 */
public interface DatabaseInterface {
  
  /**
   * Checks userId to validate that it is a userId in the database.
   * @param userId  A possible userId.
   * @return - True id userId is found in the database, false otherwise
   * @require - userId is an int >= 0
   * @ensure - returns true if userId is found in the database, false otherwise
   */
  public boolean userExists(int userId) throws DatabaseException;
  
  /**
   * Returns a String array containing several items relevant to the users 
   * name for the user specified by userId.
   * Such that: <br>
   * result[0] = userId<br>
   * result[1] = user first name<br>
   * result[2] = user last name
   * result[3] = user title
   * 
   * @param userId a valid user id
   * @return - array containing the user Id, user first name, and user last 
   * name for userId.
   * @require - userId must be a valid user id
   * @ensure - Returns a String array containing the user Id, user first name, and user last 
   * name for userId.
   */
  public String[] getUserName(int userId) throws DatabaseException;
  
  /**
   * Returns the user login name for userId
   * @param userId A valid user id
   * @return - the user login name for userId
   * @require - userId must be a valid user id
   * @ensure - Returns the user login name for userId
   */
  public String getUserLogin(int userId) throws DatabaseException;
  
  /**
   * Returns the password hash for userId
   * @param userId A valid user id
   * @return - the password hash for userId
   * @require - userId must be a valid user id
   * @ensure - Returns the password hash for userId
   */
  public String getUserPw(int userId) throws DatabaseException;
  
  /**
   * Returns the value of the getUserIsAdmin field in the users table
   * @param userId
   * @return 
   */
  public boolean getUserIsAdmin(int userId) throws DatabaseException;
  
  /**
   * Returns an int array of all user ids in the database
   * @return 
   */
  public int[] getUserAllIds() throws DatabaseException;
  
  /**
   * Returns a String array of all fields for the auction specified by auctionId.
   * <br>
   * return[0] = id<br>
   * return[1] = owner<br>
   * return[2] = title<br>
   * return[3] = type<br>
   * return[4] = current asking price<br>
   * return[5] = current bid<br>
   * return[6] = buyout price<br>
   * return[7] = start time<br>
   * return[8] = stop time<br>
   * return[9] = description<br>
   * return[10]= imageLinks<br>
   * return[11]= has buyout price (0=false, 1=true)
   * 
   * @param auctionId
   * @return 
   */
//  public String[] getAuctionData(int auctionId) throws SQLException;
  
  /**
   * Get the user id for the user who created/owns the auction specified by auctionId
   * @param auctionId
   * @return 
   */
  public int getAuctionOwner(int auctionId) throws DatabaseException;
  
  /**
   * get the auction title for the auction specified by auctionId
   * @param auctionId
   * @return 
   */
  public String getAuctionTitle(int auctionId) throws DatabaseException;
  
  /**
   * get the auction type id for the auction specified by auctionId
   * @param auctionId
   * @return 
   */
  public int getAuctionTypeId(int auctionId) throws DatabaseException;
  
  /**
   * get the name for a specific type of auction specified by auctionTypeId
   * @param auctionTypeId
   * @return 
   */
  public String getAuctionTypeName(int auctionTypeId) throws DatabaseException;
  
  /**
   * get the current asking price for a specific auction.id
   * <br> This will be given in cents.
   * @param auctionId
   * @return 
   */
  public int getAuctionCurrentAsk(int auctionId) throws DatabaseException;
  
  /**
   * get the current bid for a specific auction id.
   * <br> This will be given in cents.
   * @param auctionId
   * @return 
   */
  public int getAuctionCurrentBid(int auctionId) throws DatabaseException;
  
  /**
   * get the auction buyout price for a specific auction id
   * <br> This will be given in cents.
   * @param auctionId
   * @return 
   */
  public int getAuctionBuyout(int auctionId) throws DatabaseException;
  
  /**
   * get an auction start time for a specific auction id
   * @param auctionId
   * @return 
   */
  public long getAuctionStartTime(int auctionId) throws DatabaseException;
  
  /**
   * get the time an auction stop time for a specific auction id
   * @param auctionId
   * @return 
   */
  public long getAuctionStopTime(int auctionId) throws DatabaseException;
  
  /**
   * Get an array of all auction ids
   * @return 
   */
  public int[] getAuctionAllIds() throws DatabaseException;
 
  /**
   * Add a user to the database and return the userId for that user
   */
  public int addUser(String loginName, String loginPassword) throws DatabaseException;
  
  /**
   * Adds an auction to the database with all auction fields and returns the auctionId
   * @param buyout - 
   * @param currentAsk
   * @param currentBid
   * @param description
   * @param hasBuyout
   * @param imageLinks
   * @param owner
   * @param startTime
   * @param stopTime
   * @param title
   * @param auctionType 
   */
  public int addAuction(int buyout, int currentAsk, int currentBid, 
          String description, boolean hasBuyout,String imageLinks,
          int ownerId, int startTime, int stopTime, String title, int auctionType) throws DatabaseException;
  
  /**
   * Create an empty auction. 
   * @param ownerId
   * @return ownerID
   */
  public int addAuction(int ownerId) throws DatabaseException;

  public boolean getAuctionEnabled(int auctionId) throws DatabaseException;
  
  /**
   * Update an auction buyout price
   * @param auctionId
   * @param buyoutPrice 
   */
  public void updateAuctionBuyout(int auctionId, int buyoutPrice) throws DatabaseException;
  
  /**
   * Update an auctions current asking price
   * @param auctionId
   * @param currentAsking 
   */
  public void updateAuctionCurrentAsking(int auctionId, int currentAsking) throws DatabaseException;
  
  /**
   * Update an auctions current bid price
   * @param auctionId
   * @param currentBid 
   */
  public void updateAuctionCurrentBid(int auctionId, int currentBid) throws DatabaseException;
  
  /**
   * Update an auction description
   * @param auctionId
   * @param description 
   */
  public void updateAuctionDescription(int auctionId, String description) throws DatabaseException;
  
  /**
   * update an auction has buyout setting
   * @param auctionId
   * @param hasBuyout 
   */
  public void updateAuctionHasBuyout(int auctionId, boolean hasBuyout) throws DatabaseException;
  
 
  /**
   * update an auction start time
   * @param auctionId
   * @param startTime 
   */
  public void updateAuctionStartTime(int auctionId, long startTime) throws DatabaseException;
  
  /**
   * update an auction stop time
   * @param auctionId
   * @param stopTime 
   */
  public void updateAuctionStopTime(int auctionId, long stopTime) throws DatabaseException;
  
  /**
   * update an auction title
   * @param auctionId
   * @param auctionTitle 
   */
  public void updateAuctionTitle(int auctionId, String auctionTitle) throws DatabaseException;
  
  /**
   * update an auction type.
   * @param auctionId
   * @param auctionTypeId  - the id for the auction type in question
   */
  public void updateAuctionType(int auctionId, int auctionTypeId) throws DatabaseException;
  
  /**
   * update the first name of the user indicated by userId to the new value given
   * by newFirstName.
   * @require userExists(userId) == true, newFirstName != null
   * @ensure the value of the first name field of the user indicated by userId is 
   * updated to the value of the String given by newFirstName
   * @param userId
   * @param newFirstName
   * @throws SQLException 
   */
  public void updateUserFirstName(int userId, String newFirstName) throws DatabaseException;
  
  /**
   * update the last name of the user indicated by userId to the new value given
   * by newLastName.
   * @require userExists(userId) == true, newLastName != null
   * @ensure the value of the last name field of the user indicated by userId is 
   *          updated to the value of the String given by newLastName
   * @param userId
   * @param newLastName
   * @throws SQLException 
   */
  public void updateUserLastName(int userId, String newLastName) throws DatabaseException;
  
  
  /**
   * update the title of the user indicated by userId to the new value given
   * by newTitle.
   * @require userExists(userId) == true, newTitle != null
   * @ensure the value of the title field of the user indicated by userId is updated 
   *          to the value of the String given by title
   * @param userId
   * @param newTitle
   * @throws SQLException 
   */
  public void updateUserTitle(int userId, String newTitle) throws DatabaseException;
  
  
  /**
   * update the admin status of the user indicated by userId to the new value given
   * by isAdminStatus.
   * @require userExists(userId) == true, isAdminStatus != null
   * @ensure the value of the isAdmin field of the user indicated by userId is 
   *          updated to 0 if isAdminStatus is false, or 1 if isAdminStatus is true
   * @param userId
   * @param isAdminStatus
   * @throws SQLException 
   */
  public void updateUserIsAdmin(int userId, boolean isAdminStatus) throws DatabaseException;
  
  
  /**
   * update the login of the user indicated by userId to the new value given
   * by newLogin.
   * @require userExists(userId) == true, newLogin != null
   * @ensure the value of the login field of the user indicated by userId is 
   *          updated to the value given by newLogin
   * @param userId
   * @param newLogin
   * @throws SQLException 
   */
  public void updateUserLogin(int userId, String newLogin) throws DatabaseException;
  
  
  /**
   * update the password of the user indicated by userId to the new value given
   * by newPW.
   * @require userExists(userId) == true, newPW != null
   * @ensure the value of the password field of the user indicated by userId is 
   *          updated to the value given by newPW
   * @param userId
   * @param newPW
   * @throws SQLException 
   */
  public void updateUserPassword(int userId, String newPW) throws DatabaseException;

   /**
   * returns the bid user for the bid specified by bidId
   * @param bidId
   * @return
   * @throws SQLException
   */
  public int getBidUser(int bidId) throws DatabaseException;
  
}
