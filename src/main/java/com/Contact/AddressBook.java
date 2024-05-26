package com.Contact;

import java.sql.*;
import java.util.Scanner;

public class AddressBook {
    public void addContact() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb","root","zaqwer786");
            PreparedStatement ps = con.prepareStatement("insert into contact values(?,?,?)");
            ps.setString(1,name);
            ps.setString(2,phoneNumber);
            ps.setString(3,email);
            int s = ps.executeUpdate();
            if (s>0) {
                System.out.println("Data inserted in database");
            }
            else {
                System.out.println("Data not inserted");
            }
            con.close();
        }
        catch (Exception e){
            System.out.println("Name or Phone Number or Email already exist");
        }
    }

    public void displayContacts() {

        System.out.println("Address Book:");

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb","root","zaqwer786");
                    Statement s = con.createStatement();
                    ResultSet r = s.executeQuery("select * from testdb.contact");
                    while (r.next() == true) {
                        System.out.print("Name: " + r.getString(1) + "|");
                        System.out.print("Phone Number: " + r.getString(2) + "|");
                        System.out.println("Email: " + r.getString(3));
                    }
                    s.close();
                    con.close();
                }
                catch (Exception e){
                    System.out.println("Error on getting Details from Database");
                }
    }
    public void updateContact() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb","root","zaqwer786");
            Scanner scanner = new Scanner(System.in);
            System.out.println("Select name to update");
            String selectName = scanner.nextLine();
            String updateQuery = "update testdb.contact set name = ?, phoneNumber = ?, email = ? where name = "+ "\'"+selectName+"\'";
            PreparedStatement ps = con.prepareStatement(updateQuery);
            System.out.print("Enter New Name: ");
            String newName = scanner.nextLine();
            System.out.print("Enter  New phone number: ");
            String newPhoneNumber = scanner.nextLine();
            System.out.print("Enter New email: ");
            String newEmail = scanner.nextLine();
            ps.setString(1,newName);
            ps.setString(2,newPhoneNumber);
            ps.setString(3,newEmail);
            int updated = ps.executeUpdate();
            System.out.println(updated + " " + "Rows Updated");
            con.close();
        }
        catch (Exception e) {
            System.out.println("Update Error");
        }
    }

    public void searchContact() {
        System.out.println("Searched Contact");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb","root","zaqwer786");
            Statement s = con.createStatement();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Search by Name");
            String selectName = scanner.nextLine();
            String updateQuery = "select * from testdb.contact where name = " + "\'" + selectName + "\'";
            ResultSet rs = s.executeQuery(updateQuery);
            while (rs.next() == true) {
                System.out.println("name: " + rs.getString(1));
                System.out.println("phoneNumber: " + rs.getString(2));
                System.out.println("email: " + rs.getString(3));
            }
            s.close();
            con.close();
        }
        catch (Exception e){
            System.out.println("Error on Getting Details from Database");
        }
    }

    public void deleteContact() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select name to Delete Contact");
        String selectName = scanner.nextLine();
        System.out.println("Are you want to delete " + "\"" + selectName + "\"" + " from Contact: y/n");
        char res = scanner.next().charAt(0);
        if (res == 'y') {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "zaqwer786");
                System.out.println("Select name to Delete Contact");
                String updateQuery = "delete from testdb.contact where name = " + "\'" + selectName + "\'";
                PreparedStatement ps = con.prepareStatement(updateQuery);
                int s = ps.executeUpdate();
                System.out.println(s + " " + "Rows Deleted");
                con.close();
            } catch (Exception e) {
                System.out.println("Delete Error");
            }
        }
        else {
            System.out.println("Contact not deleted");
        }
    }

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook();
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.println("\nAddress Book Menu:");
            System.out.println("1. Add Contact");
            System.out.println("2. Display Contacts");
            System.out.println("3. Update Contact");
            System.out.println("4. Search Contact");
            System.out.println("5. Delete Contact");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addressBook.addContact();
                    break;
                case 2:
                    addressBook.displayContacts();
                    break;
                case 3:
                    addressBook.updateContact();
                    break;
                case 4:
                    addressBook.searchContact();
                    break;
                case 5:
                    addressBook.deleteContact();
                    break;
                case 6:
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 3.");
            }
        }
        scanner.close();
    }
}