package PasswordManager.Interface;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import PasswordManager.Application.Settings;
import PasswordManager.Interface.Components.PMButton;
import PasswordManager.Interface.Components.PMLabel;
import PasswordManager.Interface.Components.PMPasswordField;
import PasswordManager.Interface.Components.PMTextField;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationScreen extends JFrame implements ActionListener {
    Container container = getContentPane();
    PMLabel userLabel = new PMLabel("Username");
    PMLabel passwordLabel = new PMLabel("Password");
    PMLabel repeatPasswordLabel = new PMLabel("Repeat Password");
    PMTextField userTextField = new PMTextField();
    PMPasswordField passwordField = new PMPasswordField();
    PMPasswordField repeatPasswordField = new PMPasswordField();
    PMButton registerButton = new PMButton("Register");

    /**
     * Default constructor, builds all necessary UI elements
     */
    public RegistrationScreen() {
        this.setTitle("Please Login");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(Settings.WindowWidth, Settings.WindowHeight);
        this.setResizable(false);
        container.setLayout(new GridBagLayout());
        addComponentsToContainer();
        addActionEvent();
    }

    /**
     * Generates and binds all components
     */
    public void addComponentsToContainer() {
        JPanel mainPanel = new JPanel();
        mainPanel.setSize((int)(Settings.WindowWidth*0.6), (int)(Settings.WindowHeight*0.8));
        mainPanel.setLayout(new GridLayout(0, 1));
        JPanel userDataPanel = new JPanel();
        userDataPanel.add(userLabel);
        userDataPanel.add(userTextField);
        mainPanel.add(userDataPanel);
        JPanel passDataPanel = new JPanel();
        passDataPanel.add(passwordLabel);
        passDataPanel.add(passwordField);
        mainPanel.add(passDataPanel);
        JPanel repeatPassDataPanel = new JPanel();
        repeatPassDataPanel.add(repeatPasswordLabel);
        repeatPassDataPanel.add(repeatPasswordField);
        mainPanel.add(repeatPassDataPanel);
        mainPanel.add(registerButton);
        container.add(mainPanel);
    }

    /**
     * Connects all 1 buttons
     */
    public void addActionEvent() {
        registerButton.addActionListener(this);
    }

    /**
     * builds all 1 button functions
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            String userText = userTextField.getText();
            String pwdText = new String(passwordField.getPassword());
            String rpwdText = new String(repeatPasswordField.getPassword());
            if (pwdText.equals(rpwdText)) {
                this.replaceWithFrame(new PassLibraryScreen());
            } else {
                JOptionPane.showMessageDialog(this, "Password and repeating password must be identical");
            }
        }
    }
    public void replaceWithFrame(JFrame frame) {
        this.dispose();
        int posX = this.getX();
        int posY = this.getY();
        frame.setBounds(posX, posY, Settings.WindowWidth, Settings.WindowHeight);
        frame.setVisible(true);
    }
}
