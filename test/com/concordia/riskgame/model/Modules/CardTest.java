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