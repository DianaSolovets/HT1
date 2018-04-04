import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class UsersPage {

	private WebDriver driver;

	@FindBy(xpath = "//A[@href='addUser'][text()='Создать пользователя']")
	private WebElement createUserLink;

	@FindBy(xpath = "//tr/td[contains(.,'someuser')]")
	private WebElement someUser;

	public UsersPage (WebDriver driver){
		this.driver = driver;

		if((!driver.getTitle().equals("Пользователи [Jenkins]")) || !driver.getCurrentUrl().equals("http://localhost:8080/securityRealm/")) {
			throw new IllegalStateException("Открыта неверная страница.");
		}
	}

	public boolean createUserLinkIsEnabled() {
		return createUserLink.isEnabled();
	}

	public AddUserPage createUserLinkClick(){
		createUserLink.click();
		return PageFactory.initElements(driver, AddUserPage.class);
	}

	public boolean isUserPresented(String name){
		List<WebElement> users = driver.findElements(By.xpath("//tr/td[contains(.,'" + name + "')]")); ////tr/td[text()='" + name + "']"
		return users.size() == 1;
	}

	public DeleteSomeUserPage deleteUser (String username){
		driver.findElement(By.cssSelector("a[href*='user/" + username + "/delete']")).click();
		return PageFactory.initElements(driver, DeleteSomeUserPage.class);
	}
}
