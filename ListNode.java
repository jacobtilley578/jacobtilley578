// Programmer: Jacob Tilley
// Date: 8/8/2023
// Program: Personal phonebook/contacts list
// Purpose: Creates a single ListNode/contact for the PhonebookManager's linked list

public class ListNode {
    String name;
    String address;
    String phone;
    ListNode next;
    
    // Constructs a new ListNode
    public ListNode(String name, String address, String phone, ListNode next) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.next = next;
    } // end of ListNode constructor
    
}  // end of ListNode class
