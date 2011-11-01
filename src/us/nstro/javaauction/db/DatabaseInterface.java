/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.nstro.javaauction.db;

/**
 *
 * @author steven
 */
public interface DatabaseInterface {

  /**
   * Adds an auction to the database and returns the auctionId for that auction
   * @require
   * buyout > 0
   * currentAsk > 0
   * currentBid > 0
   * description != null
   * userExists(ownerId) == true
   * startTime >= 0
   * stopTime > 0
   * title != null
   * auctionTypeExists(auctionType) == true
   * @ensure Adds an auction to the database and returns the auctionId for that auction
   *
   * @param buyout
   * @param currentAsk
   * @param currentBid
   * @param description
   * @param hasBuyout
   * @param ownerId
   * @param startTime
   * @param stopTime
   * @param title
   * @param auctionType
   * @return
   * @throws DatabaseException
   */
  int addAuction(int buyout, int currentAsk, int currentBid, String description, boolean hasBuyout, int ownerId, int startTime, int stopTime, String title, int auctionType) throws DatabaseException;

  /**
   * Adds an auction to the db and returns the auctionId for that auction. This is the
   * preferred method for creating a new user, followed by updating fields as necessary
   * @require  userExists(ownerId) == true
   * @ensure Adds an auction to the db and returns the auctionId for that auction
   * @param ownerId
   * @return
   * @throws DatabaseException
   */
  int addAuction(int ownerId) throws DatabaseException;

  /**
   * Adds a new bid to the database and returns the bidId for that bid
   * @require auctionExists(auctionId) == true, userExists(userId) == true, bidAmount >= 0
   * @ensure Adds a new bid to the database and returns the bidId for that bid
   * @param auctionId
   * @param userId
   * @param bidAmount
   * @return
   * @throws DatabaseException
   */
  int addBid(int auctionId, int userId, int bidAmount) throws DatabaseException;

  /**
   * Adds a user to the database and returns the userId for that user.<br>
   * The only information entered for the user with this method, being login and password,
   * this method is essentially a way to enter the user to establish the userId. All other
   * user data is expected to be entered with other user methods using the returned
   * userId.
   * @require loginName != null, loginPassword != null
   * @ensure Adds a user to the database and returns the userId for that user
   * @param loginName the login name for this user
   * @param loginPassword The un-hashed password for the user,
   * @return the user id associated with this user
   * @throws DatabaseException
   */
  int addUser(String loginName, String loginPassword) throws DatabaseException;

  /**
   * returns the number of auctions in the database
   * @ensure returns the number of auctions in the database
   * @return
   * @throws DatabaseException
   */
  int auctionCount() throws DatabaseException;

  /**
   * returns true if an auction exists with the auction Id given by auctionId, false otherwise
   * @require auctionId > 0
   * @ensure returns true if an auction exists with the auction Id given by auctionId, false otherwise
   * @param auctionId
   * @return
   * @throws DatabaseException
   */
  boolean auctionExists(int auctionId) throws DatabaseException;

  /**
   * Returns true if an auction type exists with the auction Type Id given by auctionTypeId, false otherwise
   * @require auctionTypeId > 0
   * @ensure Returns true if an auction type exists with the auction Type Id given by auctionTypeId, false otherwise
   * @param auctionTypeId
   * @return
   * @throws DatabaseException
   */
  boolean auctionTypeExists(int auctionTypeId) throws DatabaseException;

  /**
   * returns true if there exists 1 and only 1 bid in the database which is
   * identified by bidId
   * @require bidExists(bidId) == true
   * @ensure returns true if there exists 1 and only 1 bid in the database which is
   * identified by bidId
   * @param bidId
   * @return
   * @throws DatabaseException
   */
  boolean bidExists(int bidId) throws DatabaseException;

  /**
   * returns true if the bid is expired, false if the bid is still active
   * @require bidExists(bidId) == true
   * @ensure returns true if the bid is expired, false if the bid is still active
   * @param bidId
   * @return
   * @throws DatabaseException
   */
  boolean bidIsExpired(int bidId) throws DatabaseException;

  /**
   * Sets the bid specified by bidId to expired
   * @require bidExists(bidId) == true
   * @ensure Sets the bid specified by bidId to expired
   * @param bidId
   * @throws DatabaseException
   */
  void bidSetExpired(int bidId) throws DatabaseException;

  /**
   * Deletes the auction specified by auctionId from the database
   * @require auctonExists(auctionId) == true
   * @ensure Deletes the auction specified by auctionId from the database
   * @param auctionId
   * @throws DatabaseException
   */
  void deleteAuction(int auctionId) throws DatabaseException;

