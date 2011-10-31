/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author bbecker
 */
public class Prompt {

    private BufferedReader read;

    public Prompt() {
        this.read = new BufferedReader(new InputStreamReader(System.in));
    }
    
    public String getString(String prompt) {
        System.out.print(prompt + ": ");
        try {
            return this.read.readLine();
        } catch(IOException ioe) {
            return "";
        }
    }

    public Integer getInteger(String prompt) {
        System.out.print(prompt + ": ");
        try {
            return Integer.parseInt(this.read.readLine());
        } catch(IOException ioe) {
            return null;
        } catch(NumberFormatException nfe) {
            return -1;
        }
    }

    public Float getFloat(String prompt) throws IOException {
        System.out.print(prompt + " (e.g. 1.92): ");
        return Float.parseFloat(this.read.readLine());
    }

    public Date getDate(String prompt) throws IOException {
        try {
            String format = "MM-dd-yyyy";
            System.out.print(prompt + " (" + format + "): ");
            Date date = new SimpleDateFormat(format, Locale.ENGLISH).parse(this.read.readLine());
            return date;
        } catch(ParseException pe) {
            return new Date();
        }
    }

}
