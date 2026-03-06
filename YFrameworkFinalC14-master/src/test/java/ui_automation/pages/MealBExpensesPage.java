package ui_automation.pages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui_automation.utilities.Driver;

public class MealBExpensesPage {
    private static final Logger LOGGER = LogManager.getLogger(MealBExpensesPage.class);

    public MealBExpensesPage(){
        PageFactory.initElements(Driver.getInstance().getDriver(), this);
        LOGGER.info("MealBExpensesPage is initialized.");
    }

    @FindBy(xpath = "//span//button[contains(text(), 'Add expense')]")
    public WebElement addExpenseDropdown;
    @FindBy(xpath = "//div[@class=\"panel panel-flat\"]//a[text() = 'Other']")
    public WebElement otherExpenseOption;


}
