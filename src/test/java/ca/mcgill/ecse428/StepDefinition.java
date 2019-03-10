package ca.mcgill.ecse428;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.fail;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class StepDefinition {

	private WebDriver chromeDriver;
	//	private final String gmailPassword = "ECSE428ASSB";
	//	private final String gmailUsername = "ellina.kouam.sangona@gmail.com";
	private final String gmailPassword = "Azerty1@";
	private final String gmailUsername = "assignmentb428@gmail.com";
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
		chromeDriver.findElement(By.xpath("//textarea[@name='to']")).sendKeys("chaimaefahmi@outlook.com");
		chromeDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		chromeDriver.findElement(By.xpath("//input[@name='subjectbox']")).sendKeys("Test");
		WebElement element = chromeDriver.findElement(By.xpath("//div[@class='Ar Au']//div"));
		element.click();
		element.sendKeys("Hello");
		System.out.println("Given working");
	}

	//When
	@When("^I select the attach files button$")
	public void ISelectTheAttachFilesButton() throws Throwable {
		chromeDriver.findElement(By.xpath("//div[contains(@class, 'a1 aaA aMZ')]")).click();  
	}

	//And
	@And("^select the file to attach in my file explorer$")
	public void ISelectTheFileToAttach() throws Throwable {
		File file = new File("Image.png");

		StringSelection stringSelection= new StringSelection(file.getAbsolutePath());

		//Copy to clipboard 
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

		Robot robot = new Robot();

		robot.keyPress(KeyEvent.VK_META);                
		robot.keyPress(KeyEvent.VK_TAB);                 
		robot.keyRelease(KeyEvent.VK_META);                 
		robot.keyRelease(KeyEvent.VK_TAB);                 
		robot.delay(500);


		robot.keyPress(KeyEvent.VK_META);

		robot.keyPress(KeyEvent.VK_SHIFT);                  
		robot.keyPress(KeyEvent.VK_G);                 
		robot.keyRelease(KeyEvent.VK_META);                
		robot.keyRelease(KeyEvent.VK_SHIFT);                  
		robot.keyRelease(KeyEvent.VK_G);

		robot.keyPress(KeyEvent.VK_META);                 
		robot.keyPress(KeyEvent.VK_V);                 
		robot.keyRelease(KeyEvent.VK_META);                  
		robot.keyRelease(KeyEvent.VK_V);

		robot.keyPress(KeyEvent.VK_ENTER);                 
		robot.keyRelease(KeyEvent.VK_ENTER);  

		robot.delay(1000);                 

		robot.keyPress(KeyEvent.VK_ENTER);                  
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	//And
	@And("^I click open on the file explorer$")
	public void IClickOpenOnTheFileExplorer() throws Throwable {
		try {
		chromeDriver.findElement(By.id("Open")).click();
		fail("Expected NoSuchElementException");
		}
		catch(NoSuchElementException e) {
			
		}
	}

	//Then
	@Then("^the file is included in my email$")
	public void theFileIsIncludedInTheEmail() throws Throwable {
	}

	//Then
	@Then("^the open button should be non interactable$")
	public void theOpenButtonShouldBeNonInteratable() throws Throwable {
	}

	@And("^the email is sent$")
	public void theEmailIsSent() throws Throwable {
		chromeDriver.findElement(By.xpath("//div[contains(text(),'Send')]")).click();
		chromeDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);   
	}


	//Helper fcts
	public void setupSeleniumDrivers() {
		if (chromeDriver == null){
			//System.setProperty("webdriver.chrome.driver", "C:\\Users\\ellin_000\\Desktop\\chromedriver.exe");
			System.setProperty("chromedriver", "");
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
