package ui_automation.pages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui_automation.utilities.Driver;

public class MealBLandingPage {

    private static final Logger LOGGER = LogManager.getLogger(MealBLandingPage.class);

    public MealBLandingPage(){
        PageFactory.initElements(Driver.getInstance().getDriver(), this);
        LOGGER.info("MealBLandingPage is initialized.");
    }

    @FindBy(xpath = "//*[text() = 'SIGN IN']")
    public WebElement signInButton;

}
