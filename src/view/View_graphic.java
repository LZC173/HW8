package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.*;

import controller.GameController;
public class View_graphic {
  private Scanner scanner;
  private String[] inputs;
  private String playerName;
  JFrame frame; // the frame that we would use!!!!
  private GameController controller;

  public void setController(GameController controller) {
    //for graphic mode
    this.controller = controller;
  }

  public String getPlayerName() {
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());

    JLabel label = new JLabel("===== Welcome to the Game! =====\nEnter your name:");
    JTextField textField = new JTextField(15);

    panel.add(label, BorderLayout.NORTH); // welcome message at upside
    panel.add(textField, BorderLayout.CENTER); // input message at middle

    int result = JOptionPane.showConfirmDialog(
            null, panel, "Enter Name", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
    );

    if (result == JOptionPane.OK_OPTION) {
      String name = textField.getText().trim();
      if (name.isEmpty()) {
        name = "Player";
      }
      playerName = name;
      return playerName;
    } else {
      playerName = "Player"; //
      return playerName;
    }
  }

  public void createAndShowGUI() {

    JFrame frame = new JFrame("Game Window");
    frame.setSize(1000, 1000);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.frame = frame;

    // text field input
    JTextField inputField = new JTextField();

    //
    JButton submitButton = new JButton("submit");

    //
    JPanel inputPanel = new JPanel(new BorderLayout());
    inputPanel.add(inputField, BorderLayout.CENTER);
    inputPanel.add(submitButton, BorderLayout.EAST);
    frame.add(inputPanel, BorderLayout.SOUTH);

    submitButton.addActionListener(e -> {
      String text = inputField.getText().trim();
    });


    // split the panel in to 4 part
    JPanel mainPanel = new JPanel(new GridLayout(2, 2));
    JPanel topLeftPanel = new JPanel();
    JPanel topRightPanel = new JPanel();
    JPanel bottomLeftPanel = new JPanel();
    JPanel bottomRightPanel = new JPanel();


    JButton northButton = new JButton("North");
    JButton southButton = new JButton("South");
    JButton eastButton = new JButton("East");
    JButton westButton = new JButton("West");





    //make a directional panel
    JPanel directionPanel = new JPanel(new GridLayout(2, 3));
    directionPanel.add(new JLabel());
    directionPanel.add(northButton);
    directionPanel.add(new JLabel());
    directionPanel.add(westButton);
    directionPanel.add(southButton);
    directionPanel.add(eastButton);


    topRightPanel.setLayout(new BorderLayout());
    topRightPanel.add(directionPanel, BorderLayout.CENTER);

    mainPanel.add(topLeftPanel);
    mainPanel.add(topRightPanel);
    mainPanel.add(bottomLeftPanel);
    mainPanel.add(bottomRightPanel);

    frame.add(mainPanel, BorderLayout.CENTER);

    northButton.addActionListener(e -> {
      controller.getCommand(new String[]{"north", ""});
    });

    southButton.addActionListener(e -> {
    });

    eastButton.addActionListener(e -> {

    });

    westButton.addActionListener(e -> {
    });

    frame.setVisible(true);// only at last row
  }
  // Try GUI



}
