package ca.mcgill.ecse428;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.File;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class StepDefinition {

	private WebDriver chromeDriver;
	private final String gmailPassword = "ECSE428ASSB";
	private final String gmailUsername = "ellina.kouam.sangona@gmail.com";
	private final String GMAIL_LOGIN_URL = "https://accounts.google.com/ServiceLogin?continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&service=mail&sacu=1&rip=1";
	private final String INBOX_URL = "https://mail.google.com/mail/u/0/#inbox";
	private final String NEW_MESSAGE_URL = "https://mail.google.com/mail/u/0/#inbox?compose=new";
	
	//Given
	@Given("^I am on the new email page$")
	public void i_am_on_the_new_email_page() throws Throwable{
		setupSeleniumDrivers();
		goTo(GMAIL_LOGIN_URL);
		
		//Find username text box, enter username and click next to head to password page
		WebElement userName = chromeDriver.findElement(By.name("identifier"));
		userName.sendKeys(gmailUsername);
		chromeDriver.findElement(By.id("identifierNext")).click();

		//Set explicit wait for password textbox to be detectable, enter password and head to inbox 
		WebDriverWait wait = new WebDriverWait(chromeDriver, 10);
		WebElement password = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='password']")));
		password.sendKeys(gmailPassword);
		chromeDriver.findElement(By.id("passwordNext")).click();
		
		//Set explicit wait for the compose button to be detectable
		WebElement composeBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='z0']/div")));
		composeBtn.click();
		
		System.out.println("Given working");
	}
	

	//Helper fcts
	public void setupSeleniumDrivers() {
		if (chromeDriver == null){
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\ellin_000\\Desktop\\chromedriver.exe");
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
