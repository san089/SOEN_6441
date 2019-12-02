/**
 *
 * This Module is the view for card exchange
 *
 * @author Hai Feng
 *
 * @version 2.0
 * @see https://www.ultraboardgames.com/risk/game-rules.php
 *
 */

package com.concordia.riskgame.view;

import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.model.Modules.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * This class is the card exchange view.
 */

public class CardExchangeView extends JFrame implements Observer {

    private static final long serialVersionUID = 1L;
    private static CardExchangeView cardExchangeView;
    private JFrame cardWindow;
    private JPanel cardPanel;
    private JButton exchangerButton;
    private JButton exitButton;
    private BufferedImage infantry;
    private BufferedImage cavalry;
    private BufferedImage artillery;
    private JLabel image1;
    private JLabel image2;
    private JLabel image3;
    private JLabel num1;
    private JLabel num2;
    private JLabel num3;
    private JLabel player;
    private JTextField use1;
    private JTextField use2;
    private JTextField use3;
   // private Gameplay gameplay = Gameplay.getInstance();
    String currentPlayer;
    String text1;
    String text2;
    String text3;

    /**
     * Card exchange view is singleton model.
     * @return The only one instance of card exchange view.
     */
    public static CardExchangeView getInstance() {
        if (cardExchangeView == null){
            cardExchangeView = new CardExchangeView();
        }
        return cardExchangeView;
    }

    /**
     * Constructor
     */
    public CardExchangeView(){
        text1 = String.valueOf(Gameplay.getInstance().getCurrentPlayer().getNumOfInfCard());
        text2 = String.valueOf(Gameplay.getInstance().getCurrentPlayer().getNumOfCavCard());
        text3 = String.valueOf(Gameplay.getInstance().getCurrentPlayer().getNumOfArtCard());
        currentPlayer = "Current Player: " + Gameplay.getInstance().getCurrentPlayer().getPlayerName();


        JFrame.setDefaultLookAndFeelDecorated(true);
        cardWindow=new JFrame("Card Exchange");

        try {
            infantry = ImageIO.read(new File("./infantry.png"));
            cavalry = ImageIO.read(new File("./cavalry.png"));
            artillery = ImageIO.read(new File("./artillery.png"));
        } catch (Exception e) {
            System.out.println("Image file load error");
        }


        image1 = new JLabel(new ImageIcon(infantry));
        image2 = new JLabel(new ImageIcon(cavalry));
        image3 = new JLabel(new ImageIcon(artillery));


        cardWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardPanel = new JPanel();
        cardPanel.setLayout(null);
        cardPanel.setVisible(true);


        image1.setVisible(true);
        image2.setVisible(true);
        image3.setVisible(true);
        image1.setBounds(60, 50, 250, 400);
        image2.setBounds(220, 50, 250, 400);
        image3.setBounds(400, 50, 250, 400);
        cardPanel.add(image1);
        cardPanel.add(image2);
        cardPanel.add(image3);

        player = new JLabel();
        player.setVisible(true);
        player.setBounds(175, 50, 150, 150);
        player.setText(currentPlayer);
        cardPanel.add(player);

        num1 = new JLabel();
        num2 = new JLabel();
        num3 = new JLabel();
        num1.setVisible(true);
        num2.setVisible(true);
        num3.setVisible(true);
        num1.setBounds(175, 110, 100, 100);
        num2.setBounds(335, 110, 100, 100);
        num3.setBounds(510, 110, 100, 100);
        num1.setText(text1);
        num2.setText(text2);
        num3.setText(text3);
        cardPanel.add(num1);
        cardPanel.add(num2);
        cardPanel.add(num3);

        use1 = new JTextField();
        use2 = new JTextField();
        use3 = new JTextField();
        use1.setVisible(true);
        use2.setVisible(true);
        use3.setVisible(true);
        use1.setBounds(165, 350, 50, 20);
        use2.setBounds(325, 350, 50, 20);
        use3.setBounds(500, 350, 50, 20);
        cardPanel.add(use1);
        cardPanel.add(use2);
        cardPanel.add(use3);

        exchangerButton =new JButton("Exchange");
        exchangerButton.setVisible(true);
        exchangerButton.setBounds(200, 410, 121, 21);
        //cardPanel.add(exchangerButton);

        exitButton =new JButton("Exit");
        exitButton.setVisible(true);
        exitButton.setBounds(350, 410, 121, 21);
        //cardPanel.add(exitButton);

        // exchangerButton.addActionListener(this);
        //exitButton.addActionListener(this);
        cardWindow.getContentPane().add(cardPanel, BorderLayout.CENTER);

        cardWindow.pack();
        //gameWindow.setFont(Font.CE);
        cardWindow.setSize(700, 700);
        cardWindow.setLocationRelativeTo(null);
        cardWindow.setVisible(true);
    }
    /**
     * Observer update method
     * @param observable Observable object
     * @param o an Object type variable.
     */
    @Override
    public void update(Observable observable, Object o) {

        currentPlayer = "Current Player: " + Gameplay.getInstance().getCurrentPlayer().getPlayerName();
        player.setText(currentPlayer);
        text1 = Integer.toString(Gameplay.getInstance().getCurrentPlayer().getNumOfInfCard());
        text2 = Integer.toString(Gameplay.getInstance().getCurrentPlayer().getNumOfCavCard());
        text3 = Integer.toString(Gameplay.getInstance().getCurrentPlayer().getNumOfArtCard());
        num1.setText(text1);
        num2.setText(text2);
        num3.setText(text3);

    }

    public void setVisible(boolean b){
        if (b == false) {
            cardWindow.setVisible(false);
        } else {
            cardWindow.setVisible(true);
        }
    }
}