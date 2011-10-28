/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package us.nstro.javaauction.db;

import java.sql.SQLException;

/**
 *
 * @author bbecker
 */
public class SQLDatabaseException extends DatabaseException {

    private SQLException wrappedException;

    public SQLDatabaseException(SQLException exception) {
        this.wrappedException = exception;
    }

    public Exception getWrappedException() {
        return this.wrappedException;
    }

}
