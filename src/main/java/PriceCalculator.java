import database.PersonDB;
import database.TicketDB;
import person.Person;
import ticket.*;

import java.util.ArrayList;
import java.util.HashMap;

public class PriceCalculator {
    private PersonDB personDB;
    private TicketDB ticketDB;

    public PriceCalculator(){
        this.personDB = PersonDB.getInstance();
        this.ticketDB = TicketDB.getInstance();
    }

    public HashMap<Person, Double> calculatePrices(){
        // Name of person + dept
        HashMap<Person, Double> pricesToPay = new HashMap<>();
        // Name of all tickets
        ArrayList<String> allTickets = new ArrayList<>();
        // Person + list of all names of tickets they payed for
        HashMap<Person, ArrayList<String>> everyone = this.personDB.getHashMap();

        int totalPeople = 0;

        // Initialize Array with all Names and 0's
        for(Person x: everyone.keySet()) {
            pricesToPay.put(x, 0.0);
            totalPeople +=1;
        }

        // Get a list with all the names of the tickets
        for(ArrayList<String> e: everyone.values()){
            allTickets.addAll(e);
        }

        // Go over all tickets
        for(String e: allTickets){
            Ticket temp = ticketDB.getTicket(e);

            if (temp instanceof unevenTicket){
                System.out.println("Ticket " + e + " is uneven ticket: calculating prices");
                // get per person the price of the ticket
                HashMap<Person, Double> pricePerPerson = ((RestaurantTicket) temp).getPricePerPerson();
                // Go over all persons who must pay
                for(Person s: pricePerPerson.keySet()){
                    // update the value in prices to pay
                    pricesToPay.replace(s, pricePerPerson.getOrDefault(s, 0.0)+pricesToPay.getOrDefault(s, 0.0));
                }

            } else if (temp instanceof evenTicket){
                System.out.println("Ticket " + e + " is even ticket: calculating prices");
                // total price of the ticket
               double price = ((PlaneTicket) temp).getTotalPrice();
               // Add the totalPrice/#persons to every person
               for(Person x: pricesToPay.keySet()){
                   pricesToPay.replace(x, pricesToPay.get(x) + price/totalPeople);
               }
            }

        }
        return pricesToPay;
    }

}
