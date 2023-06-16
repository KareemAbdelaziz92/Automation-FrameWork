package Tests;


import org.testng.Assert;
import org.testng.annotations.Test;

import Pages.Homepage;
import Pages.Login_Module;

public class login_module_Test extends TestBase {
	Homepage home ; 
	Login_Module login ;

	@Test
	public void Admin_can_login_successfully () {

		//   home = new Homepage(driver) ;
		//  home.open_homepage();

		login = new Login_Module(driver);
		login.Admin_Login();


		Assert.assertTrue(login.SuccessMessage.getText().contains("Satota"));






	}

}
