package com.concordia.riskgame.controller;

import com.concordia.riskgame.model.Modules.Continent;
import com.concordia.riskgame.model.Modules.Country;
import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.model.Modules.Map;
import com.concordia.riskgame.utilities.ConquestMapAdapter;
import com.concordia.riskgame.utilities.ConquestMapTools;
import com.concordia.riskgame.utilities.DominationMapTools;
import com.concordia.riskgame.utilities.Phases;
import com.concordia.riskgame.view.MapEditorView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


/**
 * This class performs commands related to editing map components e.g. editcountry, editccontinent, editneighbour
 */
public class MapEditorController implements ActionListener {

    private MapEditorView mapEditorView;
    private Map gameMap;
    

   	    /**
	    * Instantiates a new map editor controller.
	    *
	    * @param mapEditorView the map editor view
	    */
	   public MapEditorController(MapEditorView mapEditorView) {
   		
        this.mapEditorView = mapEditorView;
        this.gameMap = mapEditorView.gameMap;
        	
   	}

    /**
     * Setter method to setup GameMap
     * @param gameMap game Map
     */
    public void setGameMap(Map gameMap) {
		this.gameMap = gameMap;
		
	}

    /**
     * Getter method to get GameMap
     * @return gameMap
     */
    public Map getGameMap() {
		return gameMap;
				
    }
    
    


    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String button = e.getActionCommand();

