package com.concordia.riskgame.model.Modules;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Continent {

	private String continentName;
	private int continentOwnerIndex;
	private int controlValue;
	private LinkedHashMap<String,Country> containingCountries;
	
	
	
	public Continent(String continentName, int controlValue) {
		this.continentName = continentName;
		this.continentOwnerIndex = -1;
		this.controlValue = controlValue;
		this.containingCountries=new LinkedHashMap<String,Country>();
	}



	public String getContinentName() {
		return continentName;
	}



	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}



	public int getContinentOwnerIndex() {
		return continentOwnerIndex;
	}



	public void setContinentOwnerIndex(int continentOwnerIndex) {
		this.continentOwnerIndex = continentOwnerIndex;
	}



	public int getControlValue() {
		return controlValue;
	}



	public void setControlValue(int controlValue) {
		this.controlValue = controlValue;
	}



	public LinkedHashMap<String, Country> getContainingCountries() {
		return containingCountries;
	}



	public void setContainingCountries(LinkedHashMap<String, Country> containingCountries) {
		this.containingCountries = containingCountries;
	}
	
	
	
	
	
	
	
	
}
