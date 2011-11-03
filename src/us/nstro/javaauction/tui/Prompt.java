/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package us.nstro.javaauction.tui;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Get information from the user in a console.
 *
 * @author bbecker
 */
public class Prompt {

    private BufferedReader read;

    /**
      * Create a new Prompt object.
      */
    public Prompt() {
        this.read = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Get a string from the user after a given prompt.
     * 
     * @param prompt
     * @return
     */
    public String getString(String prompt) {
        System.out.print(prompt + ": ");
        try {
            return this.read.readLine();
        } catch(IOException ioe) {
            return "";
        }
    }

    /**
     * Get a string from the user after a given prompt without echoing the
     * data, if the Console is not available (e.g. NetBeans) the getPassword
     * will use getString instead, and it will show the password.
     * 
     * @param prompt
     * @return
     */
    public String getPassword(String prompt) {
        char[] passwd;
        Console cons;
        if ((cons = System.console()) != null &&
            (passwd = cons.readPassword("%s: ", prompt)) != null) {
            return new String(passwd);
        } else {
            return this.getString(prompt);
        }
    }

    /**
     * Get an integer from the user after a given prompt.
     * 
     * @param prompt
     * @return
     */
    public Integer getInteger(String prompt) {
        System.out.print(prompt + ": ");
        try {
            return Integer.parseInt(this.read.readLine());
        } catch(IOException ioe) {
            return -1;
        } catch(NumberFormatException nfe) {
            return -1;
        }
    }

    /**
     * Get a floating point value from the user after a given prompt.
     * 
     * @param prompt
     * @return
     */
    public Float getFloat(String prompt) {
        System.out.print(prompt + " (e.g. 1.92): ");
        try {
            return Float.parseFloat(this.read.readLine());
        } catch(IOException ioe) {
            return -1.0f;
        } catch(NumberFormatException nfe) {
            return -1.0f;
        }
    }

    /**
     * Get a yes or no value from the user returning it as a boolean, Y
     * being true and N (or other) being false.
     * 
     * @param prompt
     * @return
     */
    public Boolean getYesNo(String prompt) {
        System.out.print(prompt + " [Y or N]: ");
        try {
            return this.read.readLine().equalsIgnoreCase("Y");
        } catch(IOException ioe) {
            return false;
        } catch(NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Get a date from the user.
     * 
     * @param prompt
     * @return
     */
    public Date getDate(String prompt) {
        try {
            String format = "MM-dd-yyyy";
            System.out.print(prompt + " (" + format + "): ");
            Date date = new SimpleDateFormat(format, Locale.ENGLISH).parse(this.read.readLine());
            return date;
        } catch(ParseException pe) {
            return new Date();
        } catch(IOException ioe) {
            return new Date();
        }
    }

}
