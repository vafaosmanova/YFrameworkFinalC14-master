package ui_automation.pages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui_automation.utilities.Driver;

public class MealBCreateExpenseFormPage {
    private static final Logger LOGGER = LogManager.getLogger(MealBCreateExpenseFormPage.class);

    public MealBCreateExpenseFormPage(){
        PageFactory.initElements(Driver.getInstance().getDriver(), this);
        LOGGER.info("MealBCreateExpenseFormPage is initialized.");
    }

    @FindBy(xpath = "//*[text() = 'Create Other expense']")
    public WebElement formModalHeader;
    @FindBy(id = "ExpenseDateTime")
    public WebElement expenseDateInputBox;
    @FindBy(css = "[class=picker__button--today][aria-controls=ExpenseDateTime]")
    public WebElement todaysDateButton;
    @FindBy(id = "Amount")
    public WebElement expenseAmountInputBox;
    @FindBy(id = "name")
    public WebElement otherExpenseNameInputBox;
    @FindBy(css = "[data-id=OtherExpenseNameId]")
    public WebElement expenseNameDropdown;
    @FindBy(xpath = "//li[@data-original-index='4']//*[text() = 'Other']")
    public WebElement otherExpenseNameOption;
    @FindBy(xpath = "//*[text() = 'Save']")
    public WebElement saveButton;

}
