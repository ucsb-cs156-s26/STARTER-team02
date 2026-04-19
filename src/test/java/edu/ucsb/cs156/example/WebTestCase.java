package edu.ucsb.cs156.example;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import edu.ucsb.cs156.example.testconfig.WireMockConfigurer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

@ActiveProfiles("integration")
@EnableWireMock(@ConfigureWireMock(port = 0, registerSpringBean = true, globalTemplating = true))
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({WireMockConfigurer.class})
public abstract class WebTestCase {
  @LocalServerPort private int port;

  @Value("${app.playwright.headless:true}")
  private boolean runHeadless;

  @Autowired private WireMockServer wireMockServer;

  @Autowired private WireMockConfigurer wireMockConfigurer;

  protected Browser browser;
  protected Page page;

  @BeforeEach
  public void setup() {
    wireMockConfigurer.configure();
  }

  @AfterEach
  public void teardown() {
    browser.close();
  }

  public void setupUser(boolean isAdmin) {
    if (isAdmin) {
      wireMockConfigurer.setUserAdmin();
    }

    browser =
        Playwright.create()
            .chromium()
            .launch(new BrowserType.LaunchOptions().setHeadless(runHeadless));

    BrowserContext context = browser.newContext();
    page = context.newPage();

    String url = String.format("http://localhost:%d/oauth2/authorization/my-oauth-provider", port);
    page.navigate(url);

    if (isAdmin) {
      page.locator("#username").fill("admingaucho@ucsb.edu");
    } else {
      page.locator("#username").fill("cgaucho@ucsb.edu");
    }

    page.locator("#password").fill("password");
    page.locator("#submit").click();
  }
}
