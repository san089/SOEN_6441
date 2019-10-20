#### What I have done:
1. Add three commands (temporily):
- attack fromcountry tocountry numofattackdice numofdefensivedice;
- attack fromcountry tocountry auto;
- attack none;
2. Add "AttackPhaseController".
3. CommandController:
- modify placeAll(). After placeall command, game will go into game play. So add initial statement under placeall();
- modify placeArmy(). After every place army, check if all the reinforcement are 0, if so, do the same thing as above, and change to reinforcement phase.
- modify reinfoce function. Call static method in ReinforcementController
- add attack().
- add fortify().
4. Add ReinforcementController
5. Add AttackController
6. GamePlay.java
- add data filed "private ArrayList<Player> removedPlayer", after each attack, if win, check if defensive player is out, if so, add this player to this list. At the end of each turn, next player in queue pop out, if this list contains this player, then kick it out, pop next.
- add roundRobinPlayer(); After game play initialized, current player changes according to queue.
- modify assignReinforcementArmies(). Because we can know how many reinforcement player will get only after previous player's end, so this function only assgin reinforcement to current player. (Need to add card exchange army count)
7. Player.java
- add data filed "cardFlag" and reset card flag method. At each attack phase, once conquered one country, set current player's card flag, means this player can get a card at the end of his turn. after give him a card, reset this flag.

- add data filed "cardExchangeIndex", according to game rules, this index is used to calculate how many armies will get from card exchanging.

