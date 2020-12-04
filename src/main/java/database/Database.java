package database;

import java.util.HashMap;
import java.util.Observable;

public abstract class Database<T, S> extends Observable {

    protected final HashMap<T, S> db;

    public Database(){
        this.db = new HashMap<>();
    }

    public boolean inDb(T name){
        return db.containsKey(name);
    }

    public void addToDb(T key, S Value){
        if(!db.containsKey(key)){
            this.db.put(key, Value);
            setChanged();
            notifyObservers(key);
        }
    }
}
