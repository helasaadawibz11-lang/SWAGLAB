package Steps;

import Pages.LoginPage;
import Pages.LogoutPage;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en_old.Ac;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginStep {
    WebDriver driver  ;
    LoginPage loginPage;

    @Given("je suis sur la page Login")
    public void je_suis_sur_la_page_login() {

        this.driver = Hook.driver;
        loginPage = new LoginPage(driver);
        driver.get("https://www.saucedemo.com/");
    }
    @When("je saisi l'username {string}")
    public void je_saisi_l_username(String username) {
        loginPage.enterusername(username);
    }

    @When("je saisi le mot de passe {string}")
    public void je_saisi_le_mot_de_passe(String password) {
        loginPage.enterpassword(password);
    }

    @When("je click sur le bouton Login")
    public void je_click_sur_le_bouton_login() {loginPage.Login();}

    @Then("redirection vers la page Home")
    public void redirection_vers_la_page_home() {
        String Actuallink= driver.getCurrentUrl();
        String TitlePage= loginPage.getTitleHomePage();
        Assert.assertEquals("login failed","https://www.saucedemo.com/inventory.html",Actuallink);
        System.out.println("Successuful Login ! at the homePage we can see the Section  " +TitlePage);

    }
    @Then("un msg derreur doit safficher {string}")
    public void unMsgDerreurDoitSafficher(String msgErreur) {
        String msgactuel = loginPage.getmessage();
        Assert.assertTrue("Msg d'erreur inadequat ou introuvable ",msgactuel.contains(msgErreur));
        System.out.println("Alerte affiché sur site : " +msgactuel);
    }

    @Given("je tente d'accéder directement à la page {string}")
    public void jeTenteDAccéderDirectementÀLaPage(String URL) {
        this.driver = Hook.driver;
        loginPage = new LoginPage(driver);
        driver.get(URL);

    }

    @Then("je suis redirigé vers la page Login")
    public void jeSuisRedirigéVersLaPageLogin() {

        String Ref= loginPage.getReferenceLoginPage();
        String ActualTexte= "Accepted usernames are:" ;
        Assert.assertTrue("Login page unreached " ,ActualTexte.contains(Ref));
        System.out.println("vous etes sur la page login ");


    }

    @And("j'appuie sur la touche Entrée du clavier")
    public void jAppuieSurLaToucheEntréeDuClavier() {
        loginPage.PressEnter();

    }

    @Then("les caractères saisis dans le champ mot de passe doivent être masqués")
    public void lesCaractèresSaisisDansLeChampMotDePasseDoiventÊtreMasqués() {

        String ActualType = loginPage.getTypePassword();
        String ExpectedType = "password" ;

        Assert.assertTrue("le champs password n'est pas masqué ", ActualType.contains(ExpectedType));
        System.out.println("apres verification le champs password est de type masqué " +ActualType);
    }
}
