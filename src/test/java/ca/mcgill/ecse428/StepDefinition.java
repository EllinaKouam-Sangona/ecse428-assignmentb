package ca.mcgill.ecse428;

import cucumber.annotation.en.And;
import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class StepDefinition {

	private WebDriver chromeDriver;
	private final String PATH_TO_CHROME_DRIVER = "/Users/ellin_000/Downloads/chromedriver_win32";
	private final String NEW_MESSAGE_URL = "https://mail.google.com/mail/u/0/#inbox?compose=new";
	private final String PATH_TO_IMAGE_1 = "";
	private final String PATH_TO_IMAGE_2 = "";
	private final String PATH_TO_IMAGE_3 = "";
	private final String PATH_TO_IMAGE_4 = "";
	private final String ATTACH_FILES_BUTTON = "";
	//private final String
	
	//Given
	@Given("^I am on the new email page$")
	public void givenOnNewEmailPage() throws Throwable{
		setupSeleniumDrivers();
		goTo(NEW_MESSAGE_URL);
	}
	
	//Helper fcts
	

	public void setupSeleniumWebDrivers() {
		if (chromeDriver == null){
			System.setProperty("webdriver.chrome.driver", PATH_TO_CHROME_DRIVER);
			chromeDriver = new ChromeDriver();
			System.out.print("Finished settin up chrome driver.\n");
		}
	}

	public void goTo(String url){
		if (chromeDriver != null){
			chromeDriver.get(url);
		}
	}
}
