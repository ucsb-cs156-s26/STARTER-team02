package edu.ucsb.cs156.example;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class WebTestCase {
  @LocalServerPort protected int port;

  @Value("${app.playwright.headless:true}")
  private boolean runHeadless;

  protected Browser browser;
  protected Page page;

  @BeforeEach
  public void setup() {
    browser =
        Playwright.create()
            .chromium()
            .launch(new BrowserType.LaunchOptions().setHeadless(runHeadless));

    BrowserContext context = browser.newContext();
    page = context.newPage();
  }

  @AfterEach
  public void teardown() {
    page.close();
    browser.close();
  }
}
