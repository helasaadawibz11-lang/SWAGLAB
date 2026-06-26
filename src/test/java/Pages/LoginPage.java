package Pages;

import net.bytebuddy.asm.Advice;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class LoginPage {
    WebDriver driver ;
    WebDriverWait wait ;

    @FindBy (id ="user-name" )
    WebElement Usernameinput ;
    @FindBy (id = "password")
    WebElement passwordinput;
    @FindBy (id="login-button")
    WebElement BtnLogin ;
    @FindBy (css = "#login_button_container > div > form > div.error-message-container.error")
    WebElement msgErreur ;
    @FindBy(css = "#header_container > div.header_secondary_container > span")
    WebElement TitleHomePage;
    @FindBy(css ="#login_credentials > h4" )
    WebElement ReferenceLoginPAge ;

    public LoginPage (WebDriver driver ){
        this.driver=driver;
        PageFactory.initElements(driver,this);
        this.wait=new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public void enterusername (String username){
       Usernameinput.clear();
        Usernameinput.sendKeys(username);
    }

    public void enterpassword (String password){
       passwordinput.clear();
        passwordinput.sendKeys(password);
    }
    public void Login (){BtnLogin.click();
    }

    public String getmessage (){
        wait.until(ExpectedConditions.visibilityOf(msgErreur));
        String Alerte = msgErreur.getText();

        return Alerte ;
    }
    public String getTitleHomePage(){

        wait.until(ExpectedConditions.visibilityOf(TitleHomePage));
        String Title = TitleHomePage.getText();

        return Title ;
    }
    public String getReferenceLoginPage(){
        wait.until(ExpectedConditions.visibilityOf(ReferenceLoginPAge));
        String Title = ReferenceLoginPAge.getText();

        return Title ;

    }
    public void PressEnter (){
        passwordinput.sendKeys(Keys.ENTER);
    }

    public String getTypePassword(){
        String Value= passwordinput.getAttribute("type");
        return Value ;
    }


}
