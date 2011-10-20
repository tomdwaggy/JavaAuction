/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package us.nstro.javaauction.handler;

/**
 *
 * A Ticker interface, which is like a timer with multiple possible
 * implementations.
 *
 * @author bbecker
 */
public interface Ticker {
    public void addTickHandler(Runnable task);
    public void clearTickHandlers();
}
