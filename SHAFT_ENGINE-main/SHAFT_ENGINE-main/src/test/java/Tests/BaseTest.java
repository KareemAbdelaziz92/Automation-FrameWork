package Tests;

//import Pages.ProdValidation.SendEmailWithAttachment;
//import Pages.Superapp.IOS.LoginPage;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.Browser;
import org.testng.annotations.Parameters;

import com.shaft.driver.SHAFT;
//import com.shaft.driver.SHAFT.GUI.
//import Pages.Superapp.IOS.SuperAppLogin;
//import Pages.Superapp.IOS.SuperAppLoginIOS;
//import Report.ReportManager;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.remote.AutomationName;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class BaseTest {
    public static SHAFT.GUI.WebDriver driver;
    public static String reportName;
    public static String errorText = "NA";
    public static String device;
    public static boolean loginStatus;
    public static String AppEnv;
    public static boolean sendEmailStatus;
    public static boolean networkErrorStatus;
    public static int slNo = 0;
    public static String Screenshotstatus = System.getProperty("Screenshotstatus");
    public static String executionStatus = "No";
    public static String executionSummary = "None";
    public static int iPassCount = 0;
    public static int iFailCount = 0;
    public static int TCCount = 1;
    static Calendar calander = Calendar.getInstance();
    static SimpleDateFormat formater = new SimpleDateFormat("dd-MM-YY HH:mm:ss");
    public static String iStartTime = formater.format(calander.getTime());
    public static String SStartTime = formater.format(calander.getTime());
    //   static SuperAppLogin superapplogin;
    //  static SuperAppLoginIOS superapploginIOS;
    public static String excelFilePath;
    //  static ReportManager reportManager;
    public static boolean reloginStatus = false;

    @Parameters({"loginname", "password", "executionDevie", "newLogin"})
    @BeforeClass(alwaysRun = true)
    public static void setUp(String loginname, String password, String executionDevie, String newLogin) throws Exception {
        System.out.println("Last Run Status is : " + executionStatus);
        System.out.println("loginStatus: " + loginStatus);
        System.out.println("login name: " + loginname);

        //reportManager.ExecutionSuiteSummary();
        if (newLogin.equalsIgnoreCase("Yes")) {
            System.out.println("newLogin is Yes");
//            if (driver != null) {
//                System.out.println("relogin with same session");
//                new LoginPage(driver).reLogin(loginname, password);
//                reloginStatus =true;
//            } else {}

            System.out.println("create new session");
            configEnv(executionDevie);

//            if (!loginname.equalsIgnoreCase("None") && !reloginStatus) {
            if (!loginname.equalsIgnoreCase("None")) {
                System.out.println("loginname is Yes");
                login(loginname, password);
                for (int i = 0; i < 2; i++) {
                    if (!BaseTest.loginStatus) {
                        driver.quit();
                        System.out.println("i before config = " + i);
                        Thread.sleep(10000);
                        configEnv(executionDevie);
                        login(loginname, password);
                        System.out.println("i after config = " + i);
                    } else {
                        break;
                    }
                }
            }
        } else if (newLogin.equalsIgnoreCase("No") && (driver == null || !loginStatus)) {
            System.out.println("driver is null");
            System.out.println("newLogin is No");
            System.out.println("loginStatus : " + loginStatus);
            System.out.println("create new session");
            configEnv(executionDevie);
        }
    }

    @Parameters({"loginname", "password", "executionDevie"})
    @AfterClass(alwaysRun = true)
    public void resetDriver(String loginname, String password, String executionDevie) throws Exception {
        final By reLogin = AppiumBy.accessibilityId("com.alrajhicapital:id/feedback_secondary_button_one");
        Boolean elementStatusReLogin = null;
        //  reportManager = new ReportManager(driver);
        System.out.println("Last Run Status is : " + executionStatus);
        System.out.println("username: " + loginname);
        System.out.println("password: " + password);
        System.out.println("loginStatus: " + loginStatus);
//        executionStatus = "Success";
        device = executionDevie;
        if (!executionStatus.equalsIgnoreCase("Success")) {
            System.out.println("relogin with same session - resetDriver");
            //   new LoginPage(driver).reLogin(loginname, password, executionDevie);
        } else if (loginStatus || loginname.equalsIgnoreCase("None")) {
            try {
                //        reportManager.navigatetoHome();
                elementStatusReLogin = driver.getDriver().findElement(reLogin).isDisplayed();
            } catch (Exception e) {
                // TODO: handle exception
                elementStatusReLogin = false;
            }
            if (elementStatusReLogin) {
                driver.element().click(reLogin);
                //     new LoginPage(driver).reLogin(loginname, password, executionDevie);
            }
//            else {
//                ReportManager.navigatetoHome();
//            }
        }
    }

    @AfterMethod(alwaysRun = true)
    //@AfterTest(alwaysRun = true)
    public void afterMethod() throws Exception {
        //  reportManager = new ReportManager(driver);
        if (!executionStatus.equalsIgnoreCase("Success") && (!errorText.equalsIgnoreCase("Network Error"))) {
            if (System.getProperty("os.name").contains("Windows")) {
                excelFilePath = System.getProperty("user.dir") + "\\Execution_Summary\\Execution_Summary_Report.xlsx";
            } else {
                excelFilePath = System.getProperty("user.dir") + "/Execution_Summary/Execution_Summary_Report.xlsx";
            }
            System.out.println("excelPath: " + excelFilePath);
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheet("Summary_Report");
            //int rowCount = TCCount;
            int rowCount = sheet.getLastRowNum();
            System.out.println("rowCount: in After Method" + rowCount);
            Cell cell2Update = sheet.getRow(rowCount).getCell(0);
            String testScript = cell2Update.getStringCellValue();
            inputStream.close();
            workbook.close();
            if (executionSummary.equalsIgnoreCase("None")) {
                //      reportManager.LogResult("micFail", testScript, "Error on : " + testScript);
                //      reportManager.TestCaseExecutiveSummary();
            }
            if ((sendEmailStatus) && (!reportName.contains("Login")) && slNo > 1) {
                //      SendEmailWithAttachment.SendEmail();
            }
        }

    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() throws Exception {
        //   reportManager.ExecutionSuiteSummary();
        driver.quit();
    }

    public static void configEnv(String executionDevie) {
        //  reportManager = new ReportManager(driver);
        AppEnv = (System.getProperty("applicationENV"));
        sendEmailStatus = (Boolean.parseBoolean(System.getProperty("sendEmailStatus")));
        executionStatus = "Success";
        device = executionDevie;
        System.out.println(System.getProperty("executionAddress"));
        if (System.getProperty("executionAddress").equalsIgnoreCase("local")) {

            localSetup(device);
        }else if (System.getProperty("executionAddress").equalsIgnoreCase("browserstack")) {
            browserStackSetup();
        }
        if (Objects.equals(device, "Android")) {
            androidSetup();
        } else if (Objects.equals(device, "IOS")) {
            iosSetup();
        } else if (Objects.equals(device, "Web")) {
            webSetup();
        }
        driver = new SHAFT.GUI.WebDriver();
        System.out.println(">>>>>> driver initialized <<<<<<");
    }


    public static void login(String loginname, String password) {
        System.out.println(loginname);
        System.out.println(password);
        try {
            //    reportManager.CreateResultFile("superAppLogin", "appLogin");
            //   new LoginPage(driver).login(loginname, password);
            //   reportManager.TestCaseExecutiveSummary();
        } catch (Exception e) {
            // reportManager.LogResult("micFail", "Login to App", "Error on Login: /n" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void webSetup() {
        SHAFT.Properties.platform.set().targetPlatform(Platform.MAC.name());
        SHAFT.Properties.web.set().targetBrowserName(Browser.CHROME.browserName());
        SHAFT.Properties.flags.set().autoMaximizeBrowserWindow(true);

    }

    public static void localSetup(String executionDevice) {
        System.out.println("Test");
        if(device.equalsIgnoreCase("web")){
            SHAFT.Properties.platform.set().executionAddress(System.getProperty("executionAddress"));
        }
        else{
            SHAFT.Properties.platform.set().executionAddress(System.getProperty("localAppium"));
            SHAFT.Properties.mobile.set().deviceName(System.getProperty("localDeviceName"))
                    .udid(System.getProperty("localUuid")).app(System.getProperty("localApp"));
        }

    }

    public static void browserStackSetup() {
        SHAFT.Properties.platform.set().executionAddress(System.getProperty("executionAddress"));
        SHAFT.Properties.timeouts.set().lazyLoadingTimeout(60).waitForRemoteServerToBeUp(true)
                .scriptExecutionTimeout(60).pageLoadTimeout(60).defaultElementIdentificationTimeout(60);
        SHAFT.Properties.browserStack.set().accessKey(System.getProperty("accessKey"));
        SHAFT.Properties.browserStack.set().username(System.getProperty("username"));
        SHAFT.Properties.browserStack.set().local(Boolean.parseBoolean(System.getProperty("local")));
        SHAFT.Properties.browserStack.set().debug(Boolean.parseBoolean(System.getProperty("debug")));
        SHAFT.Properties.browserStack.set()
                .enableBiometric(Boolean.parseBoolean(System.getProperty("enableBioMetric")));
        SHAFT.Properties.reporting.set().captureWebDriverLogs(true);
        SHAFT.Properties.browserStack.set().networkLogs(true);
    }

    public static void androidSetup() {
        SHAFT.Properties.platform.set().targetPlatform(Platform.ANDROID.name());
        SHAFT.Properties.mobile.set().automationName(AutomationName.ANDROID_UIAUTOMATOR2);
        SHAFT.Properties.browserStack.set().appUrl(System.getProperty("appUrl"));
        SHAFT.Properties.browserStack.set().deviceName(System.getProperty("deviceName"));
        SHAFT.Properties.browserStack.set().platformVersion(System.getProperty("platformVersion"));
        SHAFT.Properties.browserStack.set().appName(System.getProperty("appName"));
    }

    public static void iosSetup() {
        SHAFT.Properties.platform.set().targetPlatform(Platform.IOS.name());
        SHAFT.Properties.mobile.set().automationName(AutomationName.IOS_XCUI_TEST);
        SHAFT.Properties.browserStack.set().appUrl(System.getProperty("appUrl_IOS"));
        SHAFT.Properties.browserStack.set().deviceName(System.getProperty("deviceName_IOS"));
        SHAFT.Properties.browserStack.set().platformVersion(System.getProperty("platformVersion_IOS"));
        SHAFT.Properties.browserStack.set().appName(System.getProperty("appName_IOS"));
        SHAFT.Properties.browserStack.set().acceptInsecureCerts(false);
    }

    public static String captureScreenShotMobAndriod(SHAFT.GUI.WebDriver driver, String screenshotName) {
        try {
            Thread.sleep(1500);
            String imagesDirectory = "./AutomationReports/screenShots" + "/";
            //String imagesDirectory = System.getProperty("user.dir") + "/screenShots" + "/";
            Calendar calander = Calendar.getInstance();
            SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yy_hh_mm_ss");
            File src = ((TakesScreenshot) driver.getDriver()).getScreenshotAs(OutputType.FILE);
            String dest = imagesDirectory + screenshotName + "-" + formater.format(calander.getTime()) + ".png";
            File destination = new File(dest);
            FileUtils.copyFile(src, destination);
            return dest;
        } catch (Exception e) {
            System.out.println(e.toString());
            return e.toString();
        }
    }

    public static void writeDatainExcel(String scriptName, String currentStatus, String reportPath) {
        if (System.getProperty("os.name").contains("Windows")) {
            excelFilePath = System.getProperty("user.dir") + "\\Execution_Summary\\Execution_Summary_Report.xlsx";
        } else {
            excelFilePath = System.getProperty("user.dir") + "/Execution_Summary/Execution_Summary_Report.xlsx";
        }

        try {
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheet("Summary_Report");
            int rowCount = sheet.getLastRowNum();
            Row row = sheet.createRow(++rowCount);
            Cell celltoupdate = row.createCell(0);
            celltoupdate.setCellValue(scriptName);
            celltoupdate = row.createCell(1);
            celltoupdate.setCellValue(currentStatus);
            celltoupdate = row.createCell(2);
            celltoupdate.setCellValue(reportPath);
            inputStream.close();
            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
        }
    }

    public static void updateResult(String currentStatus) {

        if (System.getProperty("os.name").contains("Windows")) {
            excelFilePath = System.getProperty("user.dir") + "\\Execution_Summary\\Execution_Summary_Report.xlsx";
        } else {
            excelFilePath = System.getProperty("user.dir") + "/Execution_Summary/Execution_Summary_Report.xlsx";
        }
        try {
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = WorkbookFactory.create(inputStream);

            Sheet sheet = workbook.getSheet("Summary_Report");
            int rowCount = sheet.getLastRowNum();
            //int rowCount = TCCount;
            Cell cell2Update = sheet.getRow(rowCount).getCell(0);
            String testScript = cell2Update.getStringCellValue();
            //System.out.println(testScript);
            //System.out.println(currentStatus);
            if (!testScript.contains("Login") && (currentStatus.equalsIgnoreCase("Success"))) {
                cell2Update = sheet.getRow(rowCount).getCell(1);
                cell2Update.setCellValue("Pass");
            } else {
                cell2Update = sheet.getRow(rowCount).getCell(1);
                cell2Update.setCellValue("Fail");
            }

            inputStream.close();
            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
        }
    }


}



