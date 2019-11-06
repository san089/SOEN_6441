/**
 *
 * Test Class for cards.
 *
 * @author Sanchit
 *
 * @version 2.0
 * @see https://www.ultraboardgames.com/risk/game-rules.php
 *
 */

package com.concordia.riskgame.model.Modules;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CardTest {

    @Test
    public void testIfEnum(){
        assertEquals("ARTILLERY", Card.ARTILLERY.name());
    }

    @Test
    public void getCard() {
        
    }
}