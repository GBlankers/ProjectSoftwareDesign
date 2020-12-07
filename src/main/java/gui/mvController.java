package gui;

import database.PersonDB;
import database.TicketDB;
import gui.frames.mainFrame;

import java.util.Observable;
import java.util.Observer;

public class mvController implements Observer {

    private mainFrame parent;

    private PersonDB personDB;
    private TicketDB ticketDB;

    private Observable observable;

    public mvController(mainFrame parentFrame, Observable observable){
        this.parent = parentFrame;
        this.personDB = PersonDB.getInstance();
        this.ticketDB = TicketDB.getInstance();
        this.observable = observable;
    }


    @Override
    public void update(Observable o, Object arg) {

    }
}
