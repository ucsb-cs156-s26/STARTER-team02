package edu.ucsb.cs156.example.testconfig;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.WireMockServer;

/**
 * This is a service for mocking authentication using wiremock
 *
 * <p>It is a static utility class that stubs out the required OAuth2 endpoints that Spring Security
 * requires to allow users to sign in.
 */
public class WireMockConfigurer {

  public static void loadOAuthStubs(WireMockServer wireMockServer, boolean isAdmin) {
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

    if (isAdmin) {
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
    } else {
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
  }
}
