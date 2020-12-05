package priceCalculator;

import database.PersonDB;
import database.TicketDB;
import person.Person;
import ticket.*;

import java.util.ArrayList;
import java.util.HashMap;

public class PriceCalculator {
    // https://stackoverflow.com/questions/934775/changing-value-after-its-placed-in-hashmap-changes-whats-inside-hashmap
    // hashmap a = hashmap b => a will change when b is changed
    // => b.clone() or new HashMap<>(b)

    private final PersonDB personDB;
    private final TicketDB ticketDB;
    // Name of person + dept to person in hashmap per person
    private HashMap<Person, HashMap<Person, Double>> pricesToPay;

    public PriceCalculator(){
        this.personDB = PersonDB.getInstance();
        this.ticketDB = TicketDB.getInstance();
        pricesToPay = new HashMap<>();
    }


    public void calculatePrices(){
        // Name of all tickets
        ArrayList<String> allTickets = new ArrayList<>();
        // Person + list of all names of tickets they payed for
        HashMap<Person, ArrayList<String>> everyone = new HashMap<>(this.personDB.getHashMap());

        int totalPeople = 0;
        int totalTickets = 0;
        int num = 0;

        System.out.print("Calculating prices [ / ]\r");

        HashMap<Person, Double> temp;

        // Initialize Hashmap
        for(Person x: everyone.keySet()) {
            pricesToPay.put(x, new HashMap<>());
            totalPeople += 1;
        }

        // Get a list with all the names of the tickets
        for(ArrayList<String> x: everyone.values()){
            for(String s: x){
                allTickets.add(s);
                totalTickets += 1;
            }
        }

        // Go over all tickets
        for(String e: allTickets){

            System.out.print("Calculating prices [" + num + "/" + totalTickets +"]\r");

            Ticket tempTicket = ticketDB.getTickets(e);
            Person payer = tempTicket.getPayer();
            temp = new HashMap<>();
            num += 1;

            if (tempTicket instanceof unevenTicket){
                // get per person the price of the ticket
                HashMap<Person, Double> pricePerPerson = ((unevenTicket) tempTicket).getPricePerPerson();
                // get the existing debts for the payer
                HashMap<Person, Double> oldPrices = new HashMap<>(pricesToPay.get(payer));
                // for every person update the debts they have
                for(Person x: everyone.keySet()){
                    if(x != payer) {
                        double old = oldPrices.getOrDefault(x, 0.0);
                        double newPrice = pricePerPerson.getOrDefault(x, 0.0);
                        temp.put(x, old + newPrice);
                    }
                }
                pricesToPay.replace(payer, temp);

            } else if (tempTicket instanceof evenTicket){
                // total price of the ticket
                double price = ((evenTicket) tempTicket).getTotalPrice();
               // Add the totalPrice/#persons to every person
                double ppp = price/totalPeople;

                for(Person x: pricesToPay.keySet()){
                    if(x != payer)
                        temp.put(x, pricesToPay.get(payer).getOrDefault(x, 0.0) + ppp);
               }
                pricesToPay.replace(payer, temp);
            }
        }

        num = totalTickets;
        System.out.println("Calculating prices [" + num + "/" + totalTickets +"]\r");
    }

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

    private void printHashMap(HashMap<Person, Double> hm){
        for(Person x: hm.keySet()){
            System.out.println(x + " " + hm.getOrDefault(x, 0.0));
        }
    }

}
