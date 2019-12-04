/**
 *
 * This Module rolls the dice for gameplay.
 *
 * @author Hai Feng
 * @version 1.0
 * @see https://www.ultraboardgames.com/risk/game-rules.php
 *
 */

package com.concordia.riskgame.model.Modules;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/**
 * Dice class is used to roll, provide the result of attack
 * @since Build I
 */


public class Dice implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int NUMBER_CEIL = 6;

    /**
     * Roll one dice.
     *
     * @return the int
     */
    public int rollOneDice(){
        int r = new Random().nextInt(NUMBER_CEIL);
        return r + 1;
    }

    /**
     * Rolling n dices.
     *
     * @param n , number of dice are used: n
     * @return dice result as an array which is descending, for comparing battle result
     */
    public ArrayList<Integer> rollNDice(int n){
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++){
            result.add(rollOneDice());
        }
        Collections.sort(result);
        Collections.reverse(result);
        return result;

    }
}
