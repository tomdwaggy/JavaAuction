/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package us.nstro.javaauction.type;

/**
 *
 * @author bbecker
 */
public class SingleValue<Type> implements Selection<Type> {

    private Type value;

    public SingleValue(Type value) {
        this.value = value;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean contains(Type value) {
        return this.value.equals(value);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof SingleValue) {
            SingleValue other = (SingleValue) o;
            return this.value.equals(other.value);
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "[" + this.value + "]";
    }
    
}
