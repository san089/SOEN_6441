package com.concordia.riskgame.model.Modules.Stratigies;

import com.concordia.riskgame.model.Modules.Strategy;

public class Cheater implements Strategy {

    private String strategyName = "Cheater";
    public String ANSI_PURPLE = "\u001B[35m";

    public String getColor() {
        return ANSI_PURPLE;
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
