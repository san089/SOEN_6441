/**
 *
 * Test Class for Dice.
 *
 * @author Sanchit
 *
 * @version 2.0
 * @see https://www.ultraboardgames.com/risk/game-rules.php
 *
 */


package com.concordia.riskgame.model.Modules;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

// TODO: Auto-generated Javadoc
public class DiceTest {
    private Dice randomNo;
    private int r;
    private int n;


    /**
     * Sets the up.
     */
    @Before
    public void setUp()  {
        randomNo= new Dice();
        r = new Random().nextInt(6);
        n=2;
    }

    /**
     * Roll N dice.
     */
    @Test
    public void rollNDice() {
        ArrayList<Integer> result = randomNo.rollNDice(3);
        int max = Collections.max(result);
        int min = Collections.min(result);

        Assert.assertTrue(max<=6&&min>=1);
    }

    @Test
    public void rollOneDice() {
        int num = randomNo.rollOneDice();
        Assert.assertTrue(num<=6&&num>=1);
    }

}
