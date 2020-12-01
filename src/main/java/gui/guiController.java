package gui;

import database.PersonDB;
import gui.frames.mainFrame;

import javax.swing.*;
import java.awt.*;

public class guiController{
    //Main frame
    private JFrame mainFrame;
    //TODO Add ticket frame

    public guiController(){
        mainFrame = new mainFrame("Money Tracker", PersonDB.getInstance());
        initialize();
    }

    public void initialize(){
        mainFrame.setVisible(true);
    }
}
