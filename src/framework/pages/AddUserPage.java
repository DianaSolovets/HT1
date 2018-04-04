import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class AddUserPage {

	private WebDriver driver;

	@FindBy(xpath = "//INPUT[@id='username']")
	private WebElement usernameTextBox;

	@FindBy(xpath = "//INPUT[@name='password1']")
	private WebElement passPasswordBox;

	@FindBy(xpath = "//INPUT[@name='password2']")
	private WebElement confirmPassPasswordBox;

	@FindBy(xpath = "//INPUT[@name='fullname']")
	private WebElement fullnameTextBox;

	@FindBy(xpath = "//INPUT[@name='email']")
	private WebElement emailTextBox;

	@FindBy(css = "div.error")
	private WebElement errorReadonlyText;

	@FindBy(xpath = "//BUTTON[@id='yui-gen3-button']")
	private WebElement createUserButton;

	public AddUserPage(WebDriver driver){
		this.driver = driver;

		if((!driver.getTitle().equals("Создать пользователя [Jenkins]")) || !driver.getCurrentUrl().equals("http://localhost:8080/securityRealm/addUser")) {
			throw new IllegalStateException("Открыта неверная страница.");
		}
	}

	public List<WebElement> getFieldsByType(String type) {
		return driver.findElements(By.xpath("//input[@type='" + type + "']"));
	}

	public UsersPage createUser (String username, String password, String fullname, String email, boolean isSuccessExpected){
		usernameTextBox.sendKeys(username);
		passPasswordBox.sendKeys(password);
		confirmPassPasswordBox.sendKeys(password);
		fullnameTextBox.sendKeys(fullname);
		emailTextBox.sendKeys(email);
		createUserButton.click();
		if (isSuccessExpected){
			return PageFactory.initElements(driver, UsersPage.class);
		}
		return null;

	}

	public boolean isErrorMessagePresented(){
		return driver.getPageSource().contains("\"\" is prohibited as a full name for security reasons.");
	}


}
