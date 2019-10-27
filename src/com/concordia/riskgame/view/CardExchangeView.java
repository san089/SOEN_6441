package com.concordia.riskgame.view;

import com.concordia.riskgame.controller.CardExchangeController;
import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.utilities.Phases;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CardExchangeView extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
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
    private Gameplay gameplay = Gameplay.getInstance();


    public CardExchangeView() throws IOException {
        String text1 = String.valueOf(gameplay.getCurrentPlayer().getNumOfInfCard());
        String text2 = String.valueOf(gameplay.getCurrentPlayer().getNumOfCavCard());
        String text3 = String.valueOf(gameplay.getCurrentPlayer().getNumOfArtCard());
        String currentPlayer = "Current Player: " + gameplay.getCurrentPlayer().getPlayerName();


        JFrame.setDefaultLookAndFeelDecorated(true);
        cardWindow=new JFrame("Card Exchange");

        infantry = ImageIO.read(new File("./infantry.png"));
        cavalry = ImageIO.read(new File("./cavalry.png"));
        artillery = ImageIO.read(new File("./artillery.png"));

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
        cardPanel.add(exchangerButton);

        exitButton =new JButton("Exit");
        exitButton.setVisible(true);
        exitButton.setBounds(350, 410, 121, 21);
        cardPanel.add(exitButton);

        exchangerButton.addActionListener(this);
        exitButton.addActionListener(this);
        cardWindow.getContentPane().add(cardPanel, BorderLayout.CENTER);

        cardWindow.pack();
        //gameWindow.setFont(Font.CE);
        cardWindow.setSize(700, 700);
        cardWindow.setLocationRelativeTo(null);
        cardWindow.setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        CardExchangeController controller = new CardExchangeController();

        String n1 = use1.getText();
        String n2 = use2.getText();
        String n3 = use3.getText();

        if (actionEvent.getSource() == exchangerButton) {
            if (!controller.checkInput(n1, n2, n3)) {
                JOptionPane.showMessageDialog(cardWindow,
                        "Incorrect number!", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            controller.exchange();
            return;
        }
        if (actionEvent.getSource() == exitButton) {
            if (!controller.verifyNumber(n1, n2, n3)) {
                JOptionPane.showMessageDialog(cardWindow,
                        "Incorrect number!", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            int num1 = 0, num2 = 0, num3 = 0;
            if (!n1.equals("")) {
                num1 = Integer.parseInt(n1);
            }
            if (!n2.equals("")) {
                num2 = Integer.parseInt(n2);
            }
            if (!n3.equals("")) {
                num3 = Integer.parseInt(n3);
            }
            if (num1 + num2 + num3 >= 5) {
                JOptionPane.showMessageDialog(cardWindow,
                        "You must exchange your card!", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
            }else {
                cardWindow.setVisible(false);
                gameplay.assignReinforcementArmies();
                System.out.println("You still have " + gameplay.getCurrentPlayer().getArmyCount() + " armies!" );
                gameplay.setCurrentPhase(Phases.Reinforcement);
            }

        }

    }
}