/**
 *
 * This Module is the view for Map Editor functionalities
 *
 * @author Shubham, Sucheta
 *
 * @version 2.0
 * @see https://www.ultraboardgames.com/risk/game-rules.php
 *
 */

package com.concordia.riskgame.view;
import com.concordia.riskgame.controller.MapEditorController;
import com.concordia.riskgame.model.Modules.Continent;
import com.concordia.riskgame.model.Modules.Country;
import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.model.Modules.Map;
import com.concordia.riskgame.model.Modules.Player;
import com.concordia.riskgame.utilities.Constants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;


/**
 * This class is the view of the MapEditor Functionalities
 */
public class MapEditorView extends JFrame implements Serializable,Observer {
    private JLabel countriesLabel;
    private JLabel continentLabel;
    private ToolBar toolBar;
    public Map gameMap;
    private MapEditorController mapEditorController;
    private JTree mapTree;
    private JScrollPane treeScrollPane;
    private JPanel contientLabelViewPanel;
    private JPanel countryLabelViewPanel;
    private List<String> countries = new ArrayList<String>();
    private String data[][];
    private String countryColumn[];
    private JTable tableMatrix;
    private JScrollPane scrollPane;
    private JScrollPane playerScrollPane;
    private static final long serialVersionUID = 45443434343L;


