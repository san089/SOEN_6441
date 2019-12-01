package com.concordia.riskgame.view;

import com.concordia.riskgame.controller.CommandController;
import com.concordia.riskgame.model.Modules.Card;
import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.model.Modules.Player;
import com.concordia.riskgame.utilities.Phases;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.Assert.*;

public class CardExchangeViewTest {

   CardExchangeView cardExchangeView;
   Player currentPlayer;
 //  Gameplay gameplay = Gameplay.getInstance();
   Scanner scanner;
   String command;


    @Before
    public void setUp() throws Exception {
        scanner = new Scanner(System.in);
        currentPlayer = new Player(3, "test");
        Card newCard = Card.getCard(Card.class);
        currentPlayer.addNewCard(newCard);
        newCard = Card.getCard(Card.class);
        currentPlayer.addNewCard(newCard);
        newCard = Card.getCard(Card.class);
        currentPlayer.addNewCard(newCard);
        newCard = Card.getCard(Card.class);
        currentPlayer.addNewCard(newCard);
        newCard = Card.getCard(Card.class);
        currentPlayer.addNewCard(newCard);
        newCard = Card.getCard(Card.class);
        currentPlayer.addNewCard(newCard);
        newCard = Card.getCard(Card.class);
        currentPlayer.addNewCard(newCard);
        newCard = Card.getCard(Card.class);
        currentPlayer.addNewCard(newCard);
        newCard = Card.getCard(Card.class);
        currentPlayer.addNewCard(newCard);
        newCard = Card.getCard(Card.class);
        currentPlayer.addNewCard(newCard);
        Gameplay.getInstance().setCurrentPlayer(currentPlayer);
        Gameplay.getInstance().setCurrentPhase(Phases.Reinforcement);
        Gameplay.getInstance().addObserver(CardExchangeView.getInstance());

    }

    @Test
    public void update() throws IOException {
        command = "exchangecards 1 1 1";
        CommandController.parseCommand(command);

    }

    @After
    public void tearDown() throws Exception {
    }

}