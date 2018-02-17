package pages;

import base.CommonAPI;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends CommonAPI {
    @FindBy(css = "#twotabsearchtextbox")
    public static WebElement search;

    public void searchbar(){
        search.sendKeys("Book",(Keys.ENTER));
    }

}