    /**
     * map editor constructor.
     *
     * @param gameMap object of Map class
     */
    public MapEditorView(Map gameMap) {
    	super("Map Editor Window");
        this.gameMap = gameMap;
        setSize(Constants.MAP_EDITOR_WIDTH, Constants.MAP_EDITOR_HEIGHT);
        setMinimumSize(new Dimension(Constants.MAP_EDITOR_WIDTH, Constants.MAP_EDITOR_HEIGHT));
        setResizable(false);
        setLocationRelativeTo(null);
        mapEditorController= new MapEditorController(this);
        addComponents(mapEditorController);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            
            }
        });
        Gameplay.getInstance().addObserver(this);
        
    }

    /**
     * adding components to map editor.
     *
     * @param mapEditorController object of MapEditorController class
     */
    public void addComponents(MapEditorController mapEditorController) {
        setLayout(null);
        Border blackline = BorderFactory.createLineBorder(Color.black);
        Dimension frameSize = this.getSize();
        toolBar = new ToolBar(mapEditorController);
        toolBar.setBounds(0, 0, frameSize.width, 40);
        contientLabelViewPanel = new JPanel();
        continentLabel = new JLabel("Continent Tree");
        Dimension continentSize = continentLabel.getPreferredSize();
        continentLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));
        continentLabel.setBounds(140, 50, continentSize.width + 500, continentSize.height);
        contientLabelViewPanel.setBounds(10, 35, frameSize.width - 950, 35);
        add(contientLabelViewPanel);
        contientLabelViewPanel.setBackground(Color.lightGray);
        contientLabelViewPanel.setLayout(new FlowLayout());
        continentLabel.setFont(new Font("dialog", 1, 15));
        contientLabelViewPanel.setBorder(blackline);
        contientLabelViewPanel.add(continentLabel);

        treeScrollPane = new JScrollPane(mapTree);
        treeScrollPane.setBounds(10, 70, frameSize.width - 950, frameSize.height - 600);
        countryLabelViewPanel = new JPanel();
        countriesLabel = new JLabel("Countries Matrix");
        Dimension countriesSize = countriesLabel.getPreferredSize();
        countriesLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));
        countriesLabel.setBounds(240, 50, countriesSize.width + 500, countriesSize.height);
        countryLabelViewPanel.setBounds(contientLabelViewPanel.getBounds().x + (int) (contientLabelViewPanel.getBounds().getWidth()), 35,frameSize.width - 300, 35);
        add(countryLabelViewPanel);
        countryLabelViewPanel.setBackground(Color.lightGray);
        countryLabelViewPanel.setLayout(new FlowLayout());
        countriesLabel.setFont(new Font("dialog", 1, 15));
        countryLabelViewPanel.setBorder(blackline);
        countryLabelViewPanel.add(countriesLabel);
        scrollPane = new JScrollPane(tableMatrix);
        scrollPane.setBounds(treeScrollPane.getBounds().x + (int) (treeScrollPane.getBounds().getWidth()), 70,frameSize.width - 300, frameSize.height - 600);
        
        
        add(scrollPane);
        add(treeScrollPane);
        add(toolBar);

        countriesMatrix(gameMap);
        createTree(gameMap);
    }


    /**
     * Countries matrix.
     *
     * @param gameMap the game map
     */
    public void countriesMatrix(Map gameMap){
      //  System.out.println("inside countriesMAtrix");
        countries = gameMap.listOfCountryNames();
        int noOfCountries = countries.size();
        DefaultTableModel dtm = new DefaultTableModel(noOfCountries,noOfCountries) {
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        data = new String[noOfCountries][noOfCountries + 1];
        countryColumn = new String[noOfCountries + 1];
        countryColumn[0] = "**";
        int i = 0;
        int j = 0;
        for(String countryName:countries) {
            data[i++][0] = countryName;
            countryColumn[++j] =  countryName;
        }
        dtm.setDataVector(data, countryColumn);
        tableMatrix = new JTable(dtm) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component component = super.prepareRenderer(renderer, row, col);
                String value = (String) dtm.getValueAt(row, col);
                if (value.equals("N")) {
                    component.setBackground(Color.LIGHT_GRAY);
                }
                else {
                    component.setBackground(Color.WHITE);
                }
                return component;
            }
        };
        tableMatrix.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPane.getViewport().removeAll();
        scrollPane.getViewport().add(tableMatrix);
        for(Continent currentContinent:gameMap.getContinents()) {
            for(Country currentCountry:currentContinent.getCountriesPresent()) {
                int row_length = data.length;
                int column_length = data[0].length;
                for(i = 0;i<row_length;i++){
                    if(currentCountry.getCountryName().equalsIgnoreCase(data[i][0])) {
                        for(j = 1;j<column_length;j++){
                            if(!currentCountry.getListOfNeighbours().contains(countryColumn[j])) {
                                tableMatrix.setValueAt("N", i, j);
                            }
                            else {
                                tableMatrix.setValueAt("Y", i, j);
                            }
                        }
                    }
                }
            }
        }
        tableMatrix.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent ev) {
                int row = tableMatrix.rowAtPoint(ev.getPoint());
                int col = tableMatrix.columnAtPoint(ev.getPoint());
                String source = data[row][0];
                String neighbour = countryColumn[col];
                if(tableMatrix.getValueAt(row, col) == "Y") {
                    for(Continent continent:gameMap.getContinents()) {
                        for(Country country:continent.getCountriesPresent()) {
                            if(country.getCountryName().trim().equalsIgnoreCase(source.trim())) {
                                country.getListOfNeighbours().remove(neighbour);
                                tableMatrix.setValueAt("N", row, col);
                                for(String s:country.getListOfNeighbours()) {
                                    System.out.println("country " + s);
                                }
                                System.out.println();
                                System.out.println();
                            }
                        }
                    }
                }
                else {
                    if(!source.trim().equalsIgnoreCase(neighbour)) {
                        if(tableMatrix.getValueAt(row, col) == "N") {
                            for(Continent continent:gameMap.getContinents()) {
                                for(Country country:continent.getCountriesPresent()) {
                                    if(country.getCountryName().trim().equalsIgnoreCase(source.trim())) {
                                        country.getListOfNeighbours().add(neighbour);
                                        tableMatrix.setValueAt("Y", row, col);
                                        for(String s:country.getListOfNeighbours()) {
                                            System.out.println("country" + s);
                                        }
                                        System.out.println();
                                        System.out.println();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }


    /**
     * Creates the tree.
     *
     * @param gameMap the game map
     */
    public void createTree(Map gameMap) {
    	
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Map - " + gameMap.getName() + "");
        for (Continent continent : gameMap.getContinents()) {
            DefaultMutableTreeNode branch = new DefaultMutableTreeNode(continent.getContinentName());
            for (Country country : continent.getCountriesPresent()) {
                DefaultMutableTreeNode subBranch = new DefaultMutableTreeNode(country.getCountryName());
                branch.add(subBranch);
				if(country.getOwnedBy()!=null) 
				{
                DefaultMutableTreeNode ownerName = new DefaultMutableTreeNode(country.getOwnedBy().getPlayerName());
                subBranch.add(ownerName);
				DefaultMutableTreeNode armyCount = new DefaultMutableTreeNode(country.getNoOfArmiesPresent());
				subBranch.add(armyCount);
				}
                
            }
            top.add(branch);
        }
        mapTree = new JTree(top);
        treeScrollPane.getViewport().add(mapTree);
                
    }
    
    
    
    public void addPlayerViewComponents() {
    	Border blackline = BorderFactory.createLineBorder(Color.black);        	
    	JPanel playerLabelViewPanel = new JPanel();
        JLabel playerLabel = new JLabel("Player View");
        playerLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));
        playerLabel.setBounds(140, 450, playerLabel.getPreferredSize().width + 500, playerLabel.getPreferredSize().height);
        playerLabelViewPanel.setBounds(10, 470, this.getSize().width - 40, 35);
        add(playerLabelViewPanel);
        playerLabelViewPanel.setBackground(Color.lightGray);
        playerLabelViewPanel.setLayout(new FlowLayout());
        playerLabel.setFont(new Font("dialog", 1, 15));
        playerLabelViewPanel.setBorder(blackline);
        playerLabelViewPanel.add(playerLabel);
        
            	
    	playerScrollPane=createTable();
    	playerScrollPane.setBounds(10, 500, this.getSize().width - 40, 350);
    	add(playerScrollPane);
    	
    	  
     }
    
    public JScrollPane createTable() {
    	
		String[] columnNames = { "Player Name ", "Countries Owned", "Number of Armies", "Continents Owned"};
		//Gameplay gameInstance=Gameplay.getInstance();
		List<Player> playerList= Gameplay.getInstance().getPlayers();
        int rowCount=Gameplay.getInstance().getSelectedMap().listOfCountryNames().size() + playerList.size() - 1;
		Object[][] data = new Object[rowCount][4];
		int row=0;
		for(Player player:playerList) {
			//System.out.println("RowCount "+rowCount);
			String playerName=player.getPlayerName();
			List<Country> playerOwnedCountryList=Gameplay.getInstance().getSelectedMap().getOwnedCountries(playerName);
			List<Continent> continentList=Gameplay.getInstance().getSelectedMap().getOwnedContinents(playerName);
			String continentsOwned="";
			for(Continent continent:continentList)
				continentsOwned+=", "+continent.getContinentName();
			continentsOwned=(continentsOwned.contentEquals(""))?("None"):(continentsOwned.substring(1));
			if(playerOwnedCountryList.size()==0) {
				Object[] rowData= {playerName,"","",continentsOwned};
				data[row]=rowData;
				++row;
		
			}
			else {	
			for(Country country:playerOwnedCountryList)
			{
			if(playerOwnedCountryList.get(0).equals(country))
			{	Object[] rowData= {playerName,country.getCountryName(),country.getNoOfArmiesPresent(),continentsOwned};
				data[row]=rowData;
			}
			else
			{	Object[] rowData= {"",country.getCountryName(),country.getNoOfArmiesPresent(),""};
				data[row]=rowData;
			}
			++row;
			}
			}
			
		}
		
		final JTable table = new JTable(data, columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		table.setEnabled(false);

//Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);

//Add the scroll pane to this panel.
		return (scrollPane);
    	
    }

	@Override
	public void update(Observable arg0, Object arg1) {
		
		if(arg1.toString().contains("showmap")) {
		if(playerScrollPane!=null) {
		remove(playerScrollPane);
		playerScrollPane=createTable();
    	playerScrollPane.setBounds(10, 500, this.getSize().width - 40, 350);
    	add(playerScrollPane);
		}
		}
    	
	}

	

    
}
