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
  - Does the page contain a login form? You will need to provide some plausible logic for detecting such a form in at least some cases. Consider the multiple ways (and human languages) that might be used to construct login forms. We expect the login forms on [Spiegel][https://www.spiegel.de/meinspiegel/login.html] and [Github][https://github.com/login] to be detectable.
  
  
  [Spiegel]: <https://github.com/joemccann/dillinger>
  [Github]: <https://github.com/joemccann/dillinger>
