import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DeleteSomeUserPage {

	private WebDriver driver;

	@FindBy(xpath = "//BUTTON[@id='yui-gen3-button']")
	private WebElement confirmDeletingSomeUser;

	public DeleteSomeUserPage (WebDriver driver){
		this.driver = driver;

		if((!driver.getTitle().equals("Jenkins")) || !driver.getCurrentUrl().contains("/delete")) {
			throw new IllegalStateException("Открыта неверная страница.");
		}
	}

	public UsersPage confirmDeleteUser (){
		confirmDeletingSomeUser.click();
		return PageFactory.initElements(driver, UsersPage.class);
	}

	public boolean isQuestionPresented (){
		return driver.getPageSource().contains("Вы уверены, что хотите удалить пользователя из Jenkins?");
	}
}
