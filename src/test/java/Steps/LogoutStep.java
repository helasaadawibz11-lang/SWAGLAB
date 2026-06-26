package Steps;

import Pages.LoginPage;
import Pages.LogoutPage;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LogoutStep {

    WebDriver driver;
    WebDriverWait wait;
    LogoutPage logoutPage;

    @Given("je suis sur la page Home")
    public void jeSuisSurLaPageHome() {
        this.driver = Hook.driver;
        logoutPage = new LogoutPage(driver);
        driver.get("https://www.saucedemo.com/inventory.html");

    }

    @When("je click sur le bouton Menu")
    public void jeClickSurLeBoutonMenu() {
        logoutPage.clickMenuBtn();
        System.out.println("Menu button is clicked  ");
    }

    @And("je click sur le bouton logout")
    public void jeClickSurLeBoutonLogout() {
        logoutPage.clickLogoutBtn();
        System.out.println("Logout button is clicked  ");

    }


    @And("je click sur le bouton retour de la pageweb")
    public void jeClickSurLeBoutonRetourDeLaPageweb() {
        logoutPage.navigateToPreviousPage();
        System.out.println("navigate To Previous Page button is clicked  ");


    }

    @Then("affichage Erreurmsg {string}")
    public void affichageErreurmsg(String Erreurmsg) {

        String ActualAlerte = logoutPage.getErreurmsg();
        String ActualPage = driver.getCurrentUrl();
        String UrlExpected = "https://www.saucedemo.com/";
        Assert.assertTrue("Alerte non affiché ", ActualAlerte.contains(Erreurmsg));
        Assert.assertTrue("page login unreached ", ActualPage.contains(UrlExpected));
        System.out.println("tu es toujours sur la page login , Alerte affiché suite a ton action " + ActualAlerte);

    }

    @And("je ferme l'onglet du navigateur")
    public void jeFermeLOngletDuNavigateur() {

        driver.manage().deleteAllCookies();
        System.out.println("Session effacée (simulation de fermeture d'onglet)");

    }

    @And("je réouvre le site SwagLabs")
    public void jeRéouvreLeSiteSwagLabs() {
        driver.get("https://www.saucedemo.com/");
        System.out.println("la page web est réouverte ");


    }

    @Then("je suis toujours sur la page Login et non connecté")
    public void jeSuisToujoursSurLaPageLoginEtNonConnecté() {
        String Ref = logoutPage.getReferenceLoginPage();
        String Expectedtexte = "Accepted usernames are:";
        Assert.assertTrue("Login page unreached ", Ref.contains(Expectedtexte));
        System.out.println("vous etes toujours sur la page login ");
    }

    @And("je tente daccéder directement à l URL {string}")
    public void jeTenteDaccéderDirectementÀLURL(String url_interne) {
        driver.get(url_interne);
    }

    @And("un msg derreur de session doit s'afficher")
    public void unMsgDerreurDeSessionDoitSAfficher() {

        String Alerte = logoutPage.getErreurmsg();
        System.out.println("alerte affiché sur le site: " + Alerte);

    }
}
