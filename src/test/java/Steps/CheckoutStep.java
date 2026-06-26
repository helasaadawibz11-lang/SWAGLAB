package Steps;

import Pages.CheckoutPage;
import Pages.ProductPage;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class CheckoutStep {
    WebDriver driver;
    CheckoutPage checkoutpage ;
    ProductPage productPage;



    @Given("j'ai des articles dans mon panier et je suis sur mon panier")
    public void jAiDesArticlesDansMonPanierEtJeSuisSurMonPanier() {
        this.driver=Hook.driver;
        checkoutpage=new CheckoutPage(driver);
        //  On utilise ProductPage car visuellement, on est encore sur le panier
        productPage = new ProductPage(driver);
        productPage.AddTocart();
        productPage.AddtocartDeuxiemeproduit();
        productPage.ClickPanier();
        System.out.println("je suis sur le panier qui contient mes produits ajoutés ");
    }
    @When("je click checkout")
    public void jeClickCheckout() {
        checkoutpage.ClickCheckout();
        System.out.println("click bouton checkout reussi , on passe au checkout ! ");

    }

    @And("je saisi Firstname {string}")
    public void jeSaisiFirstname(String name) {checkoutpage.nameinput(name);
    }
    @And("je saisi Lastname {string}")
    public void jeSaisiLastname(String last) {checkoutpage.lastnameinput(last);}

    @And("je saisi Codepostal {string}")
    public void jeSaisiCodepostal(String code) {checkoutpage.codeinput(code);

    }

    @And("je click Continue")
    public void jeClickContinue() {checkoutpage.ClickContinue();
    }

    @And("je click Finish")
    public void jeClickFinish() {checkoutpage.ClickFinish();}

    @Then("successful checkout")
    public void successfulCheckout() {

        String Actualmsg =  checkoutpage.getMsgSuccessfulCheckout();
        String ActualLink = driver.getCurrentUrl();
        Assert.assertEquals("Finish BTN failed ", "https://www.saucedemo.com/checkout-complete.html", ActualLink);
        Assert.assertEquals("msg Successful checkout is missed", "Thank you for your order!", Actualmsg);
        System.out.println("Apres validation le checkout est reussi , Successful msg lu sur site : "+Actualmsg);

    }

    @Then("Checkout failed affichage msg {string}")
    public void cehckoutFailedAffichageMsg(String Expectedmsg) {

        String ActualMsg = "";

        try {
            // recuperation message
            ActualMsg = checkoutpage.getmsgfailedcheckout();
            System.out.println("L'alerte lue sur le site est : " + ActualMsg);

            // Si l'élément est trouvé, on fait la validation standard
            Assert.assertTrue("Le message d'erreur attendu n'est pas affiché parfaitement. Attendu : ["
                            + Expectedmsg + "] mais obtenu : [" + ActualMsg + "]",
                    ActualMsg.contains(Expectedmsg));

        } catch (Exception e) {
            // Si l'élément n'existe pas du tout sur la page (TimeoutException / NoSuchElementException)
            Assert.fail("❌ Test Échoué : Le message d'erreur attendu [" + Expectedmsg + "] n'est pas affiché sur le site (aucun bloc d'alerte détecté).");
        }
    }

    @Then("le total checkout est correct")
    public void leTotalCheckoutEstCorrect() {

      double TotalPriceItemsPanier = checkoutpage.CalculPriceitemsPanier();
        System.out.println("total panier est" +TotalPriceItemsPanier);
        double TotalPanierAvanttax= checkoutpage.getTotalPAnierAvantTax();
        double TaxPanier= checkoutpage.getTaxPAnier();
        double TotalFinalPanier= checkoutpage.getTotalFinalPanier();

        double TotalTheoriquePanier = TotalPanierAvanttax + TaxPanier ;

        // comparaison calcul total items dans le panier avec le total panier avant tax affiché sur site
        Assert.assertEquals("Total Panier avant tax est erroné", TotalPriceItemsPanier, TotalPanierAvanttax, 0.01);

        System.out.println("apres validation des données , total panier avant tax "+TotalPanierAvanttax + "correspond au total des prix produits dans mon panier "+TotalPriceItemsPanier);

        //Comparaison Total final panier avec tax affiché sur site avec le calcul theorique (tot panier+ tax du checkout)
        Assert.assertEquals("Total Final panier apres les tax est erroné ", TotalFinalPanier, TotalTheoriquePanier, 0.01);

        System.out.println("apres validation des données , total panier final dans le site avec les tax  "+TotalFinalPanier + "correspond au total theorique du panier avec les tax  "+TotalTheoriquePanier);

    }


    @Then("les données Payment Information ,Shipping Information et Price Total sont disponibles")
    public void lesDonnéesPaymentInformationShippingInformationEtPriceTotalSontDisponibles() {

        String paymentinfo = checkoutpage.getPaymentInformation();
        String ShippingInfo = checkoutpage.getShippingInfo();
        String PriceInfo = checkoutpage.getPRiceTotalInfo();

        System.out.println("les données de paiment dans l'interface checkout sont disponibles " +
                "et recuperables et sont affichées comme suit " + " \n "
        +paymentinfo + " \n " +ShippingInfo+ " \n " +PriceInfo );

    }

    @And("je click sur le bouton Cancel")
    public void jeClickSurLeBoutonCancel() {
       checkoutpage.ClickCancelCheckout() ;
       System.out.println("click sur le bouton cancel checkout reussi ... ");
    }

    @Then("je suis redirigé vers la page du panier")
    public void jeSuisRedirigéVersLaPageDuPanier() {

        String URLEXPECTED = "https://www.saucedemo.com/cart.html";
        String ActualURL = driver.getCurrentUrl();

        Assert.assertEquals("redirection vers le panier est echoué ",URLEXPECTED,ActualURL);
    }


    List<String> VerifNamesPanier ;
    @When("je recupere les noms des articles de mon panier")
    public void jeRecupereLesNomsDesArticlesDeMonPanier() {

         VerifNamesPanier = checkoutpage.getItemsNames();
        System.out.println(" les articles disponibles dans mon panier sont : " + VerifNamesPanier );

    }

    @And("mon panier affiche toujours les articles ajoutés")
    public void monPanierAfficheToujoursLesArticlesAjoutés() {
        List<String> ActualNamesPanier = checkoutpage.getItemsNames();

        //validation
        Assert.assertEquals("les produits paniers sont changés" , ActualNamesPanier,VerifNamesPanier);

        //log de clarification test
        System.out.println("apres validations des données , les articles paniers persistent  dans mon panier méme apres annulation checkout " + " \n "  +"articles dans mon panier avant checkout : " +VerifNamesPanier
        + " \n " + " articles dans mon panier aprés annulation checkout et retour au panier : " +ActualNamesPanier );
    }

    @And("je j'accede au panier")
    public void jeJAccedeAuPanier() {
        productPage = new ProductPage(driver);
        productPage.ClickPanier();
        System.out.println("Panier ouvert ! ");
    }



    @And("je click sur le bouton Back Home")
    public void jeClickSurLeBoutonBackHome() {
        checkoutpage.BackhomeClick() ;
        System.out.println("Checkout complete , the Back home button is clicked ! ");
    }

    @And("Mon panier est vide apres validation achat")
    public void monPanierEstVideApresValidationAchat() {
        productPage = new ProductPage(driver);
        productPage.ClickPanier();
        System.out.println("Panier ouvert ! ");
        List<String> ActualNamesPanier = checkoutpage.getItemsNames();

        //validation
        Assert.assertTrue("Mon panier n'est pas vide " , ActualNamesPanier.isEmpty());
        System.out.println("apres validation des données panier ,Mon panier est vide ");


    }

    @Given("je suis sur la page d'accueil avec un panier vide")
    public void jeSuisSurLaPageDAccueilAvecUnPanierVide() {
        this.driver=Hook.driver;
        checkoutpage=new CheckoutPage(driver);
        //  On utilise ProductPage car visuellement, on est encore sur le panier
        productPage = new ProductPage(driver);
        productPage.ClickPanier();
        System.out.println("Panier ouvert ! ");
        List<String> ActualNamesPanier = checkoutpage.getItemsNames();

        //validation
        Assert.assertTrue("Mon panier n'est pas vide " , ActualNamesPanier.isEmpty());
        System.out.println("apres validation des données panier ,Mon panier est vide ");    }



    @Then("le système bloque le checkout avec la non redirection vers la page Checkout Complete")
    public void leSystèmeBloqueLeCheckoutAvecLaNonRedirectionVersLaPageCheckoutComplete() {
        String ActualURL = driver.getCurrentUrl();
        System.out.println(" l'url actuel de la page est : "+ActualURL);
        String ExpectedURL="https://www.saucedemo.com/checkout-step-two.html";

        Assert.assertEquals("Apres validation des URL ,le Blocage checkout n'est pas effectué a la phase 2 ", ExpectedURL,ActualURL);


    }

    @And("je fais un retour arrière avec le navigateur")
    public void jeFaisUnRetourArrièreAvecLeNavigateur() {
        driver.navigate().back();
        System.out.println("click bouton retour page precedante reussi ! ");
    }
}
