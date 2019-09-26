/*
 * 
 */
package com.concordia.riskgame.model.Modules;

import java.util.ArrayList;
import java.util.LinkedHashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class Continent.
 */
public class Continent {

	/** The continent name. */
	private String continentName;
	
	/** The continent owner index. */
	private int continentOwnerIndex;
	
	/** The control value. */
	private int controlValue;
	
	/** The containing countries. */
	private LinkedHashMap<String,Country> containingCountries;
	
	
	
	/**
	 * Instantiates a new continent.
	 *
	 * @param continentName the continent name
	 * @param controlValue the control value
	 */
	public Continent(String continentName, int controlValue) {
		this.continentName = continentName;
		this.continentOwnerIndex = -1;
		this.controlValue = controlValue;
		this.containingCountries=new LinkedHashMap<String,Country>();
	}



	/**
	 * Gets the continent name.
	 *
	 * @return the continent name
	 */
	public String getContinentName() {
		return continentName;
	}



	/**
	 * Sets the continent name.
	 *
	 * @param continentName the new continent name
	 */
	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}



	/**
	 * Gets the continent owner index.
	 *
	 * @return the continent owner index
	 */
	public int getContinentOwnerIndex() {
		return continentOwnerIndex;
	}



	/**
	 * Sets the continent owner index.
	 *
	 * @param continentOwnerIndex the new continent owner index
	 */
	public void setContinentOwnerIndex(int continentOwnerIndex) {
		this.continentOwnerIndex = continentOwnerIndex;
	}



	/**
	 * Gets the control value.
	 *
	 * @return the control value
	 */
	public int getControlValue() {
		return controlValue;
	}



	/**
	 * Sets the control value.
	 *
	 * @param controlValue the new control value
	 */
	public void setControlValue(int controlValue) {
		this.controlValue = controlValue;
	}



	/**
	 * Gets the containing countries.
	 *
	 * @return the containing countries
	 */
	public LinkedHashMap<String, Country> getContainingCountries() {
		return containingCountries;
	}



	/**
	 * Sets the containing countries.
	 *
	 * @param containingCountries the containing countries
	 */
	public void setContainingCountries(LinkedHashMap<String, Country> containingCountries) {
		this.containingCountries = containingCountries;
	}
	
	
	
	
	
	
	
	
}
