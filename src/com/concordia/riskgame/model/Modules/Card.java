/*
 * 
 */
package com.concordia.riskgame.model.Modules;


import java.util.Random;

public enum Card {
    INFANTRY("Infantry"),
    CAVALRY("Cavalry"),
    ARTILLERY("Artillery");

    private final String cardType;
    //Constructor
    Card(String cardType){
        this.cardType = cardType;
    }

    /**
     * Every time player conquer one country, he can get a random level card by this method
     * getEnumConstants function can get the list of cards.
     * @param cardObject
     * @return one random kind of cards
     */
    public static Card getCard(Class<Card> cardObject) {
        return cardObject.getEnumConstants()[new Random().nextInt(cardObject.getEnumConstants().length)];
    }

}
