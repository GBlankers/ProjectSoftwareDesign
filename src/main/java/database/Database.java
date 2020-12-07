package database;

import java.util.HashMap;
import java.util.Observable;

// Generic types T and S to work with both databases => write common methods only once here
public abstract class Database<T, S> extends Observable {

    // Hashmap is available for all classes in this package
    protected final HashMap<T, S> db;

    // Initialize the hashmap
    public Database(){
        this.db = new HashMap<>();
    }

    // Check if key in hashmap
    public boolean inDb(T name){
        return db.containsKey(name);
    }

    // Add pair to db
    public void addToDb(T key, S Value){
        if(!db.containsKey(key)){
            this.db.put(key, Value);
            setChanged();
            notifyObservers(key);
        }
    }

    // get value from key
    public S getTickets(T key){
        return db.getOrDefault(key, null);
    }

    // return the whole hashmap => price calculation
    public HashMap<T, S> getHashMap(){
        return db;
    }
}
