/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.nstro.javaauction.db;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Steven Bolin
 */
public class TUI_test1 {
  
    private Scanner in;
    private UserManager userManager;
    private SQLiteConnection conn;
    private int userId;
    

  /**
   * Constructor
   */
  public TUI_test1() {
    conn = new SQLiteConnection();
        this.in = new Scanner(System.in);
        userManager = new UserManager();
        this.userId = -1;
  }
  
  private void showMainMenu() {
    if(userId > 0) {
      System.out.println("Welcome " + userManager.getUserById(userId).getfirstName());
    }
    System.out.println();
    System.out.println();
    System.out.println("1. Create User Account");
    System.out.println("2. Login");
    System.out.println("3. Logout");
    System.out.println("0. Exit");
  }
  
  private void executeChoice(int choice) {
    
    if(choice == 1) {
      this.createUser();
    
    } else if( choice == 2) {
      this.login();
    } else if( choice == 3) {
      this.logout();
    } else {
      System.out.println(choice + " is not a valid choice.");
    }
  }
  
  public void start() {
    int choice = -1;
    while (choice != 0) {
      showMainMenu();
      choice = readIntWithPrompt("Enter choice: ");
      executeChoice(choice);
    }
    
  }
  
  private int readIntWithPrompt(String prompt) {
    System.out.print(prompt); System.out.flush();
    int input = -1;
    Scanner tin = new Scanner(System.in);
    // loop to prevent negative values
    while(input < 0) {
      // loop to ignore tring entries
      while(!tin.hasNextInt()) {
        tin.nextLine();
        System.out.print(prompt); System.out.flush();
      }
      input = tin.nextInt();
      
      if(input<0) {
        System.out.print(prompt); System.out.flush();
      }
      tin.nextLine();
    }
    
    return input;
  }
  
  
     private String readStringWithPrompt(String prompt) {
    System.out.print(prompt); System.out.flush();
    // loop to collect the input
    while(!in.hasNext()) {
      in.nextLine();
      System.out.print(prompt); System.out.flush();
    }
    String input = in.next();
    in.nextLine();
    return input;
  }
     
     
    private void createUser() {
      
      String[] name = {"FirstName", "LastName", "Title"};
       
       String login = this.readStringWithPrompt("Enter the user login name:");
       String password =  this.readStringWithPrompt("Enter the user password:");
       
       
       
        try {

          userId = conn.addUser(login, password);
          userManager.addUser(userId, name, login);
          String firstName = this.readStringWithPrompt("Enter first name:");
          String lastName = this.readStringWithPrompt("Enter last name:");
          String title = this.readStringWithPrompt("Enter title:");

          userManager.getUserById(userId).updateFirstName(firstName);
          userManager.getUserById(userId).updateLastName(lastName);
          userManager.getUserById(userId).updateTitle(title);

        } catch (DatabaseException ex) {
          Logger.getLogger(TUI_test1.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    private void login() {
      
      int again = 0;
      
      while(userId < 0 && again != 1) {
      String login = this.readStringWithPrompt("Enter login:");
      String password = this.readStringWithPrompt("Enter password");
      
      
        try {

          userId = conn.login(login, password);

          if(userId < 0) {
            System.out.println("Username/password combination was not found. Please try again.");
            again = this.readIntWithPrompt("Enter 0 to exit, or any other number to try again:");
          }

        } catch (DatabaseException ex) {
          Logger.getLogger(TUI_test1.class.getName()).log(Level.SEVERE, null, ex);
        }

          } //  end of while
      
    } // end of method
    
    private void logout() {
      this.userId = -1;
    }
     
     
} // end of class
