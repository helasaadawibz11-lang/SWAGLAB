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

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    WebElement AddtocartBtn;
    @FindBy(css = "#shopping_cart_container > a")
    WebElement Panier;
            @FindBy(linkText = "Sauce Labs Backpack")
    WebElement ItemnamePanier;
            @FindBy(linkText = "Sauce Labs Fleece Jacket")
            WebElement ProduitnamePAnier ;

    @FindBy(css = "#item_4_title_link > div")
    WebElement ItemnameBackPack;

    @FindBy(xpath = "//a[@id='item_4_title_link']/following-sibling::div[@class='inventory_item_desc']")
     WebElement descriptionBackpack;

    @FindBy(xpath = "//a[@id='item_4_title_link']/ancestor::div[@class='inventory_item_description']//div[@class='inventory_item_price']")
    WebElement priceBackpack ;

    @FindBy(css = "#item_4_title_link > div")
    List<WebElement> ItemnameProduct;
    @FindBy(css = "#item_5_title_link > div")
    WebElement product;

    @FindBy(css = "#shopping_cart_container > a > span")
     List<WebElement> cartBadgeList;
    @FindBy(css = "#add-to-cart-test\\.allthethings\\(\\)-t-shirt-\\(red\\)")
    WebElement deuxiemeproduit;
    @FindBy(id = "remove-sauce-labs-backpack")
    WebElement RemoveBtn;
    @FindBy(css = "#header_container > div.header_secondary_container > div > span > select")
    WebElement Filtreicon;
    @FindBy(css = "#header_container > div.header_secondary_container > div > span > span")
    WebElement ZTOA;
    @FindBy(className = "inventory_item_name")
    WebElement productnames;
    @FindBy(className = "inventory_item_name")
    List<WebElement> productNamesElements;

    @FindBy(id = "add-to-cart")
    WebElement BtnAddtocart;

    @FindBy (css = "#inventory_item_container > div > div > div.inventory_details_desc_container > div.inventory_details_name.large_size")
    WebElement BackpackNamePageProduct ;
    @FindBy(css = "#inventory_item_container > div > div > div.inventory_details_desc_container > div.inventory_details_price")
    WebElement BackpackPricePageProduct ;
    @FindBy(css = "#inventory_item_container > div > div > div.inventory_details_desc_container > div.inventory_details_desc.large_size")
    WebElement BackpackDecPageProduct ;
    @FindBy(id = "back-to-products")
    WebElement BackToProductsBTN ;

    @FindBy(id = "react-burger-menu-btn")
    WebElement MenuPanier ;
    @FindBy(id = "logout_sidebar_link")
    WebElement LogoutPanier ;


    public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void AddTocart() {
        wait.until(ExpectedConditions.visibilityOf(AddtocartBtn));

        AddtocartBtn.click();
    }

    public void ClickPanier() {
        wait.until(ExpectedConditions.visibilityOf(Panier));

        Panier.click();
    }



    public String  getItemNamePanier() {
        String ItemNamePanier = ItemnamePanier.getText();
        return ItemNamePanier ;

    }
    public String getProduitNAmePanier(){
        String produitNAmePanier = ProduitnamePAnier.getText();
        return produitNAmePanier ;
    }

    public void ClickItemNAme (){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(ItemnameBackPack));
        ItemnameBackPack.click();

    }

    public void clickProduit() {

        wait.until(ExpectedConditions.visibilityOf(product) );
        product.click();

    }
    public String getProduitName(){
        wait.until(ExpectedConditions.visibilityOf(product) );
        return product.getText();

    }

    public String  getBadgeText() {
        if (cartBadgeList.isEmpty()) {
            return "0";
        }
        //Si le badge est présent, on extrait le texte du premier élément de la liste
        return cartBadgeList.get(0).getText();    }

    public int getnumberBadge() {
        if (cartBadgeList.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(getBadgeText());
    }


    public void AddtocartDeuxiemeproduit() {
        wait.until(ExpectedConditions.visibilityOf(deuxiemeproduit));
        deuxiemeproduit.click();
    }

    public void ClickRemoveBtn() {
        wait.until(ExpectedConditions.visibilityOf(RemoveBtn)).click();
    }

    public boolean VerifProduitSupprime() {

        if (ItemnameProduct.isEmpty()){

            return true ;
        }

       else return false ;
    }

    public void ClickFilter() {

        Select S = new Select(Filtreicon);
        S.selectByVisibleText("Name (Z to A)");
    }

    public List<String> getAllproductNames(){
        List<String> actualNames = new ArrayList<>();
        for (WebElement element : productNamesElements) {
            actualNames.add(element.getText());
        }
        return actualNames;
    }




    public boolean verifierImagePresente(String imageRefPath) {
        // 1. Prendre une capture d'écran avec Selenium
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
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

    public void ClickAddtocart() {
        wait.until(ExpectedConditions.visibilityOf(BtnAddtocart)).click();
    }

    public void Refresh(){

        driver.navigate().refresh();
    }
    public String getItemName(){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(ItemnameBackPack));
        String Itemnameproduct = ItemnameBackPack.getText();
        return Itemnameproduct ;

    }

    public String getDescriptionItem(){

        wait.until(ExpectedConditions.visibilityOf(descriptionBackpack));
        String Description = descriptionBackpack.getText();
        return Description ;

    }

    public String getPriceItem(){

        wait.until(ExpectedConditions.visibilityOf(priceBackpack));
        String price = priceBackpack.getText();
        return price ;
    }

    public String getItemNamePageProduct(){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(BackpackNamePageProduct));
        String Itemnameproduct = BackpackNamePageProduct.getText();
        return Itemnameproduct ;

    }

    public String getDescriptionPageProduct(){

        wait.until(ExpectedConditions.visibilityOf(BackpackDecPageProduct));
        String Description = BackpackDecPageProduct.getText();
        return Description ;

    }

    public String getPricePageProduct(){

        wait.until(ExpectedConditions.visibilityOf(BackpackPricePageProduct));
        String price = BackpackPricePageProduct.getText();
        return price ;
    }

    public void ClickBackToProductsBTN (){
        wait.until(ExpectedConditions.visibilityOf(BackToProductsBTN));
        BackToProductsBTN.click();

    }
    public void ClickMenu (){
        wait.until(ExpectedConditions.visibilityOf(MenuPanier));
        MenuPanier.click();

    }
    public void ClickLogout(){
        wait.until(ExpectedConditions.visibilityOf(LogoutPanier));
        LogoutPanier.click();

    }


}
