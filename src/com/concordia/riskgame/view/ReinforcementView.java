package com.concordia.riskgame.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ReinforcementView extends JFrame implements ActionListener {

	private JFrame viewWindow;
	private TitledBorder border;
	private JPanel viewPanel;
	private JButton submitSelectionButton;
	private JButton mapSelectorButton;
	private FileNameExtensionFilter fileFilter;
	private JFileChooser mapSelector;
	private JTextField mapPath;
	private JLabel playerCountLabel = new JLabel("Select player count");
	private JLabel mapSelectorLabel = new JLabel("Select the map file");
	private JComboBox<String> playerCount;
	private String[] playerCountValues= {"1","2","3","4","5","6","7","8"};
			
	
	public ReinforcementView() {
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		viewWindow=new JFrame("REINFORCEMENT VIEW");
		viewWindow.setVisible(true);
		viewWindow.setSize(500, 500);
		viewWindow.setLocation(600,200);
		viewWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		viewPanel = new JPanel();
		viewPanel.setVisible(true);
		viewPanel.setLayout(null);
		viewWindow.add(viewPanel);
	
			
		playerCount=new JComboBox<String>(playerCountValues);
		playerCountLabel = new JLabel("Select player count");
		viewPanel.add(playerCountLabel);
		playerCountLabel.setVisible(true);
		playerCountLabel.setBounds(56, 30, 121, 20);
		viewPanel.add(playerCount);
		playerCount.setBounds(186, 30, 121, 20);
		
		

		mapPath=new JTextField();
		mapSelectorLabel = new JLabel("Select the map file");
		mapSelectorButton=new JButton("Browse");
		viewPanel.add(mapSelectorButton);
		viewPanel.add(mapSelectorLabel);
		viewPanel.add(mapPath);
		mapPath.setVisible(true);
		mapPath.setBounds(186, 60, 121, 20);
		mapSelectorLabel.setVisible(true);
		mapSelectorLabel.setBounds(56, 60, 121, 20);
		mapSelectorButton.setBounds(310, 60, 121, 20);
		mapSelectorButton.setVisible(true);
		mapSelectorButton.addActionListener(this);

		
		//mapSelectorButton.set
		
		
		submitSelectionButton=new JButton("Submit");
		viewPanel.add(submitSelectionButton);
		submitSelectionButton.setBounds(186, 90, 121, 21);
		submitSelectionButton.setVisible(true);
		submitSelectionButton.addActionListener(this);
					
	}





	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource().toString().contains("Browse"))
		{
			System.out.println("Inside Submit Player");
		
			mapSelector = new JFileChooser();
			fileFilter = new FileNameExtensionFilter(" .map", "map", "map");
			mapSelector.setDialogTitle("Select the desired map file");
			mapSelector.setCurrentDirectory(new File(System.getProperty("user.home")));
			mapSelector.setFileFilter(fileFilter);
			mapSelector.showOpenDialog(mapSelector);
			mapSelector.setLocation(500, 200);
			mapSelector.setSize(500, 500);
			mapSelector.setVisible(true);
			viewPanel.add(mapSelector);
		//	mapSelector.setVisible(true);
						
		}
		else if(event.getSource().toString().contains("Submit"))
		{
			String playerCountValue=playerCount.getSelectedItem().toString();
			//System.out.println(playerCountValue);
		}
			
		
		
	}

	
	
}
