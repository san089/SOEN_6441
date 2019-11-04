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


#### Need to be done:
1. Exception handling. : for example, if command is short than expected, there will be index outbound.
2. Card exchange strategy (before each turn, add exchanged army to reinforcement)
3. Add comments for each class and method

##### Oct 27:
How to check the phases game:

commands:
reinforce countryName num
attack fromcountry tocountry numdice
defend numdice
attack fromcountry tocountry -allout
attack -noattack
attackmove num
fortify fromCountry toCountry num
fortify -none

After populate all countries:
- 'placeall' or initial reinforcement are used up
- game goes into reinforce phase automatically, printing out phase change message and how many reinforcement current player can use
- 'reinforce countryName num'
- after all reinforcement are used up, game goes into attack phase automatically, printing out all attacks player can do
- 'attack fromCountry toCountry numDice' or 'attack fromCountry toCountry -allout'
- if above is using 'attack fromCountry toCountry numDice' , then input 'defend numDice'.
- printing out attack outcome
- if conqured, input 'attackmove num', num must be at least 1, and less than attack country have.
- all other available attacks. If no available attack, goes into fortification automatically.
- 'attack -noattack' goes into fortification.
- 'fortify fromCountry toCoutnry numArmy' or 'fortify -none'
- popup card exchange view. Input the number of card you're going to use. If not suitable, click exit.
- print how many reinforcement you have.
- continue game...
#### Nov 3
Issues:
1, Gameplay.java
assignReinforcementArmies() method: this method only revoke when one player start his turn start, depend on how many countries, continent he has, so we cannot assgin armies to all player in this method.
2, After reinforce army to countries, cannot see the army number change in player view.
3, Everytime obersers are triggered, there are many message in command prompt.
4, StartUpPhaseView, update method, after Gameplay notify obersers, this update method occur error.
