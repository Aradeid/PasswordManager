package PasswordManager.Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.table.DefaultTableModel;

import PasswordManager.Application.DataManager;
import PasswordManager.Application.Settings;
import PasswordManager.Generators.DataEntry;
import PasswordManager.Generators.PasswordEntry;

public class PrimaryWindow extends JFrame {
    //final int PRIMARY_WINDOW_WIDTH = 1000;
    //final int PRIMARY_WINDOW_HEIGHT = 600;
    private int PRIMARY_WINDOW_WIDTH;
    private int PRIMARY_WINDOW_HEIGHT;

    public PrimaryWindow() {
        super("Password Manager");
        PRIMARY_WINDOW_WIDTH = Settings.WindowWidth;
        PRIMARY_WINDOW_HEIGHT = Settings.WindowHeight;

        this.add(new LoginScreen());
    }
}
