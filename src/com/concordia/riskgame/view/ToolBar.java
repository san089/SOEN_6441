/**
 *
 * This Module is the view for toolbar that helps in map editing and selection
 *
 * @author Sucheta
 *
 * @version 2.0
 * @see https://www.ultraboardgames.com/risk/game-rules.php
 *
 */

package com.concordia.riskgame.view;

import com.concordia.riskgame.controller.MapEditorController;
import com.concordia.riskgame.model.Modules.Continent;
import com.concordia.riskgame.model.Modules.Country;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;


/**
 * ToolBar with map editor view
 */
public class ToolBar extends JPanel implements Serializable {

    private JButton addContinent;
    private JButton removeContinent;
    private JButton addCountries;
    private JButton removeCountry;
    private JButton save;
    private Continent continent;
    private Country newCountry;



    /**
     * Instantiates a new tool bar.
     *
     * @param mapEditorController the map editor controller
     */
    public ToolBar(MapEditorController mapEditorController) {
        setLayout(new FlowLayout());
        continent = new Continent();
        newCountry = new Country();
        addContinent = new JButton("Add Continent");
        addContinent.addActionListener(mapEditorController);
        removeContinent = new JButton("Remove Continent");
        removeContinent.addActionListener(mapEditorController);
        addCountries = new JButton("Add Countries");
        addCountries.addActionListener(mapEditorController);
        removeCountry = new JButton("Remove Country");
        removeCountry.addActionListener(mapEditorController);
        save = new JButton("Save");
        save.addActionListener(mapEditorController);

        setBorder(BorderFactory.createEtchedBorder());

        add(addContinent);
        add(addCountries);
        add(removeContinent);
        add(removeCountry);
        add(save);
    }
}