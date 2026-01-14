const urlFixtures = {
  fourUrls: {
    _embedded: {
      urls: [
        {
          id: 1,
          url: "https://ucsb.edu",
          shortDescription: "UCSB Home Page",
          longDescription:
            "Home Page for UC Santa Barbara, an R1 public research university",
          _links: {
            self: {
              href: "https://team02-qa.dokku-00.cs.ucsb.edu/api/rest/v1/urls/1",
            },
            url: {
              href: "https://team02-qa.dokku-00.cs.ucsb.edu/api/rest/v1/urls/1",
            },
          },
        },
        {
          id: 2,
          url: "https://github.com",
          shortDescription: "Github Home Page",
          longDescription:
            "Home Page for Github, a webapp for version control and software development (subsidiary of Microsoft)",
          _links: {
            self: {
              href: "https://team02-qa.dokku-00.cs.ucsb.edu/api/rest/v1/urls/2",
            },
            url: {
              href: "https://team02-qa.dokku-00.cs.ucsb.edu/api/rest/v1/urls/2",
            },
          },
        },
        {
          id: 3,
          url: "https://www.baeldung.com/",
          shortDescription: "Baeldung",
          longDescription: "A site with lots of resources for Spring Boot",
          _links: {
            self: {
              href: "https://team02-qa.dokku-00.cs.ucsb.edu/api/rest/v1/urls/3",
            },
            url: {
              href: "https://team02-qa.dokku-00.cs.ucsb.edu/api/rest/v1/urls/3",
            },
          },
        },
        {
          id: 4,
          url: "https://ucsb-cs156.github.io",
          shortDescription: "CS156 Home Page",
          longDescription:
            "Home Page for CMPSC 156, a course in software engineering at UC Santa Barbara",
          _links: {
            self: {
              href: "https://team02-qa.dokku-00.cs.ucsb.edu/api/rest/v1/urls/4",
            },
            url: {
              href: "https://team02-qa.dokku-00.cs.ucsb.edu/api/rest/v1/urls/4",
            },
          },
        },
      ],
    },
    _links: {
      self: {
        href: "https://team02-qa.dokku-00.cs.ucsb.edu/api/rest/v1/urls",
      },
      profile: {
        href: "https://team02-qa.dokku-00.cs.ucsb.edu/api/rest/v1/profile/urls",
      },
    },
  },
};

export { urlFixtures };
