package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.swing.*;

import controller.GameController;
public class ViewGraphic implements ViewParent {
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


  public String PicturePath(String type){

    // String image = this.controller.getPlayer().getCurrentRoom().getPicture()
    //  String RoomImagePath = Paths.get(Resource, ""
    //            + Player_name + ".png").toString();
    if(type.equals("Room P")) {
      String image = this.controller.getPlayer().getCurrentRoom().getPicture();
    }
    return "";
  }

  public void renderRoom() {
    String path = PicturePath("Room P");
    //neal
    //渲染图片
  }
  public void renderRoomDescription () {

    //Neal
    String description= this.controller.getPlayer().getCurrentRoom().getDescription();

    //渲染description
  }

  public void renderInventory() {
    //q
    //
    //渲染图片
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
      controller.getCommand(new String[]{"south", ""});
      //重新渲染，房间图片和

      renderRoom();
      renderRoomDescription();

    });

    eastButton.addActionListener(e -> {
      controller.getCommand(new String[]{"east", ""});

    });

    westButton.addActionListener(e -> {
      controller.getCommand(new String[]{"west", ""});
    });

//    examine Button.addActionListener(e -> {
//      itemName = helper();
//      controller.getCommand(new String[]{"take", "itemName"});
//    });



    frame.setVisible(true);// only at last row
  }
  // Try GUI

//  //helper
//  String  helper(){
//    create panel,
//    create button,
//    returb "hair clipper"
//  }



  /**
   * print out the message of the result of player action.
   *
   * @param message message of player action
   */
  public void displayMessage(String message) {
    ///item,


  }
  //

}
