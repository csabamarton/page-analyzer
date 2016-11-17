package com.csmarton.services;

import com.csmarton.model.Page;
import com.csmarton.services.analyzer.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.net.MalformedURLException;

@Component
public class PageAnalyzerServiceImpl implements PageAnalyzerService {

	@Autowired
	private HtmlVersionAnalyzer htmlVersionAnalyzer;

	@Autowired
	private PageTitleAnalyzer pageTitleAnalyzer;

	@Autowired
	private PageHeadingsAnalyzer pageHeadingsAnalyzer;

	@Autowired
	private PageLinkAnalyzer pageLinkAnalyzer;

	@Autowired
	private LoginFormRecognizer loginFormRecognizer;

	@Override
	public void processLink(Page page, BindingResult errors)
	{
		if (StringUtils.isEmpty(page.getUrl())) {
			errors.rejectValue("url", "error.required", "Url is required!");

			return;
		}

		try {
			Connection.Response response = Jsoup.connect(page.getUrl()).timeout(6000)
					.ignoreHttpErrors(true)
					.userAgent(
							"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36")
					.execute();

			int statusCode = response.statusCode();
			if (statusCode == 200) {
				Document fullPageDoc = response.parse();

				page.setDocument(fullPageDoc);
			} else {
				errors.rejectValue("url", "error.wrong.url", "Received error code: " + statusCode);
			}
		} catch (IllegalArgumentException | MalformedURLException e) {
			errors.rejectValue("url", "error.wrong.url", "Invalid url");
		} catch (IOException e) {
			errors.rejectValue("url", "error.wrong.url", "Something went wrong during the loading");
		}
	}

	@Override
	public void processDocument(Page page)
	{
		page.setTitle(pageTitleAnalyzer.getPageTitle(page.getDocument()));

		page.setNumOfHeadings(pageHeadingsAnalyzer.numOfHeadings(page.getDocument()));

		HtmlVersionAnalyzer.HTML_VERSIONS htmlVersion = htmlVersionAnalyzer
				.getHtmlVersion(page.getDocument());
		page.setHtmlVersion(htmlVersion.name());

		pageLinkAnalyzer.checkLinks(page);

		page.setContainsLoginForm(loginFormRecognizer.containsLoginForm(page.getDocument()));
	}


}
