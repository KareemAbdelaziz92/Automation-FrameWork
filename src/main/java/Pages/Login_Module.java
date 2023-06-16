package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Login_Module extends PageBase {

	public Login_Module(WebDriver driver) {
		super(driver);

	}

	String Adminusername = "vipinjoshi.joshi@gmail.com" ;
	String Adminpassword = "vipin@123";


	@FindBy(name="username")

	WebElement username ;

	@FindBy(name="password")

	WebElement password ;

	@FindBy(xpath="/html/body/div[2]/main/div/div/div[2]/div/div/div/div/form/div[4]/button[1]")

	WebElement loginbutton ;
	
	
	@FindBy (xpath = "/html/body/nav[2]/ul/li[1]/a")
	
	 public WebElement SuccessMessage ;

	public void Admin_Login() {

		username.sendKeys(Adminusername);
		password.sendKeys(Adminpassword);
		loginbutton.click();	 
	}

}
