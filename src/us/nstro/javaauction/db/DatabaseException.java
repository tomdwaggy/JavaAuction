package us.nstro.javaauction.db;

/**
 *
 * @author bbecker
 */
public abstract class DatabaseException extends Exception {

    public abstract Exception getWrappedException();

}