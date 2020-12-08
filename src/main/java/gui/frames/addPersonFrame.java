package gui.frames;

import factory.personFactory;
import gui.mvController;

import javax.swing.*;
import java.awt.*;

public class addPersonFrame extends JFrame{

    // Input field for the name
    public JTextField nameInput;

    // The controller which controls the button actions
    private final mvController controller;

    public addPersonFrame(String title, mvController controller){
        super(title);
        this.controller = controller;
        initialize();
    }

    // initialize variables + layout of the frame
    private void initialize(){
        this.setSize(500, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main content panel
        JPanel container = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        container.setLayout(layout);
        getContentPane().add(container);

        JLabel nameLabel = new JLabel("Name:");
        // Add person button
        JButton okButton = new JButton("Add");
        nameInput = new JTextField(40);

        this.addObjects(nameLabel, container, layout, gbc, 0, 0, 1, 1);
        this.addObjects(nameInput, container, layout, gbc, 0, 1, 1, 1);
        this.addObjects(okButton, container, layout, gbc, 0, 2, 1, 1);

        // action listener to text input => function to enter key
        nameInput.addActionListener(e -> controller.addPerson(this));

        okButton.addActionListener(e -> controller.addPerson(this));

        // Center the window on screen
        this.setLocationRelativeTo(null);
    }

    // Function to simplify the process of adding constrains to components
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
