package ui_automation.pages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui_automation.utilities.Driver;

public class MealBDashboardPage {
    private static final Logger LOGGER = LogManager.getLogger(MealBDashboardPage.class);

    public MealBDashboardPage(){
        PageFactory.initElements(Driver.getInstance().getDriver(), this);
        LOGGER.info("MealBCreateExpenseFormPage is initialized.");
    }

    @FindBy(xpath = "//*[contains(text(), 'Expenses')]")
    public WebElement expensesTab;

}
