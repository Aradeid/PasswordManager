package PasswordManager.Interface;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import PasswordManager.Application.Settings;
import PasswordManager.Interface.Components.PMButton;
import PasswordManager.Interface.Components.PMLabel;
import PasswordManager.Interface.Components.PMPasswordField;
import PasswordManager.Interface.Components.PMTextField;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame implements ActionListener {
    Container container = getContentPane();
    PMLabel userLabel = new PMLabel("Username");
    PMLabel passwordLabel = new PMLabel("Password");
    PMTextField userTextField = new PMTextField();
    PMPasswordField passwordField = new PMPasswordField();
    PMButton loginButton = new PMButton("Login");
    PMButton registerButton = new PMButton("Register");
    JCheckBox showPasswordBox = new JCheckBox("Show Password");

    public LoginScreen() {
        this.setTitle("Please Login");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(Settings.WindowWidth, Settings.WindowHeight);
        this.setResizable(false);
        container.setLayout(new GridBagLayout());
        showPasswordBox.setBounds(150,250,150,30);
        addComponentsToContainer();
        addActionEvent();
    }
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
        mainPanel.add(showPasswordBox);
        mainPanel.add(loginButton);
        mainPanel.add(registerButton);
        container.add(mainPanel);
    }
    public void addActionEvent() {
        //adding Action listener to components
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);
        showPasswordBox.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String userText;
            String pwdText;
            userText = userTextField.getText();
            pwdText = new String(passwordField.getPassword());
            if (userText.equalsIgnoreCase(Settings.UserLogin) && pwdText.equals(Settings.UserPass)) {
                //JOptionPane.showMessageDialog(this, "Login Successful");
                
                this.replaceWithFrame(new PassLibraryScreen());
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password. Sorry but no multi-user system is supported: Login=TastyTesty, Pass=TastyPass");
            }
        }
        if (e.getSource() == registerButton) {
            this.replaceWithFrame(new RegistrationScreen());
        }
        if (e.getSource() == showPasswordBox) {
            if (showPasswordBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
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