  /**
   * Deletes the auction type from the database for the auction type specified by
   * auctionTypeId. Deletes all auctions and bids associated with the auction type
   * specified by auctionType
   * @require auctionTypeExists(auctionTypeId) == true
   * @ensure Deletes the auction type from the database for the auction type specified by
   * auctionTypeId. Deletes all auctions and bids associated with the auction type
   * specified by auctionType
   *
   * @param auctionTypeId
   */
  void deleteAuctionType(int auctionTypeId);

  /**
   * deletes the bid specified by bidId from the database
   * @require bidExists(bidId) == true
   * @ensure deletes the bid specified by bidId from the database
   * @param bidId
   * @throws DatabaseException
   */
  void deleteBid(int bidId) throws DatabaseException;

  /**
   * Deletes the user specified by userId from the database. Deletes all bids and
   * auctions associated with that user from the database
   * @require userExists(userId) == true
   * @ensure Deletes the user specified by userId from the database. Deletes all bids and
   * auctions associated with that user from the database
   * @param userId
   * @throws DatabaseException
   */
  void deleteUser(int userId) throws DatabaseException;

  /**
   * Returns an int array of all auction Ids.
   * @ensure an int array of all auction Ids.
   * @return
   * @throws DatabaseException
   */
  int[] getAuctionAllIds() throws DatabaseException;

  /**
   * Returns the auction description for the auction specified by auctionId
   * @require auctionExists(auctionId) == true
   * @ensure Returns the auction description for the auction specified by auctionId
   * @param auctionId
   * @return
   * @throws DatabaseException
   */
  int getAuctionBuyout(int auctionId) throws DatabaseException;

  /**
   * Returns the current asking price of the auction specified by auctionId
   * @require auctionExists(auctionId) == true
   * @ensure returns the current asking price for the auction specified by auctionId
   * @param auctionId
   * @return
   * @throws DatabaseException
   */
  int getAuctionCurrentAsk(int auctionId) throws DatabaseException;

  /**
   * Returns the current bid for the auction specified by auctionId
   * @require auctionExists(auctionId) == true
   * @ensure Returns the current bid for the auction specified by auctionId
   * @param auctionId
   * @return
   * @throws DatabaseException
   */
  int getAuctionCurrentBid(int auctionId) throws DatabaseException;

  /**
   * Returns the description of the auction specified by auctionId
   * @require auctionExists(auctionId) == true
   * @ensure Returns the title of the auction specified by auctionId
   * @param auctionId the auction id of the auction to find the title of
   * @return the title of the auction specified by auctionId
   * @throws DatabaseException
   */
  String getAuctionDescription(int auctionId) throws DatabaseException;

  /**
   * Returns true if the auction is enabled, false otherwise
   * @require auctionExists(auctionId) == true
   * @ensure Returns true if the auction is enabled, false otherwise
   * @param auctionId
   * @return
   * @throws DatabaseException
   */
  boolean getAuctionEnabled(int auctionId) throws DatabaseException;

  /**
   * returns the status of hasBuyout for the auction specified by auctionId
   * @require auctionExists(auctionId) == true
   * @ensure returns the status of hasBuyout for the auction specified by auctionId
   * @param auctionId
   * @return
   * @throws DatabaseException
   */
  boolean getAuctionHasBuyout(int auctionId) throws DatabaseException;

  /**
   * Returns the userId for the owner of the auction specified by auctionId
   * @require auctionExists(auctionId) == true
   * @ensure Returns the userId for the owner of the auction specified by auctionId
   * @param auctionId the auction id number to find the owner of
   * @return  Returns the userId for the owner of the auction specified by auctionId
   * @throws DatabaseException
   */
  int getAuctionOwner(int auctionId) throws DatabaseException;

  /**
   * Returns the auction start time for the auction specified by auctionId. The auction
   * start time is given in milliseconds and is the epoch time, i.e. 01 jan 1970 00:00:00
   * @require auctionExists(auctionId) == true
   * @ensure returns the auction start time for the auction specified by auctionId
   * @param auctionId
   * @return
   * @throws DatabaseException
   */
  long getAuctionStartTime(int auctionId) throws DatabaseException;

  /**
   * Returns the auction stop time for the auction specified by auctionId. The auction
   * stop time is given in milliseconds  and is the epoch time, i.e. 01 jan 1970 00:00:00
   * @require auctionExists(auctionId) == true
   * @ensure returns the auction start time for the auction specified by auctionId
   *
   * @param auctionId
   * @return
   * @throws DatabaseException
   */
  long getAuctionStopTime(int auctionId) throws DatabaseException;

