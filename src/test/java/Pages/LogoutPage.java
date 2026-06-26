package Pages;

import net.bytebuddy.asm.Advice;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LogoutPage {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(id = "react-burger-menu-btn")
    WebElement menuBtn;
    @FindBy(css = "#logout_sidebar_link")
    WebElement logoutBtn;
    @FindBy(css = "#login_button_container > div > form > div.error-message-container.error")
    WebElement msgErreur;

    @FindBy(css = "#login_credentials > h4")
    WebElement ReferenceLoginPAge;

    public LogoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void clickMenuBtn() {
        menuBtn.click();
    }

    public void clickLogoutBtn() {

        wait.until(ExpectedConditions.visibilityOf(logoutBtn));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", logoutBtn);
        js.executeScript("arguments[0].click();", logoutBtn);
    }

    public void navigateToPreviousPage() {
        driver.navigate().back();
    }

    public String getErreurmsg() {
        wait.until(ExpectedConditions.visibilityOf(msgErreur));
        String Alerte = msgErreur.getText();

        return Alerte;
    }

    public String getReferenceLoginPage() {
        wait.until(ExpectedConditions.visibilityOf(ReferenceLoginPAge));
        String Title = ReferenceLoginPAge.getText();

        return Title;

    }


}




