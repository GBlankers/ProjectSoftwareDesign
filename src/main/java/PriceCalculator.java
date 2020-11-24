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
        int totalTickets = 0;
        int num = 0;

        System.out.print("Calculating prices [ / ]\r");

        // Initialize Array with all Names and 0's
        for(Person x: everyone.keySet()) {
            pricesToPay.put(x, 0.0);
            totalPeople +=1;
        }

        // Get a list with all the names of the tickets
        for(ArrayList<String> e: everyone.values()){
            allTickets.addAll(e);
            totalTickets +=1;
        }

        // Go over all tickets
        for(String e: allTickets){
            System.out.print("Calculating prices [" + num + "/" + totalTickets +"]\r");
            Ticket temp = ticketDB.getTicket(e);
            num += 1;

            if (temp instanceof unevenTicket){
                // get per person the price of the ticket
                HashMap<Person, Double> pricePerPerson = ((RestaurantTicket) temp).getPricePerPerson();
                // Go over all persons who must pay
                for(Person s: pricePerPerson.keySet()){
                    // update the value in prices to pay
                    pricesToPay.replace(s, pricePerPerson.getOrDefault(s, 0.0)+pricesToPay.getOrDefault(s, 0.0));
                }

            } else if (temp instanceof evenTicket){
                // total price of the ticket
               double price = ((PlaneTicket) temp).getTotalPrice();
               // Add the totalPrice/#persons to every person
               for(Person x: pricesToPay.keySet()){
                   pricesToPay.replace(x, pricesToPay.get(x) + price/totalPeople);
               }
            }
        }

        num = totalTickets;
        System.out.println("Calculating prices [" + num + "/" + totalTickets +"]\r");

        return pricesToPay;
    }

}
