package auction;

/**
 *  User, which represents a given user of the auction system.
*/
public class User {
    
    private int userID;
    private String name;
    
    /**
     *  Create a new user identified by the id userID and given
     *  a human-readable name.
    */
    public User(int userID, String name) {
        this.userId = userID;
        this.name = name;
    }
    
    /**
     *  Determine if two users are the same
     *
     *  @require: Object to be compared with must be a User
     *  @ensure:
     *      if o.userID = this.userID, this.equals(o) == true
     *      if o.userID != this.userID, this.equals(o) == false
    */
    public boolean equals(Object o) {
        if(o instanceof Price)
            return this.amount.compareTo(o.amount) == 0;
        else
            return false;
    }
    
}