# Multiplayer Risk Board Game
## Overview 
A Risk game consists of a connected graph map representing a world map, where each node is a country and each edge represents adjacency between countries. Two or more players can play by placing armies on countries they own, from which they can attack adjacent countries to conquer them. The objective of the game is to conquer all countries on the map.



## Architecture Diagram
![Architecture Diagram](https://github.com/san089/SOEN_6441/blob/master/Documents/ArchitectureDesign_Build2.png)
![MVC Diagrame](http://assets.processon.com/chart_image/5da4e016e4b0ea86c2afe75d.png)




# Game Commands

## Map Editor Command

    editcontinent -add continentname continentvalue -remove continentname

    editcountry -add countryname continentname -remove countryname

    editneighbor -add countryname neighborcountryname -remove countryname neighborcountryname

    showmap (show all continents and countries and their neighbors)

    savemap filename

    editmap filename

    validatemap

## Game Play   

     showmap

## Startup Phase

    loadmap filename

    gameplayer -add playername -remove playername

    populatecountries

    placearmy countryname (by each player until all players have placed all their armies)

    placeall (automatically randomly place all remaining unplaced armies for all players)


## Reinforcement Phase


    reinforce countryname num (until all reinforcement armies allocated to the player have been placed)

    exchangecards num num num
    exchangecards –none

## Attack Phase

    attack fromcountry tocountry numdice
    attack fromcountry tocountry -allout
    attack -noattack

do a single attack from **countrynamefrom** to **countynameto** using **numdice** number of dice. If **–allout** is specified, attack until no attack is possible using maximum number of dice to attack/defend. If **–noattack** is specified, stop attacking, ending the attack phase.)

    defend numdice (after attack is declared, defender chooses number of dice to defend with)

    attackmove num (After a country has been conquered, move num number of armies to this country from the attacking country.)


## Fortification Phase

    fortify fromcountry tocountry num
    fortify –none
    (move num number of armies from fromcountry to tocountry. If –none is specified, choose to not do a move during the fortification phase)
    
## Tournament Command

    tournament -M D:\SOEN_6441\Maps\Valid_Maps\BigValidMap.map|D:\SOEN_6441\Maps\Valid_Maps\BigValidMap.map|D:\SOEN_6441\Maps\Valid_Maps\BigValidMap.map -P Random|Benevolent|Aggressive -G 4 -D 10

