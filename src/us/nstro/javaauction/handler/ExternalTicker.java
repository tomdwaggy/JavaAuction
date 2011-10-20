package us.nstro.javaauction.handler;

import java.util.Collection;
import java.util.LinkedList;

/**
 * An ExternalTicker, which is an implementation of the Ticker which can have
 * its tick() method called, dispatching the tick to all of the listeners in
 * its list. This is used for testing the classes that depend on a timer in
 * the JUnit tests.
 *
 * @author bbecker
 */
public class ExternalTicker implements Ticker {
    private final Collection<Runnable> tasks;

    public ExternalTicker() {
        this.tasks = new LinkedList<Runnable>();
    }

    public void addTickHandler(Runnable task) {
        this.tasks.add(task);
    }
    
    public void clearTickHandlers() {
        this.tasks.removeAll(tasks);
    }

    public void tick() {
        for(Runnable r : tasks) {
            r.run();
        }
    }
    
}
