
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({com.concordia.riskgame.model.Modules.DiceTest.class,com.concordia.riskgame.model.Modules.GameplayTest.class,
	com.concordia.riskgame.controller.CommandControllerTest.class,
	com.concordia.riskgame.utilities.MapToolsTest.class})

/**
 * This is the Test Suite class
 */
public class TestSuite {

}