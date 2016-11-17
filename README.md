# Page Analyzer

This is a simple web application which provides the following things:
  - The user is presented with a page containing a text field into which an HTTP(s) URL can be entered
  - The form is accompanied by a button to submit the form to the server
  - The server conducts the analysis (described below) and the results are displayed below the form, preferably in a tabular form.

For a valid URL that returns HTML and which is reachable (do not forget to consider redirection!) from the server, the server should extract the following information:
  - HTML version of the document
  - Page title, if any
  - Number of headings grouped by heading level
  - Number of hypermedia links in the document, grouped into "internal" links to the same domain and "external" links to other domains
  - Does the page contain a login form? You will need to provide some plausible logic for detecting such a form in at least some cases. Consider the multiple ways (and human languages) that might be used to construct login forms. We expect the login forms on [Spiegel] and [Github] to be detectable.

The technology stack has been built on the following elements:

- Spring Boot provides the base and the configuration is java-based.
- On the client-side Thymeleaf gives the UI.
- On the backend the requests have been catched by Spring MVC
- For unit and integration tests the project uses jUnit and Mockito.

The Controller (PageController) is quite simple it only catches the requests and passes them down to the service level (PageAnalyzerService).

On the service level, the PageAnalyzerService has only two public methods. The the following processes have been encapsulated by composition:

  - HtmlVersionAnalyzer
  - PageTitleAnalyzer
  - PageHeadingsAnalyzer
  - PageLinkAnalyzer
  - LoginFormRecognizer
  
Some features, solutions which - i think - are worth mentioning:

  - In HtmlVersionAnalyzer i used Enum->Pattern key->value map. It made the code cleaner, and maybe it did not break some KISS, YAGNI rules. Using Pattern instead of Regex makes the application faster.
  - The weak part of the application is the solution for recognizing whether the current page contains login form or does not. Currently it is only search for input fields with password type. If is it contained, then probably we are on a login or sign up page. It's not perferct maybe, it needs to check some keywords which are stored in an i18n message.properties or somewhere.
  
The project has tests on two levels: on controller and service.

  [Spiegel]: <https://www.spiegel.de/meinspiegel/login.html>
  [Github]: <https://github.com/login>
