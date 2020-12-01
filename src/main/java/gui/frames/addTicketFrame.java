package gui.frames;

import database.PersonDB;
import factory.personFactory;
import factory.ticketFactory;
import ticket.Ticket;

import javax.swing.*;
import java.awt.*;

public class addTicketFrame extends JFrame{
    private JPanel container;

    private JButton okButton;
    private JTextField ticketName;

    private mainFrame parent;

    public addTicketFrame(String title, mainFrame parent){
        super(title);
        this.parent = parent;

    }

    public void initialize(){
        this.setSize(500, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void switchToMainFrame(){
        String name = ticketName.getText();
        ticketFactory fact = new ticketFactory();

        this.setVisible(false);
        parent.setVisible(true);
    }

    public void addObjects(Component component, Container container, GridBagLayout layout, GridBagConstraints gbc, int gridx, int gridy, int gridwidth, int gridheight){
        // https://stackoverflow.com/questions/30656473/how-to-use-gridbaglayout
        gbc.gridx = gridx;
        gbc.gridy = gridy;

        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;

        layout.setConstraints(component, gbc);
        container.add(component);
    }

}
