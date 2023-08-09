// Programmer: Jacob Tilley
// Date: 8/8/2023
// Program: Personal phonebook/contacts list
// Purpose: Client code allowing users to edit their own phonebook/contact list, with  
//          options to add, delete, edit, view, and save contacts. Uses a Linked List

import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;

public class PhonebookMain {
   private static Scanner in = new Scanner(System.in);
   private static PhonebookManager book = new PhonebookManager();

   public static void main(String[] args) throws FileNotFoundException {      
      run();          
   }  // end of main method
   
   // runs the phonebook program in the console
   public static void run() throws FileNotFoundException {
      String option = "";      
      System.out.println("Welcome to your personal phonebook!");
      load();
      while (!option.equals("q")) {
         menuOptions();
         option = in.nextLine().toLowerCase();         
         switch (option) {
            case "a":
               add();               
               break;
            case "d":
               delete();
               break;
            case "e":
               edit();
               break;
            case "v":
               System.out.println("\n" + book.view());
               break;
            case "q":
               break;
            default:
               System.out.println("Please enter a valid option");
         }  // end of switch/case
      }  // end of while loop
      save();
   }  // end of run method
   
   // adds a new entry to the phonebook alphabetically by last name
   public static void add() {
      boolean indexFound = false;         
      System.out.println("\nPlease enter in format shown in parenthesis");
      System.out.println("Enter (Last, First): ");
      String name = in.nextLine();
      System.out.println("Enter (address, city, and zip code): ");
      String address = in.nextLine();
      System.out.println("Enter phone number (xxx-xxx-xxxx): ");      
      String phone = in.nextLine();                     
      boolean found = book.add(name, address, phone);
      if (found) {
         System.out.println("New Contact added!\n");      
      }                               
   }  // end of add method
   
   // deletes contact from list based on name input
   public static void delete () {
      System.out.println("\nEnter the name of the contact to delete");
      System.out.print("Note: the name must match the phonebook exactly. (Last, First): ");
      String input = in.nextLine();      
      boolean found = book.delete(input);
      if (found) {
         System.out.printf("Contact %S deleted.\n", input);
      } else {
         System.out.println("Contact not found.\n");
      }  // end of if/else         
   }  // end of delete method
   
   // edits the name, address, or phone fields
   public static void edit() {
      boolean found = false;
      String input = "";
      String field = "";
      
      System.out.println("\nWhat would you like to edit?" +
                           "(N)ame, (A)ddress, or (P)hone");      
      while (!found) {  // loops until valid option selected
         field = in.nextLine().toLowerCase();
         if (!field.equals("n") && !field.equals("a") && !field.equals("p")) {
            System.out.println("Please enter N, A, or P");
         } else {
            found = true;
         }  // end of if/else                                  
      }  // end of while loop
      System.out.println("Change to: ");
      String change = in.nextLine();
      findValidName(field, change);
   }  // end of edit method
   
   // loads contact list from file
   public static void load () {
      try {
         book.load("names.txt");
      } catch (FileNotFoundException e) {
         System.out.println("\nUnable to sync contacts, please ensure you have downloaded"); 
         System.out.println("the 'names.txt' file, then re-run the program");
      }  // end of try/catch
   }  // end of load method
    
   // loops until a valid name is found in the phonebook
   public static void findValidName(String field, String change) {
      boolean found = false;
      String name = "";      
      while (!found && !name.toLowerCase().equals("q")) { // Loops until name found or quit         
         System.out.print("Enter (Last, First) to edit, or (q) to quit ");
         name = in.nextLine();    
         found = book.edit(name, field, change);
         if (found) {
            System.out.println("Contact updated\n");
            name = "q";   // to quit the loop
         } else if (name.equals("q")) {
            found = true;  // to quit the loop   
         } else {
            System.out.println("Contact not found\n");
         }  // end of if/else       
      }  // end of while loop     
   }   // end of findValidName method
   
   // displays menu option to user in console
   public static void menuOptions() {
      System.out.println("\n(a) add contact");
      System.out.println("(d) delete contact");
      System.out.println("(e) edit contact");
      System.out.println("(v) view contact list");
      System.out.println("(q) quit program");
   }  // end of menu method
   
   // saves the phonebook with changes to the same file used in the load method
   public static void save() throws FileNotFoundException {
      System.out.println("Save changes? (Y/N) ");
      char input = in.nextLine().toUpperCase().charAt(0);
      if (input == 'Y') {
         book.save();
         System.out.print("Changes saved. ");
      }
      System.out.println("Goodbye!");
   }  // end of save method
   
}  // end of TestClass