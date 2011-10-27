package us.nstro.javaauction.type;

/**
 * A Selection, which specifies a set of possibilities for selecting values.
*/
public interface Selection<Type> {

    /**
     * Is the selection empty, that is, is no value contained in it?
     */
    public boolean isEmpty();

    /**
     * Query the selection to determine if value is a valid value accepted
     * by this selection.
     *
     * @param value Value to check if in selection
     * @return whether or not value is in selection
     */
    public boolean contains(Type value);
    
}