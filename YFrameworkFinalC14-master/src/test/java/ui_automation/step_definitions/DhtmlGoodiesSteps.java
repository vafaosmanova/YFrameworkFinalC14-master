package ui_automation.step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import ui_automation.constants.Constants;
import ui_automation.pages.DhtmlGoodiesPage;
import ui_automation.utilities.ConfigurationReader;
import ui_automation.utilities.Driver;
import ui_automation.utilities.WaitHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DhtmlGoodiesSteps {

    private WebElement sourceCapital = null;
    private Actions actions = new Actions(Driver.getInstance().getDriver());
    private DhtmlGoodiesPage dhtmlGoodiesPage = new DhtmlGoodiesPage();
    private List<String> capitalNames = new ArrayList<>();

    @Given("user navigates to dhtmlgoodies countries page")
    public void user_navigates_to_dhtmlgoodies_countries_page() {
        String url = ConfigurationReader.getProperty(Constants.UI_CONFIG,"dhtmlgoodies.url");
        Driver.getInstance().getDriver().get(url);
    }

    @When("user moves capitals to corresponding countries")
    public void user_moves_capitals_to_corresponding_countries(List<Map<String, String>> table) {
        for(Map<String, String> pair: table){
            String capital = pair.get("Capital");
            String country = pair.get("Country");
            sourceCapital = dhtmlGoodiesPage.getCapitalElementByName(capital);
            WebElement targetCountry = dhtmlGoodiesPage.getCountryElementByName(country);
            actions.dragAndDrop(sourceCapital, targetCountry).perform();
            capitalNames.add(capital);
            WaitHelper.wait(1);
        }
    }

    @Then("user can see capital box is green")
    public void user_can_see_capital_box_is_green() {
        String expectedColor = "rgba(0, 255, 0, 1)";
        for(String capitalName: capitalNames){
            String actualColor = dhtmlGoodiesPage.getCapitalElementByName(capitalName).getCssValue("background-color");
            Assert.assertEquals("The colors verification failed", expectedColor, actualColor);
        }
    }


}