  /**
   * Returns the text field for the auction specified by auctionId. The text field is
   * a general purpose String which can be parsed by the auction manager.
   * @require auctionExists(auctionId) == true
   * @ensure Returns the text field for the auction specified by auctionId.
   * @param auctionId
   * @return
   * @throws DatabaseException
   */
  String getAuctionTextField(int auctionId) throws DatabaseException;

  /**
   * Returns the title of the auction specified by auctionId
   * @require auctionExists(auctionId) == true
   * @ensure Returns the title of the auction specified by auctionId
   * @param auctionId the auction id of the auction to find the title of
   * @return the title of the auction specified by auctionId
   * @throws DatabaseException
   */
  String getAuctionTitle(int auctionId) throws DatabaseException;

  /**
   * Returns the id of the auction type for the auction specified by auctionId
   * @require auctionExists(auctionId) == true
   * @param auctionId
   * @return
   * @throws DatabaseException
   */
  int getAuctionTypeId(int auctionId) throws DatabaseException;

  /**
   * Returns the name of the auctionType specified by auctionTypeId
   * @require auctionTypeExists(auctionTypeId) == true
   * @ensure Returns the name of the auction type specified by auctionTypeId
   * @param auctionTypeId
   * @return
   * @throws DatabaseException
   */
  String getAuctionTypeName(int auctionTypeId) throws DatabaseException;

  /**
   * returns the bid amount for the bid specified by bidId
   * @require bidExists(bidId) == true
   * @ensure returns the bid amount for the bid specified by bidId
   * @param bidId
   * @return
   * @throws DatabaseException
   */
  int getBidAmount(int bidId) throws DatabaseException;

  /**
   * returns the userId for owner of the bid specified by bidId
   * @require bidExists(bidId) == true
   * @ensure returns the userId for owner of the bid specified by bidId
   * @param bidId
   * @return
   * @throws DatabaseException
   */
  int getBidUserId(int bidId) throws DatabaseException;

  /**
   * used with login verification. gets the salt value from the database
   * @return
   * @throws DatabaseException
   */
  String getSalt() throws DatabaseException;

  /**
   * Returns an int array of all user ids in the database
   * @ensure Returns an int array of all user ids in the database
   * @return
   */
  int[] getUserAllIds() throws DatabaseException;

  /**
   * returns an array of the following fields of user data:<br>
   * result[0] == first name<br>
   * result[1] = last name<br>
   * result[2] = title<br>
   * result[3] = login<br>
   * result[4] = isAdmin
   * @require userExists(userId) == true
   * @ensure returns an array of the following fields of user data:<br>
   * result[0] == first name<br>
   * result[1] = last name<br>
   * result[2] = title<br>
   * result[3] = login<br>
   * result[4] = isAdmin
   * @param userId
   * @return
   * @throws DatabaseException
   */
  String[] getUserData(int userId) throws DatabaseException;

  /**
   * returns the status of userEnabled for the user specified by userId
   * @required userExists(userId)
   * @ensure returns the status of userEnabled for the user specified by userId
   * @param userId
   * @return
   * @throws DatabaseException
   */
  boolean getUserEnabled(int userId) throws DatabaseException;

  /**
   * Returns the value of the getUserIsAdmin field in the users table
   * @require userExists(userId) == true
   * @param userId
   * @return
   */
  boolean getUserIsAdmin(int userId) throws DatabaseException;

  /**
   * Returns the user login name for userId
   * @param userId A valid user id
   * @return - the user login name for userId
   * @require - userId must be a valid user id i.e. userExists(userId) == true
   * @ensure - Returns the user login name for userId
   */
  String getUserLogin(int userId) throws DatabaseException;

  /**
   * Returns a String array containing several items relevant to the users
   * name for the user specified by userId.
   * Such that: <br>
   * result[0] = user first name<br>
   * result[1] = user last name<br>
   * result[2] = user title
   *
   * @param userId a valid user id
   * @return - array containing the user Id, user first name, and user last
   * name for userId.
   * @require - userId must be a valid user id i.e. userExists(userId) == true
   * @ensure - Returns a String array containing the user first name, and user last
   * name for userId.
   */
  String[] getUserName(int userId) throws DatabaseException;

  /**
   * Returns the password hash for userId
   * @param userId A valid user id
   * @return - the password hash for userId
   * @require - userId must be a valid user id i.e. userExists(userId) == true
   * @ensure - Returns the password hash for userId
   */
  String getUserPw(int userId) throws DatabaseException;

