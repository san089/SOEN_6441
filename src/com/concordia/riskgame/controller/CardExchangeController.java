package com.concordia.riskgame.controller;

import com.concordia.riskgame.model.Modules.Card;
import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.model.Modules.Player;

public class CardExchangeController {
    Gameplay gameplay;
    Player currentPlayer;
    int num1, num2, num3;

    public CardExchangeController() {
        gameplay = Gameplay.getInstance();
        currentPlayer = gameplay.getCurrentPlayer();
        num1 = 0;
        num2 = 0;
        num3 = 0;
    }

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
    }

    public boolean checkInput(String n1, String n2, String n3) {
        if(!verifyNumber(n1, n2, n3)) {
            return false;
        }
        if (num1 > gameplay.getCurrentPlayer().getNumOfInfCard() ||
            num2 > gameplay.getCurrentPlayer().getNumOfCavCard() ||
            num3 > gameplay.getCurrentPlayer().getNumOfArtCard()) {
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

    public boolean verifyNumber(String n1, String n2, String n3) {
        if (!n1.equals("")) {
            try {
                num1 = Integer.parseInt(n1);
            }catch (NumberFormatException ex) {
                return false;
            }
        }
        if (!n2.equals("")) {
            try {
                num2 = Integer.parseInt(n2);
            }catch (NumberFormatException ex) {
                return false;
            }
        }
        if (!n3.equals("")) {
            try {
                num3 = Integer.parseInt(n3);
            }catch (NumberFormatException ex) {
                return false;
            }
        }
        return true;
    }


}
