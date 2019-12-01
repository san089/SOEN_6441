package com.concordia.riskgame.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.concordia.riskgame.model.Modules.Continent;
import com.concordia.riskgame.model.Modules.Country;
import com.concordia.riskgame.model.Modules.Map;

public class ConquestMapTools {

	
	private DominationMapTools maptools=new DominationMapTools();

	/**
	 * Parses the and validate map.
	 *
	 * @param gameMap the game map
	 * @param size the size
	 * @return true, if successful
	 */
	public boolean parseAndValidateMap(Map gameMap, int size) {
		boolean isMapValid = false;
		try {
			FileReader mapFile;
			String line = null;
			mapFile = new FileReader(gameMap.getPath() + "\\" + gameMap.getName());
			String Data = "";
			BufferedReader mapReader = new BufferedReader(mapFile);
			while ((line = mapReader.readLine()) != null) {
				if (line != "\n") {
					Data += line + "\n";
				}
			}
			if (Data.toLowerCase().indexOf("[continents]") >= 0 &&  Data.toLowerCase().indexOf("[territories]") >= 0 && Data.toLowerCase().indexOf("author") >= 0 && Data.toLowerCase().indexOf("[map]") >= 0 && Data.toLowerCase().indexOf("image") >= 0
					&& Data.toLowerCase().indexOf("wrap") >= 0 && Data.toLowerCase().indexOf("scroll") >= 0 && Data.toLowerCase().indexOf("warn") >= 0) {
				isMapValid = true;
				gameMap.setErrorOccurred(false);
				gameMap.setErrorMessage("No Errors");
			}
			else {
				isMapValid = false;
				gameMap.setErrorOccurred(true);
				gameMap.setErrorMessage("Information missing/Not in conquest format");
				return isMapValid;
			}
			String continentData = Data.substring(Data.toLowerCase().indexOf("[continents]"), Data.toLowerCase().indexOf("[territories]"));
			String countryData = Data.substring(Data.toLowerCase().indexOf("[territories]"));
			String[] countryDataArray = countryData.split("\n");
			String[] continentDataArray = continentData.split("\n");
			for (String stringManipulation : continentDataArray) {
				if (!stringManipulation.equalsIgnoreCase("[continents]") && stringManipulation.length() >= 3) {
					Continent newContinent = new Continent();
					newContinent.setContinentName(stringManipulation.substring(0, stringManipulation.indexOf("=")).toUpperCase());
					newContinent.setControlValue(Integer.parseInt(stringManipulation.substring(stringManipulation.indexOf("=") + 1)));
					gameMap.getContinents().add(newContinent);
				}
			}
			for (String stringManipulation : countryDataArray) {
				if ((!stringManipulation.equalsIgnoreCase("[territories]") && stringManipulation.length() > 3)) {
					if (stringManipulation.replaceAll("[^,]", "").length() >= 4) {
						Country newCountry = new Country();
						String[] stringManipulationArray = stringManipulation.split(",");
						newCountry.setCountryName(stringManipulationArray[0]);
						newCountry.setLatitude(Double.parseDouble(stringManipulationArray[1].trim()));
						newCountry.setLongitude(Double.parseDouble(stringManipulationArray[2].trim()));
						for (int i = 4; i < stringManipulationArray.length; i++) {
							newCountry.getListOfNeighbours().add(stringManipulationArray[i]);
						}
						for (Continent currentContinent : gameMap.getContinents()) {
							if (currentContinent.getContinentName().toLowerCase().indexOf(stringManipulationArray[3].trim().toLowerCase()) >= 0) {
								currentContinent.getCountriesPresent().add(newCountry);
							}
						}
					}
					else {
						gameMap.setErrorOccurred(true);
						gameMap.setErrorMessage("Information missing");
						break;
					}
				}
			}
			isMapValid=maptools.validateMap(gameMap,size);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return isMapValid;
	}
	
	/**
	 * Save data into file.
	 *
	 * @param gameMap the game map
	 * @param name the name
	 * @return true, if successful
	 */
	public boolean saveDataIntoFile(Map gameMap, String name) {
				String data = "[Map]\nauthor=Anonymous\nImage=NA\nwrap=no\nscroll=both\nwarn=no\n\n[Continents]\n";
				for (Continent c : gameMap.getContinents()) {
					data = data + c.getContinentName();
					if(c.getControlValue()==0){
						c.setControlValue(c.getCountriesPresent().size());	
					}
					data = data + "=" + c.getControlValue();
					data += "\n";
				}
				data += "[Territories]\n";
				for (Continent c : gameMap.getContinents()) {
					for (Country country : c.getCountriesPresent()) {
						data += country.getCountryName() + "," + country.getLatitude() + "," + country.getLongitude() + "," + c.getContinentName() + "," + String.join(",", country.getListOfNeighbours()) + "\n";
					}
				}
				PrintWriter writeData = null;
				try {
					writeData = new PrintWriter(Constants.mapLocation+name+".map");
					writeData.println(data);
					return true;
				} 
				catch (Exception e) {
					System.out.println(e.getMessage());
					return false;
				}
				finally{
					writeData.close();
				}
		}
	
	
}
