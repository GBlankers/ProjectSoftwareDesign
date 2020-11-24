package gui;

import database.PersonDB;
import gui.panels.PersonPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class Gui extends JFrame{

    PersonPanel personPanel;

    public Gui(){
        super("Money tracker");

        start();
    }

    public void start(){
        System.out.print("Gui starting [  ]\r");
        this.setSize(700, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        personPanel = new PersonPanel(PersonDB.getInstance());

        this.add(personPanel);
        setVisible(true);
    }

}
