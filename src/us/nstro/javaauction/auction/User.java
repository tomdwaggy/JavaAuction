package us.nstro.javaauction.auction;

import java.util.UUID;

/**
 *  User, which represents a given user of the auction system.
*/
public class User {
    
    private UUID userID;
    private String name;
    
    /**
     *  Create a new user identified by the id userID and given
     *  a human-readable name.
    */
    public User(UUID userID, String name) {
        this.userID = userID;
        this.name = name;
    }

    /**
     * Get the user ID.
     */
    public UUID getUserID() {
        return this.userID;
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
            return this.userID == ((User)o).userID;
        else
            return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.userID != null ? this.userID.hashCode() : 0);
        return hash;
    }
    
}