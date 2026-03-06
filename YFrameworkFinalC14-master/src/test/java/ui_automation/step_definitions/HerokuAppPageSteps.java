package ui_automation.step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import ui_automation.constants.Constants;
import ui_automation.pages.HerokuAppPage;
import ui_automation.utilities.ConfigurationReader;
import ui_automation.utilities.Driver;
import ui_automation.utilities.FileHelper;
import ui_automation.utilities.WaitHelper;

import java.io.File;
import java.nio.file.Paths;

public class HerokuAppPageSteps {

    private String uploadFileName;
    private String downloadFileName;
    private HerokuAppPage herokuAppPage = new HerokuAppPage();
    private final static Logger logger = LogManager.getLogger(HerokuAppPage.class);

    // ==================== UPLOAD ========================== //
    @Given("user navigates to heroku app")
    public void user_navigates_to_heroku_app() {
        String url = ConfigurationReader.getProperty(Constants.UI_CONFIG, Constants.HEROKU_APP_URL);
        Driver.getInstance().getDriver().get(url);
        logger.info("User navigated to " + url);
    }

    @Given("clicks on file upload page")
    public void user_clicks_on_file_upload_page() {
        herokuAppPage.fileUploadLink.click();
        logger.info("User successfully clicked on file upload page");
        WaitHelper.wait(1);
    }

    @When("user uploads a file called {string}")
    public void user_uploads_a_file_called(String fileName) {

        this.uploadFileName = fileName;
        // Path of the file
        // When you work with files and want to upload them you will need the whole path of it
        // 1. This path is not reliable - it will work only on my PC, if pushing this code to GitHub you will get an error when running the same scenario
        // To fix this issue we will utilize the Java System.getProperty() method
         String basePath = System.getProperty(Constants.USER_DIRECTORY);
         logger.info("user.dir: " + basePath);

        // String filePath = basePath + "/src/test/resources/test_data/person.png";
        // Path for widows - \src\test\resources
        // \n, \t, \p, \", \\ - sequence chars
        // backslash in java is used to mark the following char, chars as special
        String relativePath = "src/test/resources/test_data/person.png";
        String filePath = new File(relativePath).getAbsolutePath();
        // We can make java identify the file separator dynamically
        // Option 1
        // String filePath = basePath + File.separator + "src" +File.separator+ "test" +File.separator+ "resources" +File.separator+ "test_data" +File.separator+ "person.png";

        // Option 2
        //String filePath = Paths.get(basePath, "src", "test", "resources", "test_data", fileName).toString();
        // We need pass this path to sendKeys() methods
        // Note we do not need to click on the button or do any other actions
        // We simply send the file path to that input tag
        herokuAppPage.choseFileButton.sendKeys(filePath);
        logger.info("User successfully uploaded the file at: " + filePath);
        WaitHelper.wait(1);

        // submit the file
        herokuAppPage.uploadButton.click();
        logger.info("User clicked on upload button");
        WaitHelper.wait(3);
    }

    @Then("the file is successfully uploaded")
    public void the_file_is_successfully_uploaded() {
        String expectedFileName = uploadFileName;
        String actualFileName = herokuAppPage.uploadedFilesResult.getText();
        Assert.assertEquals("File name verification failed!", expectedFileName, actualFileName);
        logger.info("File name verification passed!");
    }

    // ======================= DOWNLOAD ======================== //

    @Given("clicks on file download page")
    public void clicks_on_file_download_page() {
        herokuAppPage.fileDownloadLink.click();
        logger.info("User clicked on file download page");
        WaitHelper.wait(1);
    }

    @When("user clicks on {string}")
    public void user_clicks_on(String fileName) {
        this.downloadFileName = fileName;
        herokuAppPage.lambdaTestTextFileLink.click();
        logger.info("User clicked on "+fileName+" file");
        WaitHelper.wait(1);
    }

    @Then("the file is successfully downloaded")
    public void the_file_is_successfully_downloaded() {
        String basePath = System.getProperty(Constants.USER_DIRECTORY);
        String filePath = Paths.get(basePath, "src", "test", "resources", "test_data", downloadFileName).toString();
        boolean fileExists = FileHelper.fileExists(filePath);
        Assert.assertTrue("The file existence verification failed.", fileExists);
        logger.info("The file is present at: " + filePath);
    }

    @Then("the file content matches the {string}")
    public void the_file_content_matches_the_expected_file(String expectedFileName) {
        String basePath = System.getProperty(Constants.USER_DIRECTORY);
        String actualDownloadedFilePath = Paths.get(basePath, "src", "test", "resources", "test_data", downloadFileName).toString();
        String expectedDownloadedFilePath = Paths.get(basePath, "src", "test", "resources", "test_data", expectedFileName).toString();

        boolean filesContentEquals = FileHelper.compareFilesOnBytes(expectedDownloadedFilePath, actualDownloadedFilePath);
        Assert.assertTrue("File content verification failed.", filesContentEquals);
        logger.info("The files content is identical.");

        FileHelper.deleteFile(actualDownloadedFilePath);
        logger.info("The file successfully deleted.");

    }

}
