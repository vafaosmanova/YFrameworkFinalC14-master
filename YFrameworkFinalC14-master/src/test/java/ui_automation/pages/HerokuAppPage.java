package ui_automation.pages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui_automation.utilities.Driver;

public class HerokuAppPage {
    private static final Logger LOGGER = LogManager.getLogger(HerokuAppPage.class);

    public HerokuAppPage(){
        PageFactory.initElements(Driver.getInstance().getDriver(), this);
        LOGGER.info("HerokuAppPage is initialized.");
    }

    @FindBy(xpath = "//*[text() = 'File Upload']")
    public WebElement fileUploadLink;

    @FindBy(id = "file-upload")
    public WebElement choseFileButton;

    @FindBy(id = "file-submit")
    public WebElement uploadButton;

    @FindBy(id = "uploaded-files")
    public WebElement uploadedFilesResult;

    @FindBy(xpath = "//*[text() = 'File Download']")
    public WebElement fileDownloadLink;

    @FindBy(xpath = "//*[text() = 'LambdaTest.txt']")
    public WebElement lambdaTestTextFileLink;

}
