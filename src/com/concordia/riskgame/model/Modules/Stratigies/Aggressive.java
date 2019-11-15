package com.concordia.riskgame.model.Modules.Stratigies;

import com.concordia.riskgame.model.Modules.Strategy;

public class Aggressive implements Strategy {

    private String strategyName = "Aggressive";
    public String ANSI_BLUE = "\u001B[34m";

    public String getColor() {
        return ANSI_BLUE;
    }

    @Override
    public String getStrategyName() {
        return strategyName;
    }

    public void doCardExchange(){

    }

    public void doReinforcement(){

    }

    public void doAttack(){

    }

    public void doFortification() {

    }
}
