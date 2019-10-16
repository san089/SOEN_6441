
//JUnit 4 test suite
import com.concordia.riskgame.model.Modules.Dice;
import com.concordia.riskgame.tests.StartUpPhaseTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


import com.concordia.riskgame.controller.CommandControllerTest;

@RunWith(Suite.class)
@SuiteClasses({Dice.class})

/**
 * This is the Test Suite class
 */
public class TestSuite {

}