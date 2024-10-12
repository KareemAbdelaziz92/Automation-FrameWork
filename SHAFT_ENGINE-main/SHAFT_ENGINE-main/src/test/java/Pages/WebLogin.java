package Pages;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import com.shaft.driver.SHAFT;
import com.shaft.gui.element.TouchActions.SwipeDirection;

import Tests.BaseTest;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;

public class WebLogin {
    public SHAFT.GUI.WebDriver driver;
  //  ReportManager ReportManager;
    public WebLogin(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    private final By txt_username_web = By.id("username-input");
    private final By txt_password_web = By.id("password-input");
    private final By btn_login_web = By.id("submit-button");
    private final By set_Env = By.xpath("//button[text()='sit']");
    private final By link_Dashboard = By.xpath("//a[text()='Dashboard']");
    @Step("enter username and password then login")
    public WebLogin WebLogin(String username, String password) throws Exception {
   //     ReportManager = new ReportManager(driver);
     //   ReportManager.LogResult("micPass", "Open the AlRajhi Capital WebPage", "AlRajhi Capital WebPage Opened Successfully");
        if (BaseTest.AppEnv.equalsIgnoreCase("SIT")) {
            switchToSIT();
        }
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        return this;

    }

    @Step("Click on Sit Environment")
    public WebLogin switchToSIT() throws InterruptedException {
        driver.element().verifyThat(set_Env).exists().perform();
        driver.element().click(set_Env);
     //   ReportManager.LogResult("micPass", "Click on SIT Btn", "SIT Btn Clicked Successfully");
        return this;
    }
    @Step("Enter Username")
    public WebLogin enterUsername(String username) {
        driver.element().verifyThat(txt_username_web).exists().perform();
        driver.element().typeAppend(txt_username_web, username);
      //  ReportManager.LogResult("micPass", "type Username text field", "Username text field typed Sucessfully");
        return this;
    }
    @Step("Enter Password")
    public WebLogin enterPassword(String password) {
        System.out.println("WebPWD: "+password);
        driver.element().verifyThat(txt_password_web).exists().perform();
        driver.element().typeAppend(txt_password_web, password);
      //  ReportManager.LogResult("micPass", "type Password text field", "Password text field typed Sucessfully");
        return this;
    }
    @Step("Click on Login button")
    public WebLogin clickLogin() {
        driver.element().verifyThat(btn_login_web).exists().perform();
        driver.element().click(btn_login_web);
        if (driver.element().isElementDisplayed(link_Dashboard)) {
            BaseTest.loginStatus = true;
            BaseTest.executionStatus="Success";
         //   ReportManager.LogResult("micPass", "Click on login Btn", "login Btn Clicked Sucessfully");
        }else {
            BaseTest.loginStatus = false;
          //  ReportManager.LogResult("micFail", "Click on login Btn", "Error in Login");
        }

        return this;
    }
}
