package test;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;

public class HomeTest extends HomePage {
    HomePage objHome;

    @BeforeMethod
    public void initialize() {

        objHome=PageFactory.initElements(driver, HomePage.class);
    }
      @Test
    public  void verifySearchBar(){
          objHome.searchbar();
      }
}
