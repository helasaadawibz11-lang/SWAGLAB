package Steps;

import Pages.CheckoutPage;
import Pages.LoginPage;
import Pages.ProductPage;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductStep {
    WebDriver driver ;
    ProductPage productPage;
    CheckoutPage checkoutpage ;

    int Compteurinitial;
    List<String> listeInitiale;

 // --- AJOUT OPENCV ---
 static { nu.pattern.OpenCV.loadShared(); }

    @Given("je suis sur la page dacceuil")
    public void je_suis_sur_la_page_dacceuil() {

       this.driver=Hook.driver;
        productPage=new ProductPage(driver);
        productPage.getnumberBadge();
        Compteurinitial = productPage.getnumberBadge();
        //On capture l'ordre initial (par défaut A-Z) AVANT de filtrer
        listeInitiale = productPage.getAllproductNames();
        System.out.println("compteur panier initialement = "+Compteurinitial);

    }
    @When("je note l'ordre initial de la liste des produits dans le site")
    public void jeNoteLOrdreInitialDeLaListeDesProduitsDansLeSite() {
        listeInitiale = productPage.getAllproductNames();
        System.out.println("voici l'ordre initial des produits dans le site " +listeInitiale);
    }
    @When("je click sur le bouton AddToCart")
    public void jeClickSurLeBoutonAddToCart() {
     productPage.AddTocart();
     System.out.println("Premier produit ajouté ");



 }

    @And("je click sur le panier")
    public void jeClickSurLePanier() {
        productPage.ClickPanier();

    }

    @Then("produit ajouté au panier avec succés depuis la page d'acceuil")
    public void produitAjoutéAuPanierAvecSuccésDepuisLaPageDAcceuil() {
        String ActuelProduitPanier = productPage.getItemNamePanier();
        String ExpectedProductName = productPage.getItemName();

        Assert.assertEquals("le produit ajouté n'est pas dans mon panier ", ExpectedProductName, ActuelProduitPanier);
        System.out.println("le produit present dans mon panier est " +ActuelProduitPanier);

    }
    @Then("produit ajouté au panier avec succés depuis la page produit")
    public void produitAjoutéAuPanierAvecSuccésDepuisLaPageProduit() {
        String ActuelProduitPanier = productPage.getProduitNAmePanier();
        String ExpectedProductName = productPage.getProduitName();

        Assert.assertEquals("le produit ajouté n'est pas dans mon panier ", ExpectedProductName, ActuelProduitPanier);
        System.out.println("le produit present dans mon panier est " +ActuelProduitPanier);


    }

    @When("je click sur le produit")
    public void je_click_sur_le_produit() {

     String productname= productPage.getProduitName();
     System.out.println(" je choisit le produit : " +productname);
     productPage.clickProduit();

 }

    @When("je click sur le bouton AddToCart du produit")
    public void je_click_sur_le_bouton_add_to_cart_du_produit() {
     productPage.ClickAddtocart();}

    @When("je click sur le panier du produit")
    public void je_click_sur_le_panier_du_produit() {
        productPage.ClickPanier();
    }


    @Then("notification dajout saffiche sur le panier")
    public void notificationDajoutSafficheSurLePanier() {
        String badgeContent = productPage.getBadgeText();
        Assert.assertEquals("notif failed", "1", badgeContent);
        System.out.println("j'ai ajouté un produit a mon panier , notification panier s'affiche = " +badgeContent);


    }

    @Then("incremetation panier reussi compteur panier affiche le chiffre deux")
    public void incremetationPanierReussiCompteurPanierAfficheLeChiffreDeux() {
        int CompteurPanier=productPage.getnumberBadge();
        Assert.assertEquals("incrementation erronée ",Compteurinitial+2,CompteurPanier);
        System.out.println("le compteur affiché sur le panier est " +CompteurPanier);
    }
    @And("je click sur le bouton AddToCart DeuxiemeProduit")
    public void jeClickSurLeBoutonAddToCartDeuxiemeProduit() {
        productPage.AddtocartDeuxiemeproduit();
        System.out.println("deuxiéme produit ajouté ");
        Compteurinitial = productPage.getnumberBadge();
        System.out.println("compteur panier est actuellement =  " +Compteurinitial);

    }
    @And("je click sur Remove")
    public void jeClickSurRemove() {
        productPage.ClickRemoveBtn();
    }

    @Then("produit supprimé et le compteur  se decremente")
    public void produitSuppriméEtLeCompteurSeDecremente() {
        boolean result = productPage.VerifProduitSupprime();
        Assert.assertTrue("suppression echouée  ",result=true);
        System.out.println("le produit a été supprimé avec succés ");
        int CompteurPanier=productPage.getnumberBadge();
        Assert.assertEquals("Decrementation erronée ",0,CompteurPanier);
        System.out.println("le compteur affiché sur le panier est " +CompteurPanier);

    }

    @When("je click sur filtreicon je choisi ZTOA")
    public void jeClickSurFiltreiconJeChoisiZTOA() {
        productPage.ClickFilter();
        System.out.println("Filtre Z à A sélectionné sur le site.");

    }
    @Then("produits filtrés selon Name Z to A")
    public void produitsFiltrésSelonNameZToA() {
        // On récupère la liste réelle lue sur le site via la Page Object
        List<String> actualNamesAfterFiltre = productPage.getAllproductNames();

        //  On prend notre 'listeInitiale' stockée au début, et on la trie en Java en Z-A
         List<String> expectedList = new ArrayList<>(listeInitiale);
        Collections.sort(expectedList, Collections.reverseOrder());

        // Assertion : Le Step valide le comportement attendu
        Assert.assertEquals("Le tri des produits de Z à A sur le site a échoué !", expectedList, actualNamesAfterFiltre);

        System.out.println("Le site a correctement réordonné les éléments de Z à A , voici la liste apres le filtre " +actualNamesAfterFiltre);

    }


 @Then("le badge doit apparaitre au panier et correspendre visuellement a l'image ref")
 public void leBadgeDoitApparaitreAuPanierEtCorrespendreVisuellementALImageRef() {
  // On définit le chemin de notre image de référence (le badge "1" attendu)
  String imageRef = "src/test/resources/images/badge_panier.png";

  // On appelle la méthode OpenCV dans la Page Object
  boolean estPresent = productPage.verifierImagePresente(imageRef);

  Assert.assertTrue("L'icône du panier avec le badge n'a pas été détectée !", estPresent);

 }

    @When("je selectionne un produit")
    public void jeSelectionneUnProduit() {
       String ItemNAme = productPage.getItemName() ;
       System.out.println("le produit selectionné est "+ItemNAme );
    }


    @Then("produit supprimé")
    public void produitSupprimé() {
        boolean result = productPage.VerifProduitSupprime();
        Assert.assertTrue("suppression echouée  ",result=true);
        System.out.println("le produit a été supprimé avec succés ");

    }


    @And("je click sur Remove premier produit ajouté")
    public void jeClickSurRemovePremierProduitAjouté() {
        productPage.ClickRemoveBtn();
        System.out.println("Action : suppression premier produit du panier en cours  ");

    }

    @Then("premier produit supprimé et le compteur  se decremente")
    public void premierProduitSuppriméEtLeCompteurSeDecremente() {
        boolean result = productPage.VerifProduitSupprime();
        Assert.assertTrue("suppression echouée  ",result=true);
        System.out.println("le produit a été supprimé avec succés ");
        int CompteurPanier=productPage.getnumberBadge();
        Assert.assertEquals("Decrementation erronée ",Compteurinitial-1,CompteurPanier);
        System.out.println("le compteur affiché sur le panier est " +CompteurPanier);

    }

    @And("je rafraîchis la page du navigateur")
    public void jeRafraîchisLaPageDuNavigateur() {
        productPage.Refresh();
        System.out.println("action encours : rafraichissement de la page web ");


    }



    @Then("le compteur du panier doit toujours afficher {int}")
    public void leCompteurDuPanierDoitToujoursAfficher(int expected) {

        int CompteurPanier=productPage.getnumberBadge();
        Assert.assertEquals("incrementation erronée ",expected,CompteurPanier);
        System.out.println("le compteur affiché sur le panier est " +CompteurPanier);

    }


    String ExpectedName ;
    String ExpectedDesc;
    String ExpectedPrice ;
    @When("je note le nom, la description et le prix du premier produit")
    public void jeNoteLeNomLaDescriptionEtLePrixDuPremierProduit() {
         ExpectedName = productPage.getItemName() ;
        System.out.println("mon produit choisi est : " +ExpectedName);

         ExpectedDesc = productPage.getDescriptionItem();
        System.out.println("la description du produit choisi est : "+ExpectedDesc);

        ExpectedPrice = productPage.getPriceItem();
        System.out.println("la description du produit choisi est : "+ExpectedPrice);



    }

    @And("je click sur le nom du premier produit")
    public void jeClickSurLeNomDuPremierProduit() {
        productPage.ClickItemNAme();
        System.out.println("Navigation vers la page de détails du produit.");
    }

    @Then("les informations affichées sur la page de détails doivent être identiques")
    public void lesInformationsAffichéesSurLaPageDeDétailsDoiventÊtreIdentiques() {

     String namepageproduct= productPage.getItemNamePageProduct();
        System.out.println("le nom du produit sur la page produit  est : "+namepageproduct);

        String descpageproduct= productPage.getDescriptionPageProduct();
        System.out.println("la description du produit sur la page produit est : "+descpageproduct);

        String pricepageproduct= productPage.getPricePageProduct();
        System.out.println("le prix du produit sur la page produit est : "+pricepageproduct);


        Assert.assertEquals("le nom de la page detail produit est different du nom produit ",namepageproduct,ExpectedName);
     Assert.assertEquals("la description de la page detail produit est differente de la desc produit ",descpageproduct,ExpectedDesc);
     Assert.assertEquals("le prix de la page detail produit est different du prix produit ",pricepageproduct,ExpectedPrice);

        // log de validation

        System.out.println("apres validation des données , les informations sont identiques ! ");

    }


    @And("je clique sur le bouton Back to Products")
    public void jeCliqueSurLeBoutonBackToProducts() {
        productPage.ClickBackToProductsBTN();
        System.out.println("on appuie sur le bouton Back to Products de la page detail produit ");

    }

    @Then("je suis réorienté vers la page d'accueil des produits")
    public void jeSuisRéorientéVersLaPageDAccueilDesProduits() {
        String URLPageAcceuil = "https://www.saucedemo.com/inventory.html";

        String ActualURL= driver.getCurrentUrl();
        Assert.assertEquals("on n'est pas sur la page d'acceuil du site ",URLPageAcceuil,ActualURL);
        System.out.println("apres validation de l'URL , redirection reussi vers la page d'acceuil des produits ");
    }




}
