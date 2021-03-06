/**
 *
 * Test Class for card exchange controller
 *
 * @author Sanchit, Moshood.
 *
 * @version 2.0
 * @see https://www.ultraboardgames.com/risk/game-rules.php
 *
 */

package com.concordia.riskgame.controller;

import com.concordia.riskgame.model.Modules.Card;
import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.model.Modules.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A Test class to test Card Exchange Controller.
 */
public class CardExchangeControllerTest {

    String path = System.getProperty("user.dir");
    String mapPath = path + "\\Maps\\Valid_Maps\\SmallValidMap.map";
    Scanner sc = new Scanner(System.in);
    Gameplay gameplay;
    CardExchangeController cc = new CardExchangeController();

    @Before
    public void setUp() {
        try {
            CommandController.parseCommand("loadmap "+ mapPath);
            CommandController.parseCommand("gameplayer -add Sanchit -add Sucheta");
            CommandController.parseCommand("populatecountries");
            CommandController.parseCommand("showphases");
        }
        catch(Exception ex)
        {
            System.out.println("Exception while parsing commands");
        }

    }

    @Test
    public void exchange() {
        try {
            CommandController.parseCommand("placeall");
            gameplay = Gameplay.getInstance();
            gameplay.getCurrentPlayer().setCardsOwned(new ArrayList<>(Arrays.asList(Card.CAVALRY,Card.CAVALRY, Card.CAVALRY)));
            cc.checkInput("0","3","0");
            CommandController.parseCommand("exchangecards 0 3 0");
        }
        catch (Exception e){
            System.out.println("Exception while parsing commands.");
        }
        assertTrue(gameplay.getCurrentPlayer().getArmyCount() >= 5);

    }


    @Test
    public void checkInput() {
        try {
            CommandController.parseCommand("placeall");
            gameplay = Gameplay.getInstance();
            gameplay.getCurrentPlayer().setCardsOwned(new ArrayList<>(Arrays.asList(Card.CAVALRY,Card.CAVALRY, Card.CAVALRY)));
            cc.num1 = 0;
            cc.num2 = 3;
            cc.num3 = 0;
            assertFalse(cc.checkInput("0","4","0"));
        }
        catch (Exception e){
            System.out.println("Exception while parsing commands.");
        }
    }


}