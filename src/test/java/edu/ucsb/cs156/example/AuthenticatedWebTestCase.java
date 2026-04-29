package edu.ucsb.cs156.example;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.ucsb.cs156.example.testconfig.WireMockConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

@EnableWireMock(@ConfigureWireMock(port = 0, registerSpringBean = true, globalTemplating = true))
public abstract class AuthenticatedWebTestCase extends WebTestCase {

  @Autowired private WireMockServer wireMockServer;

  public void setupUser(boolean isAdmin) {

    WireMockConfigurer.loadOAuthStubs(wireMockServer, isAdmin);
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
