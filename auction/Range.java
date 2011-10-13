package auction;

/**
 *  Range, which represents a valid range of objects, given that they can be
 *  compared to each other. In an auction, minimum and maximum bids may be
 *  specified as a range, but bids could be specified as a range for automatic
 *  bidding ala E-Bay.
*/
public class Range<Type extends Comparable> {
    
    private Type min = null;
    private Type max = null;
    
    /**
     *	Create a new range, with a minimum value of min
     *	and a maximum value of max.
    */
    public Range(Type min, Type max) {
	this.min = min;
	this.max = max;
    }
    
    /**
     *	Determine if this range contains a given value.
    */
    public contains(Type value) {
	if(this.max && this.min)
		return
			this.min.compareTo(value) <= 0 &&
			this.max.compareTo(value) >= 0;
	else if(this.min)
		return
			this.min.compareTo(value) <= 0;
	else if(this.max)
		return
			this.max.compareTo(value) >= 0;
	else
		return false;
    }
    
    /**
     *	Is this an empty range? I.e., are all possibilities invalid.
     *
     *	@ensure: !isEmpty() if contains(i) is valid for any i
    */
    public isEmpty() {
	return
		this.min == null && this.max == null ||
		this.min.compareTo(this.max) > 0;
    }
    
    /**
     *	Does this range have a minimum value?
     *
     *	@ensure: (this.min != null) == hasMinimum()
    */
    public hasMinimum() {
	return this.min != null;
    }
    
    /**
     *  Gets the minimum value in this range.
     *
     *  @require: hasMinimum()
     *  @ensure: getMinimum() <= every i which fulfills contains(i)
    */
    public getMinimum() {
	return this.min;
    }
    
    /**
     *	Does this range have a maximum value?
     *
     *	@ensure: (this.max != null) == hasMaximum()
    */
    public hasMaximum() {
	return this.max != null;
    }
    
    /**
     *  Gets the maximum value in this range.
     *
     *  @require: hasMinimum()
     *  @ensure: getMaximum() >= every i which fulfills contains(i)
    */
    public getMaximum() {
	return this.max;
    }
}