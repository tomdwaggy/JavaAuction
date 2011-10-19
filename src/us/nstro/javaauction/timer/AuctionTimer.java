/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package us.nstro.javaauction.timer;

import java.util.TimerTask;

/**
 *
 * @author bbecker
 */
public interface AuctionTimer {
    public void schedule(TimerTask task);
    public void start();
    public void cancel();
}