  /**
   * Checks the login and password for a matching combination in the database. If
   * the login/password combination is valid and the user account is enabled,
   * the userId will be returned, otherwise -1 will be returned.
   * @require login != null, password != null
   * @param login the login submitted by the user
   * @param password the un-hashed password submitted by the user
   * @return If
   * the login/password combination is valid, the userId will be returns, otherwise
   * -1 will be returned.
   * @throws DatabaseException
   */
  int login(String login, String password) throws DatabaseException;

  /**
   * Updates the enabled field of the database to the value of the argument 'value'
   * for the bid specified by the argument 'bidId'
   * @require bidExists(bidId) == true
   * @ensure Updates the enabled field of the database to the value of the argument 'value'
   * for the bid specified by the argument 'bidId'
   * @param bidId
   * @param value
   * @throws DatabaseException
   */
  void setBidEnabled(int bidId, boolean value) throws DatabaseException;

  /**
   * Updates the auction buyout price of the auction specified by auctionId to the new
   * buyout prices given by buyoutPrice
   * @require auctionExists(auctionId) == true, buyoutPrice > 0
   * @ensure Updates the auction buyout price of the auction specified by auctionId to the new
   * buyout prices given by buyoutPrice
   * @param auctionId
   * @param buyoutPrice
   * @throws DatabaseException
   */
  void updateAuctionBuyout(int auctionId, int buyoutPrice) throws DatabaseException;

  /**
   * Updates the auction current asking price of the auction specified by auctionId to the new
   * current asking price given by currentAsking
   * @require auctionExists(auctionId) == true, currentAsking > 0
   * @ensure Updates the auction current asking price of the auction specified by auctionId to the new
   * current asking price given by currentAsking
   * @param auctionId
   * @param currentAsking
   * @throws DatabaseException
   */
  void updateAuctionCurrentAsking(int auctionId, int currentAsking) throws DatabaseException;

  /**
   *
   * Updates the auction current bid price of the auction specified by auctionId to the new
   * current bid price given by currentBid
   * @require auctionExists(auctionId) == true, currentBid > 0
   * @ensure Updates the auction current bid price of the auction specified by auctionId to the new
   * current bid price given by currentBid
   * @param auctionId
   * @param currentBid
   * @throws DatabaseException
   */
  void updateAuctionCurrentBid(int auctionId, int currentBid) throws DatabaseException;

  /**
   * Updates the auction description for the auction specified by auctionId to the
   * new value given by description
   * @require auctionExists(auctionId) == true, description != null
   * @ensure Updates the auction description for the auction specified by auctionId to the
   * new value given by description
   * @param auctionId
   * @param description
   * @throws DatabaseException
   */
  void updateAuctionDescription(int auctionId, String description) throws DatabaseException;

  /**
   * Updates the auction specifed by auctionId as enabled if isEnabled == true, or false otherwise
   * @require auctionExists(auctionId) == true, isEnabled != null
   * @ensure Updates the auction specifed by auctionId as enabled if isEnabled == true, or false otherwise
   * @param auctionId
   * @param isEnabled
   * @throws DatabaseException
   */
  void updateAuctionEnabled(int auctionId, boolean isEnabled) throws DatabaseException;

  /**
   * updates the status of hasBuyout for the auction specified by auctionId to the
   * new value given by hasBuyout
   * @require auctionExists(auctionId) == true, hasBuyout != null
   * @ensure updates the status of hasBuyout for the auction specified by auctionId to the
   * new value given by hasBuyout
   * @param auctionId
   * @param hasBuyout
   * @throws DatabaseException
   */
  void updateAuctionHasBuyout(int auctionId, boolean hasBuyout) throws DatabaseException;

  /**
   * updates the auction start time for the auction specified by auctionId to the
   * new value specified by startTime. The startTime is given in milliseconds given by
   * the java date class
   * @require auctionExists(auctionId) == true, startTime >= 0
   * @ensure updates the auction start time for the auction specified by auctionId to the
   * new value specified by startTime. The startTime is given in milliseconds given by
   * the java date class
   * @param auctionId
   * @param startTime
   * @throws DatabaseException
   */
  void updateAuctionStartTime(int auctionId, long startTime) throws DatabaseException;

  /**
   * updates the auction stop time for the auction specified by auctionId to the
   * new value specified by stopTime. The stopTime given in milliseconds given by
   * the java date class
   * @require auctionExists(auctionId) == true, stopTime > 0
   * @ensure updates the auction stop time for the auction specified by auctionId to the
   * new value specified by stopTime. The stopTime given in milliseconds given by
   * the java date class
   * @param auctionId
   * @param stopTime
   * @throws DatabaseException
   */
  void updateAuctionStopTime(int auctionId, long stopTime) throws DatabaseException;

