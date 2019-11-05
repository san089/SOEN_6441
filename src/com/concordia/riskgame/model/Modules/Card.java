/*
 * 
 */
package com.concordia.riskgame.model.Modules;


import java.util.Random;

// TODO: Auto-generated Javadoc
public enum Card {
    INFANTRY("Infantry"),
    CAVALRY("Cavalry"),
    ARTILLERY("Artillery");

    private final String cardType;
    private static int numOfCards = 0;
    
    /**
     * Instantiates a new card.
     *
     * @param cardType the card type
     */
    //Constructor
    Card(String cardType){
        this.cardType = cardType;
    }

    /**
     * Every time player conquer one country, he can get a random level card by this method
     * getEnumConstants function can get the list of cards.
     *
     * @param cardObject the card object
     * @return one random kind of cards
     */
    public static Card getCard(Class<Card> cardObject) {
        numOfCards++;
        return cardObject.getEnumConstants()[new Random().nextInt(cardObject.getEnumConstants().length)];
    }
    public static int getNumOfCards() {
        return numOfCards;
    }

}
