// Programmer: Jacob Tilley
// Date: 8/8/2023
// Program: Personal phonebook/contacts list
// Purpose: Phonebook manager class uses a Linked List with custom functions to make
//          a working contact list/phonebook.

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.File;
import java.util.ArrayList;

public class PhonebookManager {
   private ListNode front;
   
   // contructs phonebook with empty linked list
   public PhonebookManager() {
      front = null;
   }  // end of PhonebookManager constructor
   
   // Allows user to change the name, address, or phone#
   public boolean edit (String name, String field, String change) {
      boolean found = false;
      ListNode current = front;
      try { 
         while (!current.name.equalsIgnoreCase(name)) {
            current = current.next;
         }  // end of while loop
         switch(field) {
            case "n":            
               editName(current, change);               
               found = true;
               break;
            case "a":
               current.address = change;
               found = true;
               break;
            case "p":
               current.phone = change;
               found = true;
               break;
            default:
         }  // end of switch/case       
      } catch (NullPointerException e) {} // do nothing  
      // end of try/catch
      return found;
   }  // end of editName method
   
   // edits the name of a contact and re-sorts into the list alphabetically
   private void editName (ListNode current, String change) {      
      current.name = change;
      delete(current.name);
      add(current.name, current.address, current.phone);
   }  // end of editName method
      
   // deletes a contact from the phonebook
   public boolean delete(String input) {
      boolean found = false;
      try {      
         if (front.name.equalsIgnoreCase(input)) {   // if first item == name
            front = front.next;  // removes link by equaling next
            found = true;
         } else {
            ListNode current = front;
            // Searches for name, goes to catch if not found           
            while (!current.next.name.equalsIgnoreCase(input)) {                      
               current = current.next;
            }  // end of while loop
            if (current.next.name.equalsIgnoreCase(input)) {  
               found = true;                         
               current.next = current.next.next; // removes link by equaling next
            } 
         }  // end of if/else
      } catch (NullPointerException e) {
         // If null, do nothing
      }  // end of try/catch
      return found;
   }  // end of delete method
   
   // adds a new contact to the phonebook alphabetically by last name
   public boolean add(String name, String address, String phone) {      
      boolean indexFound = false;
      int index = findIndex(name); 
      if (index == 0) { // adds to an empty list         
         front = new ListNode(name, address, phone, front);
      } else if (index == -1) { // special case so two names are not the exact same
         System.out.println("Contact name already exists");
         return false;
      } else {          // adds to an existing list
         ListNode current = front;         
         for (int i = 0; i < index - 1; i++) {
            current = current.next;
         }  // end of for loop
         current.next = new ListNode(name, address, phone, current.next);
      }  // end of if/else
      indexFound = true;
      return indexFound;            
   }  // end of add method
   
   // finds the index in the phonebook using a name input
   private int findIndex(String name) {
      int index = 0;
      ListNode current = front;
      char firstLetterOfName = name.toUpperCase().charAt(0);
      while (current != null) {
         char firstLetterOfCurrent = current.name.toUpperCase().charAt(0);        
         if (firstLetterOfName < firstLetterOfCurrent) {
            return index;
         } else if (name.equals(current.name)) {   // special case, no duplicate names
            return -1;  // returns a unique value
         }
         current = current.next;
         index++;
      }  // end of while loop
      return index;
   }  // end of findIndex method
   
   // loads a contact list from a file into the program
   public void load(String f) throws FileNotFoundException {
      front = null;
      Scanner in = new Scanner(new File(f));
      while (in.hasNextLine()) {
         String name = in.nextLine();
         String address = in.nextLine();
         String phone = in.nextLine();      
         ListNode newNode = new ListNode(name, address, phone, null);
         if (front == null) {
            front = newNode;
         } else {
            ListNode current = front;
            while (current.next != null) {
               current = current.next;
            }  // end of while loop
            current.next = newNode;
         }  // end of if/else      
      }  // end of while loop
      in.close();  // closes the scanner
   }  // end of load method
   
   // Displays the entire phonebook in the console
   public String view() {
      String book = "";
      ListNode current = front;
      
      System.out.println("\nPhonebook");
      // loops and prints each contact until book is empty
      while (current != null) {    
         book += current.name + "\n";
         book += "\t" + current.address + "\n";
         book += "\t" + current.phone + "\n\n";
         current = current.next; // goes to next contact
      }  // end of while loop
      return book;
   }  // end of view method
   
   // saves the phonebook and changes to the same file.
   public void save() throws FileNotFoundException {
      PrintStream out = new PrintStream(new File("names.txt"));
      ListNode current = front;
      while (current != null) {
         out.println(current.name);
         out.println(current.address);
         out.println(current.phone);
         current = current.next;
      }  // end of while loop
   }  // end of save method
   
}  // end of PhonebookManager class