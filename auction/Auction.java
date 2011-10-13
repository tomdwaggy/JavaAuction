package auction;

public interface Auction {
    
    public Range<Bid> getValidBid();
    public placeBid(Bid bid);
    
}