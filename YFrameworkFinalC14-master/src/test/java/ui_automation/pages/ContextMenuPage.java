package ui_automation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui_automation.utilities.Driver;

import java.util.List;
import java.util.NoSuchElementException;

public class ContextMenuPage {

    public ContextMenuPage(){
        PageFactory.initElements(Driver.getInstance().getDriver(), this);
    }

    @FindBy(xpath = "//span[text() = 'right click me']")
    public WebElement rightClickMeButton;

    @FindBy(css = ".context-menu-item")
    public List<WebElement> contextMenuOptions;

    public WebElement getContextOptionByName(String optionName){
        for (WebElement option: contextMenuOptions){
            if(option.getText().equalsIgnoreCase(optionName)){
                return option;
            }
        }
        throw new NoSuchElementException("There is no such option with name " + optionName);
    }


}
