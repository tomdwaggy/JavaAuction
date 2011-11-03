package us.nstro.javaauction.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

import us.nstro.javaauction.auction.Auction;
import us.nstro.javaauction.auction.DutchAuctionBuilder;

import us.nstro.javaauction.bids.Bid;
import us.nstro.javaauction.bids.Item;
import us.nstro.javaauction.bids.Price;
import us.nstro.javaauction.db.User;

import us.nstro.javaauction.handler.MockTicker;
import us.nstro.javaauction.type.SingleValue;

/**
 * Test the Dutch Auction.
 *
 * @author bbecker
 */
public class DutchAuctionTest {

    private DutchAuctionBuilder builder;
    private Auction auction;
    private MockTicker timer;

    private User vera, brian, arven, duke;

    public DutchAuctionTest() {
        this.timer = new MockTicker();
        this.builder = new DutchAuctionBuilder();

        this.vera = new User(0, new String[]{"Vera", "Stalks", ""}, "vstalks", false);
        this.brian = new User(1, new String[]{"Brian", "Becker", ""}, "bbecker", false);
        this.arven = new User(2, new String[]{"Arven", "Isme", ""}, "aisme", false);
        this.duke = new User(3, new String[]{"Duke", "Duke", ""}, "dduke", false);
        
        builder.setAuctionName("Big kitty!");
        builder.setAuctioneer(vera);
        builder.setProduct(Item.createItem("An oversized Maine coon"));
        builder.setTicker(this.timer);
        builder.setInitialPrice(new Price(5000));
        builder.setLowestPrice(new Price(500));
        builder.setDecrement(new Price(50));
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
    public void testValidPrices() {
        this.auction.startAuction();
        assertEquals(this.auction.getValidPrices(), new SingleValue<Price>(new Price(5000)));
        this.timer.tick();
        assertEquals(this.auction.getValidPrices(), new SingleValue<Price>(new Price(4950)));
        this.timer.tick();
        assertEquals(this.auction.getValidPrices(), new SingleValue<Price>(new Price(4900)));
    }

    @Test
    public void testTimerFinish() {
        this.auction.startAuction();
        assertEquals(this.auction.getValidPrices(), new SingleValue<Price>(new Price(5000)));
        for(int i = 0; i < 95; i++)
            this.timer.tick();
        assertEquals(this.auction.getValidPrices(), new SingleValue<Price>(new Price(500)));
    }

    @Test
    public void testPlaceStartingBid() {
        this.auction.startAuction();
        this.auction.placeBid(new Bid(brian, this.auction, new Price(5000)));

        assertFalse(this.auction.isOpen());
        assertFalse(this.auction.isAborted());
        assertFalse(this.auction.getWinningBids().isEmpty());
    }

    @Test
    public void testPlaceInvalidBid() {
        this.auction.startAuction();
        this.auction.placeBid(new Bid(brian, this.auction, new Price(4000)));

        assertTrue(this.auction.isOpen());
        assertFalse(this.auction.isAborted());
        assertTrue(this.auction.getWinningBids().isEmpty());
    }

    @Test
    public void testPlaceValidBid() {
        this.auction.startAuction();

        for(int i = 0; i < 4; i++)
            this.timer.tick();
        
        this.auction.placeBid(new Bid(brian, this.auction, new Price(4800)));

        assertFalse(this.auction.isOpen());
        assertFalse(this.auction.isAborted());
        assertFalse(this.auction.getWinningBids().isEmpty());
    }

    @Test
    public void testPlaceLowestBid() {
        this.auction.startAuction();
        assertEquals(this.auction.getValidPrices(), new SingleValue<Price>(new Price(5000)));
        for(int i = 0; i < 95; i++)
            this.timer.tick();

        this.auction.placeBid(new Bid(brian, this.auction, new Price(500)));

        assertFalse(this.auction.isOpen());
        assertFalse(this.auction.isAborted());
        assertFalse(this.auction.getWinningBids().isEmpty());
    }

}