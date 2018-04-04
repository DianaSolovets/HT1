import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	private WebDriver driver;

	@FindBy(xpath = "//INPUT[@id='j_username']")
	private WebElement usernameTextBox;

	@FindBy(xpath = "//INPUT[@type='password']")
	private WebElement passwordTextBox;

	@FindBy(xpath = "//BUTTON[@id='yui-gen1-button']")
	private WebElement loginButton;

	public LoginPage (WebDriver driver){
			this.driver = driver;

		if((!driver.getTitle().equals("Jenkins")) || !driver.getCurrentUrl().equals("http://localhost:8080/login?from=%2F")) {
			throw new IllegalStateException("Открыта неверная страница.");
		}
	}

	public MainPage login (String username, String password){
		usernameTextBox.sendKeys(username);
		passwordTextBox.sendKeys(password);
		loginButton.click();
		return PageFactory.initElements(driver, MainPage.class);
	}
}
