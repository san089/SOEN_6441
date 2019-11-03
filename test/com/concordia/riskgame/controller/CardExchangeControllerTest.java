package com.concordia.riskgame.controller;

import com.concordia.riskgame.model.Modules.Card;
import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.model.Modules.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CardExchangeControllerTest {

    private Gameplay gameplay;
    private Player currentPlayer;
    private int num1, num2, num3;
    private int cardExchangeIndex;

    @Before
    public void setUp() {
        gameplay = Gameplay.getInstance();
        currentPlayer = gameplay.getCurrentPlayer();
        cardExchangeIndex++;
        num1 = 0;
        num2 = 0;
        num3 = 0;

    }

    @Test
    public void exchange() {
        int currentPlayerIndex = 1;
        int currentPlayerArmies = 2;
        int reinforcement = cardExchangeIndex * 5;
        int armyCount = (currentPlayerArmies + reinforcement);
        Assert.assertEquals(7, armyCount);
        for (int i = 0; i < num1; i++) {
            assertTrue(currentPlayer.getCardsOwned().remove(Card.INFANTRY));
        }
        for (int i = 0; i < num2; i++) {
            assertTrue(currentPlayer.getCardsOwned().remove(Card.CAVALRY));
        }
        for (int i = 0; i < num3; i++) {
            assertTrue(currentPlayer.getCardsOwned().remove(Card.ARTILLERY));
        }

    }

    @Test
    public void checkInput() {
        for (int i = 0; i < num3; i++) {
            assertTrue(currentPlayer.getCardsOwned().contains(Card.ARTILLERY));
            assertTrue(currentPlayer.getCardsOwned().remove(Card.CAVALRY));
            assertTrue(currentPlayer.getCardsOwned().remove(Card.INFANTRY));
        }

    }

    @Test
    public void verifyNumber() {

    }
}