/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package us.nstro.javaauction.auction;

/**
 *
 * @author brian
 */
public enum AuctionStatus {
    NOT_STARTED,    // AbstractAuction has not started yet
    OPEN,           // AbstractAuction has started, not been closed
    CLOSED,         // AbstractAuction has been closed normally
    ABORTED         // AbstractAuction has been aborted forcefully (no winner)
}
