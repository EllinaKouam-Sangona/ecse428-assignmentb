package ca.mcgill.ecse428;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.lang.Object;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class StepDefinition {

	private WebDriver chromeDriver;
	//private final String gmailPassword = "ECSE428ASSB";
	//private final String gmailUsername = "ellina.kouam.sangona@gmail.com";
	private final String gmailPassword = "Azerty1@";
	private final String gmailUsername = "assignmentb428@gmail.com";
	private final String GMAIL_LOGIN_URL = "https://accounts.google.com/ServiceLogin?continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&service=mail&sacu=1&rip=1";
	private final String INBOX_URL = "https://mail.google.com/mail/u/0/#sent";
	private final String NEW_MESSAGE_URL = "https://mail.google.com/mail/u/0/#sent?compose=new";

	private BufferedImage img;

	//String recipient;

	//Given
	@Given("^I am on the new email page$")
	public void i_am_on_the_new__email_page() throws Throwable{
		setupSeleniumDrivers();
		goTo(INBOX_URL);

		//Find username text box, enter username and click next to head to password page
		WebDriverWait waitEmail = new WebDriverWait(chromeDriver, 10);
		WebElement elmtEmail = waitEmail.until(
		        ExpectedConditions.visibilityOfElementLocated(By.name("identifier")));
		WebElement userName = chromeDriver.findElement(By.name("identifier"));
		userName.sendKeys(gmailUsername);
		chromeDriver.findElement(By.id("identifierNext")).click();

		//Set explicit wait for password textbox to be detectable, enter password and head to inbox 
		WebDriverWait wait = new WebDriverWait(chromeDriver, 20);
		WebElement password = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='password']")));
		password.sendKeys(gmailPassword);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("passwordNext")));
		chromeDriver.findElement(By.id("passwordNext")).click();
		
		//Set explicit wait for the compose button to be detectable
		WebElement composeBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='z0']/div")));
		composeBtn.click();
		
		System.out.println("Given working");
	}
	
	//When
	@When("^I enter ([^\"]*) and the email body$")
	public void IEnterTheRecipientAndEmailBody(String recipient) throws Throwable {
		WebDriverWait wait = new WebDriverWait(chromeDriver, 10);
		WebElement elmt = wait.until(
		        ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name='to']")));
		chromeDriver.findElement(By.xpath("//textarea[@name='to']")).sendKeys(recipient);
		chromeDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		chromeDriver.findElement(By.xpath("//input[@name='subjectbox']")).sendKeys("Test");
		WebElement element = chromeDriver.findElement(By.xpath("//div[@class='Ar Au']//div"));
		element.click();
		element.sendKeys("Hello");  
	}
	
	@When("^I drag a file from my computer into the email page, ([^\"]*)$")
	public void i_drag_a_file_from_my_computer_into_the_email_page(String Image) throws Throwable{
		//Mimick drag and drop by copying file(from file system) to clipboard and pasting to new message body
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		File img = new File(Image);
		clipboard.setContents(new ImageTransferable(ImageIO.read(img)), null);
		
		//Find drop area and wait till interactable
		WebDriverWait wait = new WebDriverWait(chromeDriver, 50);
		System.out.println("before wait");
		WebElement droparea = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class='Am Al editable LW-avf']")));
		System.out.println("Till here!");
		
		//Highlight drop area
		droparea.click();
		
		//Paste image into drop area
		Actions actions = new Actions(chromeDriver);
		actions.sendKeys(Keys.chord(Keys.LEFT_CONTROL, "v")).build().perform();
		
		System.out.println("When working");
	}
	
	@When("^I select the attach file button$")
	public void i_select_the_attach_file_button() throws Throwable {
		WebDriverWait wait = new WebDriverWait(chromeDriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class, 'a1 aaA aMZ')]"))).click();  
	}
	
	@And("^I select the attach files button$")
	public void ISelectTheAttachFilesButton() throws Throwable {
		chromeDriver.findElement(By.xpath("//div[contains(@class, 'a1 aaA aMZ')]")).click();  
	}

	//And
	@And("^select the file to attach in my file explorer, ([^\"]*)$")
	public void ISelectTheFileToAttach(String Image) throws Throwable {
		File file = new File(Image);

		StringSelection stringSelection= new StringSelection(file.getAbsolutePath());

		//Copy to clipboard 
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

		Robot robot = new Robot();

		//robot.keyPress(KeyEvent.VK_META);                
		//robot.keyPress(KeyEvent.VK_TAB);                 
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
	
	/*@And("^select the file to attach in my file explorer, ([^\"]*)")
	public void i_select_the_file_to_attach(String Image) throws Throwable {
		File file = new File(Image);
		StringSelection stringSelection= new StringSelection(file.getAbsolutePath());
		//Copy to clipboard 
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
		System.out.print(stringSelection);
		Robot robot = new Robot();
		Thread.sleep(1000);
	      
		  // Press Enter
		 robot.keyPress(KeyEvent.VK_ENTER);
		 
		// Release Enter
		 robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		 
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
		//Thread.sleep(1000);
		        
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}*/

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
	@Then("^the file ([^\"]*) is included in my email$")
	public void the_file_is_included_in_my_email(String Image) throws Throwable{
		WebDriverWait wait = new WebDriverWait(chromeDriver, 10);
		WebElement elmtSend = wait.until(
		        ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), Image)]")));
		WebElement file = chromeDriver.findElement(By.xpath("//*[contains(text(), Image)]"));
		assertNotNull(file);
		System.out.println("Then working");
	}
	
	@Then("^a prompt saying Your file is larger than 25 MB appears")
	public void a_prompt_saying_Your_file_is_larger_than_25_MB_appears() throws Throwable{
		WebDriverWait wait = new WebDriverWait(chromeDriver, 30);
		//WebElement prompt = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='HyIydd']")));
		WebElement elmt = wait.until(
		        ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'larger')]")));
		//chromeDriver.findElement(By.xpath("//*[contains(text(), 'Your file is larger than')]"));
		//assertNotNull(prompt);
		assertNotNull(elmt);
		System.out.println("prompt");
	}
	
	@And("^the email is sent to ([^\"]*) with my file$")
	public void the_email_is_sent_to_with_my_file(String Recipient) throws Throwable{
		System.out.println("Filling in subject and recipient.");
		WebDriverWait wait = new WebDriverWait(chromeDriver, 100);
		//find subject area, click and write
		Thread.sleep(10000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='aoD hl']")));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='aoD hl']"))).click();
		WebElement recipientArea = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@name='to']")));
		recipientArea.sendKeys(Recipient);
		
		WebElement subjectArea = wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//input[@name='subjectbox']"))));
		String subject = "Test";
		subjectArea.sendKeys(subject);
		
		chromeDriver.findElement(By.xpath("//div[contains(text(),'Send')]")).click();
		//chromeDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Thread.sleep(1000);
		WebDriverWait wait2 = new WebDriverWait(chromeDriver, 15);
		WebElement elmt = wait2.until(
		        ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'sent.')]")));
		chromeDriver.findElement(By.xpath("//*[contains(text(), 'sent.')]")).click();
		WebElement email = chromeDriver.findElement(By.xpath("//*[contains(text(), 'Test')]"));
		assertNotNull(email);
		chromeDriver.close();
		

		System.out.println("Email" + subject +"sent!");
	}

	@Then("^the open button should be non interactable$")
	public void theOpenButtonShouldBeNonInteratable() throws Throwable {
		chromeDriver.close();
	}

	@And("^the email is sent$")
	public void theEmailIsSent() throws Throwable {	
		//chromeDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(3000);
		chromeDriver.findElement(By.xpath("//div[contains(text(),'Send')]")).click();
		//chromeDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(chromeDriver, 10);
		WebElement elmt = wait.until(
		        ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Sent')]")));
		chromeDriver.findElement(By.xpath("//*[contains(text(), 'Sent')]")).click();
		WebElement email = chromeDriver.findElement(By.xpath("//*[contains(text(), 'Test')]"));
		assertNotNull(email);
		chromeDriver.close();
	}


	
	//Helper fcts
	public void setupSeleniumDrivers() {
		if (chromeDriver == null){
			//System.setProperty("webdriver.chrome.driver", "C:\\Users\\ellin_000\\Desktop\\chromedriver.exe");
			System.setProperty("chromedriver","chromedriver");
			chromeDriver= new ChromeDriver();
			System.out.print("Finished setting up chrome driver.\n");
		}
	}

	public void goTo(String url){
		if (chromeDriver != null){
			chromeDriver.get(url);
		}
	}

	
	 public Object ImageTransferable(BufferedImage img) {
         return this.img = img;
     }

}
