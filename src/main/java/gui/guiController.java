package gui;

import database.PersonDB;
import gui.frames.mainFrame;

import javax.swing.*;

public class guiController{
    //Main frame
    private final JFrame mainFrame;

    public guiController(){
        mainFrame = new mainFrame("Money Tracker", PersonDB.getInstance());
        initialize();
    }

    public void initialize(){
        System.out.println("GUI STARTING");
        mainFrame.setVisible(true);
    }
}
