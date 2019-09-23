package com.concordia.riskgame.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class GameLauncherView extends JFrame implements ActionListener {

	private JFrame gameWindow;
	private TitledBorder border;
	private JPanel gamePanel;
	private JButton startButton;
	private JButton createMapButton;
	private JButton editMapButton;
	private JButton tournamentButton;
	private JButton loadGameButton;
	private JButton exitButton;
	private JLabel titleLabel;
	private ReinforcementView rView;

	
	
	public GameLauncherView() {
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		gameWindow=new JFrame("RISK GAME");
		gameWindow.setVisible(true);
		gameWindow.setSize(500, 500);
		gameWindow.setLocation(600,200);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		gamePanel = new JPanel();
		gamePanel.setVisible(true);
		gameWindow.add(gamePanel);
		
		
		
		startButton=new JButton("Start Game");
		startButton.setVisible(true);
		gamePanel.add(startButton);
		startButton.addActionListener(this);
		
		
	}



	@Override
	public void actionPerformed(ActionEvent event) {
		System.out.println(event.getSource());
		if(event.getSource().toString().contains("Start Game"))
		{
			System.out.println("Start Game button clicked");
			gamePanel.setVisible(false);
			rView=new ReinforcementView();
			rView.setVisible(true);
		}
			
	}
	
	
	public static void main(String args[]) {
		GameLauncherView game=new GameLauncherView();
	}

	
}
