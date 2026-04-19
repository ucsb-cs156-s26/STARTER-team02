package edu.ucsb.cs156.example.web;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import edu.ucsb.cs156.example.WebTestCase;
import org.junit.jupiter.api.Test;

public class OauthWebIT extends WebTestCase {
  @Test
  public void regular_user_can_login_logout() throws Exception {
    setupUser(false);

    assertThat(page.getByText("Log Out")).isVisible();
    assertThat(page.getByText("Welcome, cgaucho@ucsb.edu")).isVisible();

    page.getByText("Log Out").click();

    assertThat(page.getByText("Log In")).isVisible();
    assertThat(page.getByText("Log Out")).not().isVisible();
  }
}
