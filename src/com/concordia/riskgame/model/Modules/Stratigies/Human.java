package com.concordia.riskgame.model.Modules.Stratigies;

import java.io.Serializable;

import com.concordia.riskgame.model.Modules.Strategy;

public class Human implements Strategy,Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String strategyName = "Human";

    public String getStrategyName() {
        return strategyName;
    }
}
