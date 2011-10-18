package us.nstro.javaauction.type;

/**
 * A Selection, which specifies a lower bound and an optional upper
 * bound.
*/
public class Selection<Select extends Comparable<? super Select>> {
    
    private Select min = null;
    private Select max = null;

    /**
     * if upperBound is false
     */
    private boolean upperBound = false;
    
    /**
     *  Create a new Selection with a lower bound of min and no
     *  upper bound.
     */
    public Selection(Select min) {
        this.upperBound = false;
        this.min = min;
    }
    
    /**
     *  Create a new Selection with a lower bound of min and
     *  an upper bound of max.
     */
    public Selection(Select min, Select max) {
        this.upperBound = true;
        this.min = min;
        this.max = max;
    }

    /**
     * Does this selection have an upper bound?
     *
     * @ensure: if this.hasUpperBound(), this.max != null
     */
    public boolean hasUpperBound() {
        return this.upperBound;
    }

    /**
     * Get the minimum value of this selection.
     */
    public Select minimum() {
        return this.min;
    }

    /**
     * Get the maximum value of this selection.
     *
     * @require: this.hasUpperBound()
     */
    public Select maximum() {
        return this.max;
    }
    
    /**
     *	Determine if this selection contains the given value within
     *	its min and max.
     */
    public boolean contains(Select value) {
        if(this.upperBound)
            return
                this.min.compareTo(value) <= 0 &&
                this.max.compareTo(value) >= 0;
        else
            return
                this.min.compareTo(value) <= 0;
    }
    
    /**
     *	Is this an empty selection? I.e., are all possibilities invalid.
     *
     *	@ensure: !isEmpty() if contains(i) is valid for any i
    */
    public boolean isEmpty() {
        return (this.min == null && this.max == null);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Selection) {
            Selection other = (Selection) o;
            return (this.min.equals(other.min) && this.max.equals(other.max));
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.min != null ? this.min.hashCode() : 0);
        hash = 37 * hash + (this.max != null ? this.max.hashCode() : 0);
        return hash;
    }

    public String toString() {
        return "" + this.min + " " + this.max;
    }
    
}