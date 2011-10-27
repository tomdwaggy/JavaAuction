/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package us.nstro.javaauction.type;

/**
 * A selection with only a lower bound is a Minimum, for instance a
 * Minimum<Price> for lowest price that's accepted in a bid.
 *
 * @author bbecker
 */
public class Minimum<Type extends Comparable<? super Type>> implements Selection<Type> {
    private Type min = null;

    /**
     *  Create a new Selection with a lower bound of min and no
     *  upper bound.
     */
    public Minimum(Type min) {
        this.min = min;
    }

    /**
     * Get the minimum value of this selection.
     */
    public Type minimum() {
        return this.min;
    }

    /**
     *	Determine if this selection contains the given value.
     */
    public boolean contains(Type value) {
        return this.min.compareTo(value) <= 0;
    }

    /**
     *	Is this an empty selection? I.e., are all possibilities invalid.
     *
     *	@ensure: !isEmpty() if contains(i) is valid for any i
    */
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Minimum) {
            Minimum other = (Minimum) o;
            return this.min.equals(other.min);
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.min != null ? this.min.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "[" + this.min + ", inf)";
    }
}
