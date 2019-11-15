package com.concordia.riskgame.model.Modules.Stratigies;

import com.concordia.riskgame.model.Modules.Strategy;

public class Random implements Strategy {

    private String strategyName = "Random";
    public String ANSI_CYAN = "\u001B[36m";

    public String getColor() {
        return ANSI_CYAN;
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
