package us.nstro.javaauction.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import us.nstro.javaauction.auction.Auction;
import us.nstro.javaauction.auction.AscendingAuctionBuilder;

import us.nstro.javaauction.bids.Bid;
import us.nstro.javaauction.bids.Item;
import us.nstro.javaauction.bids.Price;
import us.nstro.javaauction.db.User;

/**
 * Test the Ascending Auction.
 *
 * @author bbecker
 */
public class AscendingAuctionTest {

    private AscendingAuctionBuilder builder;
    private Auction auction;

    private User vera, brian, arven, duke;

    public AscendingAuctionTest() {
        this.builder = new AscendingAuctionBuilder();

        this.vera = new User(0, new String[]{"Vera", "Stalks", ""}, "vstalks", false);
        this.brian = new User(1, new String[]{"Brian", "Becker", ""}, "bbecker", false);
        this.arven = new User(2, new String[]{"Arven", "Isme", ""}, "aisme", false);
        this.duke = new User(3, new String[]{"Duke", "Duke", ""}, "dduke", false);

        builder.setMinimumBid(new Price(5000));
        builder.setAuctionName("Big kitty!");
        builder.setAuctioneer(vera);
        builder.setProduct(Item.createItem("An oversized Maine coon"));
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testInfo() {
        assertEquals(this.auction.getInfo().getName(), "Big kitty!");
        assertEquals(this.auction.getInfo().getAuctioneer().getLogin(), "vstalks");
        assertEquals(this.auction.getInfo().getProducts().size(), 1);
        assertEquals(this.auction.getInfo().getProducts().iterator().next().getName(), "An oversized Maine coon");
    }

    @Test
    public void testAuctionStart() {
        assertFalse(this.auction.isOpen());
        assertFalse(this.auction.isAborted());

        this.auction.startAuction();
        assertTrue(this.auction.isOpen());
        assertFalse(this.auction.isAborted());
    }

    @Test
    public void testAuctionClose() {
        assertFalse(this.auction.isOpen());
        assertFalse(this.auction.isAborted());

        this.auction.startAuction();
        this.auction.closeAuction();

        assertFalse(this.auction.isOpen());
        assertFalse(this.auction.isAborted());
    }

    @Test
    public void testAuctionAbort() {
        assertFalse(this.auction.isOpen());
        assertFalse(this.auction.isAborted());

        this.auction.startAuction();
        this.auction.abortAuction();

        assertFalse(this.auction.isOpen());
        assertTrue(this.auction.isAborted());
    }

    @Before
    public void setUp() {
        this.builder.setAuctionID(0);
        this.auction = this.builder.build();
    }

    @Test
    public void testPlaceMinimumBid() {
        this.auction.startAuction();
        assertTrue(this.auction.isOpen());
        assertFalse(this.auction.isAborted());
        assertTrue(this.auction.getWinningBids().isEmpty());

        this.auction.placeBid(new Bid(brian, this.auction, new Price(5000)));
        assertFalse(this.auction.getWinningBids().isEmpty());
    }

    @Test
    public void testPlaceLowerThanMinimumBid() {
        this.auction.startAuction();
        assertTrue(this.auction.isOpen());
        assertFalse(this.auction.isAborted());
        assertTrue(this.auction.getWinningBids().isEmpty());

        this.auction.placeBid(new Bid(brian, this.auction, new Price(-100)));
        assertTrue(this.auction.getWinningBids().isEmpty());

        this.auction.placeBid(new Bid(brian, this.auction, new Price(0)));
        assertTrue(this.auction.getWinningBids().isEmpty());

        this.auction.placeBid(new Bid(brian, this.auction, new Price(4999)));
        assertTrue(this.auction.getWinningBids().isEmpty());
    }

    @Test
    public void testPlaceHigherThanPreviousBidder() {
        this.auction.startAuction();
        assertTrue(this.auction.isOpen());
        assertFalse(this.auction.isAborted());
        assertTrue(this.auction.getWinningBids().isEmpty());

        this.auction.placeBid(new Bid(brian, this.auction, new Price(5000)));
        assertFalse(this.auction.getWinningBids().isEmpty());

        this.auction.placeBid(new Bid(arven, this.auction, new Price(5050)));
        assertFalse(this.auction.getWinningBids().isEmpty());
        assertTrue(this.auction.getWinningBids().iterator().hasNext());
        assertEquals(this.auction.getWinningBids().iterator().next().getUser().getLogin(), "aisme" );

        this.auction.placeBid(new Bid(duke, this.auction, new Price(50000)));
        assertFalse(this.auction.getWinningBids().isEmpty());
        assertTrue(this.auction.getWinningBids().iterator().hasNext());
        assertEquals(this.auction.getWinningBids().iterator().next().getUser().getLogin(), "dduke" );
    }

    @Test
    public void testPlaceLowerThanPreviousBidder() {
        this.auction.startAuction();
        assertTrue(this.auction.isOpen());
        assertFalse(this.auction.isAborted());
        assertTrue(this.auction.getWinningBids().isEmpty());

        this.auction.placeBid(new Bid(brian, this.auction, new Price(50000)));
        assertFalse(this.auction.getWinningBids().isEmpty());
        assertTrue(this.auction.getWinningBids().iterator().hasNext());
        assertEquals(this.auction.getWinningBids().iterator().next().getUser().getLogin(), "bbecker" );

        this.auction.placeBid(new Bid(arven, this.auction, new Price(5050)));
        assertFalse(this.auction.getWinningBids().isEmpty());
        assertTrue(this.auction.getWinningBids().iterator().hasNext());
        assertEquals(this.auction.getWinningBids().iterator().next().getUser().getLogin(), "bbecker" );

        this.auction.placeBid(new Bid(duke, this.auction, new Price(49999)));
        assertFalse(this.auction.getWinningBids().isEmpty());
        assertTrue(this.auction.getWinningBids().iterator().hasNext());
        assertEquals(this.auction.getWinningBids().iterator().next().getUser().getLogin(), "bbecker" );
    }

    @Test
    public void testValidPrices() {
        this.auction.startAuction();

        // The valid prices should contain the minimum bid initially.
        //assertTrue(this.auction.getValidPrices().contains(new Price(5000)));

        this.auction.placeBid(new Bid(brian, this.auction, new Price(50000)));

        // The valid prices should not contain the minimum bid anymore.
        assertFalse(this.auction.getValidPrices().contains(new Price(5000)));

        // The valid prices should contain anything higher than the bid price.
        assertTrue(this.auction.getValidPrices().contains(new Price(50001)));

        // The valid prices should not contain the actual bid.
        assertFalse(this.auction.getValidPrices().contains(new Price(50000)));

    }

}