        switch (button) {
            case "Add Continent":
                {
                addContinent();
                break;
                }
            case "Add Countries":
                addCountries();
                break;
            case "Remove Continent":
                removeContinent();
                break;
            case "Remove Country":
                removeCountry();
                break;
            case "Save":
                save();
                break;
            default:
                break;
        }
    }



    /**
     * This method is integrated with the UI, and it performs add continent action
     */
    public void addContinent() {
        boolean loop = true;
        while (loop) {
            String continentName = JOptionPane.showInputDialog(null, "Enter the Continent name: ", "Add Continent", JOptionPane.OK_CANCEL_OPTION | JOptionPane.QUESTION_MESSAGE);
            int controlValue=Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the Continent Control Value: ", "Add Continent Control Value", JOptionPane.OK_CANCEL_OPTION | JOptionPane.QUESTION_MESSAGE));
            if (continentName != null) {
                if (continentName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please specify the name!");
                } else if (continentName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Name cannot be empty!");
                } else if (gameMap.searchContinent(continentName).equals(continentName)) {
                    JOptionPane.showMessageDialog(null, "Continent name already exists!");
                } else if (gameMap.searchContinent(continentName.trim()).equals(continentName.trim())) {
                    JOptionPane.showMessageDialog(null, "Continent name already exists!");
                } else if ((continentName != null) && (gameMap.searchContinent(continentName) == "")) {
                	addContinentService(continentName,controlValue,true);
                	JOptionPane.showMessageDialog(null, "Continent"+continentName+"Added !");
                    
                    mapEditorView.createTree(getGameMap());
                    loop = false;
                }
            } else {
                loop = false;
            }
        }
    }
    
    /**
     * Adds the continent service.
     *
     * @param continentName the continent name
     * @param controlValue the control value
     * @param calledByUI the called by UI
     */
    public void addContinentService(String continentName,int controlValue,boolean calledByUI) {
    	if(!calledByUI) {
    		if (continentName.isEmpty()) {
                System.out.println("Please specify the name!");return;
            } else if (continentName.trim().isEmpty()) {
                System.out.println("Name cannot be empty!");return;
            } else if (gameMap.searchContinent(continentName).equals(continentName)) {
                System.out.println("Continent name already exists!");return;
            } else if (gameMap.searchContinent(continentName.trim()).equals(continentName.trim())) {
                System.out.println("Continent name already exists!");return;
            }
    	}
    	Continent continent=new Continent();
    	continent.setContinentName(continentName);
    	continent.setControlValue(controlValue);
        gameMap.addContinent(continent);
        System.out.println("Continent "+continentName+" Added to Map");
    }

    
    
    /**
     * Adds the countries to the game map.
     */
    public void addCountries() {
        JTextField inputCountry = new JTextField();
        String lastContinent = "";
        int flag = 0;
        if (gameMap.getContinents().size() == 0) {
            JOptionPane.showMessageDialog(null, "Please specify continent first!");
        } else {
            String continents[] = new String[gameMap.getContinents().size()];
            int count = 0;
            for (Continent name : gameMap.getContinents()) {
                continents[count++] = name.getContinentName();
            }
            JComboBox<Object> continentBox = new JComboBox<Object>(continents);
            Object[] message = {"Select continent name : ", continentBox, "Enter the Country name : ", inputCountry};
            continentBox.setSelectedIndex(0);
            boolean loop = true;
            while (loop) {
                int countryName = JOptionPane.showConfirmDialog(null, message, "Country Name",
                        JOptionPane.OK_CANCEL_OPTION);

                if (countryName == JOptionPane.OK_OPTION) {
                    for (Continent continentName : gameMap.getContinents()) {
                        for (Country name : continentName.getCountriesPresent()) {
                            if (name.getCountryName().equals(inputCountry.getText())) {
                                flag = 1;
                                break;
                            }
                        }
                    }
                                        
                    if (inputCountry.getText() == null || inputCountry.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please specify the name!");
                    } else if (gameMap.getContinents().size() == 0) {
                        JOptionPane.showMessageDialog(null, "Please enter continent first");
                    } else if (flag == 1) {
                        flag = 0;
                        JOptionPane.showMessageDialog(null, "Country already exists!");
                    } else {
                    	addCountriesService((String)continentBox.getItemAt(continentBox.getSelectedIndex()),inputCountry.getText(),true);
                        mapEditorView.createTree(getGameMap());
                         mapEditorView.countriesMatrix(getGameMap());
                        loop = false;
                    }
                } else {
                    loop = false;
                }
            }
        }
    }

    /**
     * Adds the countries service.
     *
     * @param continentName the continent name
     * @param countryName the country name
     * @param calledbyUI the calledby UI
     */
    public void addCountriesService(String continentName,String countryName,boolean calledbyUI) {
    	if(!calledbyUI) {
    		 if (gameMap.getContinents().size() == 0) {
    	            System.out.println("Please specify continent first!");
    	            return;
    	        }
    		 if (countryName == null || countryName.trim().isEmpty()) {
                 System.out.println("Please specify the name!");
                 return;
                 }
    		
            for (Continent continentNameIter : gameMap.getContinents()) {
            if(continentNameIter.searchCountry(countryName)!=null)
            {
                    System.out.println("Country "+countryName+" already exists!");
                    return;
                }
            }
        }
    	
    	   	   	
    	 Continent tempContinent = null;
    	 boolean flag=false;
         for (Continent name : gameMap.getContinents()) {
             if (name.getContinentName().equals(continentName)) {
                 tempContinent = name;flag=true;
             }
         } 
    	 if(!flag) {
    		 System.out.println("The continent"+continentName +" do not exist in the selected Map");return;}
    	 
    	List<String> countryNames = new ArrayList<String>();
        countryNames = gameMap.listOfCountryNames();
        Country newCountry = new Country();
        newCountry.setCountryName(countryName);
        tempContinent.addCountry(newCountry);
        System.out.println("Country "+countryName+" added to "+tempContinent.getContinentName());
        
      }
    
    
    
    
    

    /**
     * Removes the continent from game map.
     */
    public void removeContinent() {
        if (gameMap.getContinents().size() == 0) {
        	System.out.println("-------->MAP OBEJCT "+gameMap);
            JOptionPane.showMessageDialog(null, "Map Contains Zero Continent!");
        } else {
            String continents[] = new String[gameMap.getContinents().size()];
            int count = 0;
            for (Continent name : gameMap.getContinents()) {
                continents[count++] = name.getContinentName();
            }
            JComboBox<Object> continentBox = new JComboBox<Object>(continents);
            continentBox.setSelectedIndex(-1);
            JOptionPane.showConfirmDialog(null, continentBox, "Select continent name : ", JOptionPane.OK_CANCEL_OPTION);
            if (continentBox.getSelectedIndex() > -1) {
                int result = JOptionPane.showConfirmDialog(null, "Deleting Continent will delete all the countries! \n Do you want to Continue ?");
                if (result == JOptionPane.YES_OPTION) {
                	removeContinentService((String) continentBox.getItemAt(continentBox.getSelectedIndex()),true);
                   mapEditorView.createTree(getGameMap());
                   mapEditorView.countriesMatrix(getGameMap());
                }
            }
        }
    }
    
    
    /**
     * Removes the continent service.
     *
     * @param continentName the continent name
     * @param calledfromUI the calledfrom UI
     */
    public void removeContinentService(String continentName,boolean calledfromUI) {
    	if(!calledfromUI) {
    		boolean flag=false;
    		for(Continent continent:gameMap.getContinents())
    			if(continent.getContinentName().equals(continentName))
    				flag=true;
    		if(!flag) {
    			System.out.println("Continent not present in Map");
    			return;}
    		
    	}
    	System.out.println("This will remove all countries in the selected continent");
    	List<String> countriesToBeRemoved = new ArrayList<String>();
        Continent tempContinent = null;
        for (Continent name : gameMap.getContinents()) {
            if (name.getContinentName().equals(continentName)) {
                tempContinent = name;
                for (Country c : tempContinent.getCountriesPresent()) {
                    countriesToBeRemoved.add(c.getCountryName());
                }
            }
        }
        gameMap.removeContinent(tempContinent);
        for (Continent c : gameMap.getContinents()) {
            for (Country t : c.getCountriesPresent()) {
                for (String neighbours : countriesToBeRemoved) {
                    if (t.getListOfNeighbours().contains(neighbours)) {
                        t.getListOfNeighbours().remove(neighbours);
                    }
                }
            }
        }
        System.out.println("Continent "+continentName+" and it's contained countries has been removed");
    }
      


    /**
     * Removes the country from game map.
     */
    public void removeCountry() {
        if (gameMap.getContinents().size() == 0) {
            JOptionPane.showMessageDialog(null, "Map Contains Zero Continent, So no country to remove!");
        } else if (gameMap.listOfCountryNames().size() == 0) {
            JOptionPane.showMessageDialog(null, "All Continents are empty, No country to remove!");
        } else {
            ArrayList<String> continentsList = new ArrayList<String>();
            int count = 0;
            for (Continent contName : gameMap.getContinents()) {
                if (contName.getCountriesPresent().size() > 0) {
                    continentsList.add(contName.getContinentName());
                }
            }
            String[] continents = new String[continentsList.size()];
            for (String value : continentsList) {
                continents[count++] = value;
            }
            JComboBox<Object> continentBox = new JComboBox<Object>(continents);
            continentBox.setSelectedIndex(-1);
            int result = JOptionPane.showConfirmDialog(null, continentBox, "Select continent name : ", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                if (continentBox.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Please select continent");
                } else {
                    String continentNameString = (String) continentBox.getSelectedItem();
                    Continent continentName = null;
                    for (Continent name : gameMap.getContinents()) {
                        if (name.getContinentName().equals(continentNameString)) {
                            continentName = name;
                        }
                    }
                    if (continentName.getCountriesPresent().size() == 0) {
                        JOptionPane.showMessageDialog(null,
                                continentName.getContinentName() + " has no country to remove");
                    } else {
                        String countries[] = new String[continentName.getCountriesPresent().size()];
                        int val = 0;
                        for (Country countryName : continentName.getCountriesPresent()) {
                            countries[val++] = countryName.getCountryName();
                        }
                        JComboBox<Object> countryBox = new JComboBox<Object>(countries);
                        countryBox.setSelectedIndex(-1);
                        int CountryResult = JOptionPane.showConfirmDialog(null, countryBox, "Select Country name : ", JOptionPane.OK_CANCEL_OPTION);
                        if (CountryResult == JOptionPane.OK_OPTION) {
                            if (countryBox.getSelectedItem() == null) {
                                JOptionPane.showMessageDialog(null, "Please select country");
                            } else {
                            	
                                
                            	removeCountryService(continentName,(String) countryBox.getItemAt(countryBox.getSelectedIndex()),true);
                                }
                               mapEditorView.createTree(getGameMap());
                               mapEditorView.countriesMatrix(getGameMap());
                            }
                        }
                    }
                }
             else {
                System.out.println("Continent name not selected");
            }
        }
    }
    



	/**
	 * Removes the country service.
	 *
	 * @param continentName the continent name
	 * @param countryName the country name
	 * @param calledfromUI the calledfrom UI
	 */
	public void removeCountryService(Continent continentName,String countryName,boolean calledfromUI) {
			if(!calledfromUI) {
			boolean flag=false;
			for(Continent continent:gameMap.getContinents())
				if(continent.searchCountry(countryName)!=null) {
						flag=true;continentName=continent;}
	
			if(!flag) {
				System.out.println("Country not present in the Map");
				return;}
			}
	      Country tempCountry = continentName.searchCountry(countryName);
		/*
		 * for (Country name : continentName.getCountriesPresent()) { if
		 * (name.getCountryName().equalsIgnoreCase(countryName)) { tempCountry = name;
		 * break; } }
		 */
          String removedCountry = tempCountry.getCountryName();
          continentName.removeCountry(tempCountry);
        //  continentName.setControlValue(continentName.getCountriesPresent().size());
          for (Continent c : gameMap.getContinents()) {
              for (Country t : c.getCountriesPresent()) {
                  if (t.getListOfNeighbours().contains(removedCountry)) {
                      t.getListOfNeighbours().remove(removedCountry);
                  }
              }
          }
          System.out.println("Country "+countryName+" removed");
	}	
	
	
	/**
	 * Removes the neighbour service.
	 *
	 * @param countryName the country name
	 * @param neighbourCountryName the neighbour country name
	 */
	public void removeNeighbourService(String countryName,String neighbourCountryName) {
		Country[] inputCountries=checkNeighborArgumentValidity(countryName,neighbourCountryName);
		if(!((inputCountries[0]!=null) && (inputCountries[1]!=null)))
			return;
		
		if(!inputCountries[0].getListOfNeighbours().contains(neighbourCountryName)) {
				System.out.println(neighbourCountryName+" and "+countryName+" are not neighbors ");
				return;
		}
		
		else{				
			inputCountries[0].getListOfNeighbours().remove(neighbourCountryName);
			inputCountries[1].getListOfNeighbours().remove(countryName);
			System.out.println(neighbourCountryName+" and "+countryName+" are no longer neighbors ");
			}
			
			
		
	}

	
	/**
	 * Adds the neighbour service.
	 *
	 * @param countryName the country name
	 * @param neighbourCountryName the neighbour country name
	 */
	public void addNeighbourService(String countryName,String neighbourCountryName) {
			Country[] inputCountries=checkNeighborArgumentValidity(countryName,neighbourCountryName);
			if(!((inputCountries[0]!=null) && (inputCountries[1]!=null)))
				return;
			
			if(inputCountries[0].getListOfNeighbours().contains(neighbourCountryName)) {
					System.out.println(neighbourCountryName+" and "+countryName+" are already neighbors ");
					return;
			}
			
			else{				
				inputCountries[0].getListOfNeighbours().add(neighbourCountryName);
				inputCountries[1].getListOfNeighbours().add(countryName);
				System.out.println(neighbourCountryName+" and "+countryName+" are now neighbors ");
				}
				
		}		
					
	
	
	/**
	 * Check neighbor argument validity.
	 *
	 * @param countryName the country name
	 * @param neighborName the neighbor name
	 * @return the country[]
	 */
	public Country[] checkNeighborArgumentValidity(String countryName,String neighborName) {
		Country[] resultCountries=new Country[2];
		
		for(Continent continent:gameMap.getContinents()) {
			Country returnVal1=continent.searchCountry(countryName);
			Country returnVal2=continent.searchCountry(neighborName);
			if(returnVal1!=null)
					{resultCountries[0]=returnVal1;}
			if(returnVal2!=null)
					{resultCountries[1]=returnVal2;}
			if((returnVal1!=null) && (returnVal2!=null) )
				break;
			}
			
			if(resultCountries[0]==null)
				System.out.println(countryName+" is not present in the selected Map");
			
			if(resultCountries[1]==null)
				System.out.println(neighborName+" is not present in the selected Map");
				
		
			return (resultCountries);
	}




    /**
     * Save the File.
     */
    public void save() {
        DominationMapTools mapTool = new ConquestMapAdapter(new ConquestMapTools());
        if (mapTool.validateMap(gameMap, 3)) {
            System.out.println("Done");
            boolean bool = true;
            while (bool) {
                String mapName = JOptionPane.showInputDialog(null, "Please enter the map name to save");
                if (mapName != null) {
                    if (mapName.trim().isEmpty()) {
                        JOptionPane.showConfirmDialog(null, "Please enter some name!");
                    } else {
                        gameMap.setName(mapName);
                        if (mapTool.saveDataIntoFile(gameMap, mapName)) {
                            JOptionPane.showMessageDialog(null, "Map has been saved");
                        } else {
                            JOptionPane.showMessageDialog(null, "Not able to save map file, enter different map name.");
                        }
                        bool = false;
                    }
                } else {
                    bool = false;
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Map, can not save.");
            System.out.println(gameMap.getErrorMessage());
        }
    }
    
    /**
     * Save map service.
     *
     * @param mapName the map name
     */
    public void saveMapService(String mapName) {
   	 DominationMapTools mapTool = new ConquestMapAdapter(new ConquestMapTools());
        if (mapTool.validateMap(gameMap, 2)) {
            System.out.println("Done");
            boolean bool = true;
            while (bool) {
                if (mapName != null) {
                    if (mapName.trim().isEmpty()) {
                        System.out.println("Please enter some name!");
                    } else {
                        gameMap.setName(mapName);
                        if (mapTool.saveDataIntoFile(gameMap, mapName)) {
                            System.out.println("Map has been saved");
                        } else {
                            System.out.println("Not able to save map file, enter different map name.");
                        }
                        bool = false;
                    }
                } else {
                    bool = false;
                }
            }
        } else {
            System.out.println("Invalid Map, can not save.");
            System.out.println(gameMap.getErrorMessage());
        }
   }
    
    /**
     * Show map service.
     *
     * @param map the map
     */
    public void showMapService(Map map) {
    	mapEditorView.setVisible(true);
    	mapEditorView.createTree(map);
        mapEditorView.countriesMatrix(map);
        if(!Gameplay.getInstance().getCurrentPhase().equals(Phases.MapEditor))
        	mapEditorView.addPlayerViewComponents();
    
    }
}
