package edu.ucsb.cs156.example.web;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import edu.ucsb.cs156.example.WebTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SwaggerWebIT extends WebTestCase {
  @BeforeEach
  public void setup() {
    super.setup();
    String url = String.format("http://localhost:%d/swagger-ui/index.html", port);
    page.navigate(url);
  }

  @Test
  public void swagger_page_can_be_loaded() throws Exception {
    assertThat(page.getByText("Swagger: UCSB CMPSC 156 team01")).isVisible();

    assertThat(page.getByText("Home Page")).isVisible();

    assertThat(page.getByText("H2 Console (only on localhost)")).isVisible();
  }

  /**
   * This test checks that the Swagger page has a few of then endpoints for the UCSBDiningCommons
   * API. It is probably not necessary to test all controllers or endpoints; we are mainly checking
   * that swagger is appropriate configured and operational. Presumably, if one controller's
   * endpoints are present, then all controllers' endpoints are present. Think of it as a kind of
   * "smoke test" to make sure that Swagger is working, rather than a comprehensive test of the
   * Swagger page.
   */
  @Test
  public void swagger_page_has_endpoints_for_UCSBDiningCommons() throws Exception {

    assertThat(
            page.getByRole(
                AriaRole.LINK,
                new Page.GetByRoleOptions().setName("UCSBDiningCommons").setExact(true)))
        .isVisible();

    assertThat(page.getByText("Get a single commons")).isVisible();

    assertThat(page.getByText("Update a single commons")).isVisible();
  }
}
