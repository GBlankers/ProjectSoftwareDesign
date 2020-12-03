package gui.frames;

import database.PersonDB;
import factory.personFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class addPersonFrame extends JFrame{
    private JPanel container;

    private JButton okButton;
    private JTextField nameInput;
    private JLabel nameLabel;

    private mainFrame parent;

    public addPersonFrame(String title, mainFrame parent){
        super(title);
        this.parent = parent;
        initialize();
    }

    private void initialize(){
        this.setSize(500, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        container = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        container.setLayout(layout);
        getContentPane().add(container);

        nameLabel = new JLabel("Name:");
        okButton = new JButton("Add");
        nameInput = new JTextField(40);

        this.addObjects(nameLabel, container, layout, gbc, 0, 0, 1, 1);
        this.addObjects(nameInput, container, layout, gbc, 0, 1, 1, 1);
        this.addObjects(okButton, container, layout, gbc, 0, 2, 1, 1);

        nameInput.addActionListener(e -> switchToMainFrame());

        okButton.addActionListener(e -> switchToMainFrame());

        // Center the window on screen
        this.setLocationRelativeTo(null);
    }

    private void switchToMainFrame(){
        String name = nameInput.getText();
        personFactory fact = new personFactory();
        PersonDB.getInstance().addPerson(fact.addPerson(name));
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
