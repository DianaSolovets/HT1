import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class tests {

	private ChromeDriver driver;
	private MainPage mainPage;

	@BeforeSuite
	public void startBrowser() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Gleb\\IdeaProjects\\HT1\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(3, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.get("http://localhost:8080");

		mainPage = PageFactory.initElements(driver, LoginPage.class)
				.login("diana.solovets@gmail.com", "Bastard85");
	}

	@BeforeMethod
	public void navigateToMainPage() {
		driver.navigate().to("http://localhost:8080");
	}

	@AfterSuite
	public void closeBrowser() {
		driver.quit();
	}

	@Test
	public void elementsDtAndDdArePresentedTest() {
		ManageJenkinsPage manageJenkinsPage = mainPage.clickManageJenkins();

		Assert.assertTrue(manageJenkinsPage.isManageUsersLinkPresented(), "На странице не появляется элемент dt с текстом «Manage Users»");
		Assert.assertEquals(manageJenkinsPage.getManageUsersLinkDescription(), "Создание, удаление и модификция пользователей, имеющих право доступа к Jenkins", "На странице не появляется элемент dd с текстом «Create/delete/modify users that can log in to this Jenkins»");
	}

	@Test
	public void createUserLinkIsEnabledTast() {
		UsersPage usersPage = mainPage.clickManageJenkins().manageUsersLinkClick();
		Assert.assertTrue(usersPage.createUserLinkIsEnabled(), "Ссылка «Create User недоступна.»");
	}

	@Test
	public void typeAndNumberOfFieldsTest() {
		AddUserPage addUserPage = mainPage.clickManageJenkins().manageUsersLinkClick().createUserLinkClick();
		List <WebElement> textFields = addUserPage.getFieldsByType("text");
		List <WebElement> passwordFields = addUserPage.getFieldsByType("password");

		Assert.assertEquals(textFields.size(), 3, "Страница не содержит 3 элемента типа text");
		Assert.assertEquals(passwordFields.size(), 2, "Страница не содержит 2 элемента типа password");

		for (WebElement textField: textFields){
			String value = textField.getAttribute("value");
			Assert.assertTrue(value.equals(""), "Текстовые поля не пустые.");
		}

		for (WebElement passwordField: passwordFields){
			String value = passwordField.getAttribute("value");
			Assert.assertTrue(value.equals(""), "Поля для ввода пароля не пустые.");
		}
	}

	@Test
	public void elementsTrAndTdArePresentedTest() {
		String username = "someuser" + new Date().getTime();
		String password = "somepassword";
		String fullname = "Some Full Name";
		String email = "some@addr.dom";

		AddUserPage addUserPage = mainPage.clickManageJenkins().manageUsersLinkClick().createUserLinkClick();

		UsersPage usersPage = addUserPage.createUser(username, password, fullname, email, true);
		Assert.assertTrue(usersPage.isUserPresented(username), "На странице отсутствует строка таблицы, в которой находится ячейка с текстом «" + username + "».");

		DeleteSomeUserPage deleteSomeUserPage = usersPage.deleteUser(username);
		deleteSomeUserPage.confirmDeleteUser();
	}

	@Test
	public void createUserWithEmptyFieldFullnameTest() {
		String username = "someuser" + new Date().getTime();
		String password = "somepassword";
		String email = "some@addr.dom";
		String error = "\"\" is prohibited as a full name for security reasons.";

		AddUserPage addUserPage = mainPage.clickManageJenkins().manageUsersLinkClick().createUserLinkClick();
		UsersPage usersPage = addUserPage.createUser(username, password, "", email, false);
		Assert.assertTrue(addUserPage.isErrorMessagePresented(), "На странице отсутствует текст «" + error + "»");
	}

	@Test
	public void textAboutDeletingUserIsPresentedTest() {
		String username = "someuser" + new Date().getTime();
		String password = "somepassword";
		String fullname = "Some Full Name";
		String email = "some@addr.dom";

		AddUserPage addUserPage = mainPage.clickManageJenkins().manageUsersLinkClick().createUserLinkClick();
		UsersPage usersPage = addUserPage.createUser(username, password, fullname, email, true);
		DeleteSomeUserPage deleteSomeUserPage = usersPage.deleteUser(username);
		Assert.assertTrue(deleteSomeUserPage.isQuestionPresented(), "На странице не появляется текст «Are you sure about deleting the user from Jenkins?».");
		deleteSomeUserPage.confirmDeleteUser();
	}

	@Test
	public void elementsTrAndTdWithUsernameAreNotPresentedTest()  {
		String username = "someuser" + new Date().getTime();
		String password = "somepassword";
		String fullname = "Some Full Name";
		String email = "some@addr.dom";

		AddUserPage addUserPage = mainPage.clickManageJenkins().manageUsersLinkClick().createUserLinkClick();
		UsersPage usersPage = addUserPage.createUser(username, password, fullname, email, true);
		DeleteSomeUserPage deleteSomeUserPage = usersPage.deleteUser(username);
		deleteSomeUserPage.confirmDeleteUser();
		List<WebElement> elementsDel = driver.findElements(By.cssSelector("a[href*='user/" + username + "/delete']"));
		List<WebElement> elementsTd = driver.findElements(By.xpath("//tr/td[contains(.,'" + username + "')]"));
		Assert.assertEquals(elementsDel.size(), 0, "На странице присутствует элемент со ссылкой с атрибутом href равным «user/someuser/delete».");
		Assert.assertEquals(elementsTd.size(),0, "На странице присутствует строка таблицы, в которой находится ячейка с текстом «" + username + "».");
	}

	@Test
	public void linkDeleteAdminIsNotPresentedTest() {
		String username = "someuser" + new Date().getTime();
		String password = "somepassword";
		String fullname = "Some Full Name";
		String email = "some@addr.dom";

		AddUserPage addUserPage = mainPage.clickManageJenkins().manageUsersLinkClick().createUserLinkClick();
		UsersPage usersPage = addUserPage.createUser(username, password, fullname, email, true);
		DeleteSomeUserPage deleteSomeUserPage = usersPage.deleteUser(username);
		deleteSomeUserPage.confirmDeleteUser();
		List<WebElement> elementsDel = driver.findElements(By.cssSelector("a[href*='user/admin/delete']"));
		Assert.assertEquals(elementsDel.size(), 0,"На странице присутствует ссылка с атрибутом href равным «user/admin/delete».");
	}

	@Test
	public void cyclingAutoRefreshLinkTest() {
		Assert.assertTrue(mainPage.autoRefreshEnableLinkClick().autoRefreshDisableLinkIsEnabled());
		Assert.assertTrue(mainPage.autoRefreshDisableLinkClick().autoRefreshEnableLinkIsEnabled());
	}
}