  /**
   * Updates the auction specified by auctionId with the new value specified by newString
   * @require auctionExists(auctionId) == true, newString != null
   * @ensure Updates the auction specified by auctionId with the new value specified by newString
   * @param auctionId
   * @param newString
   * @throws DatabaseException
   */
  void updateAuctionTextField(int auctionId, String newString) throws DatabaseException;

  /**
   * updates the title of the auction specified by auctionId to the new value given by
   * auctionTitle
   * @require auctionExists(auctionId) == true, auctionTitle != null
   * @ensure updates the title of the auction specified by auctionId to the new value given by
   * auctionTitle
   * @param auctionId
   * @param auctionTitle
   * @throws DatabaseException
   */
  void updateAuctionTitle(int auctionId, String auctionTitle) throws DatabaseException;

  /**
   * Updates the auction type for the auction specified by auctionId, to the new auction
   * type given by auctionTypeId
   * @require auctionExists(auctionId) == true, auctionTypeExists(auctionTypeId) == true
   * @ensure Updates the auction type for the auction specified by auctionId, to the new auction
   * type given by auctionTypeId
   * @param auctionId
   * @param auctionTypeId
   * @throws DatabaseException
   */
  void updateAuctionType(int auctionId, int auctionTypeId) throws DatabaseException;

  /**
   * updates the bid amount for the bid specified by bidId
   * @require this.bidExists(bidId) == true<br>
   * bidAmount > 0
   * @ensure  updates the bid amount for the bid specified by bidId
   * @param bidId
   * @param bidAmount
   * @throws DatabaseException
   */
  void updateBidAmount(int bidId, int bidAmount) throws DatabaseException;

  /**
   * update the first name of the user indicated by userId to the new value given
   * by newFirstName.
   * @require userExists(userId) == true, newFirstName != null
   * @ensure update the first name of the user indicated by userId to the new value given
   * by newFirstName.
   * @param userId
   * @param newFirstName
   * @throws DatabaseException
   */
  void updateUserFirstName(int userId, String newFirstName) throws DatabaseException;

  /**
   * update the admin status of the user indicated by userId to the new value given
   * by isAdminStatus.
   * @require userExists(userId) == true, isAdminStatus != null
   * @ensure the value of the isAdmin field of the user indicated by userId is
   * updated to 0 if isAdminStatus is false, or 1 if isAdminStatus is true
   * @param userId
   * @param isAdminStatus
   * @throws DatabaseException
   */
  void updateUserIsAdmin(int userId, boolean isAdminStatus) throws DatabaseException;

  /**
   * update the last name of the user indicated by userId to the new value given
   * by newLastName.
   * @require userExists(userId) == true, newLastName != null
   * @ensure update the last name of the user indicated by userId to the new value given
   * by newLastName.
   * @param userId
   * @param newFirstName
   * @throws DatabaseException
   */
  void updateUserLastName(int userId, String newLastName) throws DatabaseException;

  /**
   * update the login of the user indicated by userId to the new value given
   * by newLogin.
   * @require userExists(userId) == true, newLogin != null
   * @ensure the value of the login field of the user indicated by userId is
   * updated to the value given by newLogin
   * @param userId
   * @param newLogin
   * @throws DatabaseException
   */
  void updateUserLogin(int userId, String newLogin) throws DatabaseException;

  /**
   * update the password of the user indicated by userId to the new value given
   * by newPW.
   * @require userExists(userId) == true, newPW != null
   * @ensure the value of the password field of the user indicated by userId is
   * updated to the value given by newPW
   * @param userId
   * @param newPW
   * @throws DatabaseException
   */
  void updateUserPassword(int userId, String newPW) throws DatabaseException;

  /**
   * update the title of the user indicated by userId to the new value given
   * by newTitle.
   * @require userExists(userId) == true, newTitle != null
   * @ensure update the title of the user indicated by userId to the new value given
   * by newTitle
   * @param userId
   * @param newTitle
   * @throws DatabaseException
   */
  void updateUserTitle(int userId, String newTitle) throws DatabaseException;

  /**
   * Checks userId to validate that it is a userId in the database.
   * @param userId  A possible userId.
   * @return - True id userId is found in the database, false otherwise
   * @require - userId is an int >= 0
   * @ensure - returns true if userId is found in the database, false otherwise
   */
  boolean userExists(int userId) throws DatabaseException;

}
