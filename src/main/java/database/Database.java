package database;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;

// Generic types T and S to work with both databases => write common methods only once here
public abstract class Database<T, S> extends Observable implements Iterable<T>{

    // Hashmap is available for all classes in this package
    protected final HashMap<T, S> db;

    // Initialize the hashmap
    public Database(){
        this.db = new HashMap<>();
    }

    // Check if key in hashmap
    public boolean inDb(T key){
        return db.containsKey(key);
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

    // Reset the db
    public void clear(){
        db.clear();
    }

    // Get size
    public int size(){
        return db.size();
    }

    @Override
    public @NotNull Iterator<T> iterator(){
        return new Iterator<T>() {

            final Iterator<T> hmIterator = db.keySet().iterator();

            @Override
            public boolean hasNext() {
                return hmIterator.hasNext();
            }

            @Override
            public T next() {
                return hmIterator.next();
            }
        };
    }
}
