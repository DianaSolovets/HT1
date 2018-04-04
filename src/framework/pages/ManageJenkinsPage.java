import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ManageJenkinsPage {

	private WebDriver driver;

	@FindBy(xpath = "//a[@title='Управление пользователями']//dt")
	private WebElement manageUsersLink;

	@FindBy(xpath = "//a[@title='Управление пользователями']//dd")
	private WebElement manageUsersReadonlyText;

	public ManageJenkinsPage (WebDriver driver){
		this.driver = driver;

		if((!driver.getTitle().equals("Настройка Jenkins [Jenkins]")) || !driver.getCurrentUrl().equals("http://localhost:8080/manage")) {
			throw new IllegalStateException("Открыта неверная страница.");
		}
	}

	public boolean isManageUsersLinkPresented(){
		return manageUsersLink.isDisplayed();
	}

	public String getManageUsersLinkDescription(){
		return manageUsersReadonlyText.getText();
	}

	public UsersPage manageUsersLinkClick(){
		manageUsersLink.click();
		return PageFactory.initElements(driver, UsersPage.class);
	}
}
