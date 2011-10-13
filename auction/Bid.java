package auction;

public class Bid implements Comparable<Bid> {
    
    private Integer amount;
    
    public Bid(Integer amount) {
        this.amount = amount;
    }
    
    public boolean compareTo(Bid o) {
        return this.amount.compareTo(o.amount);
    }
    
}