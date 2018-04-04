import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SomeUserPage {

	private WebDriver driver;

	@FindBy(partialLinkText = "Удалить")
	private WebElement deleteUser;

	public SomeUserPage (WebDriver driver){
		this.driver = driver;

		if((!driver.getTitle().equals("Some Full Name [Jenkins]")) || !driver.getCurrentUrl().equals("http://localhost:8080/securityRealm/user/someuser/")) {
			throw new IllegalStateException("Открыта неверная страница.");
		}
	}
}
