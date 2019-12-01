package com.concordia.riskgame.utilities;

import com.concordia.riskgame.model.Modules.Map;

public class ConquestMapAdapter extends DominationMapTools {
	
	private ConquestMapTools c_map;

	public ConquestMapAdapter(ConquestMapTools c_map) {
		super();
		this.c_map = c_map;
	}
	
	@Override
	public boolean parseAndValidateMap(Map gameMap, int size) {
		if(Constants.mapType.equalsIgnoreCase("conquest"))
			return c_map.parseAndValidateMap(gameMap, size);
		else
			return super.parseAndValidateMap(gameMap, size);
	}
	
	
	@Override
	public boolean saveDataIntoFile(Map gameMap, String name) {
		if(Constants.mapType.equalsIgnoreCase("conquest"))
			return c_map.saveDataIntoFile(gameMap, name);
		else
			return super.saveDataIntoFile(gameMap, name);
	}
	

}
