package us.nstro.javaauction.auction;

import us.nstro.javaauction.types.selection.Selection;

/**
 * The Sealed First Bid Auction is an auction in which all of the bidders bid
 * without the value that they bid being released. The highest bid is the
 * winning bid after the timeout.
 *
 * @author bbecker
 */
public class SealedFirstBidAuction extends AbstractAuction {

    private Selection<Price> sealedValidPrice;

    /**
     * Creates a new Sealed First Bid Auction with a minimum bid price of
     * min. The minimum bid is visible, no further bids are visible.
     */
    public SealedFirstBidAuction(AuctionInfo info, Price min) {
        super(info);
        this.updateValidPrices(new Selection<Price>(min));
        this.sealedValidPrice = new Selection<Price>(min);
    }

    /**
     * Places the bid 'bid' on this auction. After bidding, the hidden
     * sealedBids attribute will be set to contain the actual valid bids,
     * and bids which are lower are silently dropped.
     *
     * @require: getValidBids().contains(bid)
     */
    public void placeBid(Bid bid) {
        if(this.sealedValidPrice.contains(bid.getPrice())) {
            this.setCurrentWinningBid(bid);
            this.sealedValidPrice = new Selection<Price>(bid.getPrice().next(new Price(1)));
        }
    }

}
