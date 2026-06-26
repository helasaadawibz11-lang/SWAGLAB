package Steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.HashMap;
import java.util.Map;

public class Hook {

    // La variable doit être statique pour être partagée avec LoginSteps
    public static WebDriver driver;
    @Before
    public void setup() {
        if (driver == null) {

            ChromeOptions options = new ChromeOptions();
            // des options ajoutées pour desactiver le check des fuites de données et les bulles d'info

            options.addArguments("--disable-features=SafeBrowsingPasswordCheck");
            options.addArguments("--disable-features=PasswordLeakDetection");
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-notifications");

            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            // Cette ligne empêche spécifiquement la bulle de protection de s'ouvrir
            prefs.put("profile.password_manager_leak_detection", false);

            options.setExperimentalOption("prefs", prefs);

            //on crée le driver avec les options
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();

            //Pour Edge :
            //mvn test -Dbrowser=edge
            // String browser = System.getProperty("browser", "chrome"); // Chrome par défaut

            //if (browser.equalsIgnoreCase("edge")) {
            //  driver = new EdgeDriver();
            // } else {
            //    driver = new ChromeDriver();
            // }

        }
    }

    @After
    public void tearDown(Scenario scenario) {
        // Vérification cruciale : on ne prend la capture QUE si le driver existe
        if (driver != null) {
            if (scenario.isFailed()) {
                try {
                    final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    scenario.attach(screenshot, "image/png", "Bug_Opencart");
                } catch (Exception e) {
                    System.out.println("Erreur lors de la capture : " + e.getMessage());
                }
            }
            // On ferme et on remet à null pour le prochain test
            driver.quit();
            driver = null;
        }
    }
}

