package com.concordia.riskgame.controller;

import com.concordia.riskgame.model.Modules.Continent;
import com.concordia.riskgame.model.Modules.Country;
import com.concordia.riskgame.model.Modules.Map;
import com.concordia.riskgame.utilities.MapTools;
import com.concordia.riskgame.view.MapEditorView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


public class MapEditorController extends Observable implements ActionListener {
  //  private Continent continent;
    private com.concordia.riskgame.view.MapEditorView mapEditorView;
    private Map gameMap;
    

    public MapEditorController(MapEditorView mapEditorView) {
        this.gameMap = mapEditorView.gameMap;
        this.mapEditorView = mapEditorView;
		/*
		 * setChanged(); notifyObservers(mapEditorView);
		 */}


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
                } else if (gameMap.searchContinent(continentName).equalsIgnoreCase(continentName)) {
                    JOptionPane.showMessageDialog(null, "Continent name already exists!");
                } else if (gameMap.searchContinent(continentName.trim()).equalsIgnoreCase(continentName.trim())) {
                    JOptionPane.showMessageDialog(null, "Continent name already exists!");
                } else if ((continentName != null) && (gameMap.searchContinent(continentName) == "")) {
                	addContinentService(continentName,controlValue,true);
                	JOptionPane.showMessageDialog(null, "Continent"+continentName+"Added !");
                    
                    mapEditorView.createTree();
                    loop = false;
                }
            } else {
                loop = false;
            }
        }
    }
    
    public void addContinentService(String continentName,int controlValue,boolean calledByUI) {
    	if(!calledByUI) {
    		if (continentName.isEmpty()) {
                System.out.println("Please specify the name!");return;
            } else if (continentName.trim().isEmpty()) {
                System.out.println("Name cannot be empty!");return;
            } else if (gameMap.searchContinent(continentName).equalsIgnoreCase(continentName)) {
                System.out.println("Continent name already exists!");return;
            } else if (gameMap.searchContinent(continentName.trim()).equalsIgnoreCase(continentName.trim())) {
                System.out.println("Continent name already exists!");return;
            }
    	}
    	Continent continent=new Continent();
    	continent.setContinentName(continentName);
    	continent.setControlValue(controlValue);
        gameMap.addContinent(continent);
        System.out.println("Continent "+continentName+" Added to Map");
    }

    
    
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
                            if (name.getCountryName().equalsIgnoreCase(inputCountry.getText())) {
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
                        mapEditorView.createTree();
                         mapEditorView.countriesMatrix();
                        loop = false;
                    }
                } else {
                    loop = false;
                }
            }
        }
    }

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
            for (Country name : continentNameIter.getCountriesPresent()) {
                if (name.getCountryName().equalsIgnoreCase(countryName)) {
                    System.out.println("Country "+countryName+" already exists!");
                    return;
                }
            }
        }
    	}
    	
    	   	
    	 Continent tempContinent = null;
    	 boolean flag=false;
         for (Continent name : gameMap.getContinents()) {
             if (name.getContinentName().equalsIgnoreCase(continentName)) {
                 tempContinent = name;flag=true;
             }
         } 
    	 if(!flag) {
    		 System.out.println("The continent"+continentName +" do not exist in the selected Map");return;}
    	 
    	List<String> countryNames = new ArrayList<String>();
        countryNames = gameMap.listOfCountryNames();
        Country newCountry = new Country();
        newCountry.setCountryName(countryName);
        if (countryNames.size() > 0) {
            newCountry.setListOfNeighbours(countryNames);
        }
        for (Country country : tempContinent.getCountriesPresent()) {
            country.getListOfNeighbours().add(countryName);
        }
        tempContinent.addCountry(newCountry);
        System.out.println("Country "+countryName+" added to map");
        
      //  tempContinent.setControlValue(tempContinent.getCountriesPresent().size());
    }
    
    
    
    
    

    public void removeContinent() {
      //  System.out.println("Aya 1");
        if (gameMap.getContinents().size() == 0) {
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
                   mapEditorView.createTree();
                   mapEditorView.countriesMatrix();
                }
            }
        }
    }
    
    
    public void removeContinentService(String continentName,boolean calledfromUI) {
    	if(!calledfromUI) {
    		boolean flag=false;
    		for(Continent continent:gameMap.getContinents())
    			if(continent.getContinentName().contentEquals(continentName))
    				flag=true;
    		if(!flag) {
    			System.out.println("Continent not present in Map");
    			return;}
    		
    	}
    	System.out.println("This will remove all countries in the selected continent");
    	List<String> countriesToBeRemoved = new ArrayList<String>();
        Continent tempContinent = null;
        for (Continent name : gameMap.getContinents()) {
            if (name.getContinentName().equalsIgnoreCase(continentName)) {
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
                        if (name.getContinentName().equalsIgnoreCase(continentNameString)) {
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
                               mapEditorView.createTree();
                               mapEditorView.countriesMatrix();
                            }
                        }
                    }
                }
             else {
                System.out.println("Continent name not selected");
            }
        }
    }
    



	public void removeCountryService(Continent continentName,String countryName,boolean calledfromUI) {
			if(!calledfromUI) {
			boolean flag=false;
			for(Continent continent:gameMap.getContinents())
				for(Country country:continent.getCountriesPresent())
					if(country.getCountryName().equalsIgnoreCase(countryName)) {
						flag=true;continentName=continent;}
	
			if(!flag) {
				System.out.println("Country not present in the Map");
				return;}
			}
	      Country tempCountry = null;
          for (Country name : continentName.getCountriesPresent()) {
              if (name.getCountryName().equalsIgnoreCase(countryName)) {
                  tempCountry = name;
                  break;
              }
          }
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
	
	
	public void removeNeighbourService(String countryName,String neighbourCountryName) {
		boolean countryflag=false,neighbourcountryflag=false;
		for(Continent continent:gameMap.getContinents()) {
			for(Country country:continent.getCountriesPresent()) {
				if(country.getCountryName().equalsIgnoreCase(countryName)) {
					countryflag=true;
					for(String neighbourCountry:country.getListOfNeighbours()) {
						if(neighbourCountry.equalsIgnoreCase(neighbourCountryName)) {
							country.getListOfNeighbours().remove(neighbourCountry);
							System.out.println(neighbourCountryName+" removed from the neighbour list of "+countryName);
							neighbourcountryflag=true;
						}
					}
						if(!neighbourcountryflag)
							System.out.println(neighbourCountryName+" is not a neighbor of "+countryName);
					}
							
				}
			}
		
			if(!countryflag)
			System.out.println("CountryName"+" is not present in the map");
			
	}
	
	public void addNeighbourService(String countryName,String neighbourCountryName) {
		boolean countryflag=false,neighborcountryflag=false;;
		for(Continent continent:gameMap.getContinents()) {
			for(Country country:continent.getCountriesPresent()) {
				if(country.getCountryName().equalsIgnoreCase(countryName)) {
					countryflag=true;
					for(String neighbourCountry:country.getListOfNeighbours()) {
						if(neighbourCountry.equalsIgnoreCase(neighbourCountryName)) {
							System.out.println(neighbourCountryName+" is already a neighbour of "+countryName);
							neighborcountryflag=true;
							break;
						}
					}
						if(!neighborcountryflag) {
							country.getListOfNeighbours().add(neighbourCountryName);
							System.out.println(neighbourCountryName+" added as a neighbour of "+countryName);
							
						}
					}
			}
		}
				
			if(!countryflag)
			System.out.println("CountryName"+" is not present in the map");
			
	}





    public void save() {
        MapTools mapTool = new MapTools();
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
    
    
    
    public void showMapService() {
    	mapEditorView.setVisible(true);
    	mapEditorView.createTree();
        mapEditorView.countriesMatrix();
    
    }
}
