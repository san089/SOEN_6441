/**
 *
 * The Strategy interface for strategy pattern implementation
 * @author Sanchit Kumar
 * @version 2.0
 *
 *
 */
package com.concordia.riskgame.model.Modules;

public interface Strategy {

    default String getColor(){
        return "";
    }

    default void doCardExchange(){

    }

    default void doReinforcement() {
    }

    default void doAttack() {
    }

    default void doFortification() {
    }

    default String getStrategyName(){
        return "";
    }

}
