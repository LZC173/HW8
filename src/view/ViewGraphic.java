package view;

import java.awt.*;

import javax.swing.*;

import controller.GameController;
public class ViewGraphic implements ViewParent {
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

    JLabel label = new JLabel("Enter a name for your Player Avatar:   ");
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
      this.controller.getPlayer().getCurrentRoom().getPicture();
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






    // split the panel in to 4 part
    JPanel mainPanel = new JPanel(new GridLayout(2, 2));
    JPanel topLeftPanel = new JPanel();
    JPanel topRightPanel = new JPanel();
    JPanel bottomLeftPanel = new JPanel();
    JPanel bottomRightPanel = new JPanel();


    // Load direction button icons (from resources folder)
    ImageIcon northIcon = resizeIcon("resources/resources/images/north.png", 40, 40);
    ImageIcon southIcon = resizeIcon("resources/resources/images/south.png", 40, 40);
    ImageIcon eastIcon = resizeIcon("resources/resources/images/east.png", 40, 40);
    ImageIcon westIcon = resizeIcon("resources/resources/images/west.png", 40, 40);

    JButton northButton = new JButton(northIcon);
    JButton southButton = new JButton(southIcon);
    JButton eastButton = new JButton(eastIcon);
    JButton westButton = new JButton(westIcon);

    // remove borders and background for image-only buttons
    JButton[] buttons = { northButton, southButton, eastButton, westButton };
    for (JButton button : buttons) {
      button.setBorderPainted(false);
      button.setContentAreaFilled(false);
      button.setFocusPainted(false);
    }

    //make a directional panel
    JPanel directionPanel = new JPanel(new GridLayout(3, 3));
    directionPanel.add(new JLabel());
    directionPanel.add(northButton);
    directionPanel.add(new JLabel());
    directionPanel.add(westButton);
    directionPanel.add(new JLabel());
    directionPanel.add(eastButton);
    directionPanel.add(new JLabel());
    directionPanel.add(southButton);
    directionPanel.add(new JLabel());


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

  // helper method for resizing icons
  private ImageIcon resizeIcon(String path, int width, int height) {
    ImageIcon icon = new ImageIcon(path);
    Image img = icon.getImage();
    Image resized = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    return new ImageIcon(resized);
  }


  /**
   * print out the message of the result of player action.
   *
   * @param message message of player action
   */
  public void displayMessage(String message) {
    ///item,
    System.out.println(message);

  }

  @Override
  public void displayMenu() {

  }

  @Override
  public String[] getInput() {
    return new String[0];
  }
  //

}
