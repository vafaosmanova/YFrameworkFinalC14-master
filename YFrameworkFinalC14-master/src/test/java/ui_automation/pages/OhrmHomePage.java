package ui_automation.pages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui_automation.utilities.Driver;
import ui_automation.utilities.WaitHelper;

import java.util.List;

public class OhrmHomePage {
    private static final Logger LOGGER = LogManager.getLogger(OhrmHomePage.class);
    public OhrmHomePage(){
        PageFactory.initElements(Driver.getInstance().getDriver(), this);
        LOGGER.info("OhrmHomePage is initialized.");
    }
    @FindBy(id = "welcome")
    public WebElement welcomeMessage;

    @FindBy(xpath = "//div[@class='menu']//b")
    public List<WebElement> tabs;

    @FindBy(id  = "menu_pim_viewPimModule")
    public WebElement pimTab;

    @FindBy(id = "menu_admin_UserManagement")
    public WebElement userManagementSubTab;

    @FindBy(id = "menu_admin_Job")
    public WebElement jobSubTab;

    @FindBy(id = "menu_admin_viewSystemUsers")
    public WebElement usersSubTab;

    @FindBy(css = ".head>h1")
    public WebElement systemUsersPageHeader;


    public void clickOnTabByName(String tabName){
        LOGGER.info("Attempting to click on the tab: " + tabName);
        WebElement tabToClick = getTabWebElementByName(tabName);
        WaitHelper.waitForClickablility(tabToClick, 10);
        tabToClick.click();
        LOGGER.info("Clicked on the tab: " + tabName);
    }

    public WebElement getTabWebElementByName(String tabName){
        LOGGER.info("Searching for tab with name: " + tabName);
        WaitHelper.waitForVisibility(tabs, 10);
        for(WebElement tab : tabs){
            WaitHelper.waitForTextToBePresentInElement(tab, tabName, 10);
            if(tabName.equalsIgnoreCase(tab.getText())){
                LOGGER.info("Found tab with name: " + tabName);
                return tab;
            }
        }
        LOGGER.error("No tab found with name: " + tabName);
        throw new NoSuchElementException("No such tab name as '" + tabName + "'");
    }


    // Locate all tabs as a list
    // This will give us the ability to access the list whenever we need at once
    @FindBy(css = ".menu li>a.firstLevelMenu")
    public List<WebElement> allTabs;

    public WebElement getTabByName(String tabName){
        for(WebElement tab : allTabs){
            if(tab.getText().equalsIgnoreCase(tabName)){
                return tab;
            }
        }
        throw new NoSuchElementException("The tab with such content does not exist " + tabName);
    }

}
