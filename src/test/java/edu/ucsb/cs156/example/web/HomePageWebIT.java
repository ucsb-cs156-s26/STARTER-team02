package edu.ucsb.cs156.example.web;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import edu.ucsb.cs156.example.WebTestCase;
import org.junit.jupiter.api.Test;

public class HomePageWebIT extends WebTestCase {
  @Test
  public void home_page_shows_greeting() throws Exception {
    String url = String.format("http://localhost:%d/", port);
    page.navigate(url);

    assertThat(
            page.getByText(
                "This is a webapp containing a bunch of different Spring Boot/React examples."))
        .isVisible();
  }
}
