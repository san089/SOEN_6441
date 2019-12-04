package com.concordia.riskgame.utilities;

import com.concordia.riskgame.model.Modules.Map;

/*
 * Adapter class to for conquest map implementation.
 * */

/**
 * The Class ConquestMapAdapter.
 */
public class ConquestMapAdapter extends DominationMapTools {
	
	private ConquestMapTools c_map;

	/**
	 * Instantiates a new conquest map adapter.
	 *
	 * @param c_map the c map
	 */
	public ConquestMapAdapter(ConquestMapTools c_map) {
		super();
		this.c_map = c_map;
	}
	
	/* (non-Javadoc)
	 * @see com.concordia.riskgame.utilities.DominationMapTools#parseAndValidateMap(com.concordia.riskgame.model.Modules.Map, int)
	 */
	@Override
	public boolean parseAndValidateMap(Map gameMap, int size) {
		if(Constants.mapType.equalsIgnoreCase("conquest"))
			return c_map.parseAndValidateMap(gameMap, size);
		else
			return super.parseAndValidateMap(gameMap, size);
	}
	
	
	/* (non-Javadoc)
	 * @see com.concordia.riskgame.utilities.DominationMapTools#saveDataIntoFile(com.concordia.riskgame.model.Modules.Map, java.lang.String)
	 */
	@Override
	public boolean saveDataIntoFile(Map gameMap, String name) {
		if(Constants.mapType.equalsIgnoreCase("conquest"))
			return c_map.saveDataIntoFile(gameMap, name);
		else
			return super.saveDataIntoFile(gameMap, name);
	}
	

}
