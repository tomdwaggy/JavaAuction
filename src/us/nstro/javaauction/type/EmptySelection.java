/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package us.nstro.javaauction.type;

/**
 * The null type for the Selection interface. This is an empty set that
 * contains nothing.
 *
 * @author bbecker
 */
public class EmptySelection<Type> implements Selection<Type> {

    /**
     * Create a new empty selection.
     */
    public EmptySelection() {
    }

    /**
     * The null type is always empty.
     * @return boolean whether the selection is empty.
     *
     * @ensure: isEmpty()
     */
    public boolean isEmpty() {
        return true;
    }

    /**
     * The null type contains nothing.
     * @param value the value to check if it is in the valid selections.
     * @return boolean whether value is in the valid selections.
     *
     * @ensure: contains(i) == false, for all i
     */
    public boolean contains(Type value) {
        return false;
    }

    @Override
    public String toString() {
        return "[]";
    }
    
}
