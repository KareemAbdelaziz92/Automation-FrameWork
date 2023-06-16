package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Homepage extends PageBase{

	public Homepage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(linkText="https://workforce-staging.iamdeveloper.in/")
	
	WebElement HomePage ;
	
	public void open_homepage() {
		
		HomePage.click();
	}

}
