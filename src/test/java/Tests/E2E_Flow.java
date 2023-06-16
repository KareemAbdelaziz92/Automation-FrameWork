package Tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.presidentio.testdatagenerator.*;

import junit.framework.Assert;
import net.datafaker.Faker;
import net.datafaker.annotations.*;
public class E2E_Flow {
	
	
	ChromeDriver driver ;
	@BeforeTest
	
	public void init() {
		
		
		driver = new ChromeDriver();
		
		driver.navigate().to("https://workforce.iamdeveloper.in/");
		
		driver.manage().window().maximize();
		
	}
	
	@Test(priority = 0)
	
public void Login_with_AdminCredentials() {
		
		String username = "vipinjoshi.joshi@gmail.com" ;
		
		String Password = "vipin@123" ;
			
	driver.findElement(By.name("username")).sendKeys(username);
	driver.findElement(By.name("password")).sendKeys(Password);
	driver.findElement(By.xpath("/html/body/div[2]/main/div/div/div[2]/div/div/div/div/form/div[4]/button[1]")).click();
	//  driver.findElement(By.xpath("/html/body/main/div/div/div[1]/nav/div/ul/li[2]/ul/li[2]/a/span")).click();

		
	}
	
	
//	@SuppressWarnings("deprecation")
	//@Test(priority = 1)
	
public void ClientCompanyManager_Creation() {
		
		Faker faker = new Faker();
		
		String CompanyLastname = faker.name().lastName();
		String CompanyFirstname = faker.name().firstName();
		String CompanyEmail = CompanyFirstname+"@gmail.com";
		String CompanyMobile = "246712" ;  
		

    driver.findElement(By.xpath("/html/body/main/div/div/div[1]/nav/div/ul/li[2]/ul/li[2]/a/span")).click();
	driver.findElement(By.xpath("/html/body/main/div/div/div[2]/div[1]/div[1]/div/div[2]/div/a")).click();
	driver.findElement(By.name("last_name")).sendKeys(CompanyLastname);
	driver.findElement(By.name("first_name")).sendKeys(CompanyFirstname);
	driver.findElement(By.name("email")).sendKeys(CompanyEmail);
	driver.findElement(By.name("mobile_no")).sendKeys(CompanyMobile);
	driver.findElement(By.name("send_email_later")).click();
	driver.findElement(By.xpath("/html/body/main/div/div/div[2]/div[2]/div[2]/div/div/form/div[7]/div[4]/button")).click();
	String Success = driver.findElement(By.className("alert-success")).getText();
	Assert.assertEquals(Success, true, true);
	
	}


public void Contractor_Creation() {
	
	String username = "vipinjoshi.joshi@gmail.com" ;
	
	String Password = "vipin@123" ;
	
	driver.findElement(By.xpath("//html/body/main/div/div/div[1]/nav/div/ul/li[2]/ul/li[7]/a")).click();

		
driver.findElement(By.name("username")).sendKeys(username);
driver.findElement(By.name("password")).sendKeys(Password);

	
}

@Test(priority = 2)

public void Employee_Creation() {
	
	String username = "vipinjoshi.joshi@gmail.com" ;
	
	String Password = "vipin@123" ;
	
	driver.findElement(By.xpath("//html/body/main/div/div/div[1]/nav/div/ul/li[2]/ul/li[7]/a")).click();
	
driver.findElement(By.name("username")).sendKeys(username);
driver.findElement(By.name("password")).sendKeys(Password);
driver.findElement(By.xpath("/html/body/div[2]/main/div/div/div[2]/div/div/div/div/form/div[4]/button[1]")).click();

	
}



	
	

}
