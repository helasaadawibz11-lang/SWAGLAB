package Pages;

import org.junit.Assert;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductPage {
    WebDriver driver;
    WebDriverWait wait;

    By AddtocartBtn= By.id("add-to-cart-sauce-labs-backpack");
    By Panier=By.cssSelector("#shopping_cart_container > a");
    By ItemnamePanier=By.cssSelector("#item_4_title_link > div");
    By ItemnameProduct1=By.cssSelector("#item_4_title_link > div");
    @FindBy(css = "#item_4_title_link > div")
           List<WebElement>  ItemnameProduct;

    By product= By.cssSelector("#item_5_title_link > div");
    By badge = By.cssSelector("#shopping_cart_container > a > span");
    By deuxiemeproduit=By.cssSelector("#add-to-cart-test\\.allthethings\\(\\)-t-shirt-\\(red\\)");
    By RemoveBtn =By.id("remove-sauce-labs-backpack");
    //By Filtreicon =By.cssSelector("#header_container > div.header_secondary_container > div > span > select");
    @FindBy(css ="#header_container > div.header_secondary_container > div > span > select")
          WebElement   Filtreicon ;
    By ZTOA =By.cssSelector("#header_container > div.header_secondary_container > div > span > span");
    By productnames =By.className("inventory_item_name");
    By CheckoutBtn= By.id("checkout");
    By Firstname = By.id("first-name");
    By Lastname=By.id("last-name");
    By codepostal=By.id("postal-code");
    By ContinueBtn =By.id("continue");
    By Finish=By.id("finish");
    By MsgSuccesfulCheckout=By.cssSelector("#checkout_complete_container > h2");
    By msgcheckoutfailed =By.xpath("//*[@id=\"checkout_info_container\"]/div/form/div[1]/div[4]/h3");
    //By itemprice = By.className("inventory_item_price");
    By itemprice = By.xpath("//div[@class='inventory_item_price']");
    By totalavantTax=By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[2]/div[6]");
    By Tax =By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[2]/div[7]");
    By TotalFinal=By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[2]/div[8]");


    public ProductPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
        wait=new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public void AddTocart(){
       wait.until(ExpectedConditions.visibilityOfElementLocated(AddtocartBtn));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", driver.findElement(AddtocartBtn));}

    public  void ClickPanier(){
       wait.until(ExpectedConditions.visibilityOfElementLocated(Panier));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", driver.findElement(Panier));
    }
    public void verifPanier (){
        String ActualLink= driver.getCurrentUrl();
        Assert.assertEquals("panier non ouvert", "https://www.saucedemo.com/cart.html",ActualLink);
    }
    public void VerifAjoutProduitPanier(){
        String Actualproduct=driver.findElement(ItemnamePanier).getText();
        String Expectedproduct=driver.findElement(ItemnameProduct1).getText();

        Assert.assertEquals("Ajout echoué",Expectedproduct,Actualproduct);
    }

    public void clickProduit(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(product)).click();
    }

    public String getBadgeText(){
        if(driver.findElements(badge).size() == 0){
            return "0";
        }
        return wait.until(ExpectedConditions.visibilityOfElementLocated(badge)).getText();
    }
    public int getnumberBadge(){
        if(driver.findElements(badge).size() == 0){
        return 0;
    }
        return Integer.parseInt(getBadgeText());
    }
    public void VerifBadgeisEmpty(){
        Assert.assertEquals("panier pas vide",0,getnumberBadge());
    }
    public void VerifBadge(){

        Assert.assertEquals("notif failed","1",getBadgeText());
    }
    public void AddtocartDeuxiemeproduit(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(deuxiemeproduit));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", driver.findElement(deuxiemeproduit));
    }
    public  void ClickRemoveBtn(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(RemoveBtn)).click();
    }
    public void VerifProduitSupprime(){
        /*if(driver.findElements(ItemnameProduct).size() == 0){
            return "ok, produit supprimé";
        }
        return "produit trouvé"; */
    Assert.assertTrue(ItemnameProduct.isEmpty());
    }

    public void ClickFilter(){
       // wait.until(ExpectedConditions.visibilityOfElementLocated(Filtreicon)).click();
        Select S =new Select(Filtreicon);
        S.selectByVisibleText("Name (Z to A)");
    }
    //public void clickOptionZTOA(){
       // wait.until(ExpectedConditions.visibilityOfElementLocated(ZTOA)).click();}

    public void VerifFiltrerFromZToA(){
        List<WebElement> elements=driver.findElements(productnames);
        List<String> ActualNames=new ArrayList<>();
        for (WebElement e1:elements){
            ActualNames.add(e1.getText());
        }
        List<String> ExpectedList=new ArrayList<>(ActualNames);
        ExpectedList.sort(Collections.reverseOrder());
        Assert.assertEquals("Sort from Z to A failed", ExpectedList,ActualNames);

    }
    public  void ClickCheckout(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(CheckoutBtn));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", driver.findElement(CheckoutBtn));
    }
    public void verifcheckoutPage(){
        String ActualLink= driver.getCurrentUrl();
        Assert.assertEquals("","https://www.saucedemo.com/checkout-step-one.html",ActualLink);
    }
    public void nameinput (String name ){
      WebElement Name=  wait.until(ExpectedConditions.visibilityOfElementLocated(Firstname));
        //Name.clear();
        Name.sendKeys(name);
        //WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(Firstname));
        //JavascriptExecutor js = (JavascriptExecutor) driver;
        //js.executeScript("arguments[0].value=arguments[1];", element, name);
    }
    public void lastnameinput (String last ){
       WebElement LAST= wait.until(ExpectedConditions.visibilityOfElementLocated(Lastname));
       //LAST.clear();
       LAST.sendKeys(last);
       // WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(Lastname));
       // JavascriptExecutor js = (JavascriptExecutor) driver;
       // js.executeScript("arguments[0].value=arguments[1];", element, last);
    }
    public void codeinput (String code ){
        WebElement CODE=wait.until(ExpectedConditions.visibilityOfElementLocated(codepostal));
        //CODE.clear();
        CODE.sendKeys(code);
        //WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(codepostal));
        //JavascriptExecutor js = (JavascriptExecutor) driver;
        //js.executeScript("arguments[0].value=arguments[1];", element, code);
    }
    public  void ClickContinue(){
        wait.until(ExpectedConditions.elementToBeClickable(ContinueBtn)).click();
        //wait.until(ExpectedConditions.elementToBeClickable(ContinueBtn));
       //JavascriptExecutor js = (JavascriptExecutor) driver;
        //js.executeScript("arguments[0].click();", driver.findElement(ContinueBtn));
    }
    public void VerifClickcontinue(){
        String Actuallink = driver.getCurrentUrl();
        Assert.assertEquals("continue BTN failed ","https://www.saucedemo.com/checkout-step-two.html",Actuallink);
    }
    public  void ClickFinish(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(Finish)).click();
    }
    public void SuccessfulCheckout(){
        String ActualLink= driver.getCurrentUrl();
        Assert.assertEquals("Finish BTN failed ","https://www.saucedemo.com/checkout-complete.html",ActualLink);
        String Actualmsg = driver.findElement(MsgSuccesfulCheckout).getText();
        Assert.assertEquals("msg missed","Thank you for your order!",Actualmsg);

    }
    public String getmsgfailedcheckout(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(msgcheckoutfailed)).getText();

    }
    public double calculTotalPanier (){
        List<WebElement> priceitems = driver.findElements(itemprice);

        double total = 0;

        for (WebElement e : priceitems) {
            double price = Double.parseDouble(e.getText().replace("$", ""));
            total += price;
        }

        return total;
    }
    public void veriftotalprice(){

        double TotalAvantTax= Double.parseDouble(driver.findElement(totalavantTax).getText().replace("Item total: $", ""));
        Assert.assertEquals("total erroné",calculTotalPanier(),TotalAvantTax);
        double tax1= Double.parseDouble(driver.findElement(Tax).getText().replace("Tax: $", ""));
        double Totalfinal1= TotalAvantTax+tax1;
        double totalfinalAffiché= Double.parseDouble(driver.findElement(TotalFinal).getText().replace("Total: $", ""));
        Assert.assertEquals("totalfinalfailed",Totalfinal1,totalfinalAffiché,0.01);
    }
    public boolean verifierImagePresente(String imageRefPath) {
        // 1. Prendre une capture d'écran avec Selenium
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        Mat scene = Imgcodecs.imread(scrFile.getAbsolutePath());
        Mat template = Imgcodecs.imread(imageRefPath);

        // 2. Comparaison d'images

        Mat result = new Mat();
        Imgproc.matchTemplate(scene, template, result, Imgproc.TM_CCOEFF_NORMED);

        // 3. Vérifier le score (0.9 = 90% de ressemblance)
        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
        // --- AJOUT : Affichage dans la console ---
        // On multiplie par 100 pour avoir un pourcentage plus lisible
        double scorePourcentage = mmr.maxVal * 100;
        System.out.println("--------------------------------------------");
        System.out.println("Analyse OpenCV terminée !");
        System.out.println("Image recherchée : " + imageRefPath);
        System.out.format("Score de ressemblance : %.2f%%\n", scorePourcentage);
        System.out.println("--------------------------------------------");

        return mmr.maxVal >= 0.9;

    }

}
