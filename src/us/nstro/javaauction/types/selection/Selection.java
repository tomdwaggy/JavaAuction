package us.nstro.javaauction.types.selection;

/**
 *  Selection, which represents a valid set of objects which will be accepted
 *  by a particular method.
*/
public interface Selection<Select> {
    
    /**
     *	Determine if this selection contains a given value.
    */
    public boolean contains(Select value);
    
    /**
     *	Is this an empty selection? I.e., are all possibilities invalid.
     *
     *	@ensure: !isEmpty() if contains(i) is valid for any i
    */
    public boolean isEmpty();
    
}