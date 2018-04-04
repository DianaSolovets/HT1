import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {

	private WebDriver driver;

	@FindBy(xpath = "//A[@href='/manage'][text()='Настроить Jenkins']")
	private WebElement manageJenkins;

	@FindBy(partialLinkText = "Включить автообновление страниц")
	private WebElement autoRefreshEnableLink;

	@FindBy(partialLinkText = "Отключить автообновление страниц")
	private WebElement autoRefreshDisableLink;

	public MainPage (WebDriver driver){
		this.driver = driver;

		if((!driver.getTitle().equals("ИнфоПанель [Jenkins]")) || !driver.getCurrentUrl().equals("http://localhost:8080/")) {
			throw new IllegalStateException("Открыта неверная страница.");
		}
	}

	public ManageJenkinsPage clickManageJenkins (){
		manageJenkins.click();
		return PageFactory.initElements(driver, ManageJenkinsPage.class);
	}

	public MainPage autoRefreshEnableLinkClick(){
		autoRefreshEnableLink.click();
		return this;
	}

	public MainPage autoRefreshDisableLinkClick(){
		autoRefreshDisableLink.click();
		return this;
	}

	public boolean autoRefreshEnableLinkIsEnabled(){
		return autoRefreshEnableLink.isEnabled();
	}

	public boolean autoRefreshDisableLinkIsEnabled(){
		return autoRefreshDisableLink.isEnabled();
	}


}
