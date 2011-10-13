package auction;

public class Range<Type extends Comparable> {
    
    private Type min = null;
    private Type max = null;
    
    public Range(Type min, Type max) {
	this.min = min;
	this.max = max;
    }
    
    public contains(Type value) {
	return
		this.min.compareTo(value) <= 0 &&
		this.max.compareTo(value) >= 0;
    }
    
    public getMinimum() {
	return this.min;
    }
    
    public getMaximum() {
	return this.max;
    }
}