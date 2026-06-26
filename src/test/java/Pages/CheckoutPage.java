package Pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CheckoutPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(id = "checkout")
    WebElement CheckoutBtn;
    @FindBy(id = "first-name")
    WebElement Firstname;
    @FindBy(id = "last-name")
    WebElement Lastname;
    @FindBy(id = "postal-code")
    WebElement codepostal;
    @FindBy(id = "continue")
    WebElement ContinueBtn;
    @FindBy(id = "finish")
    WebElement Finish;
    @FindBy(css = "#checkout_complete_container > h2")
    WebElement MsgSuccesfulCheckout;
    @FindBy(xpath = "//*[@id=\"checkout_info_container\"]/div/form/div[1]/div[4]/h3")
    WebElement msgcheckoutfailed;
    @FindBy(xpath = "//div[@class='inventory_item_price']")
    WebElement itemprice;
    @FindBy(xpath = "//*[@id=\"checkout_summary_container\"]/div/div[2]/div[6]")
    WebElement totalavantTax;
    @FindBy(xpath = "//*[@id=\"checkout_summary_container\"]/div/div[2]/div[7]")
    WebElement Tax;
    @FindBy(xpath = "//*[@id=\"checkout_summary_container\"]/div/div[2]/div[8]")
    WebElement TotalFinal;
    @FindBy(xpath = "//div[@class='inventory_item_price']")
    List<WebElement> priceitems ;
    @FindBy(css = "#checkout_summary_container > div > div.summary_info > div:nth-child(1)")
    WebElement paymentInfoLabel ;
    @FindBy(css = "#checkout_summary_container > div > div.summary_info > div:nth-child(2)")
    WebElement paymentInfoValue ;
    @FindBy(css = "#checkout_summary_container > div > div.summary_info > div:nth-child(3)")
    WebElement shippingInfoLabel ;
    @FindBy(css = "#checkout_summary_container > div > div.summary_info > div:nth-child(4)")
    WebElement shippingInfoValue ;
    @FindBy(css = "#checkout_summary_container > div > div.summary_info > div:nth-child(5)")
    WebElement TotalinfoLabel ;

    @FindBy(id = "cancel")
    WebElement CancelCheckoutBTN ;

    @FindBy(className = "inventory_item_name")
    List<WebElement> NamesItems ;
    @FindBy(id = "back-to-products")
    WebElement BackHomeBtn ;



    public CheckoutPage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    public void ClickCheckout() {
        wait.until(ExpectedConditions.visibilityOf(CheckoutBtn));
        CheckoutBtn.click();
    }



    public void nameinput(String name) {
        WebElement Name = wait.until(ExpectedConditions.visibilityOf(Firstname));
        Name.clear();
        Name.sendKeys(name);

    }

    public void lastnameinput(String last) {
        WebElement LAST = wait.until(ExpectedConditions.visibilityOf(Lastname));
        LAST.clear();
        LAST.sendKeys(last);

    }

    public void codeinput(String code) {
        WebElement CODE = wait.until(ExpectedConditions.visibilityOf(codepostal));
        CODE.clear();
        CODE.sendKeys(code);

    }

    public void ClickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(ContinueBtn)).click();

    }


    public void ClickFinish() {
        wait.until(ExpectedConditions.visibilityOf(Finish)).click();
    }

    public String getMsgSuccessfulCheckout() {

        wait.until(ExpectedConditions.visibilityOf(MsgSuccesfulCheckout));
        String Actualmsg = MsgSuccesfulCheckout.getText();
        return  Actualmsg ;
    }

    public String getmsgfailedcheckout() {
        return wait.until(ExpectedConditions.visibilityOf(msgcheckoutfailed)).getText();

    }

    public double CalculPriceitemsPanier() {

        double total = 0;

        for (WebElement e : priceitems) {
            double price = Double.parseDouble(e.getText().replace("$", ""));
            total += price;
        }

        return total;
    }

    public double getTotalPAnierAvantTax() {

        double TotalAvantTax = Double.parseDouble(totalavantTax.getText().replace("Item total: $", ""));
        return TotalAvantTax ;
    }
    public double getTotalFinalPanier(){
        double totalfinalAffiché = Double.parseDouble(TotalFinal.getText().replace("Total: $", ""));
        return totalfinalAffiché ;

    }
    public double getTaxPAnier(){
        double tax1 = Double.parseDouble(Tax.getText().replace("Tax: $", ""));
        return tax1 ;


    }
    public String getPaymentInformation(){
        String paymentinfo= paymentInfoLabel.getText();
        String paymentvalue=paymentInfoValue.getText();

        return paymentinfo + " : " + paymentvalue;

    }
    public  String  getShippingInfo(){

        String shippinglabel=shippingInfoLabel.getText();
        String shippingValue=shippingInfoValue.getText();
        return shippinglabel + " : " + shippingValue;

    }
    public String getPRiceTotalInfo(){
        String totalPriceInfo= TotalinfoLabel.getText();
        String ItemTotal = totalavantTax.getText();
        String tax=Tax.getText();
        String Total=TotalFinal.getText();

        return totalPriceInfo + " \n " + ItemTotal + " \n " + tax + " \n " + Total  ;    }


    public void ClickCancelCheckout(){

        wait.until(ExpectedConditions.visibilityOf(CancelCheckoutBTN));
        CancelCheckoutBTN.click();

    }

    public List<String> getItemsNames() {
        List<String> names = new ArrayList<>();

        for (WebElement e : NamesItems) {
            String n = e.getText();
            names.add(n);
        }

        return names;
    }

    public void  BackhomeClick (){

        wait.until(ExpectedConditions.visibilityOf(BackHomeBtn));
        BackHomeBtn.click();

    }

}


