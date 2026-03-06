package ui_automation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui_automation.utilities.Driver;

import java.util.List;
import java.util.NoSuchElementException;

public class DhtmlGoodiesPage {

    public DhtmlGoodiesPage(){
        PageFactory.initElements(Driver.getInstance().getDriver(), this);
    }

    @FindBy(xpath = "//*[@class = 'dragableBox' and starts-with(@id, 'box')]")
    public List<WebElement> capitals;

    @FindBy(css = ".dragableBoxRight")
    public List<WebElement> countries;

    public WebElement getCapitalElementByName(String capitalName){
        for (WebElement capital: capitals){
            if(capital.getText().equalsIgnoreCase(capitalName)){
                return capital;
            }
        }
        throw new NoSuchElementException("There is no such element with name " + capitalName);
    }

    public WebElement getCountryElementByName(String countryName){
        for (WebElement country: countries){
            if(country.getText().equalsIgnoreCase(countryName)){
                return country;
            }
        }
        throw new NoSuchElementException("There is no such element with name " + countryName);
    }

}
