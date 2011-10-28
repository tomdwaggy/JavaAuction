package us.nstro.javaauction.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Steven Bolin
 */
public class SQLiteConnection implements DatabaseInterface {
  
    private String dbName;
    private Connection connection;

  /**
     * Constructor - for the default db
     */
    public SQLiteConnection() {
        connection = null;
        
        // set the dbName to the default db
        this.dbName = "./database/auction.db";
    }
    
    /**
     * Constructor - for a  specific db
     * @param dbLocation - the path, relative or absolute to a db to use
     * @require dbLocation is a valid SQLite 3 db at the specified location
     */
    public SQLiteConnection(String dbLocation) {
      
        connection = null;
        this.dbName = dbLocation;
    }
    
    private void doConnect() {
        try {
            connection = null;
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SQLiteConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void doDisconnect() throws SQLException {

        if(connection != null) {
          connection.close();
        }          

        connection = null;
    }
    
    /**
     * Executes an SQL query where there is no return result required
     * @param sqlB - a valid SQL command
     * @require the sqlB query not require a result
     * @throws SQLException 
     */
    private void doExec(String sql) throws DatabaseException {
        try {
            this.doConnect();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout for 30 seconds
            statement.executeUpdate(sql);
            this.doDisconnect();
        } catch (SQLException sqle) {
            throw new SQLDatabaseException(sqle);
        }
    }
    
    /**
     * Executes an SQL query which returns a single result. It must be typecast by
     * by the client method to the required type
     * @param sqlB - a valid SQL query
     * @return a single result from the query
     * @require the sqlB query return only one result, i.e. one cell
     * @throws SQLException 
     */
    private Object doGetOneResult(String sql) throws DatabaseException {
        try {
            doConnect();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery(sql);
            Object retVal = rs.getObject(1);

            doDisconnect();
        
            return retVal;
        } catch (SQLException sqle) {
            throw new SQLDatabaseException(sqle);
        }
    }
    
    /**
     * Executes an SQL query which returns one int value
     * @param sql
     * @return
     * @throws SQLException 
     */
    private int doGetOneInt(String sql) throws DatabaseException {
        try {
            doConnect();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery(sql);
            int retVal = rs.getInt(1);

            doDisconnect();

            return retVal;
        } catch (SQLException sqle) {
            throw new SQLDatabaseException(sqle);
        }
    }
    
    /**
     * Executes an SQL query which returns one long value
     * @param sql
     * @return
     * @throws SQLException 
     */
    private long doGetOneLong(String sql) throws DatabaseException {
        try {
            doConnect();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery(sql);
            long retVal = rs.getLong(1);

            doDisconnect();

            return retVal;
        } catch (SQLException sqle) {
            throw new SQLDatabaseException(sqle);
        }
    }
    
    /**
     * Executes an SQL query which returns one int value
     * @param sql
     * @return
     * @throws SQLException 
     */
    private String doGetOneString(String sql) throws DatabaseException {
        try {
            doConnect();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery(sql);
            String retVal = rs.getString(1);

            doDisconnect();

            return retVal;
        } catch (SQLException sqle) {
            throw new SQLDatabaseException(sqle);
        }
    }
    
    /**
     * Executes an SQL query which returns one column of int results
     * @param sqlB - a valid SQL query which returns one column of results
     * @return - an int array of the results of the query
     * @require the SQL query return only one column of results
     * @throws SQLException 
     */
    private int[] doOneColumnInts(String sql) throws DatabaseException {
        try {
            ArrayList<Integer> alist = new ArrayList<Integer>();
            doConnect();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                // read the result set
                alist.add(rs.getInt(1));
            }
            doDisconnect();

            // create an array the size of the arraylist
            int output[] = new int[alist.size()];
            // convert the ArrayList<Integer> to an int[]
            // I did look and the alist.toArray method doesnt work with primitives
            for(int i=0; i < alist.size(); i++) {
                output[i] = alist.get(i);
            }


            return output;
        } catch (SQLException sqle) {
            throw new SQLDatabaseException(sqle);
        }
    }
    
    /**
     * This method prepared unfriendly characters for entry into the db
     * @param input
     * @return 
     */
    private String doCleanText(String input) {
      
      return input.replaceAll("'", "''");

    }

  
  /**
   * Checks userId to validate that it is a userId in the database.
   * @param userId  A possible userId.
   * @return - True id userId is found in the database, false otherwise
   * @require - userId is an int >= 0
   * @ensure - returns true if userId is found in the database, false otherwise
   */
  public boolean userExists(int userId) throws DatabaseException {
    return (this.doGetOneInt("SELECT COUNT(rowid) FROM users WHERE rowid='"+userId+"';") > 0);
  }
  
  /**
   * returns an array of the following fields of user data:<br>
   * result[0] == first name<br>
   * result[1] = last name<br>
   * result[2] = title<br>
   * result[3] = login<br>
   * result[4] = isAdmin
   * @param userId
   * @return
   * @throws SQLException 
   */
  public String[] getUserData(int userId) throws DatabaseException {
    String result[] = new String[5];
    String[] userName = this.getUserName(userId);
    
    result[0] = userName[0]; // first name
    result[1] = userName[1]; // last name
    result[2] = userName[2]; // title
    result[3] = this.getUserLogin(userId);
    result[4] = "" + this.getUserIsAdmin(userId);
    
    return result;
  }

  
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
  public String[] getUserName(int userId) throws DatabaseException {
    
    String[] result = new String[3];
    result[0] = this.doGetOneString("SELECT firstName FROM users where rowid='"+userId+"';");
    result[1] = this.doGetOneString("SELECT lastName FROM users where rowid='"+userId+"';");
    result[2] = this.doGetOneString("SELECT title FROM users where rowid='"+userId+"';");
    
    return result;
  }

  
  /**
   * Returns the user login name for userId
   * @param userId A valid user id
   * @return - the user login name for userId
   * @require - userId must be a valid user id i.e. userExists(userId) == true
   * @ensure - Returns the user login name for userId
   */
  public String getUserLogin(int userId) throws DatabaseException {
    return this.doGetOneString("SELECT login FROM users WHERE rowid='"+userId+"';");
  }

  
  /**
   * Returns the password hash for userId
   * @param userId A valid user id
   * @return - the password hash for userId
   * @require - userId must be a valid user id i.e. userExists(userId) == true
   * @ensure - Returns the password hash for userId
   */
  public String getUserPw(int userId) throws DatabaseException {
    return this.doGetOneString("SELECT password FROM users WHERE rowid='"+userId+"';");
  }

  
  /**
   * Returns the value of the getUserIsAdmin field in the users table
   * @param userId
   * @return 
   */
  public boolean getUserIsAdmin(int userId) throws DatabaseException {
    return (this.doGetOneInt("SELECT isAdmin FROM users WHERE rowid='"+userId+"';") > 0);
  }

  
  /**
   * Returns an int array of all user ids in the database
   * @return 
   */
  public int[] getUserAllIds() throws DatabaseException {
    return this.doOneColumnInts("SELECT rowid FROM users");
  }

  
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
//  public String[] getAuctionData(int auctionId) throws SQLException {
//    
//    String[] result = new String[12];
//    result[0] = ""+auctionId;
//    result[1] = this.doGetOneString("SELECT owner FROM auctions WHERE rowid='"+auctionId+"';");
//    result[2] = this.doGetOneString("SELECT title FROM auctions WHERE rowid='"+auctionId+"';");
//    result[3] = this.doGetOneString("SELECT type FROM auctions WHERE rowid='"+auctionId+"';");
//    result[4] = this.doGetOneString("SELECT currentAsk FROM auctions WHERE rowid='"+auctionId+"';");
//    result[5] = this.doGetOneString("SELECT currentBid FROM auctions WHERE rowid='"+auctionId+"';");
//    result[6] = this.doGetOneString("SELECT buyout FROM auctions WHERE rowid='"+auctionId+"';");
//    result[7] = this.doGetOneString("SELECT startTime FROM auctions WHERE rowid='"+auctionId+"';");
//    result[8] = this.doGetOneString("SELECT stopTime FROM auctions WHERE rowid='"+auctionId+"';");
//    result[9] = this.doGetOneString("SELECT description FROM auctions WHERE rowid='"+auctionId+"';");
//    result[10] = this.doGetOneString("SELECT imageLinks FROM auctions WHERE rowid='"+auctionId+"';");
//    result[11] = this.doGetOneString("SELECT hasBuyout FROM auctions WHERE rowid='"+auctionId+"';");
//    return result;
//  }

  /**
   * Returns the userId for the owner of the auction specified by auctionId
   * @require auctionId > 0
   * @ensure Returns the userId for the owner of the auction specified by auctionId
   * @param auctionId the auction id number to find the owner of
   * @return  Returns the userId for the owner of the auction specified by auctionId
   * @throws SQLException 
   */
  public int getAuctionOwner(int auctionId) throws DatabaseException {
    return this.doGetOneInt("SELECT owner FROM auctions WHERE rowid='"+auctionId+"';");
  }

  /**
   * Returns the title of the auction specified by auctionId
   * @require auctionId > 0
   * @ensure Returns the title of the auction specified by auctionId
   * @param auctionId the auction id of the auction to find the title of
   * @return the title of the auction specified by auctionId
   * @throws SQLException 
   */
  public String getAuctionTitle(int auctionId) throws DatabaseException {
    return this.doGetOneString("SELECT title FROM auctions WHERE rowid='"+auctionId+"';");
  }

  /**
   * Returns the id of the auction type for the auction specified by auctionId
   * @require auctionId > 0
   * @param auctionId
   * @return
   * @throws SQLException 
   */
  public int getAuctionTypeId(int auctionId) throws DatabaseException {
    return this.doGetOneInt("SELECT type FROM auctions WHERE rowid='"+auctionId+"';");
  }

  
  public String getAuctionTypeName(int auctionTypeId) throws DatabaseException {
    return this.doGetOneString("SELECT name FROM auctionType WHERE rowid='"+auctionTypeId+"';");
  }

  
  public int getAuctionCurrentAsk(int auctionId) throws DatabaseException {
    int result = this.doGetOneInt("SELECT currentAsk FROM auctions WHERE rowid='"+auctionId+"';");
    return result;
  }

  
  public int getAuctionCurrentBid(int auctionId) throws DatabaseException {
    int result = this.doGetOneInt("SELECT currentBid FROM auctions WHERE rowid='"+auctionId+"';");
    return result;
  }

  
  public int getAuctionBuyout(int auctionId) throws DatabaseException {
    int result = this.doGetOneInt("SELECT buyout FROM auctions WHERE rowid='"+auctionId+"';");
    return result;
  }

  
  public long getAuctionStartTime(int auctionId) throws DatabaseException {

    return this.doGetOneLong("SELECT startTime FROM auctions WHERE rowid = '"+auctionId+"';");
  }

  
  public long getAuctionStopTime(int auctionId) throws DatabaseException {

    return this.doGetOneLong("SELECT stopTime FROM auctions WHERE rowid = '"+auctionId+"';");
  }

  
  public int[] getAuctionAllIds() throws DatabaseException {
    return this.doOneColumnInts("SELECT rowid FROM auctions;");
  }
  
  /**
     * Returns an array of the links for the auction specified by auctionId
     * @param auctionId
     * @return
     * @throws SQLException 
     */
//  public String[] getAuctionImageLinks(int auctionId) throws SQLException {
//    
//    String linkString = this.doGetOneString("SELECT imageLinks FROM auctions WHERE rowid='"+auctionId+"';");
//    String[] linkArray = linkString.split(",");
//    
//    for(int i=0; i<linkArray.length; i++) {
//      linkArray[i] = linkArray[i].trim();
//    }
//    
//    return linkArray;
//  }

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
   * @throws SQLException 
   */
  public int addUser(String loginName, String loginPassword) throws DatabaseException {
    
    long tStamp = new Date().getTime();
    String pwHash = this.doHashPassword(loginPassword);
    
    this.doExec("INSERT INTO users VALUES(1,'"+tStamp+"', 'No first name entered yet'"
            + ", -1, 'No last name entered yet', '"+
            this.doCleanText(loginName)+"', '"+pwHash+"', '');");
    
    return this.doGetOneInt("SELECT rowid FROM users WHERE timestamp='"+tStamp+"';");
  }
  
  public void deleteUser(int userId) throws DatabaseException {
    
    // delete user (disable the account)
    
    // delete all of users auctions?
    
    // delete all of users bids?
    
    
    throw new UnsupportedOperationException("Not supported yet.");
  }

  /**
   * Adds an auction to the database and returns the auctionId for that auction
   * @param buyout
   * @param currentAsk
   * @param currentBid
   * @param description
   * @param hasBuyout
   * @param imageLinks
   * @param ownerId
   * @param startTime
   * @param stopTime
   * @param title
   * @param auctionType
   * @return
   * @throws SQLException 
   */
  public int addAuction(int buyout, int currentAsk, int currentBid, 
  String description, boolean hasBuyout, String imageLinks, 
  int ownerId, int startTime, int stopTime, String title,
  int auctionType) throws DatabaseException {
    
    
    long tStamp = new Date().getTime();
    
    this.doExec("INSERT INTO auctions VALUES('"+tStamp+"','"+buyout+"','"+currentAsk+"','"+
            currentBid+"',''"+this.doCleanText(description)+"'',''"+hasBuyout+"'',''"+imageLinks+"'','"+
            ownerId+"','"+startTime+"','"+stopTime+"',''"+this.doCleanText(title)+"'','"+auctionType+"');");
    
    return this.doGetOneInt("SELECT rowid FROM auctions WHERE timestamp = '"+tStamp+"';");
    
  }

  /**
   * Adds an auction to the db and returns the auctionId for that auction
   * @param ownerId
   * @return
   * @throws SQLException 
   */
  public int addAuction(int ownerId) throws DatabaseException {
    
    long tStamp = new Date().getTime();
    
    this.doExec("INSERT INTO auctions VALUES(1,"+tStamp+",0,0,0,'No Description Provided Yet.','true','','"+ownerId+"',0,0,'No Title Provided Yet',0);");
    
    return this.doGetOneInt("SELECT rowid FROM auctions WHERE timestamp = '"+tStamp+"';");
  }

  public void deleteAuction(int auctionId) throws DatabaseException {
    this.doExec("DELETE FROM auctions WHERE rowid = '"+auctionId+"';");
  }
  
  public boolean getAuctionEnabled(int auctionId) throws DatabaseException {
    
    return (this.doGetOneInt("SELECT enabled FROM auctions WHERE rowid = '"+auctionId+"';") > 0);
  }
  
  public void updateAuctionEnabled(int auctionId, boolean isEnabled) throws DatabaseException {
    
    int enabledValue = 0;
    
    if(isEnabled)
      enabledValue = 1;
    
    this.doExec("UPDATE auctions SET [enabled] = '"+enabledValue+"' WHERE rowid = '"+auctionId+"';");
  }
  
  public void deleteAuctionType(int auctionTypeId) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  public void updateAuctionBuyout(int auctionId, int buyoutPrice) throws DatabaseException {
    this.doExec("UPDATE auctions SET [buyout] = '"+buyoutPrice+"' WHERE rowid = '"+auctionId+"';");
  }

  
  public void updateAuctionCurrentAsking(int auctionId, int currentAsking) throws DatabaseException {
    this.doExec("UPDATE auctions SET [currentAsk] = '"+currentAsking+"' WHERE rowid = '"+auctionId+"';");
  }

  
  public void updateAuctionCurrentBid(int auctionId, int currentBid) throws DatabaseException {
    this.doExec("UPDATE auctions SET [currentBid] = '"+currentBid+"' WHERE rowid = '"+auctionId+"';");
  }

  
  public void updateAuctionDescription(int auctionId, String description) throws DatabaseException {
    this.doExec("UPDATE auctions SET [description] = '"+this.doCleanText(description)+"' WHERE rowid = '"+auctionId+"';");
  }

  
  public void updateAuctionHasBuyout(int auctionId, boolean hasBuyout) throws DatabaseException {
    int updateValue = hasBuyout ? 1 : 0;
    this.doExec("UPDATE auctions SET [hasBuyout] = '"+updateValue+"' WHERE rowid = '"+auctionId+"';");
  }
  
  public boolean getAuctionHasBuyout(int auctionId) throws DatabaseException {
    int buyoutValue;
    boolean result = this.doGetOneInt("SELECT hasBuyout FROM auctions WHERE rowid = '"+auctionId+"';") == 1 ? true : false;
    return result;
  }

  
  public void updateAuctionStartTime(int auctionId, long startTime) throws DatabaseException {
    this.doExec("UPDATE auctions SET [startTime] = '"+startTime+"' WHERE rowid = '"+auctionId+"';");
  }

  
  public void updateAuctionStopTime(int auctionId, int stopTime) throws DatabaseException {
    this.doExec("UPDATE auctions SET [stopTime] = '"+stopTime+"' WHERE rowid = '"+auctionId+"';");
  }

  
  public void updateAuctionTitle(int auctionId, String auctionTitle) throws DatabaseException {
    this.doExec("UPDATE auctions SET [title] = '"+this.doCleanText(auctionTitle)+"' WHERE rowid = '"+auctionId+"';");
  }

  
  public void updateAuctionType(int auctionId, int auctionTypeId) throws DatabaseException {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  /**
   * update the first name of the user indicated by userId to the new value given
   * by newFirstName.
   * @require userExists(userId) == true, newFirstName != null
   * @param userId
   * @param newFirstName
   * @throws SQLException 
   */
  public void updateUserFirstName(int userId, String newFirstName) throws DatabaseException {
    this.doExec("UPDATE users SET firstName = '"+this.doCleanText(newFirstName)+"' WHERE rowid = '"+userId+"';");
  }
  
  /**
   * update the last name of the user indicated by userId to the new value given
   * by newLastName.
   * @require userExists(userId) == true, newLastName != null
   * @param userId
   * @param newFirstName
   * @throws SQLException 
   */
  public void updateUserLastName(int userId, String newLastName) throws DatabaseException {
    
    this.doExec("UPDATE users SET lastName = '"+this.doCleanText(newLastName)+"' WHERE rowid = '"+userId+"';");
  }
  
  /**
   * update the title of the user indicated by userId to the new value given
   * by newTitle.
   * @require userExists(userId) == true, newTitle != null
   * @param userId
   * @param newTitle
   * @throws SQLException 
   */
  public void updateUserTitle(int userId, String newTitle) throws DatabaseException {
    this.doExec("UPDATE users SET title = '"+this.doCleanText(newTitle)+"' WHERE rowid = '"+userId+"';");
  }
  
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
  public void updateUserIsAdmin(int userId, boolean isAdminStatus) throws DatabaseException {
    int status = isAdminStatus ? 1 : 0;
    this.doExec("UPDATE users SET isAdmin = '"+status+"' WHERE rowid = '"+userId+"';");
  }
  
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
  public void updateUserLogin(int userId, String newLogin) throws DatabaseException {
    this.doExec("UPDATE users SET login = '"+this.doCleanText(newLogin)+"' WHERE rowid = '"+userId+"';");
  }
  
  
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
  public void updateUserPassword(int userId, String newPW) throws DatabaseException {
    
    String hashed = this.doHashPassword(newPW);
    this.doExec("UPDATE users SET password = '"+hashed+"' WHERE rowid = '"+userId+"';");
  }
  
  /**
   * Hashes the password for use in this class
   * @param password
   * @return 
   */
  private String doHashPassword(String password) {
    
    BCrypt crypt = new BCrypt();
    return BCrypt.hashpw(password, BCrypt.gensalt(12));
  }
  
  /**
   * Checks to see if there is one and only one row in the users table in the 
   * database where the login and password match the arguments login and the hash of
   * password.
   * @require login != null and password != null
   * @ensure true if there is one and only one row matching login and the hash of 
   *          password, false otherwise
   * @param login
   * @param password
   * @return true if there is one and only one row matching login and the hash of 
   *          password, false otherwise
   * @throws SQLException 
   */
  private boolean loginSuccessful(String login, String password) throws DatabaseException {
    
    String hashed = this.doHashPassword(password);
    return ((this.doGetOneInt("SELECT COUNT(rowid) FROM users WHERE login='"+this.doCleanText(login)+"' AND password='"+hashed+"';")) == 1);
  }
  
  /**
   * Checks the login and password for a matching combination in the database. If 
   * the login/password combination is valid and the user account is enabled, 
   * the userId will be returned, otherwise -1 will be returned.
   * 
   * @param login the login submitted by the user
   * @param password the un-hashed password submitted by the user
   * @return If 
   * the login/password combination is valid, the userId will be returns, otherwise
   * -1 will be returned.
   * @throws SQLException 
   */  
  public int login(String login, String password) throws DatabaseException {
    
    String hashed = this.doHashPassword(password);
    int result = -1;
    if(this.loginSuccessful(login, password)) {
      result = this.doGetOneInt("SELECT rowid FROM users WHERE login='"+this.doCleanText(login)+"' AND password='"+hashed+"';");
    
      if(!this.getUserEnabled(result))
        result = -1;
    }
      
    return result;
  }
  
  
  public int auctionCount() throws DatabaseException {
    return this.doGetOneInt("SELECT COUNT(*) FROM auctions;");
  }
  
  public boolean getUserEnabled(int userId) throws DatabaseException {
    
    boolean result = false;
    if(this.doGetOneInt("SELECT enabled FROM users WHERE rowid = '"+userId+"';") > 0)
      result = true;
    
    return result;
  }
  
/************************************************************************************
*  bid transactions 
************************************************************************************/
  
  /**
   * Adds a new bid to the database and returns the bidId for that bid
   * @param auctionId
   * @param userId
   * @param bidAmount
   * @return
   * @throws SQLException 
   */
  public int addBid(int auctionId, int userId, int bidAmount) throws DatabaseException {
    
    long tStamp = new Date().getTime();
    
    this.doExec("INSERT INTO bids VALUES('0', "+tStamp+"','"+auctionId+"','"+userId+"', '"+bidAmount+"');");
    
    return this.doGetOneInt("SELECT rowid FROM bids WHERE timestamp = '"+tStamp+"';");
    
  }
  
  /**
   * returns true if there exists 1 and only 1 bid in the database which is 
   * identified by bidId
   * @param bidId
   * @return
   * @throws SQLException 
   */
  public boolean bidExists(int bidId) throws DatabaseException {
    return (this.doGetOneInt("SELECT COUNT(*) FROM bids WHERE rowid = '"+bidId+"';") == 1);
  }
  
  /**
   * returns true if the bid is expired, false if the bid is still active
   * @param bidId
   * @return
   * @throws SQLException 
   */
  public boolean bidIsExpired(int bidId) throws DatabaseException {
    boolean result = (this.doGetOneInt("SELECT expired FROM bids WHERE rowid='"+bidId+"';")>0) ? true : false;
    
    return result;
  }
  
  /**
   * Sets the bid specified by bidId to expired
   * @param bidId
   * @throws SQLException 
   */
  public void bidSetExpired(int bidId) throws DatabaseException {
    this.doExec("UPDATE bids SET expired = '1' WHERE rowid='"+bidId+"'");
  }
  
  /**
   * updates the bid amount for the bid specified by bidId
   * @require this.bidExists(bidId) == true<br>
   *          bidAmount > 0
   * @ensure  updates the bid amount for the bid specified by bidId
   * @param bidId
   * @param bidAmount
   * @throws SQLException 
   */
  public void updateBidAmount(int bidId, int bidAmount) throws DatabaseException {
    this.doExec("UPDATE bids SET amount = '"+bidAmount+"' WHERE rowid = '"+bidId+"';");
  }
  
  /**
   * returns the bid amount for the bid specified by bidId
   * @param bidId
   * @return
   * @throws SQLException 
   */
  public int getBidAmount(int bidId) throws DatabaseException {
    return this.doGetOneInt("SELECT amount FROM bids WHERE rowid = '"+bidId+"';");
  }
  
  /**
   * deletes the bid specified by bidId from the database
   * @param bidId
   * @throws SQLException 
   */
  public void deleteBid(int bidId) throws DatabaseException {
    this.doExec("DELETE * FROM bids WHERE rowid = '"+bidId+"';");
  }




  
} // end of class
