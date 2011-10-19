package us.nstro.javaauction.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.UUID;
import us.nstro.javaauction.auction.Auction;
import us.nstro.javaauction.auction.AuctionBuilder;
import us.nstro.javaauction.auction.User;
import us.nstro.javaauction.auction.strategy.DutchAuctionStrategy;
import us.nstro.javaauction.bids.Item;
import us.nstro.javaauction.bids.Price;
import us.nstro.javaauction.timer.ExternalAuctionTimer;
import us.nstro.javaauction.type.Selection;

/**
 *
 * @author bbecker
 */
public class DutchAuctionTest {

    private Auction auction;
    private ExternalAuctionTimer timer;

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
        builder.setAuctionID(UUID.randomUUID());
        builder.setAuctionName("Big kitty!");

        User auctioneer = new User(UUID.randomUUID(), "Vera Stalks");
        builder.setAuctioneer(auctioneer);

        Item product = new Item(UUID.randomUUID(), "An oversized Maine coon");
        builder.setProduct(product);

        this.timer = new ExternalAuctionTimer();
        DutchAuctionStrategy dutch = new DutchAuctionStrategy(timer, new Price(5000), new Price(50), new Price(500));
        dutch.startAuctionTimer();

        builder.setStrategy(dutch);

        this.auction = builder.createAuction();
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
    public void testAuctionPrices() {
        assertEquals(this.auction.getValidPrices(), new Selection<Price>(new Price(5000), new Price(5000)));
        this.timer.tick();
        assertEquals(this.auction.getValidPrices(), new Selection<Price>(new Price(4950), new Price(4950)));
        this.timer.tick();
        assertEquals(this.auction.getValidPrices(), new Selection<Price>(new Price(4900), new Price(4900)));
    }

}