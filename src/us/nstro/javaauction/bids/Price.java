package us.nstro.javaauction.bids;

/**
 *  Price, which represents a price for a bid, buyout, etc. in an auction.
*/
public class Price implements Comparable<Price> {

    private Integer amount;
    
    /**
     *  Create a new price for the specified amount.
     *
     *  @require: amount > 0
    */
    public Price(Integer amount) {
        this.amount = amount;
    }

    /**
     * Get the previous valid price.
     */
    public Price prev(Price drop) {
        return new Price(this.amount - drop.amount);
    }

    /**
     * Get the next valid price.
     */
    public Price next(Price raise) {
        return new Price(this.amount + raise.amount);
    }
    
    /**
     *  Compare a price with another price, returning the highest value.
     *
     *  @require: o.amount > 0
     *  @ensure: compareTo(o) > 0 if price is higher, compareTo(o) < 0 if
     *      price is lower, compareTo(o) == 0 if prices are equal
    */
    public int compareTo(Price o) {
        return this.amount.compareTo(o.amount);
    }
    
    /**
     *  Determine if two prices are equal.
     *
     *  @require: Object to be compared with must be a Price
     *  @ensure: if o.amount == this.amount, this.equals(o) must return true,
     *      otherwise it must return false
    */
    @Override
    public boolean equals(Object o) {
        if(o instanceof Price)
            return this.amount.compareTo(((Price)o).amount) == 0;
        else
            return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.amount != null ? this.amount.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        String extraZero = (this.amount % 100) >= 10 ? "" : "0";
        return "$" + (this.amount / 100) + "." + extraZero + (this.amount % 100);
    }
    
}