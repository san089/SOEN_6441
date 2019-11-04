# SOEN_6441
SOEN 6441 Risk Game



## Architecture Diagram
![Architecture Diagram](https://github.com/san089/SOEN_6441/blob/master/Documents/ArchitectureDesign.JPG)




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
