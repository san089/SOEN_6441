/**
 *
 * This Module contains the high level constants used in the entire application
 *
 * @author Sucheta.
 *
 * @version 1.0
 * @see https://www.ultraboardgames.com/risk/game-rules.php
 *
 */
package com.concordia.riskgame.utilities;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * Class containing all the constants used in project
 */
public class Constants {

    public static final int WIDTH = 640;
    public static final int HEIGHT = WIDTH / 12 * 9;
    public static final int MAP_EDITOR_WIDTH = 1200;
    public static final int MAP_EDITOR_HEIGHT = MAP_EDITOR_WIDTH / 12 * 10;
    public static final int COMMON_VIEW_WIDTH = 800;
    public static final int COMMON_VIEW_HEIGHT = COMMON_VIEW_WIDTH / 12 * 10;
    public static final Border blackline = BorderFactory.createLineBorder(Color.black);     
    public static final String mapLocation=System.getProperty("user.dir")+"\\Maps\\Valid_Maps\\";
    public static final String mapType="Domination";
    public static final String saveLocation="./Saved_Games/";
}
