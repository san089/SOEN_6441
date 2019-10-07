 package com.concordia.riskgame.utilities;

 import com.concordia.riskgame.model.Modules.Continent;
 import com.concordia.riskgame.model.Modules.Country;
 import com.concordia.riskgame.model.Modules.Map;

 import javax.swing.*;
 import javax.swing.filechooser.FileNameExtensionFilter;
 import java.io.*;
 import java.util.ArrayList;
 import java.util.HashSet;
 import java.util.Iterator;
 import java.util.List;



public class MapTools  {
	


	public String pickMapFile(Map gameMap) {
		String sAppendFileName = null;
		try {
			String importFileName;
			JFileChooser chooser;
			chooser = new JFileChooser();
			chooser.setDialogTitle("Choose Map file");
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.addChoosableFileFilter(new FileNameExtensionFilter("*.map", "map","bin"));
			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				importFileName = chooser.getSelectedFile().getAbsolutePath();
				if (importFileName.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "File name invalid");
				} 
				else {
					if (importFileName.trim().substring(importFileName.length() - 4).equals(".map") || 
							importFileName.trim().substring(importFileName.length() - 4).equals(".bin")) {
						File f = new File(importFileName);
						gameMap.setName(f.getName());
						gameMap.setPath(importFileName.substring(0, importFileName.lastIndexOf("\\")));
						JOptionPane.showMessageDialog(null, "File in Correct format");
						System.out.println(gameMap.getPath());
						System.out.println(gameMap.getName());
						sAppendFileName=gameMap.getPath();
					}
					else {
						JOptionPane.showMessageDialog(null, "File name invalid");
						String sPrint = importFileName.trim().substring(importFileName.length() - 4);
						System.out.println(sPrint);
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return sAppendFileName;
	}

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
			if (Data.toLowerCase().indexOf("[continents]") >= 0 && Data.toLowerCase().indexOf("[territories]") >= 0 && Data.toLowerCase().indexOf("author") >= 0 && Data.toLowerCase().indexOf("[map]") >= 0) {
				isMapValid = true;
				gameMap.setErrorOccurred(false);
				gameMap.setErrorMessage("No Errors");
			}
			else {
				isMapValid = false;
				gameMap.setErrorOccurred(true);
				gameMap.setErrorMessage("Information missing");
				return isMapValid;
			}
			String authorData = Data.substring(Data.toLowerCase().indexOf("[map]"), Data.toLowerCase().indexOf("[continents]"));
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
			isMapValid=validateMap(gameMap,size);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return isMapValid;
	}
	

	public boolean validateMap(Map gameMap,int size) {
		if (!checkEmptyContinent(gameMap)) {
			if (!checkDuplicateContinents(gameMap)) {
				if (!checkDuplicateCountries(gameMap)) {
					if (checkIfNeigbourExist(gameMap)) {
						if (checkMapConnectivity(gameMap)) {
							if (checkCountryCount(gameMap, size)) {
								return true;
							}
							else {
								return false;
							}
						}
						else {
							return false;
						}
					}
					else {
						return false;
					}
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	

	public boolean checkEmptyContinent(Map gameMap){
		if(gameMap.getContinents().isEmpty()){
			return true;
		}
		else{
		for(Continent c:gameMap.getContinents()){
			if(c.getCountriesPresent().isEmpty()){
				gameMap.setErrorMessage("Continent "+c.getContinentName()+" has no country.");
				return true;
			}
		}
	}
		return false;
	}
	

	public boolean checkCountryCount(Map gameMap,int size){
		if(gameMap.listOfCountryNames().size()>=size){
			return true;
		}
		else{ 
			gameMap.setErrorMessage("No. of countries less than "+size+" hence invalid.");
			return false;
		}
	}
	
	/**
	 * Checks if there are multiple continents with same name or not
	 * @param gameMap map file to be validated
	 * @return true if map contains duplicate continent names else false
	 */
	public boolean checkDuplicateContinents(Map gameMap) {
		List<String> continentNames = gameMap.listOfContinentNames();
		HashSet<String> set = new HashSet<String>(continentNames);
		ArrayList<String> result = new ArrayList<String>(set);
		if (!(result.size() == continentNames.size())) {
			gameMap.setErrorMessage("Duplicate Continents present");
			return true;
		}
		return false;
	}
	

	public boolean checkDuplicateCountries(Map gameMap) {
		List<String> countryNames = gameMap.listOfCountryNames();
		HashSet<String> set = new HashSet<String>(countryNames);
		ArrayList<String> result = new ArrayList<String>(set);
		if (!(result.size() == countryNames.size())) {
			gameMap.setErrorMessage("Duplicate Countries present");
			return true;
		}
		return false;
	}
	
	

	public boolean checkDuplicateNeighbours(Map gameMap) {
		for (Continent continent : gameMap.getContinents()) {
			for (Country country : continent.getCountriesPresent()) {
				List<String> neighbours = country.getListOfNeighbours();
				HashSet<String> set = new HashSet<String>(neighbours);
				ArrayList<String> result = new ArrayList<String>(set);
				if (!(result.size() == neighbours.size())) {
					gameMap.setErrorMessage("Duplicate Neighbours present");
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks if there neighbors for any country exists or not
	 * @param gameMap map file to be validated
	 * @return true if map contains neighbors else false
	 */
	public boolean checkIfNeigbourExist(Map gameMap) {
		List<String> list =  gameMap.listOfCountryNames();
		List<String> listOfCountries = new ArrayList<String>();
		for(String name : list) {
			listOfCountries.add(name.toLowerCase());
		}
		for (Continent c : gameMap.getContinents()) {
			for (Country country : c.getCountriesPresent()) {
				for (String neighbour : country.getListOfNeighbours()) {
					if (!listOfCountries.contains(neighbour.toLowerCase())) {
						gameMap.setErrorMessage("Neighbour not part of countries list "+neighbour+" neighbour");
						return false;
					}
				}
			}
		}
		return true;
	}
	

	class Graph {
		private int Value; 
		private ArrayList<Integer> adj[]; 
		

		Graph(int val) {
			this.Value = val;
			adj = new ArrayList[val];
			for (int i = 0; i < val; ++i) {
				adj[i] = new ArrayList();
			}
		}


		void addEdge(int edge1, int edge2) {
			adj[edge1].add(edge2);
		}


		void dfsUtil(int val, Boolean visited[]) {
			visited[val] = true;
			Iterator<Integer> i = adj[val].listIterator();
			while (i.hasNext()) {
				int num;
				num = i.next();
				if (!visited[num]) {
					dfsUtil(num, visited);
				}
			}
		}
		

		Graph getTranspose() {
			Graph graph = new Graph(Value);
			for (int v = 0; v < Value; v++) {
				Iterator<Integer> i = adj[v].listIterator();
				while (i.hasNext()) {
					graph.adj[i.next()].add(v);
				}
			}
			return graph;
		}


		Boolean isStronglyConnected() {
			Boolean visited[] = new Boolean[Value];
			for (int i = 0; i < Value; i++) {
				visited[i] = false;
			}
			dfsUtil(0, visited);

			for (int i = 0; i < Value; i++) {
				if (visited[i] == false) {
					return false;
				}
			}

			Graph graph = getTranspose();

			for (int i = 0; i < Value; i++) {
				visited[i] = false;
			}

			graph.dfsUtil(0, visited);

			for (int i = 0; i < Value; i++) {
				if (visited[i] == false) {
					return false;
				}
			}
			return true;
		}
	}

	boolean checkMapConnectivity(Map gameMap) {
		List<String> list =  gameMap.listOfCountryNames();
		List<String> listOfCountries = new ArrayList<String>();
		for(String name : list) {
			listOfCountries.add(name.toLowerCase());
		}
		int noOfVertices = listOfCountries.size();
		Graph graph = new Graph(noOfVertices);
		for (int i = 0; i < noOfVertices; i++) {
			for (Continent c : gameMap.getContinents()) {
				for (Country country : c.getCountriesPresent()) {
					if (country.getCountryName().equals(gameMap.listOfCountryNames().get(i))) {
						List<String> neighbours = country.getListOfNeighbours();
						for (String current : neighbours) {
							int index = listOfCountries.indexOf(current.toLowerCase());
							graph.addEdge(i, index);
						}
					}
				}
			}
		}
		if (graph.isStronglyConnected()) {
			System.out.println("Yes, Map is strongly connected");
			return true;
		} 
		else {
			System.out.println("No, Map is not strongly connected");
			gameMap.setErrorMessage("Map is not strongly connected");
			return false;
		}
	}
	

	public boolean saveDataIntoFile(Map gameMap, String name) {
				String data = "[Map]\nauthor=Anonymous\n[Continents]\n";
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
					writeData = new PrintWriter(name+".map");
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

