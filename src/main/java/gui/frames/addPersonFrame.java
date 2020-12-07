package gui.frames;

import factory.personFactory;

import javax.swing.*;
import java.awt.*;

public class addPersonFrame extends JFrame{
    // Main content panel
    private JPanel container;

    // Add person button
    private JButton okButton;

    // Input field for the name
    private JTextField nameInput;
    private JLabel nameLabel;

    // remember the parent to switch frames back
    private mainFrame parent;

    public addPersonFrame(String title, mainFrame parent){
        super(title);
        this.parent = parent;
        initialize();
    }

    // initialize variables + layout of the frame
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

        // action listener to text input => function to enter key
        nameInput.addActionListener(e -> switchToMainFrame());

        okButton.addActionListener(e -> switchToMainFrame());

        // Center the window on screen
        this.setLocationRelativeTo(null);
    }

    // switch back to the main frame and add the person
    private void switchToMainFrame(){
        String name = nameInput.getText();
        personFactory fact = new personFactory();
        fact.addPerson(name);
        this.setVisible(false);
        parent.setVisible(true);
        parent.refresh();
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
