package Tests;

import Tests.BaseTest;
import org.testng.annotations.Test;
import Pages.WebLogin;

public class WebLogintest extends BaseTest {

    WebLogin  WebLogin ;


    @Test(priority = 1)
    public void WithdrawCash_ARC_ARB() {

        if (loginStatus) {
            System.out.println("SuccessfullyLogintoWeb");
        }else {
            System.out.println("Error on Login");
        }
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
