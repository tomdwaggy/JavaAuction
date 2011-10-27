package us.nstro.javaauction.handler;

import java.util.Collection;
import java.util.LinkedList;
import java.util.TimerTask;

/**
 * An TimerTicker which is an implementation of a Ticker that utilizes a
 * Timer to fire its handler.
 *
 * @author bbecker
 */
public class TimerTicker extends TimerTask implements Ticker {
    private final Collection<Runnable> tasks;

    public TimerTicker() {
        this.tasks = new LinkedList<Runnable>();
    }

    public void addTickHandler(Runnable task) {
        this.tasks.add(task);
    }

    public void clearTickHandlers() {
        this.tasks.removeAll(tasks);
    }

    public void run() {
        for(Runnable r : tasks)
            r.run();
    }

}
