package us.nstro.javaauction.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.UUID;
import us.nstro.javaauction.auction.AuctionBuilder;
import us.nstro.javaauction.auction.DutchAuction;
import us.nstro.javaauction.auction.User;
import us.nstro.javaauction.bids.Bid;
import us.nstro.javaauction.bids.Item;
import us.nstro.javaauction.bids.Price;
import us.nstro.javaauction.handler.ExternalTicker;
import us.nstro.javaauction.type.Selection;

/**
 *
 * @author bbecker
 */
public class DutchAuctionTest {

    private DutchAuction auction;
    private ExternalTicker timer;

    public DutchAuctionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        AuctionBuilder builder = new AuctionBuilder();
        builder.setAuctionName("Big kitty!");
        builder.setAuctioneer(User.createUser("Vera Stalks"));
        builder.setProduct(Item.createItem("An oversized Maine coon"));

        this.timer = new ExternalTicker();
        this.auction = builder.createDutchAuction(timer, new Price(5000), new Price(50), new Price(500));
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testInfo() {
        assertEquals(this.auction.getInfo().getName(), "Big kitty!");
        assertEquals(this.auction.getInfo().getAuctioneer().getName(), "Vera Stalks");
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

    @Test
    public void testValidPrices() {
        this.auction.startAuction();
        assertEquals(this.auction.getValidPrices(), new Selection<Price>(new Price(5000), new Price(5000)));
        this.timer.tick();
        assertEquals(this.auction.getValidPrices(), new Selection<Price>(new Price(4950), new Price(4950)));
        this.timer.tick();
        assertEquals(this.auction.getValidPrices(), new Selection<Price>(new Price(4900), new Price(4900)));
    }

    @Test
    public void testTimerFinish() {
        this.auction.startAuction();
        assertEquals(this.auction.getValidPrices(), new Selection<Price>(new Price(5000), new Price(5000)));
        for(int i = 0; i < 95; i++)
            this.timer.tick();
        assertEquals(this.auction.getValidPrices(), new Selection<Price>(new Price(500), new Price(500)));
    }

    @Test
    public void testPlaceStartingBid() {
        this.auction.startAuction();
        this.auction.placeBid(new Bid(User.createUser("Brian"), this.auction, new Price(5000)));

        assertFalse(this.auction.isOpen());
        assertFalse(this.auction.isAborted());
        assertFalse(this.auction.getWinningBids().isEmpty());
    }

    @Test
    public void testPlaceInvalidBid() {
        this.auction.startAuction();
        this.auction.placeBid(new Bid(User.createUser("Brian"), this.auction, new Price(4000)));

        assertTrue(this.auction.isOpen());
        assertFalse(this.auction.isAborted());
        assertTrue(this.auction.getWinningBids().isEmpty());
    }

    @Test
    public void testPlaceValidBid() {
        this.auction.startAuction();

        for(int i = 0; i < 4; i++)
            this.timer.tick();
        
        this.auction.placeBid(new Bid(User.createUser("Brian"), this.auction, new Price(4800)));

        assertFalse(this.auction.isOpen());
        assertFalse(this.auction.isAborted());
        assertFalse(this.auction.getWinningBids().isEmpty());
    }

    @Test
    public void testPlaceLowestBid() {
        this.auction.startAuction();
        assertEquals(this.auction.getValidPrices(), new Selection<Price>(new Price(5000), new Price(5000)));
        for(int i = 0; i < 95; i++)
            this.timer.tick();

        this.auction.placeBid(new Bid(User.createUser("Brian"), this.auction, new Price(500)));

        assertFalse(this.auction.isOpen());
        assertFalse(this.auction.isAborted());
        assertFalse(this.auction.getWinningBids().isEmpty());
        
    }

}