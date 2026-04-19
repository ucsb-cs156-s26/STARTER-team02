package edu.ucsb.cs156.example.testconfig;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Profile;

/**
 * This is a service for mocking authentication using wiremock
 *
 * <p>This class relies on property values. For hints on testing, see: <a href=
 * "https://www.baeldung.com/spring-boot-testing-configurationproperties">https://www.baeldung.com/spring-boot-testing-configurationproperties</a>
 */
@Slf4j
@Profile("integration")
@TestComponent
public class WireMockConfigurer {

  @Autowired private WireMockServer wireMockServer;

  public void configure() {
    wireMockServer.stubFor(
        get(urlPathMatching("/oauth/authorize.*"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "text/html")
                    .withBodyFile("login.html")));

    wireMockServer.stubFor(
        post(urlPathEqualTo("/login"))
            .willReturn(
                temporaryRedirect(
                    "{{formData request.body 'form' urlDecode=true}}{{{form.redirectUri}}}?code={{{randomValue length=30 type='ALPHANUMERIC'}}}&state={{{form.state}}}")));

    wireMockServer.stubFor(
        post(urlPathEqualTo("/oauth/token"))
            .willReturn(
                okJson(
                    "{\"access_token\":\"{{randomValue length=20 type='ALPHANUMERIC'}}\",\"token_type\": \"Bearer\",\"expires_in\":\"3600\",\"scope\":\"https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email openid\"}")));

    wireMockServer.stubFor(
        get(urlPathMatching("/userinfo"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                                        {
                                          "sub": "107126842018026740288",
                                          "name": "Chris Gaucho",
                                          "given_name": "Chris",
                                          "family_name": "Gaucho",
                                          "picture": "https://lh3.googleusercontent.com/a/ACg8ocJpOe2SqIpirdIMx7KTj1W4OQ45t6FwpUo40K2V2JON=s96-c",
                                          "email": "cgaucho@ucsb.edu",
                                          "email_verified": true,
                                          "locale": "en",
                                          "hd": "ucsb.edu"
                                        }
                                        """)));
  }

  /** This method will set the WireMock server to return a /userinfo endpoint for an admin. */
  public void setUserAdmin() {
    wireMockServer.stubFor(
        get(urlPathMatching("/userinfo"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                      {
                        "sub": "107126842018026740288",
                        "name": "Admin GaucSho",
                        "given_name": "Admin",
                        "family_name": "Gaucho",
                        "picture": "https://lh3.googleusercontent.com/a/ACg8ocJpOe2SqIpirdIMx7KTj1W4OQ45t6FwpUo40K2V2JON=s96-c",
                        "email": "admingaucho@ucsb.edu",
                        "email_verified": true,
                        "locale": "en",
                        "hd": "ucsb.edu"
                      }
                      """)));
  }
}
