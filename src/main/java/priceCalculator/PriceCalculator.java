package priceCalculator;

import database.PersonDB;
import database.TicketDB;
import person.Person;
import ticket.*;
import ticket.evenTickets.evenTicket;
import ticket.unevenTickets.unevenTicket;

import java.util.HashMap;

public class PriceCalculator {
    // https://stackoverflow.com/questions/934775/changing-value-after-its-placed-in-hashmap-changes-whats-inside-hashmap
    // hashmap a = hashmap b => a will change when b is changed
    // => b.clone() or new HashMap<>(b)

    private final PersonDB personDB;
    private final TicketDB ticketDB;

    // Name of person + dept to person in hashmap per person
    private final HashMap<Person, HashMap<Person, Double>> pricesToPay;

    // Constructor to initialize db's and hashmap
    public PriceCalculator(){
        this.personDB = PersonDB.getInstance();
        this.ticketDB = TicketDB.getInstance();
        this.pricesToPay = new HashMap<>();
    }

    // Calculate the price mapping
    public void calculatePrices(){
        // clear the previous mapping
        pricesToPay.clear();

        // For console logging
        int totalPeople = personDB.size();
        int totalTickets = ticketDB.size();
        int num = 0;

        System.out.print("Calculating prices [ / ]\r");

        // Hashmap with prices to pay to 1 person, new with every new ticket
        // => problems with references
        HashMap<Person, Double> temp;

        // Initialize Hashmap + list with al the tickets
        for(Person x: personDB) {
            pricesToPay.put(x, new HashMap<>());
        }

        // Go over all tickets
        for(String e: ticketDB){

            // For console logging
            System.out.print("Calculating prices [" + num + "/" + totalTickets +"]\r");

            // Get the ticket
            Ticket tempTicket = ticketDB.getTickets(e);
            // Get the person who payed
            Person payer = tempTicket.getPayer();
            // New temp hashmap
            temp = new HashMap<>();

            // For console logging
            num += 1;

            if (tempTicket instanceof unevenTicket){
                // get per person the price of the ticket
                HashMap<Person, Double> pricePerPerson = ((unevenTicket) tempTicket).getPricePerPerson();
                // get the existing debts for the payer
                HashMap<Person, Double> oldPrices = new HashMap<>(pricesToPay.get(payer));
                // for every person update the debts they have
                for(Person x: personDB){
                    if(x != payer) {
                        double old = oldPrices.getOrDefault(x, 0.0);
                        double newPrice = pricePerPerson.getOrDefault(x, 0.0);
                        temp.put(x, old + newPrice);
                    }
                }
                // Replace the old mapping with the new one
                pricesToPay.replace(payer, temp);

            } else if (tempTicket instanceof evenTicket){
                // total price of the ticket
                double price = ((evenTicket) tempTicket).getTotalPrice();
               // Add the totalPrice/#persons to every person
                double ppp = price/totalPeople;

                // Add this price the every person
                for(Person x: pricesToPay.keySet()){
                    if(x != payer)
                        temp.put(x, pricesToPay.get(payer).getOrDefault(x, 0.0) + ppp);
               }
                // Replace the old mapping with the new one
                pricesToPay.replace(payer, temp);
            }
        }

        System.out.println("Calculating prices [" + num + "/" + totalTickets +"]\r");
        simplifyMapping();
    }

    // Simplify the price map: If A has to pay to B and B has to pay to A => mapping can be simplified
    public void simplifyMapping(){
        for(Person x: this.pricesToPay.keySet()){
            for(Person y: this.pricesToPay.get(x).keySet()){
                if(this.pricesToPay.get(x).getOrDefault(y, 0.0) > this.pricesToPay.get(y).getOrDefault(x, 0.0)){
                    this.pricesToPay.get(x).replace(y, this.pricesToPay.get(x).getOrDefault(y, 0.0) - this.pricesToPay.get(y).getOrDefault(x, 0.0));
                    this.pricesToPay.get(y).replace(x, 0.0);
                } else if(this.pricesToPay.get(x).getOrDefault(y, 0.0) < this.pricesToPay.get(y).getOrDefault(x, 0.0)){
                    this.pricesToPay.get(y).replace(x, this.pricesToPay.get(y).getOrDefault(x, 0.0) - this.pricesToPay.get(x).getOrDefault(y, 0.0));
                    this.pricesToPay.get(x).replace(y, 0.0);
                } else {
                    this.pricesToPay.get(x).replace(y, 0.0);
                    this.pricesToPay.get(y).replace(x, 0.0);
                }
            }
        }
    }

    // Print the mapping to the console
    public void printMapping(){
        HashMap<Person , Double> temp;
        HashMap<Person, HashMap<Person, Double>> priceMap = new HashMap<>(this.pricesToPay);
        for(Person x: priceMap.keySet()){
            temp = priceMap.getOrDefault(x, null);
            if(!temp.isEmpty()){
                System.out.println(x);
                printHashMap(temp);
            }
        }
    }

    // Extra method for print mapping
    private void printHashMap(HashMap<Person, Double> hm){
        for(Person x: hm.keySet()){
            System.out.println(x + " " + hm.getOrDefault(x, 0.0));
        }
    }

    // Get the total mapping of all debts
    public HashMap<Person, HashMap<Person, Double>> getPricesToPay() {
        return pricesToPay;
    }
}
