/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package us.nstro.javaauction.timer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.TimerTask;

/**
 *
 * @author bbecker
 */
public class ExternalAuctionTimer implements AuctionTimer {
    Collection<TimerTask> tasks;

    private boolean started = false;

    public ExternalAuctionTimer() {
        this.tasks = new LinkedList<TimerTask>();
    }

    public void schedule(TimerTask task) {
        this.tasks.add(task);
    }
    
    public void cancel() {
        this.tasks.removeAll(tasks);
    }

    public void tick() {
        if(started)
            for(TimerTask t : tasks) {
                t.run();
            }
    }

    public void start() {
        this.started = true;
    }
    
}
