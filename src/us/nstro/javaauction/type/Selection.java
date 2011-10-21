package us.nstro.javaauction.type;

/**
 * A Selection, which specifies a lower bound and an optional upper
 * bound.
*/
public interface Selection<Type> {

    public boolean isEmpty();
    public boolean contains(Type value);
    
}