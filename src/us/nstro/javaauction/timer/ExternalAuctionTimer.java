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
        for(TimerTask t : tasks) {
            t.run();
        }
    }
    
}
