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

        try {

      if(this.needsSalt()) this.addSalt();

        } catch (DatabaseException ex) {
          Logger.getLogger(SQLiteConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Constructor - for a  specific db
     * @param dbLocation - the path, relative or absolute to a db to use
     * @require dbLocation is a valid SQLite 3 db at the specified location
     */
    public SQLiteConnection(String dbLocation) {

        connection = null;
        this.dbName = dbLocation;
    try {
      if(this.needsSalt()) this.addSalt();
    } catch (DatabaseException ex) {
      Logger.getLogger(SQLiteConnection.class.getName()).log(Level.SEVERE, null, ex);
    }

    }

    /**
     * used for initial build of database to establish the salt value for the encryption
     * @return
     * @throws DatabaseException
     */
    private boolean needsSalt() throws DatabaseException {

      boolean result = true;

      if(this.countSalt() > 0) result = false;

      return result;
    }

    /**
     * used to determine if the salt has been established
     * @return
     * @throws DatabaseException
     */
    private int countSalt() throws DatabaseException {
      return this.doGetOneInt("SELECT COUNT(*) FROM salt;");
    }

    /**
     * used with login verification. gets the salt value from the database
     * @return
     * @throws DatabaseException
     */
  @Override
    public String getSalt() throws DatabaseException {
      return this.doGetOneString("SELECT salt from salt WHERE rowid = 1");
    }

    /**
     * used during initial database build to establish the salt value if needed
     * @throws DatabaseException
     */
    private void addSalt() throws DatabaseException {
      String salt = BCrypt.gensalt();
      this.doExec("INSERT INTO salt VALUES('"+salt+"');");
    }

    /**
     * opens the connection to the sqlite database
     */
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

    /**
     * closes the connection to the sqlite database
     * @throws SQLException
     */
    private void doDisconnect() throws SQLException{

        if(connection != null) {
          connection.close();
        }

        connection = null;
    }

    /**
     * Executes an SQL query where there is no return result required
     * @param sqlB - a valid SQL command
     * @require the sqlB query not require a result
     * @throws DatabaseException
     */
    private void doExec(String sql) throws DatabaseException {
        try {
            this.doConnect();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30); // set timeout for 30 seconds
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
     * @throws DatabaseException
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
     * @throws DatabaseException
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
     * @throws DatabaseException
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
     * @throws DatabaseException
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
     * @throws DatabaseException
     */
    private int[] doOneColumnInts(String sql) throws DatabaseException {
        try {
            ArrayList<Integer> alist = new ArrayList<Integer>();
            doConnect();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30); // set timeout to 30 sec.
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
     * This method prepares unfriendly characters for entry into the db
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
  @Override
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
  @Override
  public String[] getUserData(int userId) throws DatabaseException {
    String result[] = new String[5];
    String[] userName = this.getUserName(userId);

    result[0] = userName[0]; // first name
    result[1] = userName[1]; // last name
    result[2] = userName[2]; // title
    result[3] = this.getUserLogin(userId);
    result[4] = ""+this.getUserIsAdmin(userId);

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
  @Override
  public String[] getUserName(int userId) throws DatabaseException {

    String[] result = new String[3];
    result[0] = this.doGetOneString("SELECT firstName FROM users where rowid='"+userId+"';");
    result[1]  = this.doGetOneString("SELECT lastName FROM users where rowid='"+userId+"';");
    result[2]     = this.doGetOneString("SELECT title FROM users where rowid='"+userId+"';");

    return result;
  }


  /**
   * Returns the user login name for userId
   * @param userId A valid user id
   * @return - the user login name for userId
   * @require - userId must be a valid user id i.e. userExists(userId) == true
   * @ensure - Returns the user login name for userId
   */
  @Override
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
  @Override
  public String getUserPw(int userId) throws DatabaseException {
    return this.doGetOneString("SELECT password FROM users WHERE rowid='"+userId+"';");
  }


  /**
   * Returns the value of the getUserIsAdmin field in the users table
   * @require userExists(userId) == true
   * @param userId
   * @return
   */
  @Override
  public boolean getUserIsAdmin(int userId) throws DatabaseException {
    return (this.doGetOneInt("SELECT isAdmin FROM users WHERE rowid='"+userId+"';") > 0);
  }


  /**
   * Returns an int array of all user ids in the database
   * @ensure Returns an int array of all user ids in the database
   * @return
   */
  @Override
  public int[] getUserAllIds() throws DatabaseException {
    return this.doOneColumnInts("SELECT rowid FROM users");
  }

  /**
   * Returns the userId for the owner of the auction specified by auctionId
   * @require auctionExists(auctionId) == true
   * @ensure Returns the userId for the owner of the auction specified by auctionId
   * @param auctionId the auction id number to find the owner of
   * @return  Returns the userId for the owner of the auction specified by auctionId
   * @throws DatabaseException
   */
  @Override
  public int getAuctionOwner(int auctionId) throws DatabaseException {
    return this.doGetOneInt("SELECT owner FROM auctions WHERE rowid='"+auctionId+"';");
  }

  /**
   * Returns the title of the auction specified by auctionId
   * @require auctionExists(auctionId) == true
   * @ensure Returns the title of the auction specified by auctionId
   * @param auctionId the auction id of the auction to find the title of
   * @return the title of the auction specified by auctionId
   * @throws DatabaseException
   */
  @Override
  public String getAuctionTitle(int auctionId) throws DatabaseException {
    return this.doGetOneString("SELECT title FROM auctions WHERE rowid='"+auctionId+"';");
  }

  /**
   * Returns the id of the auction type for the auction specified by auctionId
   * @require auctionExists(auctionId) == true
   * @param auctionId
   * @return
   * @throws DatabaseException
   */
  @Override
  public int getAuctionTypeId(int auctionId) throws DatabaseException {
    return this.doGetOneInt("SELECT type FROM auctions WHERE rowid='"+auctionId+"';");
  }

  /**
   * Returns the name of the auctionType specified by auctionTypeId
   * @require auctionTypeExists(auctionTypeId) == true
   * @ensure Returns the name of the auction type specified by auctionTypeId
   * @param auctionTypeId
   * @return
   * @throws DatabaseException
   */
  @Override
  public String getAuctionTypeName(int auctionTypeId) throws DatabaseException {
    return this.doGetOneString("SELECT name FROM auctionType WHERE rowid='"+auctionTypeId+"';");
  }

  /**
   * Returns the current asking price of the auction specified by auctionId
   * @require auctionExists(auctionId) == true
   * @ensure returns the current asking price for the auction specified by auctionId
   * @param auctionId
   * @return
   * @throws DatabaseException
   */
  @Override
  public int getAuctionCurrentAsk(int auctionId) throws DatabaseException {
    int result = this.doGetOneInt("SELECT currentAsk FROM auctions WHERE rowid='"+auctionId+"';");
    return result;
  }

  /**
   * returns true if an auction exists with the auction Id given by auctionId, false otherwise
   * @require auctionId > 0
   * @ensure returns true if an auction exists with the auction Id given by auctionId, false otherwise
   * @param auctionId
   * @return
   * @throws DatabaseException
   */
  @Override
  public boolean auctionExists(int auctionId) throws DatabaseException {
    return this.doGetOneInt("SELECT COUNT(rowid) FROM auctions WHERE rowid = '"+auctionId+"';") > 0;
  }

  /**
   * Returns true if an auction type exists with the auction Type Id given by auctionTypeId, false otherwise
   * @require auctionTypeId > 0
   * @ensure Returns true if an auction type exists with the auction Type Id given by auctionTypeId, false otherwise
   * @param auctionTypeId
   * @return
   * @throws DatabaseException
   */
  @Override
  public boolean auctionTypeExists(int auctionTypeId) throws DatabaseException {
    return this.doGetOneInt("SELECT COUNT(rowid) FROM auctionType WHERE rowid = '"+auctionTypeId+"';") > 0;
  }

  /**
   *  Returns the current bid for the auction specified by auctionId
   * @require auctionExists(auctionId) == true
   * @ensure Returns the current bid for the auction specified by auctionId
   * @param auctionId
   * @return
   * @throws DatabaseException
   */
  @Override
  public int getAuctionCurrentBid(int auctionId) throws DatabaseException {
    int result = this.doGetOneInt("SELECT currentBid FROM auctions WHERE rowid='"+auctionId+"';");
    return result;
  }

  /**
  * Returns the description of the auction specified by auctionId
  * @require auctionExists(auctionId) == true
  * @ensure Returns the title of the auction specified by auctionId
  * @param auctionId the auction id of the auction to find the title of
  * @return the title of the auction specified by auctionId
  * @throws DatabaseException
  */
  @Override
  public String getAuctionDescription(int auctionId) throws DatabaseException {
    return this.doGetOneString("SELECT description FROM auctions WHERE rowid='"+auctionId+"';");
  }

  /**
   * Returns the auction description for the auction specified by auctionId
   * @require auctionExists(auctionId) == true
   * @ensure Returns the auction description for the auction specified by auctionId
   * @param auctionId
   * @return
   * @throws DatabaseException
   */
  @Override
  public int getAuctionBuyout(int auctionId) throws DatabaseException {
    int result = this.doGetOneInt("SELECT buyout FROM auctions WHERE rowid='"+auctionId+"';");
    return result;
  }

  /**
   * Returns the auction start time for the auction specified by auctionId. The auction
   * start time is given in milliseconds and is the epoch time, i.e. 01 jan 1970 00:00:00
   * @require auctionExists(auctionId) == true
   * @ensure returns the auction start time for the auction specified by auctionId
   * @param auctionId
   * @return
   * @throws DatabaseException
   */
  @Override
  public long getAuctionStartTime(int auctionId) throws DatabaseException {

    return this.doGetOneLong("SELECT startTime FROM auctions WHERE rowid = '"+auctionId+"';");
  }

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
  @Override
  public long getAuctionStopTime(int auctionId) throws DatabaseException {

    return this.doGetOneLong("SELECT stopTime FROM auctions WHERE rowid = '"+auctionId+"';");
  }

  /**
   * Returns an int array of all auction Ids.
   * @ensure an int array of all auction Ids.
   * @return
   * @throws DatabaseException
   */
  @Override
  public int[] getAuctionAllIds() throws DatabaseException {
    return this.doOneColumnInts("SELECT rowid FROM auctions;");
  }

  /**
   * Returns the text field for the auction specified by auctionId. The text field is
   * a general purpose String which can be parsed by the auction manager.
   * @require auctionExists(auctionId) == true
   * @ensure Returns the text field for the auction specified by auctionId.
   * @param auctionId
   * @return
   * @throws DatabaseException
   */
  @Override
  public String getAuctionTextField(int auctionId) throws DatabaseException {
    return this.doGetOneString("SELECT genericTextField FROM auctions WHERE rowid = '"+auctionId+"';");
  }

  /**
   * Updates the auction specified by auctionId with the new value specified by newString
   * @require auctionExists(auctionId) == true, newString != null
   * @ensure Updates the auction specified by auctionId with the new value specified by newString
   * @param auctionId
   * @param newString
   * @throws DatabaseException
   */
  @Override
  public void updateAuctionTextField(int auctionId, String newString) throws DatabaseException {
    this.doExec("UPDATE auctions SET [genericTextField] = '"+newString+"' WHERE rowid = '"+auctionId+"';");

  }

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
  @Override
  public int addUser(String loginName, String loginPassword) throws DatabaseException {

    long tStamp = new Date().getTime();
    String pwHash = this.doHashPassword(loginPassword);

    this.doExec("INSERT INTO users VALUES(1,'"+tStamp+"', 'No first name entered yet'"
            + ", -1, 'No last name entered yet', '"+
            this.doCleanText(loginName)+"', '"+pwHash+"', '');");

    return this.doGetOneInt("SELECT rowid FROM users WHERE timestamp='"+tStamp+"';");
  }

  /**
   * Deletes the user specified by userId from the database. Deletes all bids and
   * auctions associated with that user from the database
   * @require userExists(userId) == true
   * @ensure Deletes the user specified by userId from the database. Deletes all bids and
   * auctions associated with that user from the database
   * @param userId
   * @throws DatabaseException
   */
  @Override
  public void deleteUser(int userId) throws DatabaseException {

    // not implemented at this time
    throw new UnsupportedOperationException("Not supported yet.");
  }

  /**
   * Adds an auction to the database and returns the auctionId for that auction
   * @require
   *    buyout > 0
   *    currentAsk > 0
   *    currentBid > 0
   *    description != null
   *    userExists(ownerId) == true
   *    startTime >= 0
   *    stopTime > 0
   *    title != null
   *    auctionTypeExists(auctionType) == true
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
  @Override
  public int addAuction(int buyout, int currentAsk, int currentBid,
  String description, boolean hasBuyout,
  int ownerId, int startTime, int stopTime, String title,
  int auctionType) throws DatabaseException {


    long tStamp = new Date().getTime();

    this.doExec("INSERT INTO auctions VALUES('', "+tStamp+"','"+buyout+"','"+currentAsk+"','"+
            currentBid+"',''"+this.doCleanText(description)+"'',''"+hasBuyout+"'','imageLinks not used','"+
            ownerId+"','"+startTime+"','"+stopTime+"',''"+this.doCleanText(title)+"'','"+auctionType+"');");

    return this.doGetOneInt("SELECT rowid FROM auctions WHERE timestamp = '"+tStamp+"';");

  }

  /**
   * Adds an auction to the db and returns the auctionId for that auction. This is the
   * preferred method for creating a new user, followed by updating fields as necessary
   * @require  userExists(ownerId) == true
   * @ensure Adds an auction to the db and returns the auctionId for that auction
   * @param ownerId
   * @return
   * @throws DatabaseException
   */
  @Override
  public int addAuction(int ownerId) throws DatabaseException {

    long tStamp = new Date().getTime();

    this.doExec("INSERT INTO auctions VALUES('', 1,"+tStamp+",0,0,0,'No Description Provided Yet.','true','','"+ownerId+"',0,0,'No Title Provided Yet',0);");

    return this.doGetOneInt("SELECT rowid FROM auctions WHERE timestamp = '"+tStamp+"';");
  }

  /**
   * Deletes the auction specified by auctionId from the database
   * @require auctonExists(auctionId) == true
   * @ensure Deletes the auction specified by auctionId from the database
   * @param auctionId
   * @throws DatabaseException
   */
  @Override
  public void deleteAuction(int auctionId) throws DatabaseException {
    this.doExec("DELETE FROM auctions WHERE rowid = '"+auctionId+"';");
  }

  /**
   * Returns true if the auction is enabled, false otherwise
   * @require auctionExists(auctionId) == true
   * @ensure Returns true if the auction is enabled, false otherwise
   * @param auctionId
   * @return
   * @throws DatabaseException
   */
  @Override
  public boolean getAuctionEnabled(int auctionId) throws DatabaseException {

    return (this.doGetOneInt("SELECT enabled FROM auctions WHERE rowid = '"+auctionId+"';") > 0);
  }

  /**
   * Updates the auction specifed by auctionId as enabled if isEnabled == true, or false otherwise
   * @require auctionExists(auctionId) == true, isEnabled != null
   * @ensure Updates the auction specifed by auctionId as enabled if isEnabled == true, or false otherwise
   * @param auctionId
   * @param isEnabled
   * @throws DatabaseException
   */
  @Override
  public void updateAuctionEnabled(int auctionId, boolean isEnabled) throws DatabaseException {

    int enabledValue = (isEnabled) ? 1 : 0;

    this.doExec("UPDATE auctions SET [enabled] = '"+enabledValue+"' WHERE rowid = '"+auctionId+"';");
  }

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
  @Override
  public void deleteAuctionType(int auctionTypeId) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

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
  @Override
  public void updateAuctionBuyout(int auctionId, int buyoutPrice) throws DatabaseException {
    this.doExec("UPDATE auctions SET [buyout] = '"+buyoutPrice+"' WHERE rowid = '"+auctionId+"';");
  }

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
  @Override
  public void updateAuctionCurrentAsking(int auctionId, int currentAsking) throws DatabaseException {
    this.doExec("UPDATE auctions SET [currentAsk] = '"+currentAsking+"' WHERE rowid = '"+auctionId+"';");
  }

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
  @Override
  public void updateAuctionCurrentBid(int auctionId, int currentBid) throws DatabaseException {
    this.doExec("UPDATE auctions SET [currentBid] = '"+currentBid+"' WHERE rowid = '"+auctionId+"';");
  }


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
  @Override
  public void updateAuctionDescription(int auctionId, String description) throws DatabaseException {
    this.doExec("UPDATE auctions SET [description] = '"+this.doCleanText(description)+"' WHERE rowid = '"+auctionId+"';");
  }

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
  @Override
  public void updateAuctionHasBuyout(int auctionId, boolean hasBuyout) throws DatabaseException {
    int updateValue = hasBuyout ? 1 : 0;
    this.doExec("UPDATE auctions SET [hasBuyout] = '"+updateValue+"' WHERE rowid = '"+auctionId+"';");
  }

  /**
   * returns the status of hasBuyout for the auction specified by auctionId
   * @require auctionExists(auctionId) == true
   * @ensure returns the status of hasBuyout for the auction specified by auctionId
   * @param auctionId
   * @return
   * @throws DatabaseException
   */
  @Override
  public boolean getAuctionHasBuyout(int auctionId) throws DatabaseException {
    int buyoutValue;
    boolean result = this.doGetOneInt("SELECT hasBuyout FROM auctions WHERE rowid = '"+auctionId+"';") == 1 ? true : false;
    return result;
  }

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
  @Override
  public void updateAuctionStartTime(int auctionId, long startTime) throws DatabaseException {
    this.doExec("UPDATE auctions SET [startTime] = '"+startTime+"' WHERE rowid = '"+auctionId+"';");
  }

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
  @Override
  public void updateAuctionStopTime(int auctionId, long stopTime) throws DatabaseException {
    this.doExec("UPDATE auctions SET [stopTime] = '"+stopTime+"' WHERE rowid = '"+auctionId+"';");
  }

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
  @Override
  public void updateAuctionTitle(int auctionId, String auctionTitle) throws DatabaseException {
    this.doExec("UPDATE auctions SET [title] = '"+this.doCleanText(auctionTitle)+"' WHERE rowid = '"+auctionId+"';");
  }

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
  @Override
  public void updateAuctionType(int auctionId, int auctionTypeId) throws DatabaseException {
    this.doExec("UPDATE auctions SET [type] = '"+auctionTypeId+"' WHERE rowid = '"+auctionId+"';");
  }

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
  @Override
  public void updateUserFirstName(int userId, String newFirstName) throws DatabaseException {
    this.doExec("UPDATE users SET firstName = '"+this.doCleanText(newFirstName)+"' WHERE rowid = '"+userId+"';");
  }

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
  @Override
  public void updateUserLastName(int userId, String newLastName) throws DatabaseException {

    this.doExec("UPDATE users SET lastName = '"+this.doCleanText(newLastName)+"' WHERE rowid = '"+userId+"';");
  }

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
  @Override
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
   * @throws DatabaseException
   */
  @Override
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
   * @throws DatabaseException
   */
  @Override
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
   * @throws DatabaseException
   */
  @Override
  public void updateUserPassword(int userId, String newPW) throws DatabaseException {

    String hashed = this.doHashPassword(newPW);
    this.doExec("UPDATE users SET password = '"+hashed+"' WHERE rowid = '"+userId+"';");
  }

  /**
   * Hashes the password for use in this class
   * @param password
   * @return
   */
  private String doHashPassword(String password) throws DatabaseException {

    BCrypt crypt = new BCrypt();
    return BCrypt.hashpw(password, this.getSalt());
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
   * @throws DatabaseException
   */
  private boolean loginSuccessful(String login, String password) throws DatabaseException {

    String hashed = this.doHashPassword(password);//System.out.println("SELECT COUNT(rowid) FROM users WHERE login='"+this.doCleanText(login)+"' AND password='"+hashed+"';");

    return ((this.doGetOneInt("SELECT COUNT(rowid) FROM users WHERE login='"+this.doCleanText(login)+"' AND password='"+hashed+"';")) == 1);
  }

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
  @Override
  public int login(String login, String password) throws DatabaseException {

    String hashed = this.doHashPassword(password);
    int result = -1; System.out.println("Login successful: " +this.loginSuccessful(login, password));
    if(this.loginSuccessful(login, password)) {
      result = this.doGetOneInt("SELECT rowid FROM users WHERE login='"+this.doCleanText(login)+"' AND password='"+hashed+"';");
    }

    return result;
  }

  /**
   * returns the number of auctions in the database
   * @ensure returns the number of auctions in the database
   * @return
   * @throws DatabaseException
   */
  @Override
  public int auctionCount() throws DatabaseException {
    return this.doGetOneInt("SELECT COUNT(*) FROM auctions;");
  }

  /**
   * returns the status of userEnabled for the user specified by userId
   * @required userExists(userId)
   * @ensure returns the status of userEnabled for the user specified by userId
   * @param userId
   * @return
   * @throws DatabaseException
   */
  @Override
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
   * @require auctionExists(auctionId) == true, userExists(userId) == true, bidAmount >= 0
   * @ensure Adds a new bid to the database and returns the bidId for that bid
   * @param auctionId
   * @param userId
   * @param bidAmount
   * @return
   * @throws DatabaseException
   */
  @Override
  public int addBid(int auctionId, int userId, int bidAmount) throws DatabaseException {

    long tStamp = new Date().getTime();

    this.doExec("INSERT INTO bids VALUES('0', "+tStamp+"','"+auctionId+"','"+userId+"', '"+bidAmount+"');");

    return this.doGetOneInt("SELECT rowid FROM bids WHERE timestamp = '"+tStamp+"';");

  }

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
  @Override
  public boolean bidExists(int bidId) throws DatabaseException {
    return (this.doGetOneInt("SELECT COUNT(*) FROM bids WHERE rowid = '"+bidId+"';") == 1);
  }

  /**
   * returns true if the bid is expired, false if the bid is still active
   * @require bidExists(bidId) == true
   * @ensure returns true if the bid is expired, false if the bid is still active
   * @param bidId
   * @return
   * @throws DatabaseException
   */
  @Override
  public boolean bidIsExpired(int bidId) throws DatabaseException {
    boolean result = (this.doGetOneInt("SELECT expired FROM bids WHERE rowid='"+bidId+"';")>0) ? true : false;

    return result;
  }

  /**
   * Sets the bid specified by bidId to expired
   * @require bidExists(bidId) == true
   * @ensure Sets the bid specified by bidId to expired
   * @param bidId
   * @throws DatabaseException
   */

  @Override
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
   * @throws DatabaseException
   */
  @Override
  public void updateBidAmount(int bidId, int bidAmount) throws DatabaseException {
    this.doExec("UPDATE bids SET amount = '"+bidAmount+"' WHERE rowid = '"+bidId+"';");
  }

  /**
   * returns the bid amount for the bid specified by bidId
   * @require bidExists(bidId) == true
   * @ensure returns the bid amount for the bid specified by bidId
   * @param bidId
   * @return
   * @throws DatabaseException
   */
  @Override
  public int getBidAmount(int bidId) throws DatabaseException {
    return this.doGetOneInt("SELECT amount FROM bids WHERE rowid = '"+bidId+"';");
  }

  /**
   * deletes the bid specified by bidId from the database
   * @require bidExists(bidId) == true
   * @ensure deletes the bid specified by bidId from the database
   * @param bidId
   * @throws DatabaseException
   */
  @Override
  public void deleteBid(int bidId) throws DatabaseException {
    this.doExec("DELETE * FROM bids WHERE rowid = '"+bidId+"';");
  }

  /**
   * returns the userId for owner of the bid specified by bidId
   * @require bidExists(bidId) == true
   * @ensure returns the userId for owner of the bid specified by bidId
   * @param bidId
   * @return
   * @throws DatabaseException
   */
  @Override
  public int getBidUserId(int bidId) throws DatabaseException {
    return this.doGetOneInt("SELECT userId FROM bids WHERE rowid = '"+bidId+"';");
  }

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
  @Override
  public void setBidEnabled(int bidId, boolean value) throws DatabaseException {
    if(value == true)
      this.doExec("UPDATE bids SET enabled = 1 WHERE rowid = '"+bidId+"';");
    else
      this.doExec("UPDATE bids SET enabled = 0 WHERE rowid = '"+bidId+"';");
  }
} // end of class
