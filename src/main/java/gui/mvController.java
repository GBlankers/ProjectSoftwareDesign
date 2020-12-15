package gui;

import database.PersonDB;
import database.TicketDB;
import factory.personFactory;
import factory.ticketFactory;
import gui.frames.addEvenTicketFrame;
import gui.frames.addPersonFrame;
import gui.frames.addUnevenTicketFrame;
import gui.frames.mainFrame;
import person.Person;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class mvController{

    private final mainFrame parent;

    private final PersonDB personDB;
    private final TicketDB ticketDB;

    public mvController(mainFrame parentFrame){
        this.parent = parentFrame;
        this.personDB = PersonDB.getInstance();
        this.ticketDB = TicketDB.getInstance();
    }

    // delete person => delete from db + delete from view + delete tickets from db and view
    public void deletePerson(Person person){
        // Delete selected person
        if(person == null){
            System.out.println("Nothing selected");
        } else {
            // Get all the tickets this person payed for
            ArrayList<String> tickets = personDB.getTickets(person);
            // Remove the person from the person db => the tickets will automatically be deleted from the ticket db
            personDB.removePerson(person);
            // Remove the person from the person panel
            parent.personModel.removeElement(person);
            // Remove all the tickets from the ticket panel
            for(String x: tickets){
                parent.ticketModel.removeElement(x);
            }
        }
        // Refresh price mapping calculation
        parent.refresh();
    }

    // Delete a selected ticket
    public void deleteTicket(String ticketName){
        // Delete the selected item
        if(ticketName == null){
            System.out.println("Nothing selected");
        } else {
            // Remove the ticket from the database + from the panel
            ticketDB.removeTicket(ticketName);
            parent.ticketModel.removeElement(ticketName);
        }
        // Redo the calculations
        parent.refresh();
    }

    // switch back to the main frame and add the person
    public void addPerson(addPersonFrame addPersonFrame){
        String name = addPersonFrame.nameInput.getText();

        // Check for duplicate names
        boolean duplicateName = false;
        for(int i = 0; i<parent.personModel.size(); i++){
            if(parent.personModel.get(i).getName().equals(name)){
                duplicateName = true;
            }
        }

        // Only add to db if unique name else error message
        if(!duplicateName) {
            personFactory fact = new personFactory();
            fact.addPerson(name);
            addPersonFrame.setVisible(false);
            parent.setVisible(true);
            parent.refresh();
        } else {
            JOptionPane.showMessageDialog(addPersonFrame, "There is already a person with this name");
        }

    }

    // Switch back to the main frame and add the even ticket
    public void addTicket(addEvenTicketFrame evenTicketFrame){
        // If text fields are empty => exception => try catch
        try {
            String ticketNameText = evenTicketFrame.ticketName.getText();
            String ticketTypeText = evenTicketFrame.ticketType.getText();
            int total_Price = Integer.parseInt(evenTicketFrame.totalPrice.getText());
            Person payer = (Person) evenTicketFrame.payerComboBox.getSelectedItem();
            ticketFactory fact = new ticketFactory();

            boolean duplicateName = false;
            for(int i = 0; i<parent.ticketModel.size(); i++){
                if(parent.ticketModel.get(i).equals(ticketNameText)){
                    duplicateName = true;
                }
            }

            if(!duplicateName){
                // Try to add the ticket, wrong type => exception => try catch
                try {
                    fact.addTicket(ticketTypeText, ticketNameText, payer, total_Price);
                } catch (Exception e) {
                    // Error window
                    JOptionPane.showMessageDialog(evenTicketFrame, "Wrong ticket type\n Chose between: plane or concert");
                }
            } else {
                JOptionPane.showMessageDialog(evenTicketFrame, "There is already a ticket with this name");
            }

        } catch (Exception e){
            // Error window
            JOptionPane.showMessageDialog(evenTicketFrame, "Something went wrong");
        }

        // Change visibility
        evenTicketFrame.setVisible(false);
        parent.setVisible(true);

        // Refresh the price mapping
        parent.refresh();
    }

    // Add the ticket and switch to the main frame
    public void addTicket(addUnevenTicketFrame unevenTicketFrame){
        // If text fields are empty => exception => try catch
        try {
            String ticketNameText = unevenTicketFrame.ticketName.getText();
            String ticketTypeText = unevenTicketFrame.ticketType.getText();
            Person payer = (Person) unevenTicketFrame.payerComboBox.getSelectedItem();

            ticketFactory fact = new ticketFactory();

            // Store the debt of the persons to the payer
            HashMap<Person, Double> temp = new HashMap<>();

            // Fill up the map with the values stored in the text fields from the arraylist
            for (int i = 0; i < unevenTicketFrame.personsModel.size(); i++) {
                temp.put(unevenTicketFrame.personsModel.get(i), Double.parseDouble(unevenTicketFrame.textFields.get(i).getText()));
            }

            boolean duplicateName = false;
            for(int i = 0; i<parent.ticketModel.size(); i++){
                if(parent.ticketModel.get(i).equals(ticketNameText)){
                    duplicateName = true;
                }
            }

            if(!duplicateName){
                // Try to add the ticket, wrong type => exception => try catch
                try {
                    fact.addTicket(ticketTypeText, ticketNameText, payer, temp);

                } catch (Exception e) {
                    // error window
                    JOptionPane.showMessageDialog(unevenTicketFrame, "Wrong ticket type\n Chose between: restaurant or bar");
                }
            } else {
                JOptionPane.showMessageDialog(unevenTicketFrame, "There is already a ticket with this name");
            }

        } catch (Exception e) {
            // error window
            JOptionPane.showMessageDialog(unevenTicketFrame, "Something went wrong");
        }

        // Change visibility
        unevenTicketFrame.setVisible(false);
        parent.setVisible(true);
        // Ticket added => refresh payment mapping
        parent.refresh();
    }
}
