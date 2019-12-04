/**
 *
 * This Module is the controller to perform card exchange functionality in the gameplay.
 *
 * @author Hai Feng
 *
 * @version 2.0
 * @see https://www.ultraboardgames.com/risk/game-rules.php
 *
 */

package com.concordia.riskgame.controller;

import com.concordia.riskgame.model.Modules.Card;
import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.model.Modules.Player;

import java.util.Observable;

/**
 * Class perform card exchange command and notify card exchange view update
 */


public class CardExchangeController extends Observable {
 //   Gameplay gameplay;
    Player currentPlayer;
    int num1, num2, num3;
    /**
     * This method is the constructor of CardExchangeController.
     */
    public CardExchangeController() {
     //   gameplay = Gameplay.getInstance();
        currentPlayer = Gameplay.getInstance().getCurrentPlayer();
        num1 = 0;
        num2 = 0;
        num3 = 0;
    }
    /**
     * This method perform the card exchange, all the exchange numbers have been verified and stored into
     * member variables. After exchanging, notify card view update.
     */
    public void exchange() {

        currentPlayer.addCardExchangeNum();
        int armies = currentPlayer.getArmyCount();
        int reinforcement = currentPlayer.getCardExchangeNum() * 5;
        currentPlayer.setArmyCount(armies + reinforcement);
        System.out.println("You have got " + reinforcement + "armies from this exchange!");
        for (int i = 0; i < num1; i++) {
            currentPlayer.getCardsOwned().remove(Card.INFANTRY);
        }
        for (int i = 0; i < num2; i++) {
            currentPlayer.getCardsOwned().remove(Card.CAVALRY);
        }
        for (int i = 0; i < num3; i++) {
            currentPlayer.getCardsOwned().remove(Card.ARTILLERY);
        }
        setChanged();
        notifyObservers();
    }
    /**
     * This method check the card whether exchanging number input are valid. Only valid number return true.
     * @param n1 Number of Infantry card
     * @param n2 Number of Carvery card
     * @param n3 Number of Artillery card
     * @return valid true, invalid false
     */
    public boolean checkInput(String n1, String n2, String n3) {
        try {
            num1 = Integer.parseInt(n1);
            num2 = Integer.parseInt(n2);
            num3 = Integer.parseInt(n3);
        } catch (NumberFormatException ex) {
            return false;
        }
        if (num1 > Gameplay.getInstance().getCurrentPlayer().getNumOfInfCard() ||
            num2 > Gameplay.getInstance().getCurrentPlayer().getNumOfCavCard() ||
            num3 > Gameplay.getInstance().getCurrentPlayer().getNumOfArtCard()) {
            return false;
        }

        if (num1 ==1 && num2 ==1 && num3 == 1) {
            return true;
        }
        if (num1 == 3 && num2 == 0 && num3 == 0) {
            return true;
        }
        if (num1 == 0 && num2 == 3 && num3 == 0) {
            return true;
        }
        if (num1 == 0 && num2 == 0 && num3 == 3) {
            return true;
        }
        return false;
    }


}
