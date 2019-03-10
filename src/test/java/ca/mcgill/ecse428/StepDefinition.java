package ca.mcgill.ecse428;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import javafx.embed.swing.JFXPanel;
//import javafx.scene.image.Image;
//import javafx.scene.input.Clipboard;
import java.lang.Object;
import javafx.scene.input.ClipboardContent;

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

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.interactions.Actions;
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
	private BufferedImage img;
	
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
		WebDriverWait wait = new WebDriverWait(chromeDriver, 20);
		WebElement password = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='password']")));
		password.sendKeys(gmailPassword);
		chromeDriver.findElement(By.id("passwordNext")).click();
		
		//Set explicit wait for the compose button to be detectable
		WebElement composeBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='z0']/div")));
		composeBtn.click();
		
		System.out.println("Given working");
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
	
	@When("^I select the attach files button$")
	public void i_select_the_attach_files_button() throws Throwable {
		WebDriverWait wait = new WebDriverWait(chromeDriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class, 'a1 aaA aMZ')]"))).click();  
	}
	
	//And
		@And("^select the file to attach in my file explorer, ([^\"]*)")
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
			Thread.sleep(1000);
			        
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		}

	
	@Then("^the file ([^\"]*) is included in my email$")
	public void the_file_is_included_in_my_email(String Image) throws Throwable{
		WebDriverWait wait = new WebDriverWait(chromeDriver, 10);
		WebElement elmtSend = wait.until(
		        ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), Image)]")));
		WebElement file = chromeDriver.findElement(By.xpath("//*[contains(text(), Image)]"));
		assertNotNull(file);
		//System.out.println("File icon present!");
		System.out.println("Then working");
		//throw new PendingException();
	}
	
	@Then("^a prompt saying Your file is larger than 25 MB appears")
	public void a_prompt_saying_Your_file_is_larger_than_25_MB_appears() throws Throwable{
		WebDriverWait wait = new WebDriverWait(chromeDriver, 10);
		WebElement prompt = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='HyIydd']")));
		//wait.until(ExpectedConditions.alertIsPresent()); 
		//String prompt = chromeDriver.switchTo().alert().getText();
		assertNotNull(prompt);
		System.out.println("prompt");
		//assertThat(prompt, containsString("Your file is larger than 25 MB."));
	}
	
	@And("^the email is sent to ([^\"]*) with my file$")
	public void the_email_is_sent_with_my_file(String Recipient) throws Throwable{
		System.out.println("Filling in subject and recipient.");
		WebDriverWait wait = new WebDriverWait(chromeDriver, 70);
		//find subject area, click and write
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='aoD hl']"))).click();
		WebElement recipientArea = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@name='to']")));
		recipientArea.sendKeys(Recipient);
		
		WebElement subjectArea = wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//input[@name='subjectbox']"))));
		String subject = "Test";
		subjectArea.sendKeys(subject);
		
		WebDriverWait wait2 = new WebDriverWait(chromeDriver, 90);
		System.out.println("Sending email.");
		
		WebElement sendBtn = wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Send')]")));
		sendBtn.click();
		
		System.out.println("Heading to sent page.");
		//Go to sent page
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-tooltip='Sent']//div"))).click();
		
		System.out.println("Searching for sent email.");
		//find subject
		WebElement sentMail = chromeDriver.findElement(By.xpath("//*[contains(text(), 'Test')]"));
		assertNotNull(sentMail);

		System.out.println("Email" + subject +"sent!");
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
	
	 public Object ImageTransferable(BufferedImage img) {
         return this.img = img;
     }
	
	
}
